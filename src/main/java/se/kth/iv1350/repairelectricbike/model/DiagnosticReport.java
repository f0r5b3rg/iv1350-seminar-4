package se.kth.iv1350.repairelectricbike.model;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.repairelectricbike.integration.DiagnosticReportDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairTaskDTO;

/**
 * Contains diagnostic report information concerning a repair order.
 */
public class DiagnosticReport {
    private String diagnosticResult;
    private List<RepairTaskDTO> repairTasks;
    private double totalCost;

    DiagnosticReport() {
        this.diagnosticResult = "";
        this.repairTasks = new ArrayList<>();
        this.totalCost = 0;
    }

    DiagnosticReport(DiagnosticReportDTO diagnosticReportDTO) {
        this.diagnosticResult = diagnosticReportDTO.getDiagnosticResult();
        this.repairTasks = diagnosticReportDTO.getRepairTasks();
        this.totalCost = diagnosticReportDTO.getTotalCost();
    }

    /**
     * Creates a repair task and adds it to the diagnostic report. Calculates the
     * new total cost of all
     * repair tasks.
     *
     * @param repairTask The repair task to be added.
     */
    void addRepairTask(RepairTaskDTO repairTask) {
        repairTasks.add(repairTask);
        totalCost += repairTask.getCostToRepair();
    }

    /**
     * Converts a diagnostic report object to a diagnostic report DTO.
     *
     * @return the diagnostic report DTO.
     */

    DiagnosticReportDTO ConvertToDTO() {
        return new DiagnosticReportDTO(this.diagnosticResult, this.repairTasks, this.totalCost);
    }

    /**
     * Updates the diagnostic result of a diagnostic report.
     * 
     * @param newDiagnosticResult the new diagnostic result.
     */
    public void setDiagnosticResult(String newDiagnosticResult) {
        this.diagnosticResult = newDiagnosticResult;
    }

    /**
     * Get the diagnostic result.
     *
     * @return the diagnostic result.
     */
    public String getDiagnosticResult() {
        return diagnosticResult;
    }

    /**
     * Get the list of repair tasks.
     *
     * @return the list of repair tasks.
     */
    public List<RepairTaskDTO> getRepairTasks() {
        return repairTasks;
    }

    void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Get the total cost of all the repair tasks.
     *
     * @return the total cost.
     */
    public double getTotalCost() {
        return totalCost;
    }

}