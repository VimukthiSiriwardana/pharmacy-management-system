package com.sliit.pharmacy.strategy;

public class LoyaltyDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * 0.85; // 15% off
    }

    @Override
    public String getDiscountType() {
        return "Loyalty Member (15% off)";
    }
}