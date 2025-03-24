// package com.cst438_project2.controller;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.cst438_project2.model.User;
// import com.cst438_project2.service.UserService;

// @Controller
// public class RegisterController {

//     @Autowired
//     private UserService userService;

//     @GetMapping("/")
//     public String showRegistrationForm() {
//         return "index";
//     }

//     @PostMapping("/register")
//     public String processRegistration(@RequestParam String username, 
//                                       @RequestParam String email, 
//                                       @RequestParam String password) {

//         User user = new User(username, password, email);
//         userService.createUser(user);
//         return "redirect:/login"; 
//     }

//     @GetMapping("/login")
//     public String showLoginForm() {
//         return "login";
//     }
// }