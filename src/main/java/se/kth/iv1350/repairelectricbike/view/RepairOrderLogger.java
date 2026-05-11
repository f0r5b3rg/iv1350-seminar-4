package se.kth.iv1350.repairelectricbike.view;

import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;
import se.kth.iv1350.repairelectricbike.model.RepairOrderObserver;

public class RepairOrderLogger implements RepairOrderObserver {
    @Override
    public void orderUpdated(RepairOrderDTO repairOrderDTO) {
        // Skriver till logg.
    }
}
