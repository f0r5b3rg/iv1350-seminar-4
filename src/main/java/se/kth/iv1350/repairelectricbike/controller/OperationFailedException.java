package se.kth.iv1350.repairelectricbike.controller;

public class OperationFailedException extends Exception{
    public OperationFailedException(String msg, Exception cause) {
        super(msg, cause);
    }
}
