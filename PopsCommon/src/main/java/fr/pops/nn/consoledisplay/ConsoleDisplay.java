package fr.pops.nn.consoledisplay;

import java.util.List;

public abstract class ConsoleDisplay {

    // Attributes
    private final static int PROGRESS_BAR_LENGTH = 100;

    public static void progressBar(String prefix, double percentage){
        // Initialize what to display
        String message = "\r" + prefix + " [";
        // Compute the number of # to display
        int step = (int) Math.round(PROGRESS_BAR_LENGTH * percentage);
        // Draw #
        for(int i = 0; i < step; i++){
            message += "#";
        }
        // Fill the rest of the progress bar with empty spaces
        for(int i = step; i < PROGRESS_BAR_LENGTH; i++){
            message += " ";
        }

        // Finalize the message
        String percentageStr = String.format("%3.0f", (percentage * 100)) + " %";
        message += "] "+ percentageStr;

        System.out.print(message);
    }


    public static void progressBar(String prefix, double percentage, List<String> valuesNames, List<Double> valuesDbl){
        // Initialize what to display
        String message = "\r" + prefix + " [";
        // Compute the number of # to display
        int step = (int) Math.round(PROGRESS_BAR_LENGTH * percentage);
        // Draw #
        for (int i = 0; i < step; i++){
            message += "#";
        }
        // Fill the rest of the progress bar with empty spaces
        for (int i = step; i < PROGRESS_BAR_LENGTH; i++){
            message += " ";
        }

        // End the message
        message += "] " + (percentage * 100) + " % ; ";

        // Add the suffix
        for (int i = 0; i < valuesNames.size(); i++){
            message += valuesNames.get(i) + " = " + String.format("%3.3f", valuesDbl.get(i)) + " ; ";
        }

        System.out.print(message);
    }
}
