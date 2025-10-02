// src/main/java/com/sliit/pharmacy/controller/HomeController.java
package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.service.MedicineService;
import com.sliit.pharmacy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final MedicineService medicineService;
    private final UserService userService;

    public HomeController(MedicineService medicineService, UserService userService) {
        this.medicineService = medicineService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalMedicines", medicineService.getAllMedicines().size());
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalPrescriptions", 0); // Placeholder (update later)
        return "home";
    }
}