// src/main/java/com/sliit/pharmacy/service/FeedbackService.java
package com.sliit.pharmacy.service;

import com.sliit.pharmacy.model.Feedback;
import com.sliit.pharmacy.model.Medicine;
import com.sliit.pharmacy.model.User;
import com.sliit.pharmacy.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final MedicineService medicineService;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           UserService userService,
                           MedicineService medicineService) {
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
        this.medicineService = medicineService;
    }

    public Feedback saveFeedback(Feedback feedback, String customerEmail) {
        User customer = userService.findUserByEmail(customerEmail);
        feedback.setCustomer(customer);
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackByMedicine(Long medicineId) {
        return feedbackRepository.findByMedicineId(medicineId);
    }

    public List<Feedback> getFeedbackByCustomer(String email) {
        User customer = userService.findUserByEmail(email);
        return feedbackRepository.findByCustomerId(customer.getId());
    }

    public double getAverageRating(Long medicineId) {
        Double avg = feedbackRepository.getAverageRatingByMedicineId(medicineId);
        return avg == null ? 0.0 : avg;
    }
}