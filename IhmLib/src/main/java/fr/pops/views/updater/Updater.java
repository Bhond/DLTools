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
package fr.pops.views.updater;

import fr.pops.customnodes.plot.candlestickplot.CandleData;
import fr.pops.customnodes.plot.candlestickplot.CandlestickPlot;
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
    // Custom plots
    /**
     * Add value to an existing series
     * @param series The series to update
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public static void update(XYChart.Series<Number, Number> series, double x, double y){
        Platform.runLater(() -> series.getData().add(series.getData().size(), new XYChart.Data<>(x, y)));
    }

    // Candlestick
    /**
     * Update candlestick chart
     * by adding a new candle
     * @param chart The series to update
     * @param candleData The new candle data to display
     */
    public static void update(CandlestickPlot chart, CandleData candleData){
        Platform.runLater(() -> chart.addCandle(candleData));
    }

    /**
     * Update candlestick chart
     * by setting the price of the last candle displayed
     * @param chart The candlestick chart to update
     * @param value The new closing price
     */
    public static void update(CandlestickPlot chart, double value){
        Platform.runLater(() -> chart.updateCurrentCandle(value));
    }


}
