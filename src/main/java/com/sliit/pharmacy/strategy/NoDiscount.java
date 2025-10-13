package com.sliit.pharmacy.strategy;

public class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount;
    }

    @Override
    public String getDiscountType() {
        return "Regular Customer";
    }
}