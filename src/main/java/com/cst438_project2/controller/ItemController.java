// package com.cst438_project2.controller;

// import com.cst438_project2.model.Item;
// import com.cst438_project2.model.Categories;
// import com.cst438_project2.service.TierListService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import com.cst438_project2.repository.ItemRepository;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/items")
// public class ItemController {
//     @Autowired  // <-- Add this to inject the repository
//     private ItemRepository itemRepository;
    
//     ItemController(ItemRepository itemRepository){
//         this.itemRepository = itemRepository;
//     }

//     @GetMapping
//     public ResponseEntity<List<Item>> getAllItems() {
//         List<Item> items = itemRepository.findAll();
//         return ResponseEntity.ok(items);
//     }

//     @PostMapping
//     public ResponseEntity<Item> createNewItem(@RequestBody Item newItem) {
//         Item savedItem = itemRepository.save(newItem);
//         return ResponseEntity.ok(savedItem);
//     }
// }
