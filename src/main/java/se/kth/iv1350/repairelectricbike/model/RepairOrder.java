package se.kth.iv1350.repairelectricbike.model;

import java.time.LocalDate;

import se.kth.iv1350.repairelectricbike.integration.BikeDTO;
import se.kth.iv1350.repairelectricbike.integration.CustomerDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairOrderRegistry;
import se.kth.iv1350.repairelectricbike.integration.RepairTaskDTO;
import se.kth.iv1350.repairelectricbike.integration.State;

/**
 * Contains information about a repair task.
 *
 */
public class RepairOrder {
    private int id;
    private Customer customer;
    private BikeDTO bikeToRepair;
    private String problemDescription;
    private LocalDate estimatedCompletionDate;
    private State state;
    private DiagnosticReport diagnosticReport;

    /**
     * Creates a new instance of a repair order.
     * 
     * @param customerDTO           The customer's information. 
     * @param bikeSerialNo          The bike's serial number.
     * @param problemDescription    The customer's problem description of the bike. 
     */
    public RepairOrder(CustomerDTO customerDTO, String bikeSerialNo, String problemDescription) {
        this.id = RepairOrderRegistry.getRepairOrderCount();
        this.customer = new Customer(customerDTO);
        this.bikeToRepair = this.customer.getBikeFromSerialNo(bikeSerialNo);
        this.problemDescription = problemDescription;
        this.estimatedCompletionDate = null;
        this.state = State.NEWLY_CREATED;
        this.diagnosticReport = new DiagnosticReport();
    }

    /**
     * Converts a customer instance to a DTO. 
     * 
     * @return  the customer DTO.
     */
    public RepairOrderDTO convertToDTO() {
        return new RepairOrderDTO(this.id, this.customer.getCustomerDTO(), this.bikeToRepair, this.problemDescription, this.estimatedCompletionDate, this.state, this.diagnosticReport.ConvertToDTO());
    }

    /**
     * Creates and adds a repair task DTO to the repair order's diagnostic report.
     *
     * @param repairTaskDescription     A description of the repair task that needs to be performed.
     * @param costToRepair              The cost to perform the repair task.
     */
    public void addRepairTask(String repairTaskDescription, int costToRepair) {
        RepairTaskDTO taskToAdd = new RepairTaskDTO(repairTaskDescription, costToRepair);
        diagnosticReport.addRepairTask(taskToAdd);
    }

    /**
     * Get the repair order's id.
     * 
     * @return the value of id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the value of customer.
     * 
     * @return the value of customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Get the value of the bike to repair. 
     * 
     * @return the value of the bike to repair 
     */
    public BikeDTO getBikeToRepair() {
        return bikeToRepair;
    }

    /**
     * Get the value of the problem description.
     * 
     * @return the value of the problem description
     */
    public String getProblemDescription() {
        return problemDescription;
    }

    /**
     * Get the value of the estimated completion date. 
     * 
     * @return the value of the estimated completion date. 
     */
    public LocalDate getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    /**
     * Get the value of the state. 
     * 
     * @return the value of the state.
     */
    public State getState() {
        return state;
    }

    /**
     * Get the value of the diagnostic report.
     * 
     * @return the value of the diagnostic report. 
     */
    public DiagnosticReport getDiagnosticReport() {
        return diagnosticReport;
    }

}
