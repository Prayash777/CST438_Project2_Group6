package com.cst438_project2.controller;

import com.cst438_project2.service.UserService;
import com.cst438_project2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        User user = new User(username, email, password);
        userService.createUser(user);

        model.addAttribute("message", "Registration successful. Please log in.");

        return "login";  
    }
}
