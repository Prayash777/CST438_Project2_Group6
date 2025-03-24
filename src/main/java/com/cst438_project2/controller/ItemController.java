
// package com.cst438_project2.controller;

// import com.cst438_project2.model.Categories;
// import com.cst438_project2.model.Item;
// import com.cst438_project2.service.TierListService;
// import com.cst438_project2.repository.CategoriesRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.ui.Model;
// import org.springframework.stereotype.Controller;

// import java.util.Date;
// import java.util.List;
// import java.util.Optional;

// // @RestController
// // @RequestMapping("/api/items")
// // public class ItemController {
// //     @Autowired
// //     private TierListService tierListService;

// //     @Autowired
// //     private CategoriesRepository categoriesRepository;

// //     @GetMapping("/")
// //     public String getCategories(Model model) {
// //         List<Categories> categoriesList = tierListService.getCategories();
// //         System.out.println("Categories:---------------------------- " + categoriesList);
// //         model.addAttribute("categories", categoriesList);
        
// //         return "categories";

// //     }

// //     @PostMapping("/categories")
// //     public String createCategory(@RequestParam String categoryName, Model model) {
// //         Categories categories = new Categories(categoryName, new Date(), new Date());
// //         tierListService.saveCategories(categories);
// //         List<Categories> categoriesList = tierListService.getCategories();
// //         model.addAttribute("categories", categoriesList);
// //         return "register";
// //     }

// //     @PostMapping("/categories/{categoryId}/items")
// //     public String addItemtoCategory(
// //         @PathVariable int categoryId,
// //         @RequestParam String itemName,
// //         Model model) {
// //             Optional<Categories> categoryOpt = categoriesRepository.findById(categoryId);
// //             if (categoryOpt.isPresent()) {
// //                 Categories categories = categoryOpt.get();
// //                 Item newItem = new Item(itemName, categories);
// //                 tierListService.saveItem(newItem, categoryId);
// //             }
// //             return "redirect:/api/items/categories/" + categoryId + "/items";
// //     }

// //     @GetMapping("/categories/{categoryId}/items")
// //     public String getItemsForCategories(@PathVariable int categoryId, Model model) {
// //         Optional<Categories> categoryOpt = tierListService.getCategoriesById(categoryId);
// //         if (categoryOpt.isPresent()) {
// //             List<Item> items = tierListService.getItemsByCategory(categoryOpt.get());
// //             model.addAttribute("items", items);
// //             return "categoryItems";
// //         }
// //         return "redirect:/api/items/categories";
// //     }
// // }

// @RestController
// @RequestMapping("/api/items")
// public class ItemController {
//     @Autowired
//     private TierListService tierListService;

//     @Autowired
//     private CategoriesRepository categoriesRepository;

//     @GetMapping("/categories")
//     public List<Categories> getAllCategories() {
//         return tierListService.getCategories();
//     }

//     @PostMapping("/categories")
//     public Categories createCategory(@RequestParam String categoryName) {
//         Categories category = new Categories(categoryName, new Date(), new Date());
//         return tierListService.saveCategories(category);
//     }

//     @PostMapping("/categories/{categoryId}/items")
//     public Item addItemToCategory(@PathVariable int categoryId, @RequestParam String itemName) {
//         Optional<Categories> categoryOpt = categoriesRepository.findById(categoryId);
//         if (categoryOpt.isPresent()) {
//             Categories category = categoryOpt.get();
//             Item newItem = new Item(itemName, category);
//             return tierListService.saveItem(newItem, categoryId);
//         }
//         return null; // handle error, might want to return a 404 status
//     }

//     @GetMapping("/categories/{categoryId}/items")
//     public List<Item> getItemsForCategory(@PathVariable int categoryId) {
//         Optional<Categories> categoryOpt = tierListService.getCategoriesById(categoryId);
//         if (categoryOpt.isPresent()) {
//             return tierListService.getItemsByCategory(categoryOpt.get());
//         }
//         return null; // handle error, might want to return an empty list or 404
//     }
// }
