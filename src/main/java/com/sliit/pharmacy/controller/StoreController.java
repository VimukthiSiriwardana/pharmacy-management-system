package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreController {

    private final MedicineService medicineService;

    public StoreController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping("/store")
    public String viewStore(Model model) {
        model.addAttribute("medicines", medicineService.getAllMedicines());
        return "store/list";
    }
}