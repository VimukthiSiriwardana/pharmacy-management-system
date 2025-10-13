// src/main/java/com/sliit/pharmacy/service/DiscountContext.java
package com.sliit.pharmacy.service;

import com.sliit.pharmacy.model.User;
import com.sliit.pharmacy.strategy.DiscountStrategy;
import com.sliit.pharmacy.strategy.LoyaltyDiscount;
import com.sliit.pharmacy.strategy.NoDiscount;
import com.sliit.pharmacy.strategy.SeniorDiscount;

/**
 * Context class for the Strategy Pattern.
 * Selects and returns the appropriate discount strategy based on the user's discount type.
 */
public class DiscountContext {

    /**
     * Returns the discount strategy applicable to the given user.
     * Provides a safe fallback to NoDiscount if discountType is null.
     */
    public DiscountStrategy getStrategy(User user) {
        if (user.getDiscountType() == null) {
            return new NoDiscount(); // Safe fallback
        }
        return switch (user.getDiscountType()) {
            case SENIOR -> new SeniorDiscount();
            case LOYALTY -> new LoyaltyDiscount();
            default -> new NoDiscount();
        };
    }
}