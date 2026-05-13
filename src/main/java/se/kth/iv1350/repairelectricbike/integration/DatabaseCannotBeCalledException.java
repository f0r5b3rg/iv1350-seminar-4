package se.kth.iv1350.repairelectricbike.integration;

/**
 * Thrown when the data base cannot be accesed,
 * for example when the data base is offline. 
 */
public class DatabaseCannotBeCalledException extends RuntimeException{

    /**
     * Creates a new instance of the exception. 
     */
    public DatabaseCannotBeCalledException() {
        super("Database could not be called.");
    }
}
