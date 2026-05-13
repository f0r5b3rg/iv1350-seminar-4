package se.kth.iv1350.repairelectricbike.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.repairelectricbike.integration.*;

public class RepairOrderTest {
    private CustomerDTO customerDTO;
    private RepairOrder repairOrder;
    private List<BikeDTO> bikes;

    private RepairOrderRegistry repairOrderRegistry;

    @BeforeEach
    public void setUp() {
        bikes = new ArrayList<>(List.of(new BikeDTO("Disktrasa", "Yes", "123Drygt")));
        customerDTO = new CustomerDTO("Frödinge", "ost@kaka.se", "112", 0, bikes);

        repairOrder = new RepairOrder(customerDTO, "123Drygt", "Bell is broken");

        CustomerRegistry customerRegistry = CustomerRegistry.getCustomerRegistry();
        repairOrderRegistry = RepairOrderRegistry.getRepairOrderRegistry();

        customerRegistry.addCustomer(customerDTO);
        repairOrderRegistry.addRepairOrder(repairOrder.convertToDTO());
    }

    @AfterEach
    public void tearDown() {
        customerDTO = null;
        repairOrder = null;
        bikes = null;
        repairOrderRegistry = null;
    }

    @Test
    public void testConvertToDTO() {
        DiagnosticReportDTO diagnosticReportDTO = new DiagnosticReportDTO(
            repairOrder.getDiagnosticReport().getDiagnosticResult(), 
            repairOrder.getDiagnosticReport().getRepairTasks(), 
            repairOrder.getDiagnosticReport().getTotalCost());
        
        RepairOrderDTO expected = new RepairOrderDTO(
            repairOrder.getId(), 
            repairOrder.getCustomer().getCustomerDTO(), 
            repairOrder.getBikeToRepair(), 
            repairOrder.getProblemDescription(),
            repairOrder.getEstimatedCompletionDate(),
            repairOrder.getState(), 
            diagnosticReportDTO);

        RepairOrderDTO result = repairOrder.convertToDTO();

        boolean compare = expected.equals(result);
        assertTrue(compare, "Repair order DTO conversion failed.");
    }

    @Test
    void testAddRepairTask() {
        String repairTaskProblemDesc = "Bike doesn't exist";
        int costToRepair = 6767;
        RepairTaskDTO newRepairTask = new RepairTaskDTO(repairTaskProblemDesc, costToRepair);
        repairOrder.addRepairTask(repairTaskProblemDesc, costToRepair);
        
        boolean result = newRepairTask.equals(repairOrder.getDiagnosticReport().getRepairTasks().getFirst());
        assertTrue(result, "Repair Task not added successfully.");
    }
}
