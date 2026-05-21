package se.kth.iv1350.repairelectricbike.integration;

import se.kth.iv1350.repairelectricbike.model.PrintOut;

/**
 * Printer interface used for all printouts
 */
public class Printer {

    /**
     * Prints the specified PrintOut.
     * Dummy implementation prints to <code>System.out</code>.
     *
     * @param printOut The PrintOut instance of the repairOrder that is to be printed.
     */
    public void printRepairOrder(RepairOrderDTO repairOrderDTO) {
        PrintOut printOut = new PrintOut(repairOrderDTO);
        System.out.println(printOut.createPrintOut());
    }
}