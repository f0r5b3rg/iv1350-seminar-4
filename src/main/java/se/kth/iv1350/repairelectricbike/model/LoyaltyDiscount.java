package se.kth.iv1350.repairelectricbike.model;

public class LoyaltyDiscount implements DiscountStrategy{

    @Override
    public double calculateDiscount(RepairOrder repairOrder) {
        int noOfRepairs = repairOrder.getCustomer().getCustomerDTO().getNoOfRepairs();
        if (noOfRepairs % 3 == 0 && noOfRepairs != 0) {
            return repairOrder.getDiagnosticReport().getTotalCost() * 0.5;
        }
        return 0;
    }
}
