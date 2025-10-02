// src/main/java/com/sliit/pharmacy/repository/PrescriptionRepository.java
package com.sliit.pharmacy.repository;

import com.sliit.pharmacy.model.Prescription;
import com.sliit.pharmacy.model.Prescription.PrescriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Find prescriptions by customer's email (using nested property)
    List<Prescription> findByCustomerEmail(String email);

    // Find prescriptions by status
    List<Prescription> findByStatus(PrescriptionStatus status);

    // Count prescriptions by status
    long countByStatus(PrescriptionStatus status);
}