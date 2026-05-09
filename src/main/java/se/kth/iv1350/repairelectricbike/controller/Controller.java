package se.kth.iv1350.repairelectricbike.controller;

import java.time.LocalDate;
import java.util.List;

import se.kth.iv1350.repairelectricbike.integration.CustomerDTO;
import se.kth.iv1350.repairelectricbike.integration.CustomerRegistry;
import se.kth.iv1350.repairelectricbike.integration.Printer;
import se.kth.iv1350.repairelectricbike.integration.RegistryCreator;
import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairOrderRegistry;
import se.kth.iv1350.repairelectricbike.integration.State;
import se.kth.iv1350.repairelectricbike.model.RepairOrder;

/**
 * This is the application's only controller class. All calls to the model pass
 * through here.
 */
public class Controller {
    private CustomerRegistry customerRegistry;
    private RepairOrderRegistry repairOrderRegistry;
    private Printer printer;

    // Keeps track of the repair order actively being handled.
    private RepairOrder activeRepairOrder;

    /**
     * Creates a new instance.
     *
     * @param regCreator Used to get all classes that handle database calls.
     * @param printer    Interface to printer.
     */
    public Controller(RegistryCreator regCreator, Printer printer) {
        this.customerRegistry = regCreator.getCustomerRegistry();
        this.repairOrderRegistry = regCreator.getRepairOrderRegistry();
        this.printer = printer;
    }

    /**
     * Searches for an existing customer in the customerRegistry.
     *
     * @param phoneNumber The phone number of the sought customer.
     * @return            The customer's information.
     */
    public CustomerDTO searchCustomer(String phoneNumber) {
        return customerRegistry.searchCustomer(phoneNumber);
    }

    /**
     * Creates a new repair order for a customer and sets it as the currently active repair order.
     *
     * @param phoneNumber  The phone number of the customer.
     * @param bikeSerialNo The serial number of the customers bike.
     * @param problemDesc  The description of the problems with the customers bike.
     */
    public void createRepairOrder(String phoneNumber, String bikeSerialNo, String problemDesc)  {
        CustomerDTO customer = searchCustomer(phoneNumber);
        activeRepairOrder = new RepairOrder(customer, bikeSerialNo, problemDesc);
    }

    /**
     * Saves the active repair order in rthe epair order registry.
     */
    public void saveActiveRepairOrder() {
        RepairOrderDTO toSave = activeRepairOrder.convertToDTO();
        repairOrderRegistry.addRepairOrder(toSave);
    }

    /**
     * Saves a customer in the customer registry.
     *
     * @param customer the customer to save.
     */
    public void saveCustomer(CustomerDTO customer) {
        customerRegistry.addCustomer(customer);
    }

    /**
     * Retrieves all repair orders that match the specifed state.
     *
     * @param state The state of the repair orders.
     * @return      A list of each repair order that is in the sought state.
     */
    public List<RepairOrderDTO> findRepairOrders(State state) {
        return repairOrderRegistry.findRepairOrders(state);
    }

    /**
     * Adds a new repair task to the repair order and updates its total cost.
     *
     * @param repairTaskDescription Description of the new repairTask.
     * @param costToRepair          The cost of the new repairTask.
     */
    public void addRepairTask(String repairTaskDescription, int costToRepair) {
        activeRepairOrder.addRepairTask(repairTaskDescription, costToRepair);
        saveActiveRepairOrder();
    }

    /**
     * Updates the current state of the repair order.
     *
     * @param repairOrderID The id of the repair order.
     * @param newState      The new state of the repair order.
     */
    public void updateState(int repairOrderID, State newState) {
        repairOrderRegistry.updateState(repairOrderID, newState);
    }

    /**
     * Updates the diagnostic report of the repair order.
     *
     * @param repairOrderID    The id of the repair order.
     * @param diagnosticResult The updated description of the diagnostic report
     */
    public void updateDiagnosticResult(int repairOrderID, String diagnosticResult) {
        repairOrderRegistry.updateDiagnosticResult(repairOrderID, diagnosticResult);
    }

    /**
     * Finds the repair order and prints it.
     *
     * @param repairOrderID The id of the repair order to print.
     */
    public void printRepairOrder(int repairOrderID) {
        RepairOrderDTO repairOrderToPrint = repairOrderRegistry.getRepairOrderDTObyID(repairOrderID);
        printer.printRepairOrder(repairOrderToPrint);
    }

    /**
     * Updates the estimated completion date of the repair order.
     *
     * @param repairOrderID The id of the repair order.
     * @param estimatedDate The new estimated completion date of the repair order.
     */
    public void updateCompletionDate(int repairOrderID, LocalDate estimatedDate) {
        repairOrderRegistry.updateCompletionDate(repairOrderID, estimatedDate);
    }
}
