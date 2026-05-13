package se.kth.iv1350.repairelectricbike.model;

import se.kth.iv1350.repairelectricbike.integration.BikeDTO;
import se.kth.iv1350.repairelectricbike.integration.CustomerDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;


/**
 * The printout of a rental
 */
public class PrintOut
{
    private final RepairOrderDTO repairOrderToPrint;

    /**
     * Creates a new PrintOut instance.
     *
     * @param repairOrderToPrint The repairOrder that is to be printed.
     */
    public PrintOut(RepairOrderDTO repairOrderToPrint)
    {
        this.repairOrderToPrint = repairOrderToPrint;
    }

    /**
     * Creates a formatted string containing the data of the repairOrder.
     *
     * @return The formatted PrintOut string.
     */
    public String createPrintOut()
    {
        StringBuilder builder = new StringBuilder();
        appendLine(builder, "-----Repair order-----");
        endSection(builder);

        appendLine(builder, "Customer information: ");

        builder.append(toStringCustomerDTO(repairOrderToPrint.getCustomer()));
        endSection(builder);
        endSection(builder);


        appendLine(builder, "Repair order information: ");

        builder.append(repairOrderToPrint.getId());
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

    private String toStringCustomerDTO(CustomerDTO customerToPrint) {
        return "  Name: " + customerToPrint.getName() + "\n" +
                "  Email: " + customerToPrint.getEmail() + "\n" +
                "  Phone number: " + customerToPrint.getPhoneNumber() + "\n" +
                "  Owned bikes: " + customerToPrint.getOwnedBikes();
    }
    /*private String toStringBikeDTO(BikeDTO bikeToPrint) {
        return "brand: " + bikeToPrint.getBrand() + ", " +
                "model: " + bikeToPrint.getModel() + ", " +
                "serialNo: " + bikeToPrint.getSerialNo();
    } */
}
