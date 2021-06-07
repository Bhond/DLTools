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

import fr.pops.controllers.viewcontrollers.StockController;
import fr.pops.cst.EnumCst;
import fr.pops.customnodes.heximage.HexImage;
import fr.pops.customnodes.labelvaluepair.LabelValuePair;
import fr.pops.customnodes.neuralnetworks.networks.NeuralNetwork;
import fr.pops.customnodes.plot.candlestickplot.CandleData;
import fr.pops.customnodes.plot.candlestickplot.CandlestickPlot;
import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.views.stock.QuoteInfo;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Collection;
import java.util.HashMap;

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

    /*****************************************
     *
     * LabelValuePair
     *
     *****************************************/
    /**
     * Update a LabelValuePair
     * @param pair The node to update
     * @param value The value to display
     */
    public static void update(LabelValuePair pair, String value){
        Platform.runLater(() -> pair.setValue(value));
    }

    /*****************************************
     *
     * HexImage
     *
     *****************************************/
    /**
     * Update a HexImage
     * @param hexImage The node to update
     * @param image The image to display
     */
    public static void update(HexImage hexImage, BaseNDArray image){
        Platform.runLater(() -> hexImage.setImage(image));
    }

    /*****************************************
     *
     * ListView
     *
     *****************************************/
    /**
     * Update the list view
     * Either add or remove the item
     * @param listView The list view to update
     * @param op The operation to perform
     * @param item The item to add or remove from the list view
     * @param <T> The type of the item
     */
    public static <T> void update(ListView<T> listView, EnumCst.ListViewOps op, T item){
        switch (op){
            case ADD:
                Platform.runLater(() -> listView.getItems().add(item));
                break;
            case REMOVE:
                Platform.runLater(() -> listView.getItems().remove(item));
                break;
        }
    }

    /**
     * Update the list view
     * Either add or remove the collection of items
     * @param listView The list view to update
     * @param op The operation to perform
     * @param items The collection of items to add or remove from the list view
     * @param <T> The type of the item
     */
    public static <T> void update(ListView<T> listView, EnumCst.ListViewOps op, Collection<T> items){
        switch (op){
            case ADD:
                Platform.runLater(() -> listView.getItems().addAll(items));
                break;
            case REMOVE:
                Platform.runLater(() -> listView.getItems().removeAll(items));
                break;
        }
    }

    /*****************************************
     *
     * Neural networks
     *
     *****************************************/
    /**
     * Update the neural network configuration
     * @param neuralNetwork The neural network to update
     * @param layers
     * @param nbLayers The nb of layers
     * @param learningRate The learning rate
     * @param regularisationOn True is the regularisation is activated
     * @param l1 The l1 learning rate
     * @param l2 The l2 learning rate
     */
    public static void update(NeuralNetwork neuralNetwork, HashMap<Integer, Integer> layers, int nbLayers, double learningRate, boolean regularisationOn, double l1, double l2){
        Platform.runLater(() -> neuralNetwork.updateConfiguration(nbLayers, layers, learningRate, regularisationOn, l1, l2));
    }

    /*****************************************
     *
     * Controllers
     *
     *****************************************/
    /**
     * Update the controller
     * @param controller The controller to update
     * @param op The operation to perform
     * @param info The quote info to add or remove
     */
    public static void update(StockController controller, EnumCst.ListViewOps op, QuoteInfo info){
        switch (op){
            case ADD:
                Platform.runLater(() -> controller.addQuote(info));
                break;
            case REMOVE:
                Platform.runLater(() -> controller.removeQuote(info));
                break;
        }
    }

    /**
     * Set the current price of the given quote
     * @param info The quote info to update
     * @param price The new price to display
     */
    public static void update(QuoteInfo info, double price){
        Platform.runLater(() -> info.setPrice(price));
    }

}
