package se.kth.iv1350.repairelectricbike.integration;

/**
 * This class is responsible for instantiating all registries.
 */
public class RegistryCreator {
    private CustomerRegistry customerRegistry = new CustomerRegistry();
    private RepairOrderRegistry repairOrderRegistry = new RepairOrderRegistry();

    /**
     * Get the value of customer registry. 
     * 
     * @return the value of repair order registry. 
     */
    public CustomerRegistry getCustomerRegistry() {
        return customerRegistry;
    }

    /**
     * Get the value of repair order registry. 
     * 
     * @return the value of repair order registry.
     */
    public RepairOrderRegistry getRepairOrderRegistry() {
        return repairOrderRegistry;
    }
}

