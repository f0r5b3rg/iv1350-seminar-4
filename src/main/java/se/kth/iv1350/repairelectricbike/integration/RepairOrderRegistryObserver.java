package se.kth.iv1350.repairelectricbike.integration;

public interface RepairOrderRegistryObserver {

    /**
     * Invoked when a repair order is created.
     *
     * @param id The id of the created repair order.
     */
    void registryUpdated(RepairOrderDTO repairOrderDTO);
}
