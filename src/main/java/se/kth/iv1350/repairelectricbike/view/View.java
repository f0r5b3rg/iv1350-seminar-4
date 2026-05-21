package se.kth.iv1350.repairelectricbike.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.repairelectricbike.controller.Controller;
import se.kth.iv1350.repairelectricbike.controller.OperationFailedException;
import se.kth.iv1350.repairelectricbike.integration.*;
import se.kth.iv1350.repairelectricbike.model.LoyaltyDiscount;
import se.kth.iv1350.repairelectricbike.model.SummerDiscount;
import se.kth.iv1350.repairelectricbike.util.LogHandler;

/**
 * This program has no view, instead, this class is a placeholder for the entire
 * view.
 */
public class View {
    private final Controller controller;
    private LogHandler logger = LogHandler.getLogger();
    private ErrorMessageHandler errorMsgHandler = new ErrorMessageHandler();

    /**
     * Creates a new instance.
     *
     * @param controller The controller that is used for all operations.
     */
    public View(Controller controller) {
        this.controller = controller;
        controller.addRepairOrderRegistryObserver(new RepairOrderRegistryView());
        controller.addRepairOrderRegistryObserver(new RepairOrderRegistryLogger());
    }

    /**
     * Simulates a user input that generates calls to all system operations.
     */
    public void sampleExecution() {
        try {
            System.out.println("Sample execution started \n");
            // Creates test customers to use for sample execution.
            // The test customers are created and then passed to CustomerRegistry to be
            // saved.

            // Customer 1 bike setup.
            BikeDTO customer1Bike1 = new BikeDTO("Scott", "Tester", "123test67");
            BikeDTO customer1Bike2 = new BikeDTO("Lamborghini", "Aventador", "123bike123");
            List<BikeDTO> customer1Bikes = new ArrayList<>(List.of(customer1Bike1, customer1Bike2));

            // Customer 2 bike setup.
            BikeDTO customer2Bike1 = new BikeDTO("Artemis", "Two", "321liftoff");
            List<BikeDTO> customer2Bikes = new ArrayList<>(List.of(customer2Bike1));

            // Create all test customers.
            CustomerDTO customer1 = new CustomerDTO("Test Testsson", "test@test.com", "0707777777", 3, customer1Bikes);
            CustomerDTO customer2 = new CustomerDTO("Prov Provsdotter", "prov@prov.se", "1231231212", 0,
                    customer2Bikes);

            // Save test customers and corresponding new repair order to registries.
            ArrayList<CustomerDTO> testCustomers = new ArrayList<>(List.of(customer1, customer2));
            for (CustomerDTO customer : testCustomers) {
                // Adds customer to CustomerRegistry.
                controller.saveCustomer(customer);
                // Creates a repair order and sets it as active repair order.
                try {
                    controller.createRepairOrder(customer.getPhoneNumber(),
                            customer.getOwnedBikes().getFirst().getSerialNo(), "Bike inverted");
                } catch (CustomerNotFoundException e) {
                    writeToLogAndConsole("Customer not found in database.", e);
                }
                // Saves active repair order.
                controller.saveActiveRepairOrder();
            }

            controller.setActiveRepairOrder(0);
            controller.updateState(State.DENIED);
            controller.saveActiveRepairOrder();
            controller.setActiveRepairOrder(1);
            controller.updateState(State.DENIED);
            controller.saveActiveRepairOrder();

            // At this point the customer registry and repair order registry contains 2 test
            // objects.

            System.out.println("\n----------REPAIR ELECTRIC BIKE SCENARIO----------\n");

            // Receptionist enters customer’s phone number and
            // system searches customer registry for customer details (name and email
            // address),
            // and for details about the customer’s bike (brand, model and serial number).
            CustomerDTO foundCustomer = controller.searchCustomer("0707777777");
            System.out.printf("""
                    Result of searching for existing customer by phone number:
                        Name: %s
                        Email: %s
                        Phone number: %s
                        Number of repairs: %d
                        Owned bikes:
                    """,
                    foundCustomer.getName().toString(),
                    foundCustomer.getEmail().toString(),
                    foundCustomer.getPhoneNumber().toString(),
                    foundCustomer.getNoOfRepairs(),
                    foundCustomer.getOwnedBikes());

            for (BikeDTO bike : foundCustomer.getOwnedBikes()) {
                System.out.printf("""
                            \tBrand: %s, Model: %s, Serial number: %s
                        """,
                        bike.getBrand(),
                        bike.getModel(),
                        bike.getSerialNo());
            }

            // Receptionist asks customer for a description of the problem with the bike.
            // System creates a repair order containing customer details, bike details,
            // problem description and date.
            String customerProblemDescription = "The bike has one wheel";
            controller.createRepairOrder("0707777777", "123bike123", customerProblemDescription);
            controller.saveActiveRepairOrder();

            // Technician asks system for repair order and system presents repair order and
            // system presents repair order.
            List<RepairOrderDTO> repairOrders = controller.findRepairOrders(State.NEWLY_CREATED);
            RepairOrderDTO repairOrder = repairOrders.getLast();
            System.out.println("\nSystem presents selected repair order:");
            printRepairOrder(repairOrder);

            // Technician performs diagnostic and enters diagnostic report and proposed
            // repair tasks.
            // System updates repair order, by adding diagnostic report and proposed repair
            // tasks.
            System.out.println("\nTechnician performs a diagnosis of the bike: ");
            controller.addRepairTask("The bike misses a wheel", 999);
            controller.addRepairTask("The chain is rusty", 67);
            controller.updateDiagnosticResult("The bike is definitely broken");
            controller.updateState(State.READY_FOR_APPROVAL);
            controller.updateCompletionDate(LocalDate.of(2026, 6, 7));
            controller.saveActiveRepairOrder();

            System.out.println("\nApplying loyalty and summer discount: ");
            controller.applyDiscount(new LoyaltyDiscount());
            controller.applyDiscount(new SummerDiscount());
            controller.saveActiveRepairOrder();

            // Receptionist informs customer about diagnostic report, proposed repair tasks,
            // cost
            // for each proposed repair task, and total cost.
            List<RepairOrderDTO> updatedRepairOrders = controller.findRepairOrders(State.READY_FOR_APPROVAL);
            DiagnosticReportDTO diagnosticReport = updatedRepairOrders.getLast().getDiagnosticReport();
            System.out.println("\nPresents diagnostic report to the customer:\n");
            printDiagnosticReport(diagnosticReport);

            // Customer accepts proposed repair tasks and cost.
            // Receptionist registers that customer accepted repair order.
            controller.updateState(State.ACCEPTED);
            controller.saveActiveRepairOrder();

            // System prints repair order. The printout contains all repair order data,
            // including
            // estimation of when reparation will be completed.
            System.out.println("\nPresents all data regarding customer to customer:");
            controller.printRepairOrder();

            // Attempt to call the database, that are offline with a hardcoded phone number
            // "123"
            try {
                System.out.println("Trying to search customer while database is offline");
                controller.searchCustomer("123");
            } catch (OperationFailedException e) {
                writeToLogAndConsole("Failed to search for customer.\n", e);
            } catch (CustomerNotFoundException e) {
                errorMsgHandler.showErrorMsg("Customer was not found.");
            }

            // Attempt to search for a customer that does not exist.
            try {
                System.out.println("Trying to find a nonexisting customer, should generate an error message.");
                controller.searchCustomer("1");
            } catch (CustomerNotFoundException e) {
                errorMsgHandler.showErrorMsg("Customer correctly not found in database");
            }
        } catch (CustomerNotFoundException e) {
            errorMsgHandler.showErrorMsg("Customer could not be found among existing customers.");
        } catch (Exception e) {
            writeToLogAndConsole("Failed to execute basic flow.", e);
        }
    }

    private void writeToLogAndConsole(String consoleMsg, Exception exc) {
        errorMsgHandler.showErrorMsg(consoleMsg);
        logger.logException(exc);
    }

    private void printRepairOrder(RepairOrderDTO repairOrder) {
        System.out.printf("""
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

    private void printDiagnosticReport(DiagnosticReportDTO diagnosticReport) {
        System.out.printf("""
                Diagnostic Report:
                    Diagnostic result: %s
                    Total cost: %.1f
                    Repair tasks:
                """,
                diagnosticReport.getDiagnosticResult(),
                diagnosticReport.getTotalCost());

        for (RepairTaskDTO task : diagnosticReport.getRepairTasks()) {
            System.out.printf("""
                            Repair task description: %s, Cost to repair: %.1f
                    """,
                    task.getRepairTaskDescription(),
                    task.getCostToRepair());
        }
        System.out.println(" ");
    }
}