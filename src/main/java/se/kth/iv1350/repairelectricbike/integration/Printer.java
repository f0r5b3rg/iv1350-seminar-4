package se.kth.iv1350.repairelectricbike.integration;

/**
 * Printer interface used for all printouts
 */
public class Printer {
    
    /**
     * Prints the specified repair order.
     * Dummy implementation prints to <code>System.out</code>.
     *
     * @param repairOrderToPrint   Contains the repair order data to be printed.
     */
    public void printRepairOrder(RepairOrderDTO repairOrderToPrint) {
        String printableRepairOrder = createPrintableRepairOrder(repairOrderToPrint);
        System.out.println(printableRepairOrder);
    }

    private String createPrintableRepairOrder(RepairOrderDTO repairOrderToPrint) {
        StringBuilder builder = new StringBuilder();
        appendLine(builder, "-----Repair order-----");
        endSection(builder);

        appendLine(builder, "Customer information: ");

        builder.append(repairOrderToPrint.getCustomer().toString());
        endSection(builder);
        endSection(builder);


        appendLine(builder, "Repair order information: ");

        builder.append(repairOrderToPrint);
        endSection(builder);
        endSection(builder);
        appendLine(builder, "----------------------");

        return builder.toString();
    }


    private void appendLine(StringBuilder builder, String line) {
        builder.append(line);
        builder.append("\n");
    }

    private void endSection(StringBuilder builder) {
        builder.append("\n");
    }
}

