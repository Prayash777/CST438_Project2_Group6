package com.cst438_project2.controller;

import com.cst438_project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * GET /login
     * Show the login page.
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";  
    }

    /**
     * POST /login
     * Process the login form.
     */
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        // Authenticate the user using UserService
        if (!userService.authenticate(username, password)) {
            model.addAttribute("error", "Invalid username or password.");
            return "login"; 
        }
        
        // On successful login, you can set a message and redirect
        model.addAttribute("message", "Login successful!");
        return "redirect:/landing"; 
    }
}
