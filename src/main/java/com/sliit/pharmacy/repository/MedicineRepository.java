package com.sliit.pharmacy.repository;

import com.sliit.pharmacy.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByNameContainingIgnoreCase(String keyword);
    Page<Medicine> findByNameContainingIgnoreCaseOrManufacturerContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String name, String manufacturer, String category, Pageable pageable);
    Page<Medicine> findAll(Pageable pageable);
}