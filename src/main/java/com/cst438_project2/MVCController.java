package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MVCController {

    // A simple method that maps to a GET request for /home
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Hello, Spring Boot MVC!");
        return "home";  // This returns a view name (home.html)
    }
}