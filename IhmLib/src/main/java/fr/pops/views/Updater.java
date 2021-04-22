/*******************************************************************************
 *
 *                         PPPP     OOOO     PPPP    SSSS
 *                        PP  PP   OO  OO   PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP   SSSS
 *                        PPPP    OO    OO  PPPP        SS
 *                        PP       OO  OO   PP          SS
 *                        PP        OOOO    PP       SSSS
 *
 * Name: Updater.java
 *
 * Description: Abstract class used to update GUI components
 *              Link between the app thread and the other threads harvesting
 *              or computing new values
 *
 * Author: Charles MERINO
 *
 * Date: 20/04/2021
 *
 ******************************************************************************/
package fr.pops.views;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public abstract class Updater {

    /*****************************************
     *
     * Labels
     *
     *****************************************/
    /**
     * Update the text of the specified label
     * @param label The label to update
     * @param text The text to print
     */
    public static void update(Label label, String text){
        Platform.runLater(() -> label.setText(text));
    }

    /*****************************************
     *
     * Charts
     *
     *****************************************/
    /**
     * Add value to an existing series
     * @param series The series to update
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public static void update(XYChart.Series<Number, Number> series, double x, double y){
        Platform.runLater(() -> series.getData().add(series.getData().size(), new XYChart.Data<>(x, y)));
    }

}
