package se.kth.iv1350.repairelectricbike.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerRegistryTest {
    private RegistryCreator creator;
    private CustomerRegistry customerRegistry;
    private CustomerDTO customer;
    private List<BikeDTO> bikes;

    @BeforeEach
    public void setUp() {
        creator = new RegistryCreator();
        bikes = new ArrayList<>(List.of(new BikeDTO("Disktrasa", "Yes", "123Drygt")));
        customer = new CustomerDTO("Frödinge", "ost@kaka.se", "112", bikes);
        customerRegistry = creator.getCustomerRegistry();
        customerRegistry.addCustomer(customer);
    }

    @AfterEach
    public void tearDown() {
        creator = null;
        customer = null;
        bikes = null;
        customerRegistry = null;
    }

    @Test
    void testAddCustomer() {
        customerRegistry.addCustomer(customer);

        boolean result = customer.equals(customerRegistry.searchCustomer(customer.getPhoneNumber()));
        assertTrue(result, "Failed to add customer.");
    }

    @Test
    void testSearchCustomer() {
        boolean result = customer.equals(customerRegistry.searchCustomer(customer.getPhoneNumber()));
        
        assertTrue(result, "Failed to find customer by phone number.");
    }
}
