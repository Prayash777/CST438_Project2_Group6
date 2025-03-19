package com.cst438_project2.controller;

import com.cst438_project2.model.Categories;
import com.cst438_project2.model.Item;
import com.cst438_project2.service.TierListService;
import com.cst438_project2.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ItemController {
    @Autowired
    private TierListService tierListService;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @GetMapping("/")
    public String getAllCategories(Model model) {
        List<Categories> categoriesList = tierListService.getCategories();
        model.addAttribute("categories", categoriesList);
        return "index";
    }

    @PostMapping("/categories")
    public String createCategory(@RequestParam String categoryName, Model model) {
        Categories categories = new Categories(categoryName, new Date(), new Date());
        tierListService.saveCategories(categories);
        List<Categories> categoriesList = tierListService.getCategories();
        model.addAttribute("categories", categoriesList);
        return "index";
    }

    @PostMapping("/categories/{categoryId}/items")
    public String addItemtoCategory(
        @PathVariable int categoryId,
        @RequestParam String itemName,
        Model model) {
            Optional<Categories> categoryOpt = categoriesRepository.findById(categoryId);
            if (categoryOpt.isPresent()) {
                Categories categories = categoryOpt.get();
                Item newItem = new Item(itemName, categories);
                tierListService.saveItem(newItem, categoryId);
            }
            return "redirect:/api/categories/" + categoryId + "/items";
    }

    @GetMapping("/categories/{categoryId}/items")
    public String getItemsForCategories(@PathVariable int categoryId, Model model) {
        Optional<Categories> categoryOpt = tierListService.getCategoriesById(categoryId);
        if (categoryOpt.isPresent()) {
            List<Item> items = tierListService.getItemsByCategory(categoryOpt.get());
            model.addAttribute("items", items);
            return "categoryItems";
        }
        return "redirect:/api/categories";
    }
}
