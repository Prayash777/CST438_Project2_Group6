package com.cst438_project2.controller;

import com.cst438_project2.model.Categories;
import com.cst438_project2.service.TierListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private TierListService tierListService;

    // Create a new category
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Categories category) {
        // Validation: Ensure startDate is before endDate
        if (category.getStartDate() != null && category.getEndDate() != null) {
            if (category.getStartDate().after(category.getEndDate())) {
                return ResponseEntity.badRequest().body("Error: Start date must be before end date.");
            }
        }
        Categories savedCategory = tierListService.saveCategories(category);
        return ResponseEntity.ok(savedCategory);
    }

    // Get all categories
    @GetMapping
    public List<Categories> getAllCategories() {
        return tierListService.getCategories();
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id) {
        Optional<Categories> category = tierListService.getCategoriesById(id);
        return category.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a category
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Categories updatedCategory) {
        Optional<Categories> existingCategory = tierListService.getCategoriesById(id);
        if (existingCategory.isPresent()) {
            Categories category = existingCategory.get();

            // Update fields
            category.setCategoryName(updatedCategory.getCategoryName());
            category.setStartDate(updatedCategory.getStartDate());
            category.setEndDate(updatedCategory.getEndDate());

            // Validation: Ensure startDate is before endDate
            if (category.getStartDate() != null && category.getEndDate() != null) {
                if (category.getStartDate().after(category.getEndDate())) {
                    return ResponseEntity.badRequest().body("Error: Start date must be before end date.");
                }
            }

            tierListService.saveCategories(category);
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        Optional<Categories> category = tierListService.getCategoriesById(id);
        if (category.isPresent()) {
            tierListService.deleteCategories(id);
            return ResponseEntity.ok("Category deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Category not found.");
        }
    }
}
