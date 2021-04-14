package fr.pops.views;

import javafx.application.Platform;
import javafx.scene.control.Label;

public abstract class Updater {

    public static void update(Label label, String text){
        Platform.runLater(() -> label.setText(text));
    }


}
