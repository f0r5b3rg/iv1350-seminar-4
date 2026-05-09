package se.kth.iv1350.repairelectricbike.integration;

import java.util.List;
import java.util.Objects;

/**
 * Contains information about a customer.
 */
public class CustomerDTO {
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final List<BikeDTO> ownedBikes;

    /**
     * Creates a new instance representing a customer.
     *
     * @param name        The customer's name.
     * @param email       The customer's email.
     * @param phoneNumber The customer's phone number.
     * @param ownedBikes  The customer's bikes.
     */
    public CustomerDTO(String name, String email, String phoneNumber, List<BikeDTO> ownedBikes) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ownedBikes = ownedBikes;
    }

    @Override
    public String toString() {
        return "  Name: " + name + "\n" +
                "  Email: " + email + "\n" +
                "  Phone number: " + phoneNumber + "\n" +
                "  Owned bikes: " + ownedBikes;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof CustomerDTO other))
            return false;

        return Objects.equals(name, other.name) &&
               Objects.equals(email, other.email) &&
               Objects.equals(phoneNumber, other.phoneNumber) &&
               Objects.equals(ownedBikes, other.ownedBikes);
    }

    /**
     * Get the customer's name.
     *
     * @return the customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the customer's email.
     *
     * @return the customer's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the customer's phone number.
     *
     * @return the customer's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Get a list of the customer's owned bikes.
     *
     * @return a list containing the customer's owned bikes.
     */
    public List<BikeDTO> getOwnedBikes() {
        return ownedBikes;
    }
}
