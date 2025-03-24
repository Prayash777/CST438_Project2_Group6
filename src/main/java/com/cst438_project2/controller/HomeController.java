// package com.cst438_project2.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import com.cst438_project2.model.Categories;
// import com.cst438_project2.service.TierListService;
// import java.util.List;
// import org.springframework.ui.Model;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// @Controller
// public class HomeController {
//     private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

//     @Autowired
//     private TierListService tierListService;

//     @GetMapping("/")
//     public String home(Model model) {
//         List<Categories> categories = tierListService.getCategories();
//         if (categories != null && !categories.isEmpty()) {
//             logger.info("Categories successfully retrieved: {}", categories);
//         } else {
//             logger.warn("No categories found in the database.");
//         }
//         System.out.println("Categories: " + categories);

//         model.addAttribute("categories", categories);
//         return "index";
//     }
// }
