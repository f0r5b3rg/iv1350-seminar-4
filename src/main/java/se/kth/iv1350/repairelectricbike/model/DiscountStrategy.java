package se.kth.iv1350.repairelectricbike.model;

/**
 * Represents a strategy for calculating discounts on repair orders.
 */
public interface DiscountStrategy {

    /**
     * Calculates the discount for the specified repair order.
     * 
     * @param repairOrder The repair order the discount is calculated for.
     * @return The calculated discount amount.
     */
    double calculateDiscount(RepairOrder repairOrder);
}
