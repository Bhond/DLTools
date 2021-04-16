package fr.pops.views;

import javafx.application.Platform;
import javafx.scene.control.Label;

public abstract class Updater {

    /*****************************************
     *
     * Labels
     *
     *****************************************/
    /**
     * Update the the text of the specified label
     * @param label The label to update
     * @param text The text to print
     */
    public static void update(Label label, String text){
        Platform.runLater(() -> label.setText(text));
    }

}
