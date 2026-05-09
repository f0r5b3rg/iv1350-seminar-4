package se.kth.iv1350.repairelectricbike.integration;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all calls to the data store with registered customers. Currently, simulates database
 * retrieval by storing customers instead.
 */
public class CustomerRegistry {
  private List<CustomerData> customers;

  /**
   * Creates a list of customers to represent the customer registry. 
   */
  public CustomerRegistry() {
    this.customers = new ArrayList<>();
  }

  /**
   * Adds a customer to the customer registry.
   * 
   * @param customer the customer to be added. 
   */
  public void addCustomer(CustomerDTO customer) {
    this.customers.add(new CustomerData(
            customer.getName(),
            customer.getEmail(),
            customer.getPhoneNumber(),
            customer.getOwnedBikes()));
  }

  /**
   * Search for a customer with the matching phone number.
   *
   * @param searchedPhoneNumber The phone number that is searched for.
   * @return A customer with the matching phone number if found, else <code>null</code>.
   */
  public CustomerDTO searchCustomer(String searchedPhoneNumber) {
    for (CustomerData customer : this.customers) {
      if (hasPhoneNumber(searchedPhoneNumber, customer)) {
        return new CustomerDTO(
            customer.name, customer.email, customer.phoneNumber, customer.ownedBikes);
      }
    }
    System.out.println("Customer not found");
    return null;
  }

  private boolean hasPhoneNumber(String phoneNumber, CustomerData customer) {
    return phoneNumber.equals(customer.phoneNumber);
  }

  private class CustomerData {
    private String name;
    private String email;
    private String phoneNumber;
    private List<BikeDTO> ownedBikes;

    public CustomerData(String name, String email, String phoneNumber, List<BikeDTO> ownedBikes) {
      this.name = name;
      this.email = email;
      this.phoneNumber = phoneNumber;
      this.ownedBikes = ownedBikes;
    }
  }
}

