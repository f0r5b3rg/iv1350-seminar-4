package se.kth.iv1350.repairelectricbike.integration;

/**
 * Thrown when no customer with specified phone number exists in the data base
 */
public class CustomerNotFoundException extends Exception {

    /**
     * Creates a new instance with a specified message.
     * 
     * @param msg The exception message.
     */
    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
