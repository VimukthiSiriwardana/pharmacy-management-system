// src/main/java/com/sliit/pharmacy/controller/LoginRedirectController.java
package com.sliit.pharmacy.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginRedirectController {

    @GetMapping("/default")
    public String redirectAfterLogin(Authentication authentication) {
        // ✅ CORRECT: Check for ROLE_CUSTOMER (not ROLE_ROLE_CUSTOMER)
        boolean isCustomer = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));

        if (isCustomer) {
            return "redirect:/store";
        } else {
            return "redirect:/dashboard"; // Admins go to dashboard
        }
    }
}