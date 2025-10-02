// src/main/java/com/sliit/pharmacy/controller/TestController.java
package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test-db")
    public String testDatabase() {
        try {
            long count = userRepository.count();
            return "✅ Database connected! Total users: " + count;
        } catch (Exception e) {
            return "❌ Database connection failed: " + e.getMessage();
        }
    }
}