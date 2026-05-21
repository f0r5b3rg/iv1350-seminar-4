package se.kth.iv1350.repairelectricbike.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerRegistryTest {
    private CustomerRegistry customerRegistry;
    private CustomerDTO customer;
    private List<BikeDTO> bikes;

    @BeforeEach
    public void setUp()  {
        bikes = new ArrayList<>(List.of(new BikeDTO("Disktrasa", "Yes", "123Drygt")));
        customer = new CustomerDTO("Frödinge", "ost@kaka.se", "112", 0, bikes);
        customerRegistry = CustomerRegistry.getCustomerRegistry();
        customerRegistry.addCustomer(customer);
    }

    @AfterEach
    public void tearDown() {
        customerRegistry.resetForTesting();
        customer = null;
        bikes = null;
        customerRegistry = null;
    }

    @Test
    void testAddCustomer() throws CustomerNotFoundException {
        customerRegistry.addCustomer(customer);

        boolean result = customer.equals(customerRegistry.searchCustomer(customer.getPhoneNumber()));
        assertTrue(result, "Failed to add customer.");
    }

    @Test
    void testSearchCustomerFound() throws CustomerNotFoundException {
        boolean result = customer.equals(customerRegistry.searchCustomer(customer.getPhoneNumber()));

        assertTrue(result, "Failed to find customer by phone number.");
    }

    @Test
    void testSearchCustomerNotFound() {
        String wrongPhoneNumber = "0788888888";

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerRegistry.searchCustomer(wrongPhoneNumber);
        });

        assertTrue(exception.getMessage().contains(wrongPhoneNumber),
                "Wrong exception message, does not contain specified phone number: " + exception.getMessage());
    }

    @Test
    void testFailedDatabaseCall() {
        String hardcodedDataBaseFailureNumber = "123";

        DatabaseCannotBeCalledException exception = assertThrows(DatabaseCannotBeCalledException.class, () -> {
            customerRegistry.searchCustomer(hardcodedDataBaseFailureNumber);
        });

        assertTrue(exception.getMessage().contains("Database could not be called."),
                "The exception message did not contain the expected database failure text.");
    }
}