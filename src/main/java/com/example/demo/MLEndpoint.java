package com.example.demo;

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
        
        // Simulate tier list data
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
     * {
     *   "userId": 1,
     *   "tierList": {
     *         "title": "Top Video Games",
     *         "items": ["cat", "kitten", "kitty", "CAT", "cAt", "C4t", "dog", "puppy"]
     *   }
     * }
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
        
        // OLD HARDCODED LOGIC (using as ref, will delete later) 
        // payload.put("grouping", "Items have been grouped by similarity");
        // payload.put("similarityScores", new double[]{0.90, 0.80, 0.70});
        // payload.put("message", "Tier list processed successfully");
        
        // Normalize items to lowercase for consistent grouping.
        List<String> normalizedItems = new ArrayList<>();
        for (String word : items) {
            normalizedItems.add(word.toLowerCase());
        }
        
        // Fetch embeddings from OpenAI for the normalized items.
        List<double[]> embeddings = fetchEmbeddings(normalizedItems);
        
        // Group similar items
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
     * Calls the OpenAI API to fetch embeddings for the provided list of items.
     * 
     * @param items 
     * @return 
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
    
            if(response.getBody() == null || !response.getBody().containsKey("data")){
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
     * This version calculates a centroid for each cluster and uses that for comparison.
     * 
     * @param normalizedItems 
     * @param originalItems   
     * @param embeddings      
     * @return A map where each key is a representative normalized item and the value is a list of original items that are similar.
     */
    private Map<String, List<String>> clusterItems(List<String> normalizedItems, List<String> originalItems, List<double[]> embeddings) {
        Map<String, List<String>> clusters = new HashMap<>();
        // Maintain centroids for each cluster
        Map<String, double[]> clusterCentroids = new HashMap<>();
        double threshold = 0.90;  // Adjust this threshold as needed
        
        for (int i = 0; i < normalizedItems.size(); i++) {
            String currentNorm = normalizedItems.get(i);
            double[] currentEmbedding = embeddings.get(i);
            boolean assigned = false;
            String bestCluster = null;
            double bestSimilarity = 0.0;
            
            // Find the best matching cluster using the centroid
            for (Map.Entry<String, double[]> entry : clusterCentroids.entrySet()) {
                double similarity = cosineSimilarity(entry.getValue(), currentEmbedding);
                if (similarity > bestSimilarity) {
                    bestSimilarity = similarity;
                    bestCluster = entry.getKey();
                }
            }
            
            // If best similarity exceeds threshold, assign item to that cluster
            if (bestSimilarity >= threshold && bestCluster != null) {
                clusters.get(bestCluster).add(originalItems.get(i));
                // Update the centroid of this cluster
                double[] oldCentroid = clusterCentroids.get(bestCluster);
                int clusterSize = clusters.get(bestCluster).size();
                double[] newCentroid = updateCentroid(oldCentroid, currentEmbedding, clusterSize);
                clusterCentroids.put(bestCluster, newCentroid);
                assigned = true;
            }
            
            // If no cluster is similar enough, create a new one.
            if (!assigned) {
                clusters.put(currentNorm, new ArrayList<>(Collections.singletonList(originalItems.get(i))));
                clusterCentroids.put(currentNorm, currentEmbedding);
            }
        }
        
        return clusters;
    }
    
    /**
     * Updates the centroid of a cluster given the old centroid, a new vector, and the new cluster size.
     */
    private double[] updateCentroid(double[] oldCentroid, double[] newVector, int clusterSize) {
        double[] updated = new double[oldCentroid.length];
        for (int j = 0; j < oldCentroid.length; j++) {
            updated[j] = ((oldCentroid[j] * (clusterSize - 1)) + newVector[j]) / clusterSize;
        }
        return updated;
    }

    /**
     * Computes the cosine similarity between two vectors.
     * This method calculates the similarity between two embedding vectors,
     * which helps determine how closely related they are.
     * 
     * @param vec1 
     * @param vec2 
     * @return 
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
        // OLD HARDCODED LOGIC (using for ref, will delete later)
        // payload.put("grouping", "Items have been regrouped by similarity");
        // payload.put("similarityScores", new double[]{0.95, 0.85, 0.75});
        // payload.put("message", "Tier list replaced successfully");
        
        // NEW: Recalculate ML embeddings and group items for the updated tier list.
        Map<String, Object> tierList = (Map<String, Object>) payload.get("tierList");
        List<String> items = (List<String>) tierList.get("items");
        
        // Normalize items before processing.
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
        // Merge the updates into the existing data (old logic remains)
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
