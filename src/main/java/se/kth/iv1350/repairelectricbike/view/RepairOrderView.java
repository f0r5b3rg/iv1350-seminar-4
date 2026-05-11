package se.kth.iv1350.repairelectricbike.view;

import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;
import se.kth.iv1350.repairelectricbike.model.RepairOrderObserver;

public class RepairOrderView implements RepairOrderObserver {
    @Override
    public void orderUpdated(RepairOrderDTO repairOrderDTO) {
        System.out.printf("Repair Order with id: %d has been updated.\n", repairOrderDTO.getId());
    }
}
