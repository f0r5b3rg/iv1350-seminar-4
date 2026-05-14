package se.kth.iv1350.repairelectricbike.model;

import java.time.LocalDateTime;

/**
 * A discount strategy that gives 10% discount 
 * during the summer months May-August.
 */
public class SummerDiscount implements DiscountStrategy {

    @Override
    public double calculateDiscount(RepairOrder repairOrder) {
        LocalDateTime curTime = LocalDateTime.now();
        int month = curTime.getMonthValue();

        if (month >= 5 && month <= 8) {
            return repairOrder.getDiagnosticReport().getTotalCost() * 0.1;
        }
        return 0;
    }
}
