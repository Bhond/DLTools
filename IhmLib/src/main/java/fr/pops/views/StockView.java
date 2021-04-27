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
package fr.pops.views;

import fr.pops.controllers.viewcontrollers.StockController;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.plot.candlestickplot.CandleData;
import fr.pops.customnodes.plot.candlestickplot.CandlestickPlot;
import fr.pops.utils.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.GregorianCalendar;

public class StockView extends BaseView<StockController> {

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
    private ListView<String> stockDisplayedListView;
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
        this.stockDisplayedListView.getItems().addAll("GME", "TSLA");
        /**
         * Temp
         */

        // Create plot displaying the stock data in real time
        this.stockDataPlot = new CandlestickPlot();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        CandleData bar0 = new CandleData(gregorianCalendar, 5,5,5,5,0);
        this.stockDataPlot.addCandle(bar0);
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
     * @param currentPrice The new price
     */
    public void addCurrentPrice(double currentPrice){
        Updater.update(this.stockDataPlot, currentPrice);
    }

    /**
     * Change current price displayed
     * @param value The new price
     */
    public void addNewCandle(GregorianCalendar calendar, double openingPrice){
        CandleData candleData = new CandleData(calendar, openingPrice, openingPrice, openingPrice, openingPrice, 0);
        Updater.update(this.stockDataPlot, candleData);
    }
}
