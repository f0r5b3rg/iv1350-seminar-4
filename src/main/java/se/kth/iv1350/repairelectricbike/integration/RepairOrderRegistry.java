package se.kth.iv1350.repairelectricbike.integration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
  Contains all calls to the data store with all repair orders.
  Currently simulates database retrieval by storing repair orders instead.
 */
public class RepairOrderRegistry {
    private static int repairOrderCount;
    private List<RepairOrderData> repairOrders;

    /**
     * Creates a new instance representing the repair order registry.
     */
    RepairOrderRegistry() {
        this.repairOrders = new ArrayList<>();
    }

    /**
     * Adds a new repair order to the register.
     * 
     * @param repairOrderDTO    The repair order to be added. 
     */
    public void addRepairOrder(RepairOrderDTO repairOrderDTO) {
        // Check if repair order with the same id as the one to be added already exists.
        RepairOrderDTO foundRepairOrder = getRepairOrderDTObyID(repairOrderDTO.getId());

        // Prepare the new repair order.
        RepairOrderData newRepairOrder = new RepairOrderData(repairOrderDTO.getId(), repairOrderDTO.getCustomer(),
                repairOrderDTO.getBikeToRepair(),
                repairOrderDTO.getProblemDescription(), repairOrderDTO.getDate(), repairOrderDTO.getState(),
                repairOrderDTO.getDiagnosticReport());

        // If a repair order with the same id as the one to be added already exists, replace it in the repairOrder list.
        if (foundRepairOrder != null) {
            this.repairOrders.set(newRepairOrder.id, newRepairOrder);
            return;
        }
        // If one was not found, add it to the list.
        repairOrders.add(newRepairOrder);
        repairOrderCount += 1;
    }

    /**
     * Get the value of the amount of repair orders in the registry.
     *
     * @return  The value of the amount of repair orders.
     */
    public static int getRepairOrderCount() {
        return repairOrderCount;
    }

    /**
     * Set the value of the amount of repair orders in the registry.
     *
     * @param newCount  The new amount.
     */
    public static void setRepairOrderCount(int newCount) {
        repairOrderCount = newCount;
    }

    /**
     * Get a list of repair orders with a certain state. 
     * 
     * @param state     The state of the repair orders to retrieve. 
     * @return          A list of repair orders. 
     */
    public List<RepairOrderDTO> findRepairOrders(State state) {
        List<RepairOrderDTO> result = new ArrayList<>();
        for (RepairOrderData repairOrder : repairOrders) {
            if (state == repairOrder.state) {
                result.add(new RepairOrderDTO(repairOrder.id, repairOrder.customer, repairOrder.bikeToRepair,
                        repairOrder.problemDescription, repairOrder.estimatedCompletionDate, repairOrder.state,
                        repairOrder.diagnosticReport));
            }
        }
        return result;
    }

    /**
     * Updates the estimated completion date of a reparation
     * 
     * @param repairOrderID     The id of the repair order 
     * @param estimatedDate     The estimated completion date 
     */
    public void updateCompletionDate(int repairOrderID, LocalDate estimatedDate) {
        RepairOrderData repairOrder = repairOrders.get(repairOrderID);
        if (repairOrder != null)
            repairOrder.estimatedCompletionDate = estimatedDate;
    }

    /**
     * Retrieves a repair order from the database with the same id as repairOrderID as
     * a repair order DTO.
     *
     * @param repairOrderID     The id of the repair order to be retrieved.
     * @return                  A repair order with repairOrderID as its id. If none was found, returns null.
     */
    public RepairOrderDTO getRepairOrderDTObyID(int repairOrderID) {
        for (RepairOrderData repairOrder : repairOrders)
        {
            if (repairOrder.id == repairOrderID)
                return new RepairOrderDTO(repairOrder.id, repairOrder.customer, repairOrder.bikeToRepair,
                        repairOrder.problemDescription, repairOrder.estimatedCompletionDate, repairOrder.state,
                        repairOrder.diagnosticReport);
        }
        return null;
    }

    /**
     * Updates the state of a repair order. 
     * 
     * @param repairOrderID     The id of the repair order.
     * @param newState          The new state of the repair order.
     */
    public void updateState(int repairOrderID, State newState) {
        RepairOrderData repairOrder = repairOrders.get(repairOrderID);
        if (repairOrder != null)
            repairOrder.state = newState;
    }

    /**
     * Update the value of diagnostic result.
     * 
     * @param repairOrderID     The id of the repair order.
     * @param diagnosticResult  The diagnostiv result to be added.
     */
    public void updateDiagnosticResult(int repairOrderID, String diagnosticResult) {
        RepairOrderData repairOrder = repairOrders.get(repairOrderID);

        if (repairOrder != null)
        {
            List<RepairTaskDTO> repairTasks = repairOrder.diagnosticReport.getRepairTasks();
            int totalCost = repairOrder.diagnosticReport.getTotalCost();
            repairOrder.diagnosticReport = new DiagnosticReportDTO(diagnosticResult, repairTasks, totalCost);
        }
    }

    private class RepairOrderData {
        private int id;
        private CustomerDTO customer;
        private BikeDTO bikeToRepair;
        private String problemDescription;
        private LocalDate estimatedCompletionDate;
        private State state;
        private DiagnosticReportDTO diagnosticReport;

        private RepairOrderData(int id, CustomerDTO customer, BikeDTO bikeToRepair,
                String problemDescription, LocalDate estimatedCompletionDate,
                State state, DiagnosticReportDTO diagnosticReport) {
            this.id = id;
            this.customer = customer;
            this.bikeToRepair = bikeToRepair;
            this.problemDescription = problemDescription;
            this.estimatedCompletionDate = estimatedCompletionDate;
            this.state = state;
            this.diagnosticReport = diagnosticReport;
        }
    }
}
