package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.model.User;
import com.sliit.pharmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Show login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    // Show registration page
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // Handle user registration
    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        // Check if email already exists
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "auth/register";
        }

        // Assign CUSTOMER role to all newly registered users
        user.setRole(User.UserRole.CUSTOMER);

        // Optionally: you can hash password here if using BCrypt
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        userService.saveUser(user);

        return "redirect:/auth/login?success";
    }
}
