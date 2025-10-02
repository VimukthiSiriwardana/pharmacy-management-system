package com.sliit.pharmacy.config;

import com.sliit.pharmacy.model.User;
import com.sliit.pharmacy.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserService userService) {
        return args -> {
            // Create default admin user if not exists
            if (userService.findUserByEmail("admin@pharmacy.com") == null) {
                User admin = new User();
                admin.setFirstName("System");
                admin.setLastName("Administrator");
                admin.setEmail("admin@pharmacy.com");
                admin.setPassword("admin123"); // Plain text password (consider encoding later)
                admin.setRole(User.UserRole.ADMIN);
                userService.saveUser(admin);
                System.out.println("Default admin user created: admin@pharmacy.com / admin123");
            }
        };
    }
}
