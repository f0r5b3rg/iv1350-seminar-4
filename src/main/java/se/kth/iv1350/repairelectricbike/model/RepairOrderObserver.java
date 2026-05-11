package se.kth.iv1350.repairelectricbike.model;

import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;

public interface RepairOrderObserver {

    /**
     * Invoked when a repair order is created.
     *
     * @param id The id of the created repair order.
     */
    void orderUpdated(RepairOrderDTO repairOrderDTO);
}
