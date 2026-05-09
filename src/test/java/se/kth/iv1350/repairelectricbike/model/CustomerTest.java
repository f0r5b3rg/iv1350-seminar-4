package se.kth.iv1350.repairelectricbike.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repairelectricbike.integration.BikeDTO;
import se.kth.iv1350.repairelectricbike.integration.CustomerDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {
    private Customer customer;
    private List<BikeDTO> bikes;

    @BeforeEach
    public void setUp() {
        bikes = new ArrayList<>(List.of(
                new BikeDTO("Ford", "V1", "123Ka456"),
                new BikeDTO("Volvo", "XC60", "987FRE"),
                new BikeDTO("Audi", "RS6", "Bob5hund3"),
                new BikeDTO("Citroen", "Kaktus", "Tagg67")));

        CustomerDTO customerDTO = new CustomerDTO("Edward Cullen", "Fork@gmail.com", "0734567", bikes);
        customer = new Customer(customerDTO);
    }

    @Test
    void testGetBikeFromSerialNo() {
        BikeDTO result = customer.getBikeFromSerialNo("Tagg67");

        assertEquals(bikes.get(3), result, "Failed to retrieve bike from serial number.");
    }
}
