package se.kth.iv1350.repairelectricbike.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * This class is responsible for showing error messages to the user.
 */
class ErrorMessageHandler {

    /**
     * Displays the specified error message.
     *
     * @param msg The error message.
     */
    void showErrorMsg(String msg) {
        String errorMsgBuilder = createTime() +
                ", ERROR: " +
                msg;
        System.out.println(errorMsgBuilder);
    }

    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}
