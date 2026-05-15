package se.kth.iv1350.repairelectricbike.controller;

public class OperationFailedException extends Exception{

    /**
     * Creates a new instance with a specified message and cause.
     *
     * @param msg   The exception message.
     * @param cause The exception that caused this exception to be thrown.
     */
    public OperationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
