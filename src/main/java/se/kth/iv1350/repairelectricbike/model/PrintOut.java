package se.kth.iv1350.repairelectricbike.model;

import se.kth.iv1350.repairelectricbike.integration.BikeDTO;
import se.kth.iv1350.repairelectricbike.integration.CustomerDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairTaskDTO;

/**
 * The printout of a rental
 */
public class PrintOut {
    private final RepairOrderDTO repairOrderToPrint;

    /**
     * Creates a new PrintOut instance.
     *
     * @param repairOrderToPrint The repairOrder that is to be printed.
     */
    public PrintOut(RepairOrderDTO repairOrderToPrint) {
        this.repairOrderToPrint = repairOrderToPrint;
    }

    /**
     * Creates a formatted string containing the data of the repairOrder.
     *
     * @return The formatted PrintOut string.
     */
    public String createPrintOut() {
        StringBuilder builder = new StringBuilder();
        appendLine(builder, "-----Repair order-----");
        endSection(builder);

        appendLine(builder, "Customer information: ");

        builder.append(toStringCustomerDTO(repairOrderToPrint.getCustomer()));
        endSection(builder);


        appendLine(builder, "Repair order information: ");

        builder.append(toStringRepairOrderDTO(repairOrderToPrint));
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

    private String toStringBikeDTO(BikeDTO bikeToPrint) {
        StringBuilder builder = new StringBuilder();
        builder.append("        Brand: ")
                .append(bikeToPrint.getBrand());

        builder.append(", Model: ")
                .append(bikeToPrint.getModel());

        builder.append(", Serial number: ")
                .append(bikeToPrint.getSerialNo())
                .append("\n");
        return builder.toString();
    }

    private String toStringCustomerDTO(CustomerDTO customerToPrint) {
        StringBuilder builder = new StringBuilder();
        builder.append("    Name: ")
                .append(customerToPrint.getName())
                .append("\n");

        builder.append("    Email: ")
                .append(customerToPrint.getEmail())
                .append("\n");

        builder.append("    Phone number: ")
                .append(customerToPrint.getPhoneNumber())
                .append("\n");

        builder.append("    Number of repairs: ")
                .append(customerToPrint.getNoOfRepairs())
                .append("\n");

        builder.append("    Owned bikes:\n");

        for (BikeDTO bike: customerToPrint.getOwnedBikes()) {
            builder.append(toStringBikeDTO(bike));
        }
        return builder.toString();
    }

    private String toStringRepairOrderDTO(RepairOrderDTO repairOrderToPrint) {
        StringBuilder builder = new StringBuilder();
        builder.append("    ID: ")
                .append(repairOrderToPrint.getId())
                .append("\n");

        builder.append("    Bike to repair:\n")
                .append(toStringBikeDTO(repairOrderToPrint.getBikeToRepair()));

        builder.append("    Problem description: ")
                .append(repairOrderToPrint.getProblemDescription())
                .append("\n");

        builder.append("    State: ")
                .append(repairOrderToPrint.getState())
                .append("\n");

        builder.append("    Estimated completion date: ")
                .append(repairOrderToPrint.getEstimatedCompletionDate())
                .append("\n");

        builder.append("    Diagnostic result: ")
                .append(repairOrderToPrint.getDiagnosticReport().getDiagnosticResult())
                .append("\n");

        builder.append("    Total cost: ")
                .append(repairOrderToPrint.getDiagnosticReport().getTotalCost())
                .append("\n");

        builder.append("    Repair tasks:\n");

        for (RepairTaskDTO task : repairOrderToPrint.getDiagnosticReport().getRepairTasks()) {
            builder.append("        Repair task description: ")
                    .append(task.getRepairTaskDescription());

            builder.append(", Cost to repair: ")
                    .append(task.getCostToRepair())
                    .append("\n");
        }
        return builder.toString();
    }
}
