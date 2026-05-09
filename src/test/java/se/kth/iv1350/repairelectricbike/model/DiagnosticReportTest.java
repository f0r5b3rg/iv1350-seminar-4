package se.kth.iv1350.repairelectricbike.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.repairelectricbike.integration.DiagnosticReportDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairTaskDTO;

public class DiagnosticReportTest {
    private DiagnosticReport diagnosticReport;   

    @BeforeEach
    public void setUp() {
        diagnosticReport = new DiagnosticReport();
    }

    @Test
    void testConvertToDTO() {
        DiagnosticReportDTO expected = new DiagnosticReportDTO( diagnosticReport.getDiagnosticResult(),
         diagnosticReport.getRepairTasks(), diagnosticReport.getTotalCost());
        DiagnosticReportDTO result = diagnosticReport.ConvertToDTO();

        boolean compare = expected.equals(result);
        assertTrue(compare, "Diagnostic report DTO conversion failed.");
    }

    @Test
    void testAddRepairTask() {
        RepairTaskDTO repairTask1 = new RepairTaskDTO("Bike is too big", 1);
        diagnosticReport.addRepairTask(repairTask1);

        assertEquals(1, diagnosticReport.getTotalCost(), "Total cost calculation failed.");
        assertEquals(repairTask1, diagnosticReport.getRepairTasks().getFirst(), "Repair task addition failed.");
    }
}
