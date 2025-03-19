package com.cst438_project2.controller;

import com.cst438_project2.model.Categories;
import com.cst438_project2.model.Item;
import com.cst438_project2.service.TierListService;
import com.cst438_project2.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ItemController {
    @Autowired
    private TierListService tierListService;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categoriesList = tierListService.getCategories();
        if (categoriesList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriesList);
    }

    @PostMapping("/categories")
    public ResponseEntity<String> addCategory(@RequestBody Categories category) {
        if (category == null || category.getCategoryName().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid category data");
        }
        tierListService.saveCategories(category);
        return ResponseEntity.ok("Category added");
    }

    @PostMapping("/categories/{categoryId}/items")
    public ResponseEntity<String> addItemtoCategory(
        @PathVariable int categoryId,
        @RequestParam String itemName) {
            Optional<Categories> categoryOpt = categoriesRepository.findById(categoryId);
            if (!categoryOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Category not found");
            }
            Categories categories = categoryOpt.get();
            if (itemName == null || itemName.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid item name");
            }
            Item newItem = new Item(itemName, categories);
            tierListService.saveItem(newItem);
            return ResponseEntity.ok("Item added");
    }

    @GetMapping("/categories/{categoryId}/items")
    public ResponseEntity<List<Item>> getItemsForCategories(@PathVariable int categoryId) {
        Optional<Categories> categoryOpt = categoriesRepository.findById(categoryId);
        if (!categoryOpt.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Item> items = tierListService.getItemsByCategory(categoryOpt.get());
        return ResponseEntity.ok(items);
    }
}
