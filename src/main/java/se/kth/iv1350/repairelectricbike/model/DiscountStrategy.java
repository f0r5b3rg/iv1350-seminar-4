package se.kth.iv1350.repairelectricbike.model;

public interface DiscountStrategy {
    double calculateDiscount(RepairOrder repairOrder);
}
