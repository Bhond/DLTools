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
 * Name: StockView.java
 *
 * Description: Class defining the stock view used to display
 *              info from stock data
 *
 * Author: Charles MERINO
 *
 * Date: 20/04/2021
 *
 ******************************************************************************/
package fr.pops.views.stock;

import fr.pops.controllers.viewcontrollers.StockController;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.plot.candlestickplot.CandleData;
import fr.pops.customnodes.plot.candlestickplot.CandlestickPlot;
import fr.pops.utils.Utils;
import fr.pops.views.base.BaseView;
import fr.pops.views.updater.Updater;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StockView extends BaseView<StockController> {

    /**
     * TODO: Make Quote info draggable with a new scene
     */

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Layouts
    private HBox topBox;
    private VBox topLeftBox;
    private HBox bottomBox;

    // Controls
    private ListView<QuoteInfo> stockDisplayedListView;
    private CandlestickPlot stockDataPlot;
    private Button addStockDataButton;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     *  Nothing to be done
     */
    private StockView(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param stage Stage of the view
     */
    public StockView(Stage stage){
        // Parent
        super(stage, StrCst.NAME_STOCK_VIEW);

        // Initialisation
        this.onInit();
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the view
     */
    @Override
    protected void onInit() {
        // Set controller
        this.controller = new StockController(this);

        // Set style sheet
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_STOCK_VIEW_CSS));

        // Configure content pane
        this.configureContentPane();
    }

    /**
     * Configure the content pane
     * Build the layout of the view
     * Draw all the components holding the info
     * displayed by the view
     */
    @Override
    protected void configureContentPane() {
        // Create first layer
        this.topBox = new HBox();
        VBox.setVgrow(this.topBox, Priority.ALWAYS);
        this.topLeftBox = new VBox();
        this.bottomBox = new HBox();
        VBox.setVgrow(this.bottomBox, Priority.ALWAYS);

        // Stock names display
        this.stockDisplayedListView = new ListView<>();
        this.stockDisplayedListView.getStyleClass().add(StrCst.STYLE_CLASS_LISTVIEW);
        this.stockDisplayedListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Stock data buttons
        this.addStockDataButton = new Button("Add");
        this.addStockDataButton.setOnAction(a -> this.controller.onAddStock(a));
        this.addStockDataButton.getStyleClass().add(StrCst.STYLE_CLASS_STANDARD_BUTTON);

        /**
         * Temp
         */
        QuoteInfo qd = new QuoteInfo();
        this.stockDisplayedListView.getItems().add(qd);
        /**
         * Temp
         */
        this.stockDisplayedListView.onDragDetectedProperty().set(event -> this.controller.onDragDetected(event, this.stockDisplayedListView));
        this.stockDisplayedListView.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!observableValue.getValue()){
                this.stockDisplayedListView.getSelectionModel().clearSelection();
            }
        });

        // Create plot displaying the stock data in real time
        this.stockDataPlot = new CandlestickPlot();
        this.stockDataPlot.onDragOverProperty().set(event -> this.controller.onDragOverChart(event, this.stockDataPlot));
        VBox.setVgrow(this.stockDataPlot, Priority.ALWAYS);
        HBox.setHgrow(this.stockDataPlot, Priority.ALWAYS);

        // Build hierarchy
        this.topLeftBox.getChildren().addAll(this.stockDisplayedListView, this.addStockDataButton);
        this.topBox.getChildren().addAll(this.topLeftBox, this.stockDataPlot);
        this.rootLayout.getChildren().add(this.topBox);
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Change current price displayed
     * @param lastAccessTime The last time the stock info were accessed
     * @param price The new price
     */
    public void addCurrentPrice(long lastAccessTime, double price){
        // Update list view
        for (QuoteInfo quoteData : this.stockDisplayedListView.getItems()) {
            if (quoteData.getSymbol().equals("GME")){
                Platform.runLater(() -> quoteData.setPrice(price)); // TODO: Add it to updater
                break;
            }
        }

        // Update candleStick
        CandleData lastCandle = this.stockDataPlot.getLastCandleData();
        SimpleDateFormat simpleDateFormat = this.stockDataPlot.getSimpleDateFormat();
        boolean sameDisplayedDate = lastCandle != null && simpleDateFormat.format(new Date(lastAccessTime)).equals(simpleDateFormat.format(lastCandle.getDateTime().getTime()));
        if (sameDisplayedDate){
            Updater.update(this.stockDataPlot, price);
        } else {
            CandleData candleData = new CandleData(new Date(lastAccessTime), price, price, price, price, 0);
            Updater.update(this.stockDataPlot, candleData);
        }
    }

    /**
     * TODO: To be corrected
     * Update the list height to avoid displaying empty cells
     */
    private void updateListHeight() {
        final double height = Math.min(stockDisplayedListView.getItems().size(), 100) * stockDisplayedListView.getFixedCellSize();
        stockDisplayedListView.setPrefHeight(height + stockDisplayedListView.getFixedCellSize());
    }

}
