package com.sliit.pharmacy.strategy;

public class SeniorDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * 0.90; // 10% off
    }

    @Override
    public String getDiscountType() {
        return "Senior Citizen (10% off)";
    }
}