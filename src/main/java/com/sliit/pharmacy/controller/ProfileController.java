// src/main/java/com/sliit/pharmacy/controller/ProfileController.java
package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.model.User;
import com.sliit.pharmacy.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String viewProfile(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user", user);
        return "profile/view";
    }

    @GetMapping("/profile/edit")
    public String editProfileForm(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user", user);
        return "profile/edit";
    }

    @PostMapping("/profile/update")
    public String updateProfile(User updatedUser, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findUserByEmail(email);

        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
        currentUser.setAddress(updatedUser.getAddress());

        userService.saveUser(currentUser);
        return "redirect:/profile?updated";
    }
}