package se.kth.iv1350.repairelectricbike.integration;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Contains information about a repair order.
 */
public class RepairOrderDTO {
    private final int id;
    private final CustomerDTO customer;
    private final BikeDTO bikeToRepair;
    private final String problemDescription;
    private final LocalDate estimatedCompletionDate;
    private final State state;
    private final DiagnosticReportDTO diagnosticReport;

    /**
     * Creates a new instance of a repair order.
     * 
     * @param id                      The repair order's id.
     * @param customer                The information of the customer's.
     * @param bikeToRepair            The information of the bike to repair.
     * @param problemDescription      The customer's problem description of the
     *                                bike.
     * @param estimatedCompletionDate The estimated completion date of the
     *                                reparation.
     * @param state                   The current state of repair order.
     * @param diagnosticReport        The data for the report's diagnostic report.
     */
    public RepairOrderDTO(int id, CustomerDTO customer, BikeDTO bikeToRepair,
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        if (!(obj instanceof RepairOrderDTO other)) 
            return false;
    
        return id == other.id &&
               Objects.equals(customer, other.customer) &&
               Objects.equals(bikeToRepair, other.bikeToRepair) &&
               Objects.equals(problemDescription, other.problemDescription) &&
               Objects.equals(estimatedCompletionDate, other.estimatedCompletionDate) &&
               state == other.state &&
               Objects.equals(diagnosticReport, other.diagnosticReport);
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
    public CustomerDTO getCustomer() {
        return customer;
    }

    /**
     * Get the value of the bike to repair.
     * 
     * @return the value of the bike to repair.
     */
    public BikeDTO getBikeToRepair() {
        return bikeToRepair;
    }

    /**
     * Get the value of date.
     * 
     * @return the value of date as a LocalDate object.
     */
    public LocalDate getDate() {
        return estimatedCompletionDate;
    }

    /**
     * Get the value of problem description.
     * 
     * @return the value of problem description.
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
     * Get the value of state.
     * 
     * @return the value of state.
     */
    public State getState() {
        return state;
    }

    /**
     * Get the value of diagnostic report.
     * 
     * @return the value of diagnostic report.
     */
    public DiagnosticReportDTO getDiagnosticReport() {
        return diagnosticReport;
    }
}