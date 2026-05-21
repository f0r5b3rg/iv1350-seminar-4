package se.kth.iv1350.repairelectricbike.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.repairelectricbike.integration.*;

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
    private List<RepairOrderRegistryObserver> repairOrderRegistryObservers = new ArrayList<>();

    /**
     * Creates a new instance of a repair order.
     * 
     * @param customerDTO        The customer's information.
     * @param bikeSerialNo       The bike's serial number.
     * @param problemDescription The customer's problem description of the bike.
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

    public RepairOrder(RepairOrderDTO repairOrderDTO) {
        this.id = repairOrderDTO.getId();
        this.customer = new Customer(repairOrderDTO.getCustomer());
        this.bikeToRepair = this.customer.getBikeFromSerialNo(repairOrderDTO.getBikeToRepair().getSerialNo());
        this.problemDescription = repairOrderDTO.getProblemDescription();
        this.estimatedCompletionDate = repairOrderDTO.getEstimatedCompletionDate();
        this.state = repairOrderDTO.getState();
        this.diagnosticReport = new DiagnosticReport(repairOrderDTO.getDiagnosticReport());
    }

    /**
     * Converts a customer instance to a DTO.
     * 
     * @return the customer DTO.
     */
    public RepairOrderDTO convertToDTO() {
        return new RepairOrderDTO(this.id, this.customer.getCustomerDTO(), this.bikeToRepair, this.problemDescription,
                this.estimatedCompletionDate, this.state, this.diagnosticReport.ConvertToDTO());
    }

    /**
     * Creates and adds a repair task DTO to the repair order's diagnostic report.
     *
     * @param repairTaskDescription A description of the repair task that needs to
     *                              be performed.
     * @param costToRepair          The cost to perform the repair task.
     */
    public void addRepairTask(String repairTaskDescription, double costToRepair) {
        RepairTaskDTO taskToAdd = new RepairTaskDTO(repairTaskDescription, costToRepair);
        diagnosticReport.addRepairTask(taskToAdd);
    }

    /**
     * Updates the state of the repair order.
     * 
     * @param newState The new state of the repair order.
     */
    public void updateState(State newState) {
        this.state = newState;
    }

    /**
     * Updates the diagnostic result of the repair order.
     * 
     * @param newDiagnosticResult The new diagnostic result of the repair order.
     */
    public void updateDiagnosticResult(String newDiagnosticResult) {
        this.diagnosticReport.setDiagnosticResult(newDiagnosticResult);
    }

    /**
     * Updates the estimated completion date for a repair order.
     * 
     * @param estimatedDate The new estimated completion date of the repair order.
     */
    public void updateCompletionDate(LocalDate estimatedDate) {
        this.estimatedCompletionDate = estimatedDate;
    }

    /**
     * Applies the specified strategy discount to the repair order.
     * 
     * @param strategy The discount strategy to apply.
     */
    public void applyDiscount(DiscountStrategy strategy) {
        double discount = strategy.calculateDiscount(this);
        diagnosticReport.setTotalCost(diagnosticReport.getTotalCost() - discount);
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

    public void addRentalObserver(RepairOrderRegistryObserver obs) {
        repairOrderRegistryObservers.add(obs);
    }

    public void addRepairOrderObservers(List<RepairOrderRegistryObserver> observers) {
        repairOrderRegistryObservers.addAll(observers);
    }
}
