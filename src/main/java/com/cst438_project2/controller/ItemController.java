package com.cst438_project2.controller;

import com.cst438_project2.model.Item;
import com.cst438_project2.model.Categories;
import com.cst438_project2.service.TierListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private TierListService tierListService;

    // Create a new item
    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        // Check if the category exists
        if (item.getCategory() != null) {
            Optional<Categories> category = tierListService.getCategoriesById(item.getCategory().getCategoryId());
            if (category.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Category with ID " + item.getCategory().getCategoryId() + " does not exist.");
            }
            item.setCategory(category.get()); // Link valid category
        }

        Item savedItem = tierListService.saveItem(item);
        return ResponseEntity.ok(savedItem);
    }

    // Get all items
    @GetMapping
    public List<Item> getAllItems() {
        return tierListService.getItems();
    }

    // Get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable int id) {
        return itemRepository.findById(id)
            .map(item -> ResponseEntity.ok().body(item))
            .orElse(ResponseEntity.status(404).body("Item with ID " + id + " not found."));
    }

    // Update an item
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable int id, @RequestBody Item updatedItem) {
        Optional<Item> existingItem = tierListService.getItemsById(id);

        if (existingItem.isPresent()) {
            Item item = existingItem.get();

            // Update item details
            item.setItemName(updatedItem.getItemName());

            // Handle category updates
            if (updatedItem.getCategory() != null) {
                Optional<Categories> category = tierListService.getCategoriesById(updatedItem.getCategory().getCategoryId());
                if (category.isEmpty()) {
                    return ResponseEntity.badRequest().body("Error: Category with ID " + updatedItem.getCategory().getCategoryId() + " does not exist.");
                }
                item.setCategory(category.get());
            }

            tierListService.saveItem(item);
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.status(404).body("Item with ID " + id + " not found.");
        }
    }

    // Delete an item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable int id) {
        Optional<Item> item = tierListService.getItemsById(id);
        if (item.isPresent()) {
            tierListService.deleteItem(id);
            return ResponseEntity.ok("Item deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Item with ID " + id + " not found.");
        }
    }
}
