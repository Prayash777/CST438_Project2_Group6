package com.cst438_project2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    /**
     * GET /login
     * Show the login page (login.html).
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // Add any model attributes if needed
        return "login";  // Loads templates/login.html
    }

//     @PostMapping("/login")
//     public String processLogin(
//             @RequestParam String username,
//             @RequestParam String password,
//             Model model
//     ) {
//         // 1) Attempt authentication
//         // 2) If successful, redirect to user’s dashboard or something
//         // 3) If fail, show error message
//         return "redirect:/somePage";
//     }
}
