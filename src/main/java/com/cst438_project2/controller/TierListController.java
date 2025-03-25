package com.cst438_project2.controller;

import com.cst438_project2.dto.TierListRequest;
import com.cst438_project2.model.Categories;
import com.cst438_project2.model.TierEntry;
import com.cst438_project2.model.Tier;
import com.cst438_project2.model.User;
import com.cst438_project2.model.Item;
import com.cst438_project2.service.CategoriesService;
import com.cst438_project2.service.TierListService;
import com.cst438_project2.service.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;


@Controller
public class TierListController {

    @Autowired
    private CategoriesService categoryService;

    @Autowired
    private TierListService tierListService;

    @Autowired
    private UserService userService;
    
    @GetMapping("/tier-list")
    public String showTierListForm(Model model) {
        List<Categories> categories = categoryService.getCategories(); 
        if (categories == null || categories.isEmpty()) {
            System.out.println("No categories found!");
        } else {
            System.out.println("Retrieved Categories:");
        for (Categories cat : categories) {
            System.out.println(cat.getCategoryId() + " - " + cat.getCategoryName());
        }
    }
        model.addAttribute("categories", categories);
        return "tier"; 
    }

    @PostMapping("/tier-list")
public String saveTierList(HttpSession session, @RequestParam("category") String category,
                           @RequestParam("s-tier") String sTier,
                           @RequestParam("a-tier") String aTier,
                           @RequestParam("b-tier") String bTier,
                           @RequestParam("c-tier") String cTier,
                           @RequestParam("d-tier") String dTier,
                           RedirectAttributes redirectAttributes,
                           Principal principal) {
    try {
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            System.out.println("User not found in session!----------------------");
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/tier-list"; // Redirect if user is not found
        }

        User user = userService.findById(userId); 
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/tier-list"; // Redirect if user does not exist
        }
        System.out.println("Retrieved User: " + user.getId() + " - " + user.getUsername() + " - " + user.getEmail());

        // Retrieve the logged-in user
  
        System.out.println("Looking for category with ID: " + category);
        // Retrieve the selected category
        Categories categoryEntity = categoryService.getCategoriesById(Integer.parseInt(category));
        if (categoryEntity == null) {
            System.out.println("Invalid category selected!-----------------");
            redirectAttributes.addFlashAttribute("error", "Invalid category selected.");
            return "redirect:/tier-list";
        }

        // Save tier entries
        saveTierEntry(user, sTier, "S", categoryEntity);
        saveTierEntry(user, aTier, "A", categoryEntity);
        saveTierEntry(user, bTier, "B", categoryEntity);
        saveTierEntry(user, cTier, "C", categoryEntity);
        saveTierEntry(user, dTier, "D", categoryEntity);

        redirectAttributes.addFlashAttribute("success", "Tier list saved successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "An error occurred while saving.");
    }
    return "redirect:/tier-list";
}
 

    private void saveTierEntry(User user, String itemName, String tier, Categories category) {
         if (itemName != null && !itemName.trim().isEmpty()) {
        System.out.println("Preparing to save TierEntry for Item: " + itemName + ", Tier: " + tier);
        
        TierEntry entry = new TierEntry(user, itemName, tier, category);
        System.out.println("Entry created: " + entry);

        tierListService.saveTierEntry(entry);

        System.out.println("Saved entry for " + itemName + " successfully!");
    } else {
        System.out.println("Skipping empty item for tier: " + tier);
    }
    }

}