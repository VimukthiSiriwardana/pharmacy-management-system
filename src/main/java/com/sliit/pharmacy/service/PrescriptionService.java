// src/main/java/com/sliit/pharmacy/service/PrescriptionService.java
package com.sliit.pharmacy.service;

import com.sliit.pharmacy.model.Prescription;
import com.sliit.pharmacy.model.Prescription.PrescriptionStatus;
import com.sliit.pharmacy.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final UserService userService;

    // Directory to store uploads (must be created manually in your project)
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public PrescriptionService(PrescriptionRepository prescriptionRepository, UserService userService) {
        this.prescriptionRepository = prescriptionRepository;
        this.userService = userService;
    }

    public Prescription savePrescription(MultipartFile file, String customerEmail) throws IOException {
        // Ensure upload directory exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Validate file
        if (file.isEmpty()) {
            throw new IOException("Cannot upload empty file");
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            originalFilename = "prescription";
        }

        String extension = "";
        if (originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String uniqueFilename = "presc_" + UUID.randomUUID() + extension;
        String filePath = UPLOAD_DIR + uniqueFilename;

        // Save file to disk
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        // Save prescription record to database
        Prescription prescription = new Prescription();
        prescription.setCustomer(userService.findUserByEmail(customerEmail));
        prescription.setFileName(originalFilename);
        prescription.setFilePath("/uploads/" + uniqueFilename); // Web-accessible path
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getPrescriptionsByCustomer(String email) {
        return prescriptionRepository.findByCustomerEmail(email);
    }

    public List<Prescription> getPendingPrescriptions() {
        return prescriptionRepository.findByStatus(PrescriptionStatus.PENDING);
    }

    public Prescription approvePrescription(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        prescription.setStatus(PrescriptionStatus.APPROVED);
        prescription.setReviewedAt(LocalDateTime.now());
        return prescriptionRepository.save(prescription);
    }

    public Prescription rejectPrescription(Long id, String reason) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        prescription.setStatus(PrescriptionStatus.REJECTED);
        prescription.setReviewedAt(LocalDateTime.now());
        prescription.setRejectionReason(reason);
        return prescriptionRepository.save(prescription);
    }

    public long countPendingPrescriptions() {
        return prescriptionRepository.countByStatus(PrescriptionStatus.PENDING);
    }
}