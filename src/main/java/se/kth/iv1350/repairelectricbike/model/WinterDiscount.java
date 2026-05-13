package se.kth.iv1350.repairelectricbike.model;

import java.time.LocalDateTime;

public class WinterDiscount implements DiscountStrategy{
    LocalDateTime curTime = LocalDateTime.now();
    int month = curTime.getMonthValue();

    @Override
    public double calculateDiscount(RepairOrder repairOrder) {

        if (month >= 11 || month <= 2) {
            return repairOrder.getDiagnosticReport().getTotalCost() * 0.1;
        }
        return 0;
    }
}
