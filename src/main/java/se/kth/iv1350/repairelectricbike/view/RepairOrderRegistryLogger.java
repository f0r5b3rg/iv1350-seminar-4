package se.kth.iv1350.repairelectricbike.view;

import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairTaskDTO;
import se.kth.iv1350.repairelectricbike.integration.RepairOrderRegistryObserver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Logs repair order updates to a text file.
 */
public class RepairOrderRegistryLogger implements RepairOrderRegistryObserver {
    private static final String LOG_FILE_NAME = "repairorderupdate-log.txt";
    private PrintWriter logFile;

    /**
     * Creates a new instance and opens the log file.
     */
    public RepairOrderRegistryLogger() {
        try {
            logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME, true), true);
        } catch (IOException ex) {
            System.out.println("Could not create logger.");
            ex.printStackTrace();
        }
    }

    /**
     * Logs information about an updated repair order.
     * 
     * @param repairOrderDTO The updated repair order.
     */
    @Override
    public void registryUpdated(RepairOrderDTO repairOrder) {

        StringBuilder toPrint = new StringBuilder(String.format("""
                        \nRepair order with ID %d has been updated in registry:
                            ID: %s
                            Bike to repair:
                                Brand: %s
                                Model: %s
                                Serial number: %s
                            Problem description: %s
                            State: %s
                            Estimated completion date: %s
                            Diagnostic Report:
                                Diagnostic result: %s
                                Total cost: %.1f
                                Repair tasks:
                        """,
                repairOrder.getId(),
                repairOrder.getId(),
                repairOrder.getBikeToRepair().getBrand(),
                repairOrder.getBikeToRepair().getModel(),
                repairOrder.getBikeToRepair().getSerialNo(),
                repairOrder.getProblemDescription(),
                repairOrder.getState(),
                repairOrder.getEstimatedCompletionDate(),
                repairOrder.getDiagnosticReport().getDiagnosticResult(),
                repairOrder.getDiagnosticReport().getTotalCost()));

        for (RepairTaskDTO task : repairOrder.getDiagnosticReport().getRepairTasks()) {
            toPrint.append(String.format("""
                                Repair task description: %s, Cost to repair: %.1f
                    """,
                    task.getRepairTaskDescription(),
                    task.getCostToRepair()));
        }

        logFile.println(toPrint);
        logFile.println("\n");
    }

    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}
