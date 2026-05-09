package se.kth.iv1350.repairelectricbike.view;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.repairelectricbike.controller.Controller;
import se.kth.iv1350.repairelectricbike.integration.*;

/**
 * This program has no view, instead, this class is a placeholder for the entire
 * view.
 */
public class View {
    private final Controller controller;

    /**
     * Creates a new instance.
     *
     * @param controller The controller that is used for all operations.
     */
    public View(Controller controller) {
        this.controller = controller;
    }

    /**
     * Simulates a user input that generates calls to all system operations.
     */
    public void sampleExecution() {
        System.out.println("Sample execution started");
        // Creates test customers to use for sample execution.
        // The test customers are created and then passed to CustomerRegistry to be saved.

        // Customer 1 bike setup
        BikeDTO customer1Bike1 = new BikeDTO("Golfklubba", "Iron 7", "dragonslayer67");
        BikeDTO customer1Bike2 = new BikeDTO("Toastskagen", "ShrimpPLate", "231lobster1");
        List<BikeDTO> customer1Bikes = new ArrayList<>(List.of(customer1Bike1, customer1Bike2));
        // Customer 2 bike setup.
        BikeDTO customer2Bike1 = new BikeDTO("TrigonometriV2", "EulerE", "SinCosTan1");
        List<BikeDTO> customer2Bikes = new ArrayList<>(List.of(customer2Bike1));
        // Customer 3 bike setup.
        BikeDTO customer3Bike1 = new BikeDTO("Truck", "leksaksbil", "291uudnsd");
        List<BikeDTO> customer3Bikes = new ArrayList<>(List.of(customer3Bike1));
        // Customer 4 bike setup.
        BikeDTO customer4Bike1 = new BikeDTO("Scott", "Tester", "123test67");
        BikeDTO customer4Bike2 = new BikeDTO("Lamborghini", "Aventador", "123bike123");
        List<BikeDTO> customer4Bikes = new ArrayList<>(List.of(customer4Bike1, customer4Bike2));
        // Customer 5 bike setup.
        BikeDTO customer5Bike1 = new BikeDTO("Artemis", "Two", "321liftoff");
        List<BikeDTO> customer5Bikes = new ArrayList<>(List.of(customer5Bike1));

        // Create all test customers.
        CustomerDTO customer1 = new CustomerDTO("Pia", "pi314@gmail.com", "070676767", customer1Bikes);
        CustomerDTO customer2 = new CustomerDTO("Shaun Bannanpannkaka", "banan@djungel.com", "0702137", customer2Bikes);
        CustomerDTO customer3 = new CustomerDTO("Lasses Gunnar", "Lasso@hotmail.com", "3049343", customer3Bikes);
        CustomerDTO customer4 = new CustomerDTO("Test Testsson", "test@test.com", "0707777777", customer4Bikes);
        CustomerDTO customer5 = new CustomerDTO("Prov Provsdotter", "prov@prov.se", "1231231212", customer5Bikes);

        // Save test customers and corresponding new repair order to registries.
        ArrayList<CustomerDTO> testCustomers = new ArrayList<>(List.of(customer1, customer2, customer3, customer4, customer5));
        for (CustomerDTO customer : testCustomers) {
            // Adds customer to CustomerRegistry.
            controller.saveCustomer(customer);
            // Creates a repair order and sets it as active repair order.
            controller.createRepairOrder(customer.getPhoneNumber(), customer.getOwnedBikes().getFirst().getSerialNo(), "Bike inverted");
            // Saves active repair order.
            controller.saveActiveRepairOrder();
        }

        controller.updateState(0, State.DENIED);
        controller.updateState(1, State.DENIED);
        controller.updateState(2, State.DENIED);
        controller.updateState(3, State.DENIED);

        // At this point the customer registry and repair order registry contains 5 test objects.


        //---------- BASIC FLOW STARTS HERE ----------

        // Receptionist enters customer’s phone number and
        // system searches customer registry for customer details (name and email address),
        // and for details about the customer’s bike (brand, model and serial number).
        CustomerDTO foundCustomer = controller.searchCustomer("0707777777");
        System.out.println("\nResult of searching for existing customer by phone number:\n" + foundCustomer + "\n");

        // Receptionist asks customer for a description of the problem with the bike.
        // System creates a repair order containing customer details, bike details, problem description and date.
        String customerProblemDescription = "The bike has one wheel";
        controller.createRepairOrder("0707777777", "123bike123", customerProblemDescription);
        controller.saveActiveRepairOrder();
        int id = 5; // saved id so it is easy to change

        // Technician asks system for repair order and system presents repair order and
        // system presents repair order.
        List<RepairOrderDTO> repairOrders = controller.findRepairOrders(State.NEWLY_CREATED);
        List<RepairTaskDTO> repairTasks;
        System.out.println("Result of searching for newly created repair orders:");
        for(RepairOrderDTO order : repairOrders) {
            repairTasks = order.getDiagnosticReport().getRepairTasks();
            StringBuilder repairTask = new StringBuilder("[");
            for (RepairTaskDTO task : repairTasks) {
                repairTask.append("[").append(task.getRepairTaskDescription()).append(", ").append(task.getCostToRepair()).append("], ");
            }
            repairTask.append("]");
            System.out.printf(
                        """
                            id: %d
                            Bike to repair: %s
                            Problem description: %s
                            Diagnostic result: %s
                            Repair tasks: %s
                            State: %s
                            Estimated completion date: %s
                            Total cost: %s
                        """,
                        order.getId(),
                        order.getBikeToRepair(),
                        order.getProblemDescription(),
                        order.getDiagnosticReport().getDiagnosticResult(),
                        repairTask,
                        order.getState(),
                        order.getEstimatedCompletionDate(),
                        order.getDiagnosticReport().getTotalCost()
            );
        }
        // Technician performs diagnostic and enters diagnostic report and proposed repair tasks.
        // System updates repair order, by adding diagnostic report and proposed repair tasks.
        controller.addRepairTask("The bike misses a wheel", 999);
        controller.addRepairTask("The chain is rusty", 67);
        String diagnosticResult  = "The bike is definitely broken";
        controller.updateDiagnosticResult(id, diagnosticResult);
        controller.updateState(id, State.READY_FOR_APPROVAL);
        controller.updateCompletionDate(id, LocalDate.of(2026, 06, 7));

        // Receptionist informs customer about diagnostic report, proposed repair tasks, cost
        // for each proposed repair task, and total cost.
        List<RepairOrderDTO> updatedRepairOrders = controller.findRepairOrders(State.READY_FOR_APPROVAL);
        System.out.println("The diagnostic report and repair tasks presented to the customer:");
        DiagnosticReportDTO diagnosticReportDTO = updatedRepairOrders.getFirst().getDiagnosticReport();
        StringBuilder repairTask = new StringBuilder("[");
        for (RepairTaskDTO task : diagnosticReportDTO.getRepairTasks()) {
            repairTask.append("[").append(task.getRepairTaskDescription()).append(", ").append(task.getCostToRepair()).append("], ");
        }
        repairTask.append("]");

        System.out.printf("""
                Diagnostic Result: %s
                Repair Tasks: %s
                Total cost: %d
            """,
                diagnosticReportDTO.getDiagnosticResult(),
                repairTask,
                diagnosticReportDTO.getTotalCost()
        );

        // Customer accepts proposed repair tasks and cost.
        // Receptionist registers that customer accepted repair order.
        controller.updateState(id, State.ACCEPTED);

        // System prints repair order. The printout contains all repair order data, including
        // estimation of when reparation will be completed.
       List<RepairOrderDTO> foundRepairOrders = controller.findRepairOrders(State.ACCEPTED);
       Printer printer = new Printer();
       printer.printRepairOrder(foundRepairOrders.getFirst());
    }
}
