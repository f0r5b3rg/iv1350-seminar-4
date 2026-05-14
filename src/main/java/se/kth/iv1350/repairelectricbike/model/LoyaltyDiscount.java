package se.kth.iv1350.repairelectricbike.model;

/**
 * A discount strategy that gives 50% discount to loyal customers.
 */
public class LoyaltyDiscount implements DiscountStrategy {

    @Override
    public double calculateDiscount(RepairOrder repairOrder) {
        int noOfRepairs = repairOrder.getCustomer().getCustomerDTO().getNoOfRepairs();
        if (noOfRepairs % 3 == 0 && noOfRepairs != 0) {
            return repairOrder.getDiagnosticReport().getTotalCost() * 0.5;
        }
        return 0;
    }
}
