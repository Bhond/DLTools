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
import fr.pops.cst.EnumCst;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.plot.candlestickplot.CandleData;
import fr.pops.customnodes.plot.candlestickplot.CandlestickPlot;
import fr.pops.utils.Utils;
import fr.pops.views.base.BaseView;
import fr.pops.views.updater.Updater;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
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
    // Quote info list
    private ListView<QuoteInfo> stockDisplayedListView;

    // Quote data chart
    private CandlestickPlot stockDataPlot;

    // New quote
    private HBox addStockBox;
    private TextField addStockDataTextField;
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

        // Configure the components managing the quote infos
        this.configureQuoteInfoDisplay();

        // Create plot displaying the stock data in real time
        this.stockDataPlot = new CandlestickPlot();
        this.stockDataPlot.onDragOverProperty().set(event -> this.controller.onDragOverChart(event, this.stockDataPlot));
        VBox.setVgrow(this.stockDataPlot, Priority.ALWAYS);
        HBox.setHgrow(this.stockDataPlot, Priority.ALWAYS);

        // Build hierarchy
        this.topLeftBox.getChildren().addAll(this.stockDisplayedListView, this.addStockBox);
        this.topBox.getChildren().addAll(this.topLeftBox, this.stockDataPlot);
        this.rootLayout.getChildren().add(this.topBox);
    }

    /**
     * Configure:
     *  -   The components holding the stock infos
     *  -   The components managing the quote infos
     */
    private void configureQuoteInfoDisplay() {
        // Quote info list view
        this.stockDisplayedListView = new ListView<>();
        this.stockDisplayedListView.getStyleClass().add(StrCst.STYLE_CLASS_LISTVIEW);
        this.stockDisplayedListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.stockDisplayedListView.onDragDetectedProperty().set(event -> this.controller.onDragDetected(event, this.stockDisplayedListView));
        this.controller.setDisplayedQuotes(this.stockDisplayedListView.itemsProperty());
        this.stockDisplayedListView.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!observableValue.getValue()){
                this.stockDisplayedListView.getSelectionModel().clearSelection();
            }
        });

        // Add stock box
        this.addStockBox = new HBox();
        // Stock data text field
        this.addStockDataTextField = new TextField(StrCst.ADD_QUOTE_TEXT_FIELD_DEFAULT);
        this.addStockDataTextField.onMouseClickedProperty().set((event) -> {
            if (this.addStockDataTextField.textProperty().get().equals(StrCst.ADD_QUOTE_TEXT_FIELD_DEFAULT)){
                this.addStockDataTextField.clear();
            }
        });
        this.addStockDataTextField.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!observableValue.getValue()){
                if (this.addStockDataTextField.textProperty().get().isEmpty()){
                    this.addStockDataTextField.textProperty().setValue(StrCst.ADD_QUOTE_TEXT_FIELD_DEFAULT);
                }
            }
        });
        // Stock data button
        this.addStockDataButton = new Button(StrCst.ADD_QUOTE_BUTTON_LABEL);
        this.addStockDataButton.setOnAction(a -> {
            this.controller.onAddQuoteInfo(this.addStockDataTextField);
            this.addStockDataTextField.textProperty().setValue(StrCst.ADD_QUOTE_TEXT_FIELD_DEFAULT);
        });
        this.addStockDataButton.getStyleClass().add(StrCst.STYLE_CLASS_STANDARD_BUTTON);
        this.addStockBox.getChildren().addAll(this.addStockDataTextField, this.addStockDataButton);
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    public void updateStockInfo(String symbol, long lastAccessTime, double price){
        // Retrieve existing QuoteInfo in the list view
        QuoteInfo info = this.controller.getQuoteInfo(symbol);
        if (info == null){
            if (!symbol.equals("invalid")){
                Updater.update(this.controller, EnumCst.ListViewOps.ADD, new QuoteInfo(symbol, price, lastAccessTime));
            }
        } else {
            Updater.update(info, price);
        }


        // Update candleStick if necessary
        if (info.isPlotted()){
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
