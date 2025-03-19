// package com.cst438_project2.controller;

// import com.cst438_project2.model.User;
// //import com.cst438_project2.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import com.cst438_project2.repository.UserRepository;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/users")
// public class UserController {
//     @Autowired
//     private UserRepository userRepository;

//     // Get all users
//     @GetMapping
//     public List<User> getAllUsers() {
//         return userRepository.findAll();
//     }

//     // Create a new user
//     @PostMapping
//     public User createUser(@RequestBody User newUser) {
//         return userRepository.save(newUser);
//     }
    
// }