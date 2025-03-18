package com.cst438_project2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MVCController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Hello, Spring Boot MVC!");
        return "home"; 
    }
}