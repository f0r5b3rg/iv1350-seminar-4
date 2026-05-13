package se.kth.iv1350.repairelectricbike.startup;

import se.kth.iv1350.repairelectricbike.view.View;
import se.kth.iv1350.repairelectricbike.controller.Controller;
import se.kth.iv1350.repairelectricbike.integration.Printer;

/**
 * Contains the <code>main</code> method. Performs all startup of the
 * application.
 */

public class Main {
    /**
     * Starts the application.
     *
     * @param args The application does not take any command line parameters.
     */
    static void main(String[] args) {
        Printer printer = new Printer();
        Controller controller = new Controller(printer);
        new View(controller).sampleExecution();
    }
}
