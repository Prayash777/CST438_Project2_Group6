package com.cst438_project2.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/ml")
public class MLEndpoint {

    // Simulated "database" to store processed tier lists
    private Map<Integer, Map<String, Object>> simulatedDatabase = new HashMap<>();
    private AtomicInteger idCounter = new AtomicInteger(101); // Start IDs from 101

    // OpenAI API configuration loaded from application.properties
    @Value("${openai.api.url}")
    private String openAiUrl; 

    @Value("${openai.api.key}")
    private String openAiKey; 

    private final RestTemplate restTemplate = new RestTemplate();

    // Pre-populate sample data for testing GET endpoints
    @PostConstruct
    public void init() {
        Map<String, Object> sampleTierList = new HashMap<>();
        int sampleId = idCounter.getAndIncrement();
        sampleTierList.put("tierListId", sampleId);
        sampleTierList.put("userId", 1);
        
        Map<String, Object> tierListData = new HashMap<>();
        tierListData.put("title", "Sample Tier List");
        tierListData.put("items", Arrays.asList(
            "Ghost of Tsushima", 
            "Stardew Valley", 
            "Dead Cells", 
            "Hades II", 
            "Assassin's Creed: Black Flag", 
            "Animal Crossing: New Horizons"
        ));
        sampleTierList.put("tierList", tierListData);
        sampleTierList.put("grouping", "Sample grouping: Items grouped by similarity");
        sampleTierList.put("similarityScores", new double[]{0.88, 0.77, 0.66});
        sampleTierList.put("message", "Sample tier list loaded for testing.");
        
        simulatedDatabase.put(sampleId, sampleTierList);
    }

    /**
     * POST /api/ml/embeddings
     * Create a new ML-processed tier list.
     * 
     * Expected JSON payload:
{
    "userId": 1,
    "tierList": {
    "title": "Test Tier List",
    "items": [
      "cat", "kitten", "kitty", "CAT", "cAt", "C4t",
      "dog", "puppy", "DOG", "PUPPY", "bark", "woof",
      "apple", "Apple", "orange", "ORANGE", "mac n cheese", "macncheese",
      "table", "desk", "chair", "sofa", "couch", "COUCH"
         ]
      }
    }
     */
    @PostMapping("/embeddings")
    public ResponseEntity<?> processTierList(@RequestBody Map<String, Object> payload) {
        if (!payload.containsKey("userId") || !payload.containsKey("tierList")) {
            return ResponseEntity.badRequest().body("Missing required fields: userId and tierList.");
        }
        
        Map<String, Object> tierList = (Map<String, Object>) payload.get("tierList");
        List<String> items = (List<String>) tierList.get("items");
        if (items == null || items.isEmpty()) {
            return ResponseEntity.badRequest().body("Tier list items cannot be empty.");
        }
        
        
        // NEW: Normalize items to lowercase for consistent grouping.
        List<String> normalizedItems = new ArrayList<>();
        for (String word : items) {
            normalizedItems.add(word.toLowerCase());
        }
        
        // Fetch embeddings from OpenAI for the normalized items.
        List<double[]> embeddings = fetchEmbeddings(normalizedItems);
        
        // Group similar items using improved clustering with centroid updates.
        Map<String, List<String>> groupedItems = clusterItems(normalizedItems, items, embeddings);
        
        // Store processed tier list with new ML results.
        int id = idCounter.getAndIncrement();
        payload.put("tierListId", id);
        payload.put("grouping", groupedItems);
        payload.put("message", "Tier list processed successfully using ML");
        
        simulatedDatabase.put(id, payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(payload);
    }

    /**
     * Calls the OpenAI API to fetch embeddings for the provided list of normalized items.
     * 
     * @param items List of strings (normalized) to be embedded.
     * @return A list of embedding vectors (each as a double array).
     */
    private List<double[]> fetchEmbeddings(List<String> items) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "text-embedding-ada-002");
            requestBody.put("input", items);
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + openAiKey);
    
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
    
            ResponseEntity<Map> response = restTemplate.exchange(
                    openAiUrl, HttpMethod.POST, requestEntity, Map.class);
            
            // Log the response for debugging
            System.out.println("OpenAI API response: " + response.getBody());
    
            if (response.getBody() == null || !response.getBody().containsKey("data")) {
                throw new RuntimeException("Unexpected response from OpenAI API: " + response.getBody());
            }
    
            List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");
            List<double[]> embeddings = new ArrayList<>();
    
            for (Map<String, Object> item : data) {
                List<Double> vector = (List<Double>) item.get("embedding");
                embeddings.add(vector.stream().mapToDouble(Double::doubleValue).toArray());
            }
            return embeddings;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching embeddings: " + e.getMessage());
        }
    }
    
    /**
     * Groups similar items based on cosine similarity of their embeddings.
     * This version uses a centroid-based approach to update the cluster center as new items are added.
     * 
     * @param normalizedItems The list of normalized (lowercase) items.
     * @param originalItems   The list of original items (preserving original casing).
     * @param embeddings      The corresponding embedding vectors.
     * @return A map where each key is a representative normalized item and the value is a list of original items that are similar.
     */
    private Map<String, List<String>> clusterItems(List<String> normalizedItems, List<String> originalItems, List<double[]> embeddings) {
        Map<String, List<String>> clusters = new HashMap<>();
        // Map to store the cluster "center" (average embedding) for each cluster key.
        Map<String, double[]> clusterCenters = new HashMap<>();
        double threshold = 0.90;  // Adjust threshold as needed
        
        for (int i = 0; i < normalizedItems.size(); i++) {
            String currentItem = normalizedItems.get(i);
            double[] currentEmbedding = embeddings.get(i);
            boolean assigned = false;
            String bestClusterKey = null;
            double bestSim = 0.0;
            
            // Find the cluster whose center is most similar to the current embedding.
            for (Map.Entry<String, double[]> entry : clusterCenters.entrySet()) {
                double sim = cosineSimilarity(entry.getValue(), currentEmbedding);
                if (sim > bestSim) {
                    bestSim = sim;
                    bestClusterKey = entry.getKey();
                }
            }
            
            // If the best similarity exceeds the threshold, add the item to that cluster.
            if (bestSim >= threshold && bestClusterKey != null) {
                clusters.get(bestClusterKey).add(originalItems.get(i));
                double[] oldCenter = clusterCenters.get(bestClusterKey);
                int clusterSize = clusters.get(bestClusterKey).size();
                double[] newCenter = updateClusterCenter(oldCenter, currentEmbedding, clusterSize);
                clusterCenters.put(bestClusterKey, newCenter);
                assigned = true;
            }
            
            // If no suitable cluster was found, create a new cluster.
            if (!assigned) {
                clusters.put(currentItem, new ArrayList<>(Collections.singletonList(originalItems.get(i))));
                clusterCenters.put(currentItem, currentEmbedding);
            }
        }
        return clusters;
    }
    
    /**
     * Updates the cluster center (centroid) given the old center, a new embedding, and the updated cluster size.
     * 
     * @param oldCenter The previous center of the cluster.
     * @param newVector The new embedding to incorporate.
     * @param clusterSize The new size of the cluster (after adding the new vector).
     * @return The updated cluster center.
     */
    private double[] updateClusterCenter(double[] oldCenter, double[] newVector, int clusterSize) {
        double[] updated = new double[oldCenter.length];
        for (int i = 0; i < oldCenter.length; i++) {
            updated[i] = ((oldCenter[i] * (clusterSize - 1)) + newVector[i]) / clusterSize;
        }
        return updated;
    }
    
    /**
     * Computes the cosine similarity between two vectors.
     * This method calculates the similarity between two embedding vectors,
     * which helps determine how closely related they are.
     * 
     * @param vec1 First vector.
     * @param vec2 Second vector.
     * @return Cosine similarity between vec1 and vec2.
     */
    private double cosineSimilarity(double[] vec1, double[] vec2) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vec1.length; i++) {
            dotProduct += vec1[i] * vec2[i];
            normA += Math.pow(vec1[i], 2);
            normB += Math.pow(vec2[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /**
     * GET /api/ml/embeddings
     * Retrieve all processed tier lists.
     */
    @GetMapping("/embeddings")
    public ResponseEntity<?> getAllTierLists() {
        Collection<Map<String, Object>> allTierLists = simulatedDatabase.values();
        return ResponseEntity.ok(allTierLists);
    }

    /**
     * GET /api/ml/embeddings/{id}
     * Retrieve a specific processed tier list by ID.
     */
    @GetMapping("/embeddings/{id}")
    public ResponseEntity<?> getTierList(@PathVariable int id) {
        Map<String, Object> tierList = simulatedDatabase.get(id);
        if (tierList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tier list not found.");
        }
        return ResponseEntity.ok(tierList);
    }

    /**
     * PUT /api/ml/embeddings/{id}
     * Replace an existing tier list entirely.
     */
    @PutMapping("/embeddings/{id}")
    public ResponseEntity<?> replaceTierList(@PathVariable int id, @RequestBody Map<String, Object> payload) {
        if (!simulatedDatabase.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tier list not found.");
        }
        if (!payload.containsKey("userId") || !payload.containsKey("tierList")) {
            return ResponseEntity.badRequest().body("Missing required fields: userId and tierList.");
        }
        payload.put("tierListId", id);
        
        // NEW: Recalculate ML embeddings and group items for the updated tier list.
        Map<String, Object> tierList = (Map<String, Object>) payload.get("tierList");
        List<String> items = (List<String>) tierList.get("items");
        
        List<String> normalizedItems = new ArrayList<>();
        for (String word : items) {
            normalizedItems.add(word.toLowerCase());
        }
        List<double[]> embeddings = fetchEmbeddings(normalizedItems);
        Map<String, List<String>> groupedItems = clusterItems(normalizedItems, items, embeddings);
    
        payload.put("grouping", groupedItems);
        payload.put("message", "Tier list replaced successfully using ML");
        simulatedDatabase.put(id, payload);
        return ResponseEntity.ok(payload);
    }

    /**
     * PATCH /api/ml/embeddings/{id}
     * Partially update an existing tier list.
     */
    @PatchMapping("/embeddings/{id}")
    public ResponseEntity<?> updateTierList(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Map<String, Object> existing = simulatedDatabase.get(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tier list not found.");
        }
        // Merge the updates into the existing data.
        existing.putAll(updates);
        existing.put("message", "Tier list updated successfully");
        simulatedDatabase.put(id, existing);
        return ResponseEntity.ok(existing);
    }

    /**
     * DELETE /api/ml/embeddings/{id}
     * Delete a processed tier list.
     */
    @DeleteMapping("/embeddings/{id}")
    public ResponseEntity<?> deleteTierList(@PathVariable int id) {
        if (!simulatedDatabase.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tier list not found.");
        }
        simulatedDatabase.remove(id);
        return ResponseEntity.ok("Tier list deleted successfully.");
    }
}
