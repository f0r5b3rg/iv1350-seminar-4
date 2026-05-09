package se.kth.iv1350.repairelectricbike.integration;

public class DatabaseCannotBeCalledException extends RuntimeException{
    public DatabaseCannotBeCalledException() {
        super("Database cannot be called.");
    }
}
