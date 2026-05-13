package se.kth.iv1350.repairelectricbike.view;

import se.kth.iv1350.repairelectricbike.integration.RepairOrderDTO;
import se.kth.iv1350.repairelectricbike.model.RepairOrderObserver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class RepairOrderLogger implements RepairOrderObserver {
    private static final String LOG_FILE_NAME = "repairorderupdate-log.txt";
    private PrintWriter logFile;

    public RepairOrderLogger() {
        try {
            logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME, true), true);
        } catch (IOException ex) {
            System.out.println("Could not create logger.");
            ex.printStackTrace();
        }
    }
    @Override
    public void orderUpdated(RepairOrderDTO repairOrderDTO) {
        StringBuilder logMsgBuilder = new StringBuilder();
        logMsgBuilder.append(createTime());
        logMsgBuilder.append(", Repair order with id: ");
        logMsgBuilder.append(repairOrderDTO.getId());
        logMsgBuilder.append(" updated.");
        logFile.println(logMsgBuilder);
        logFile.println("\n");
    }

    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}
