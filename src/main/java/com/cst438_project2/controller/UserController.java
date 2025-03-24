// package com.cst438_project2.controller;

// import com.cst438_project2.service.UserService;
// import com.cst438_project2.model.User;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @Controller
// @RequestMapping("/api/users")
// public class UserController {

//     @Autowired
//     private UserService userService;

//     @GetMapping("/register")
//     public String showRegisterPage() {
//         return "register"; 
//     }
    
//     @PostMapping("/register")
//     public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
//          model.addAttribute("message", "Registration successful!");
//         return "register"; // Show register.html after submitting
//     }

//     @GetMapping("/login")
//     public String showLoginPage() {
//         return "login"; 
//     }
// }

package com.cst438_project2.controller;

import com.cst438_project2.service.UserService;
import com.cst438_project2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


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
            User user = new User(username, email, password);
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
}