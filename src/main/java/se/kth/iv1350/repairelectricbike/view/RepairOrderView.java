package se.kth.iv1350.repairelectricbike.view;

import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairTaskDTO;
import se.kth.iv1350.repairelectricbike.model.RepairOrderObserver;

/**
 * Displays updated repair orders in the console.
 */
public class RepairOrderView implements RepairOrderObserver {
    /**
     * Prints information about the updated repair order.
     * 
     * @param repairOrder The updated repair order.
     */
    @Override
    public void orderUpdated(RepairOrderDTO repairOrder) {
        System.out.printf("""
                \nRepair order with ID %d has been updated:
                    ID: %s
                    Bike to repair:
                        Brand: %s
                        Model: %s
                        Serial number: %s
                    Problem description: %s
                    State: %s
                    Estimated completion date: %s
                    Diagnostic Report:
                        Diagnostic result: %s
                        Total cost: %.1f
                        Repair tasks:
                    """,
                repairOrder.getId(),
                repairOrder.getId(),
                repairOrder.getBikeToRepair().getBrand(),
                repairOrder.getBikeToRepair().getModel(),
                repairOrder.getBikeToRepair().getSerialNo(),
                repairOrder.getProblemDescription(),
                repairOrder.getState(),
                repairOrder.getEstimatedCompletionDate(),
                repairOrder.getDiagnosticReport().getDiagnosticResult(),
                repairOrder.getDiagnosticReport().getTotalCost());

        for (RepairTaskDTO task : repairOrder.getDiagnosticReport().getRepairTasks()) {
            System.out.printf("""
                                Repair task description: %s, Cost to repair: %.1f
                    """,
                    task.getRepairTaskDescription(),
                    task.getCostToRepair());
        }
    }
}
