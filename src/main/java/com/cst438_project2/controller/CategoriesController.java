// package com.cst438_project2.controller;

// import com.cst438_project2.model.Categories;
// import com.cst438_project2.service.TierListService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import com.cst438_project2.repository.CategoriesRepository;

// import java.util.Date;
// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/categories")
// public class CategoriesController {

//     @Autowired
//     private TierListService tierListService;
    
//     private CategoriesRepository repository;
//     // Create a new category
    
//     public CategoriesController(CategoriesRepository repository){
//         this.repository = repository;
//     }

//     // Get all categories
//     @GetMapping
//     public ResponseEntity<List<Categories>> getAllCategories() {
//         List<Categories> categories = repository.findAll();
//         return ResponseEntity.ok(categories);
//     }

//     // Create a new category
//     @PostMapping
//     public ResponseEntity<Categories> createCategory(@RequestBody Categories newCategory) {
//         Categories savedCategory = repository.save(newCategory);
//         return ResponseEntity.ok(savedCategory);
//     }
// }
