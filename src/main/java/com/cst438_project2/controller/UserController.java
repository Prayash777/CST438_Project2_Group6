package com.cst438_project2.controller;

import com.cst438_project2.model.User;
import com.cst438_project2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * GET /register
     * Show the registration page.
     */
    @GetMapping("/register")
    public String showRegisterPage() {
        // Renders src/main/resources/templates/register.html
        return "register";  
    }

    /**
     * POST /register
     * Process the registration form.
     */
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        // --- Basic validations ---
        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "Username already exists. Please choose another.");
            return "register";
        }
        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters.");
            return "register";
        }
        if (email == null || email.isBlank()) {
            model.addAttribute("error", "Email is required.");
            return "register";
        }
        
        // --- Create the user ---
        User user = new User(username, email, password);
        userService.createUser(user);

        model.addAttribute("message", "Registration successful. Please log in.");
        return "redirect:/login";  // Redirect to login page
    }
    
    /**
     * GET /landing
     * Display a user landing page (after login).
     */
    @GetMapping("/landing")
    public String showLandingPage(HttpSession session, Model model) {
        // 1) Check if someone is logged in
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Not logged in, redirect to login
            return "redirect:/login";
        }

        // 2) Retrieve user from DB (optional, if you want more data)
        Optional<User> userOpt = userService.findByUsername(loggedInUser);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOpt.get();

        // 3) Put user data into the model for Thymeleaf
        model.addAttribute("username", user.getUsername());
        // Optionally, add active list or other user-specific data here

        return "landing"; // Renders landing.html
    }
}
