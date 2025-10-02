// src/main/java/com/sliit/pharmacy/controller/DashboardController.java
package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.service.MedicineService;
import com.sliit.pharmacy.service.UserService;
import com.sliit.pharmacy.service.PrescriptionService;
import com.sliit.pharmacy.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final MedicineService medicineService;
    private final UserService userService;
    private final PrescriptionService prescriptionService;
    private final OrderService orderService;

    public DashboardController(MedicineService medicineService,
                               UserService userService,
                               PrescriptionService prescriptionService,
                               OrderService orderService) {
        this.medicineService = medicineService;
        this.userService = userService;
        this.prescriptionService = prescriptionService;
        this.orderService = orderService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalMedicines", medicineService.getAllMedicines().size());
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("pendingOrders", orderService.countPendingOrders());
        model.addAttribute("pendingPrescriptions", prescriptionService.countPendingPrescriptions());
        return "dashboard";
    }
}