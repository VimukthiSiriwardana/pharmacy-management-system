// src/main/java/com/sliit/pharmacy/model/Medicine.java
package com.sliit.pharmacy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Medicine name is required")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @FutureOrPresent(message = "Expiry date must be today or in the future")
    private LocalDate expiryDate;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Low stock threshold is required")
    @Min(value = 1, message = "Low stock threshold must be at least 1")
    private Integer lowStockThreshold;

    // === Helper Methods ===
    @Transient
    public String getExpiryStatus() {
        if (expiryDate == null) return "No Expiry";
        LocalDate today = LocalDate.now();
        if (expiryDate.isBefore(today)) return "Expired";
        if (!expiryDate.isAfter(today.plusMonths(1))) return "Expiring Soon";
        return "Valid";
    }

    @Transient
    public String getExpiryCssClass() {
        if (expiryDate == null) return "";
        LocalDate today = LocalDate.now();
        if (expiryDate.isBefore(today)) return "expiry-bad";
        if (!expiryDate.isAfter(today.plusMonths(1))) return "expiry-warning";
        return "expiry-good";
    }

    @Transient
    public String getInventoryStatus() {
        if (quantity == null || quantity <= 0) return "Out of Stock";
        if (lowStockThreshold != null && quantity <= lowStockThreshold) return "Low Stock";
        return "In Stock";
    }

    @Transient
    public String getInventoryCssClass() {
        if (quantity == null || quantity <= 0) return "stock-out";
        if (lowStockThreshold != null && quantity <= lowStockThreshold) return "stock-low";
        return "stock-ok";
    }

    @Transient
    public Long getDaysUntilExpiry() {
        if (expiryDate == null) return null;
        LocalDate today = LocalDate.now();
        if (expiryDate.isAfter(today)) {
            return ChronoUnit.DAYS.between(today, expiryDate);
        }
        return 0L; // Already expired
    }

    @Transient
    public Long getDaysExpired() {
        if (expiryDate == null) return null;
        LocalDate today = LocalDate.now();
        if (expiryDate.isBefore(today)) {
            return ChronoUnit.DAYS.between(expiryDate, today);
        }
        return 0L; // Not expired yet
    }

    // === Getters & Setters ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(Integer lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }
}
