// src/main/java/com/sliit/pharmacy/controller/MedicineController.java
package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.model.Medicine;
import com.sliit.pharmacy.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping
    public String listMedicines(Model model) {
        List<Medicine> medicines = medicineService.getAllMedicines();
        model.addAttribute("medicines", medicines);
        addStatsToModel(model, medicines);
        return "medicines/list";
    }

    @GetMapping("/add")
    public String addMedicineForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "medicines/add";
    }

    @PostMapping("/add")
    public String saveMedicine(@Valid @ModelAttribute("medicine") Medicine medicine,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "medicines/add"; // Return form with error messages
        }
        medicineService.saveMedicine(medicine);
        return "redirect:/medicines";
    }

    @GetMapping("/edit/{id}")
    public String editMedicineForm(@PathVariable Long id, Model model) {
        Medicine medicine = medicineService.getMedicineById(id);
        if (medicine == null) {
            return "redirect:/medicines";
        }
        model.addAttribute("medicine", medicine);
        return "medicines/edit";
    }

    @PostMapping("/update")
    public String updateMedicine(@Valid @ModelAttribute("medicine") Medicine medicine,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "medicines/edit"; // Return form with errors
        }
        medicineService.saveMedicine(medicine);
        return "redirect:/medicines";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "redirect:/medicines";
    }

    @GetMapping("/search")
    public String searchMedicines(@RequestParam String keyword, Model model) {
        List<Medicine> results = medicineService.searchMedicines(keyword);
        model.addAttribute("medicines", results);
        model.addAttribute("searchKeyword", keyword);
        addStatsToModel(model, results);
        return "medicines/list";
    }

    // Helper method to compute stats
    private void addStatsToModel(Model model, List<Medicine> medicines) {
        long total = medicines.size();
        long lowStock = medicines.stream()
                .filter(m -> "Low Stock".equals(m.getInventoryStatus()) || "Out of Stock".equals(m.getInventoryStatus()))
                .count();
        long expiring = medicines.stream()
                .filter(m -> "Expiring Soon".equals(m.getExpiryStatus()) || "Expired".equals(m.getExpiryStatus()))
                .count();

        model.addAttribute("totalMedicines", total);
        model.addAttribute("lowStockCount", lowStock);
        model.addAttribute("expiringCount", expiring);
    }
}
