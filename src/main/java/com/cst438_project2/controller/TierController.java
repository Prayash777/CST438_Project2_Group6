package com.cst438_project2.controller;

import com.cst438_project2.model.Tier;
import com.cst438_project2.service.TierListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tiers")
public class TierController {

    @Autowired
    private TierListService tierListService;

    // Create a Tier
    @PostMapping
    public ResponseEntity<Tier> createTier(@RequestBody Tier tier) {
        return ResponseEntity.ok(tierListService.saveTier(tier));
    }

    // Get all Tiers
    @GetMapping
    public ResponseEntity<List<Tier>> getAllTiers() {
        return ResponseEntity.ok(tierListService.getTiers());
    }

    // Get a Tier by ID
    @GetMapping("/{id}")
    public ResponseEntity<Tier> getTierById(@PathVariable int id) {
        Optional<Tier> tier = tierListService.getTierById(id);
        return tier.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a Tier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTier(@PathVariable int id) {
        tierListService.deleteTier(id);
        return ResponseEntity.noContent().build();
    }
}