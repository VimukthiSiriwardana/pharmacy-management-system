// src/main/java/com/sliit/pharmacy/controller/InventoryController.java
package com.sliit.pharmacy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.sliit.pharmacy.model.Medicine;
import com.sliit.pharmacy.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/inventory") // Inventory access mapping
public class InventoryController {

    private final MedicineService medicineService;

    public InventoryController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping
    public String inventoryDashboard(Model model) {
        List<Medicine> allMedicines = medicineService.getAllMedicines();

        // Low stock items (quantity <= lowStockThreshold)
        List<Medicine> lowStockItems = allMedicines.stream()
                .filter(m -> "Low Stock".equals(m.getInventoryStatus()) || "Out of Stock".equals(m.getInventoryStatus()))
                .collect(Collectors.toList());

        // Expiring soon (within 30 days)
        List<Medicine> expiringSoon = allMedicines.stream()
                .filter(m -> "Expiring Soon".equals(m.getExpiryStatus()))
                .collect(Collectors.toList());

        // Expired items
        List<Medicine> expiredItems = allMedicines.stream()
                .filter(m -> "Expired".equals(m.getExpiryStatus()))
                .collect(Collectors.toList());

        model.addAttribute("totalMedicines", allMedicines.size());
        model.addAttribute("lowStockCount", lowStockItems.size());
        model.addAttribute("expiringCount", expiringSoon.size());
        model.addAttribute("expiredCount", expiredItems.size());
        model.addAttribute("lowStockItems", lowStockItems);
        model.addAttribute("expiringSoon", expiringSoon);
        model.addAttribute("expiredItems", expiredItems);

        return "inventory/dashboard";
    }
}
