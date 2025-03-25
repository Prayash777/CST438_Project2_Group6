package com.cst438_project2.controller;

import com.cst438_project2.service.UserService;
import com.cst438_project2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/deleteAllUsers")
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        return "index";
    }

    @GetMapping("/")
    public String showRegistrationPage() {
        return "index";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            if (userService.findByEmail(email).isPresent()) {
                model.addAttribute("error", "Email already registered.");
                return "index";
            }
            User user = new User(username, password, email);
            userService.createUser(user);
            model.addAttribute("message", "Registration successful. Please log in.");
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occured while processing the registration");
            return "error";
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            System.out.println("User not found with username: " + username);
        } else {
            System.out.println("Found user: " + user.getUsername());
            System.out.println(user.getPassword());
            System.out.println(user.getEmail());
        }
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user_id", user.getId());
            model.addAttribute("message", "Login successful");
            return "tier";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}