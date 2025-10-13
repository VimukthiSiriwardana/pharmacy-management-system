package com.sliit.pharmacy.strategy;

public interface DiscountStrategy {
    double applyDiscount(double totalAmount);
    String getDiscountType();
}