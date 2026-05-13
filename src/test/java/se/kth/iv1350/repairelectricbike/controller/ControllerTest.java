package se.kth.iv1350.repairelectricbike.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repairelectricbike.integration.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private RepairOrderRegistry repairOrderRegistry;
    private Controller controller;
    private List<BikeDTO> bikes;
    private CustomerDTO customer;
    private RepairOrderDTO repairOrder;
    private DiagnosticReportDTO diagnosticReport;

    @BeforeEach
    public void setUp() throws IOException {
        controller = new Controller(new Printer());
        repairOrderRegistry = RepairOrderRegistry.getRepairOrderRegistry();

        BikeDTO testBike1 = new BikeDTO("SixSeven", "GenAlpha", "67WC69");
        BikeDTO testBike2 = new BikeDTO("SixNine", "GenBeta", "0");
        bikes = new ArrayList<>(List.of(testBike1, testBike2));
        customer = new CustomerDTO("Douglas Andersson", "Doggelito1337@gmail.com", "07676767", 0, bikes);
        CustomerRegistry.getCustomerRegistry().addCustomer(customer);
        RepairOrderRegistry.setRepairOrderCount(0);

        diagnosticReport = new DiagnosticReportDTO(null, null, 0);
        repairOrder = new RepairOrderDTO(0, customer, bikes.getFirst(), "Hjulet är böjt :(", LocalDate.now(), State.NEWLY_CREATED, diagnosticReport);
        repairOrderRegistry.addRepairOrder(repairOrder);
        controller.setActiveRepairOrder(0);
    }

    @AfterEach
    public void tearDown() {
        repairOrderRegistry.resetForTesting();
        controller = null;
        bikes = null;
        customer = null;
        repairOrder = null;
        diagnosticReport = null;
    }

    @Test
    void testAddRepairTask() throws CustomerNotFoundException {
        String repairTaskProbDesc = "Problem löst";
        int costToRepair = 6767;

        controller.createRepairOrder(customer.getPhoneNumber(), bikes.get(1).getSerialNo(), "Problem");
        controller.addRepairTask(repairTaskProbDesc, costToRepair);

        int newId = RepairOrderRegistry.getRepairOrderCount() - 1;

        DiagnosticReportDTO result = repairOrderRegistry.getRepairOrderDTObyID(newId).getDiagnosticReport();
        RepairTaskDTO expected = new RepairTaskDTO(repairTaskProbDesc, costToRepair);

        assertEquals(result.getRepairTasks().getFirst(), expected, "Failed to add repair task.");
    }

    @Test
    void testFindRepairOrders() {
        List<RepairOrderDTO> repairOrders = controller.findRepairOrders(State.NEWLY_CREATED);

        assertEquals(1, repairOrders.size());

        for (RepairOrderDTO order : repairOrders)
            assertEquals(State.NEWLY_CREATED, order.getState(), "Failed to find repair orders by state.");
    }

    @Test
    void testCreateAndSaveActiveRepairOrder() throws CustomerNotFoundException {
        String problemDesc = "För lite öl på styret";
        controller.createRepairOrder(customer.getPhoneNumber(), bikes.get(1).getSerialNo(), problemDesc);
        controller.saveActiveRepairOrder();

        List<RepairOrderDTO> repairOrders = controller.findRepairOrders(State.NEWLY_CREATED);
        RepairOrderDTO result = repairOrders.getLast();

        System.out.println(RepairOrderRegistry.getRepairOrderCount());

        RepairOrderDTO expected = new RepairOrderDTO(1, customer, bikes.get(1), problemDesc, null, State.NEWLY_CREATED, new DiagnosticReportDTO("", new ArrayList<RepairTaskDTO>(), 0));

        assertEquals(expected, result, "Failed to create and save active repair order.");
    }

    @Test
    void testCreateRepairOrderCustomerNotFound() {
        try {
            String problemDesc = "test problem description";
            controller.createRepairOrder("07", bikes.get(1).getSerialNo(), problemDesc);
        } catch (CustomerNotFoundException e) {
            assertTrue(e.getMessage().contains("Could not find customer with phone number: 07"));
        } catch (Exception e) {
            fail("Wrong exception thrown.");
        }
    }

    @Test
    void testSaveCustomer() throws CustomerNotFoundException {
        List<BikeDTO> bikes = new ArrayList<>(List.of(new BikeDTO("Dalahäst", "Hofors2000", "123gäng456")));
        CustomerDTO customerToSave = new CustomerDTO("Linus Sandin", "sandalen67@hotmail.com", "07696969", 0, bikes);

        controller.saveCustomer(customerToSave);
        CustomerDTO result = controller.searchCustomer("07696969");

        assertEquals(customerToSave, result, "Failed to save customer.");
    }

    @Test
    void testSearchCustomer() throws CustomerNotFoundException {
        CustomerDTO result = controller.searchCustomer("07676767");
        assertEquals(customer, result, "Failed to find customer by phone number.");
    }

    @Test
    void testSearchCustomerNotFound() {
        try {
            controller.searchCustomer("007");
            fail("Customer not present in database found.");
        } catch (CustomerNotFoundException e) {
            assertTrue(e.getMessage().contains("Could not find customer with phone number: 007"));
        } catch (Exception e) {
            fail("Wrong exception thrown.");
        }
    }

    @Test
    void testUpdateCompletionDate() {
        controller.updateDiagnosticResult("Lol");
        controller.saveActiveRepairOrder();

        assertEquals("Lol", repairOrderRegistry.getRepairOrderDTObyID(0).getDiagnosticReport().getDiagnosticResult(), "Failed to update completion date.");
    }

    @Test
    void testUpdateDiagnosticResult() {
        LocalDate newDate = LocalDate.of(2026, 4, 29);
        controller.updateCompletionDate(newDate);
        controller.saveActiveRepairOrder();

        assertEquals(newDate, repairOrderRegistry.getRepairOrderDTObyID(0).getDate(), "Failed to update diagnostic result.");
    }

    @Test
    void testUpdateState() {
        State newState = State.ACCEPTED;

        controller.updateState(newState);
        controller.saveActiveRepairOrder();

        assertEquals(State.ACCEPTED, repairOrderRegistry.getRepairOrderDTObyID(0).getState(), "Failed to update state.");
    }
}
