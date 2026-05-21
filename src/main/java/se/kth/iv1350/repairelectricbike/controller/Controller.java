package se.kth.iv1350.repairelectricbike.controller;

import java.time.LocalDate;
import java.util.List;

import se.kth.iv1350.repairelectricbike.integration.*;
import se.kth.iv1350.repairelectricbike.model.*;

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
    public Controller(Printer printer) {
        this.customerRegistry = CustomerRegistry.getCustomerRegistry();
        this.repairOrderRegistry = RepairOrderRegistry.getRepairOrderRegistry();
        this.printer = printer;
    }

    /**
     * Searches for an existing customer in the customerRegistry.
     *
     * @param phoneNumber The phone number of the sought customer.
     * @return The customer's information.
     * @throws CustomerNotFoundException If no customer is found with the specified phone number.
     * @throws DatabaseCannotBeCalledException If the database cannot be reached.
     */
    public CustomerDTO searchCustomer(String phoneNumber) throws CustomerNotFoundException, OperationFailedException {
        try {
            return customerRegistry.searchCustomer(phoneNumber);
        } catch (DatabaseCannotBeCalledException e) {
            throw new OperationFailedException("Could not search for customer", e);
        }
    }

    /**
     * Creates a new repair order for a customer and sets it as the currently active
     * repair order.
     *
     * @param phoneNumber  The phone number of the customer.
     * @param bikeSerialNo The serial number of the customers bike.
     * @param problemDesc  The description of the problems with the customers bike.
     */
    public void createRepairOrder(String phoneNumber, String bikeSerialNo, String problemDesc)
            throws CustomerNotFoundException, OperationFailedException {
        CustomerDTO customer = searchCustomer(phoneNumber);
        activeRepairOrder = new RepairOrder(customer, bikeSerialNo, problemDesc);
    }

    public void setActiveRepairOrder(int id) {
        this.activeRepairOrder = new RepairOrder(repairOrderRegistry.getRepairOrderDTObyID(id));
    }

    /**
     * Saves the active repair order in the epair order registry.
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
     * Retrieves all repair orders that match the specified state.
     *
     * @param state The state of the repair orders.
     * @return A list of each repair order that is in the sought state.
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
    public void addRepairTask(String repairTaskDescription, double costToRepair) {
        activeRepairOrder.addRepairTask(repairTaskDescription, costToRepair);
    }

    /**
     * Updates the current state of the repair order.
     *
     * @param newState The new state of the repair order.
     */
    public void updateState(State newState) {
        activeRepairOrder.updateState(newState);
    }

    /**
     * Updates the diagnostic report of the repair order.
     *
     * @param diagnosticResult The updated description of the diagnostic report
     */
    public void updateDiagnosticResult(String diagnosticResult) {
        activeRepairOrder.updateDiagnosticResult(diagnosticResult);
    }

    public void applyLoyaltyDiscount() {
        activeRepairOrder.applyDiscount(new LoyaltyDiscount());
    }

    public void applySummerDiscount() {
        activeRepairOrder.applyDiscount(new SummerDiscount());
    }

    /**
     * Finds the repair order and prints it.
     */
    public void printRepairOrder() {
        printer.printRepairOrder(activeRepairOrder.convertToDTO());
    }

    /**
     * Updates the estimated completion date of the repair order.
     *
     * @param estimatedDate The new estimated completion date of the repair order.
     */
    public void updateCompletionDate(LocalDate estimatedDate) {
        activeRepairOrder.updateCompletionDate(estimatedDate);
    }

    /**
     * Add an observer that will be notified whenever a repair order is updated.
     *
     * @param obs The observer to add.
     */
    public void addRepairOrderRegistryObserver(RepairOrderRegistryObserver obs) {
        repairOrderRegistry.addRepairOrderRegistryObserver(obs);
    }
}
