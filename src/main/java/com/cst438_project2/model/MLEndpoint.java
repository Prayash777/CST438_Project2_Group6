package com.cst438_project2.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/ml")
public class MLEndpoint {

    // Simulated "database" to store processed tier lists
    private Map<Integer, Map<String, Object>> simulatedDatabase = new HashMap<>();
    private AtomicInteger idCounter = new AtomicInteger(101); // Start IDs from 101

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
     *         "items": ["Ghost of Tsushima", "Stardew Valley", "Dead Cells", "Hades II", "Assassin's Creed: Black Flag", "Animal Crossing: New Horizons"]
     *   }
     * }
     */
    @PostMapping("/embeddings")
    public ResponseEntity<?> processTierList(@RequestBody Map<String, Object> payload) {
        if (!payload.containsKey("userId") || !payload.containsKey("tierList")) {
            return ResponseEntity.badRequest().body("Missing required fields: userId and tierList.");
        }
        int id = idCounter.getAndIncrement();
        payload.put("tierListId", id);
        payload.put("grouping", "Items have been grouped by similarity");
        payload.put("similarityScores", new double[]{0.90, 0.80, 0.70});
        payload.put("message", "Tier list processed successfully");
        simulatedDatabase.put(id, payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(payload);
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
        payload.put("grouping", "Items have been regrouped by similarity");
        payload.put("similarityScores", new double[]{0.95, 0.85, 0.75});
        payload.put("message", "Tier list replaced successfully");
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
        // Merge the updates into the existing data
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