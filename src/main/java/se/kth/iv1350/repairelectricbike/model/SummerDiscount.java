package se.kth.iv1350.repairelectricbike.model;

import java.time.LocalDateTime;

public class SummerDiscount implements DiscountStrategy{
    LocalDateTime curTime = LocalDateTime.now();
    int month = curTime.getMonthValue();

    @Override
    public double calculateDiscount(RepairOrder repairOrder) {

        if (month >= 5 && month <= 8) {
            return repairOrder.getDiagnosticReport().getTotalCost() * 0.1;
        }
        return 0;
    }
}
