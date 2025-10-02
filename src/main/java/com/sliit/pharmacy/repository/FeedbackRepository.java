// src/main/java/com/sliit/pharmacy/repository/FeedbackRepository.java
package com.sliit.pharmacy.repository;

import com.sliit.pharmacy.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByMedicineId(Long medicineId);
    List<Feedback> findByCustomerId(Long customerId);

    // ✅ FIXED: Custom query for average rating
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.medicine.id = :medicineId")
    Double getAverageRatingByMedicineId(Long medicineId);
}