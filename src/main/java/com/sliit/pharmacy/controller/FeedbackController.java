// src/main/java/com/sliit/pharmacy/controller/FeedbackController.java
package com.sliit.pharmacy.controller;

import com.sliit.pharmacy.model.Feedback;
import com.sliit.pharmacy.model.Medicine;
import com.sliit.pharmacy.service.FeedbackService;
import com.sliit.pharmacy.service.MedicineService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final MedicineService medicineService;

    public FeedbackController(FeedbackService feedbackService, MedicineService medicineService) {
        this.feedbackService = feedbackService;
        this.medicineService = medicineService;
    }

    // Show feedback form for a medicine
    @GetMapping("/add/{medicineId}")
    public String showFeedbackForm(@PathVariable Long medicineId, Model model) {
        Medicine medicine = medicineService.getMedicineById(medicineId);
        model.addAttribute("medicine", medicine);
        model.addAttribute("feedback", new Feedback());
        return "feedback/add";
    }

    // Handle feedback submission
    @PostMapping("/add")
    public String submitFeedback(@ModelAttribute Feedback feedback,
                                 Authentication auth,
                                 @RequestParam Long medicineId) {
        Medicine medicine = medicineService.getMedicineById(medicineId);
        feedback.setMedicine(medicine);
        feedbackService.saveFeedback(feedback, auth.getName());
        return "redirect:/store?feedback=success";
    }

    // View all feedback for a medicine
    @GetMapping("/medicine/{medicineId}")
    public String viewMedicineFeedback(@PathVariable Long medicineId, Model model) {
        Medicine medicine = medicineService.getMedicineById(medicineId);
        model.addAttribute("medicine", medicine);
        model.addAttribute("feedbackList", feedbackService.getFeedbackByMedicine(medicineId));
        model.addAttribute("averageRating", feedbackService.getAverageRating(medicineId));
        return "feedback/medicine";
    }

    // View customer's own feedback
    @GetMapping("/my")
    public String viewMyFeedback(Authentication auth, Model model) {
        model.addAttribute("feedbackList", feedbackService.getFeedbackByCustomer(auth.getName()));
        return "feedback/my";
    }
}