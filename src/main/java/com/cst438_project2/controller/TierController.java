// package com.cst438_project2.controller;

// import com.cst438_project2.model.Tier;
// import com.cst438_project2.service.TierListService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import com.cst438_project2.repository.TierRepository;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/tiers")
// public class TierController {
//     private TierRepository tierRepository;

//     // Constructor Injection (preferred method)
//     public TierController(TierRepository tierRepository) {
//         this.tierRepository = tierRepository;
//     }

//     // GET all tiers
//     @GetMapping
//     public ResponseEntity<List<Tier>> getAllTiers() {
//         List<Tier> tiers = tierRepository.findAll();
//         return ResponseEntity.ok(tiers);
//     }
    
//      @PostMapping
//     public ResponseEntity<Tier> createNewTier(@RequestBody Tier newTier) {
//         Tier savedTier = tierRepository.save(newTier);
//         return ResponseEntity.ok(savedTier);
//     }
// }