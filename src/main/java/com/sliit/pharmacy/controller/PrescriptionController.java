// src/main/java/com/sliit/pharmacy/controller/PrescriptionController.java
package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.model.Prescription;
import com.sliit.pharmacy.service.PrescriptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    // Customer: View own prescriptions
    @GetMapping
    public String listPrescriptions(Authentication auth, Model model) {
        String email = auth.getName();
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByCustomer(email);
        model.addAttribute("prescriptions", prescriptions);
        return "prescriptions/list";
    }

    // Customer: Upload form
    @GetMapping("/upload")
    public String uploadForm() {
        return "prescriptions/upload";
    }

    // Customer: Handle upload
    @PostMapping("/upload")
    public String uploadPrescription(@RequestParam("file") MultipartFile file,
                                     Authentication auth,
                                     Model model) {
        try {
            prescriptionService.savePrescription(file, auth.getName());
            return "redirect:/prescriptions?uploaded";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload prescription. Please try again.");
            return "prescriptions/upload";
        }
    }

    // Pharmacist: View pending prescriptions
    @GetMapping("/pending")
    public String pendingPrescriptions(Model model) {
        model.addAttribute("prescriptions", prescriptionService.getPendingPrescriptions());
        return "prescriptions/pending";
    }

    // Pharmacist: Approve
    @PostMapping("/approve/{id}")
    public String approvePrescription(@PathVariable Long id) {
        prescriptionService.approvePrescription(id);
        return "redirect:/prescriptions/pending";
    }

    // Pharmacist: Reject
    @PostMapping("/reject/{id}")
    public String rejectPrescription(@PathVariable Long id,
                                     @RequestParam String reason) {
        prescriptionService.rejectPrescription(id, reason);
        return "redirect:/prescriptions/pending";
    }
}