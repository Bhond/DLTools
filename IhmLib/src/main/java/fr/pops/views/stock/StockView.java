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
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockView extends BaseView<StockController> {

    /**
     * TODO: Make Quote info draggable with a new scene
     *       Delete in the controller the QuoteInfo being removed when no update request is expected
     */

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Layouts
    private HBox topBox;
    private VBox topLeftBox;

    // Controls
    // Quote info list
    private ListView<QuoteInfo> stockDisplayedListView;

    // Quote data chart
    private TabPane chartsTabPane;

    // New quote
    private HBox stockManagingBox;
    private TextField addStockDataTextField;
    private Button addStockDataButton;
    private Button removeStockDataButton;

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
        super(stage, StrCst.NAME_STOCK_VIEW, EnumCst.Views.STOCK);

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
        this.topBox.setSpacing(10);

        // Configure the components managing the quote infos
        this.configureQuoteInfoDisplay();

        // Configure the tab pane that holds all the charts
        this.configureChartTabPane();

        // Hierarchy
        this.buildHierarchy();
    }

    /**
     * Build the hierarchy of the view
     */
    @Override
    protected void buildHierarchy() {

        // Stock managing box
        this.stockManagingBox.getChildren().addAll(this.addStockDataTextField, this.addStockDataButton, this.removeStockDataButton);

        // Content
        this.topLeftBox.getChildren().addAll(this.stockDisplayedListView, this.stockManagingBox);
        this.topBox.getChildren().addAll(this.topLeftBox, this.chartsTabPane);

        // Root
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
            boolean removingQuote = this.removeStockDataButton.focusedProperty().get();
            if (!observableValue.getValue() && !removingQuote){
                this.stockDisplayedListView.getSelectionModel().clearSelection();
            }
        });

        // Add stock box
        this.stockManagingBox = new HBox();
        this.stockManagingBox.setSpacing(5);
        // Stock data text field
        this.addStockDataTextField = new TextField(StrCst.ADD_QUOTE_TEXT_FIELD_DEFAULT);
        this.addStockDataTextField.getStyleClass().add(StrCst.STYLE_CLASS_TEXT_FIELD);
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

        // Remove Stock data button
        this.removeStockDataButton = new Button(StrCst.REMOVE_QUOTE_BUTTON_LABEL);
        this.removeStockDataButton.setOnAction(a -> {
            this.controller.onRemoveQuoteInfo(this.stockDisplayedListView, this.chartsTabPane);
            this.stockDisplayedListView.getSelectionModel().clearSelection();
        });
        this.removeStockDataButton.getStyleClass().add(StrCst.STYLE_CLASS_STANDARD_BUTTON);

    }

    /**
     * Configure the tab pane holding all the displayed candlestick charts
     */
    private void configureChartTabPane(){
        this.chartsTabPane = new TabPane();
        this.chartsTabPane.setSide(Side.BOTTOM);
        this.chartsTabPane.onDragOverProperty().set(event -> this.controller.onDragOverChartTabPane(event));
        this.chartsTabPane.onDragDroppedProperty().set(event -> this.controller.onDragDroppedChartTabPane(event));
        VBox.setVgrow(this.chartsTabPane, Priority.ALWAYS);
        HBox.setHgrow(this.chartsTabPane, Priority.ALWAYS);
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Add the quote info to its list view
     * @param info The info to add
     */
    private void addQuoteInfo(QuoteInfo info){
        this.controller.addQuote(info);
    }

    /**
     * Add candlestick chart for the stock data
     * If symbol is already present, it is selected
     * @param symbol The symbol of the quote data to display
     */
    public void addCandlestickChart(String symbol){
        if (this.controller.getQuoteInfo(symbol).isPlotted()){
            // Select the tab that displays the given symbol
            for (Tab tab : this.chartsTabPane.getTabs()){
                if (tab.getText().equals(symbol)){
                    this.chartsTabPane.getSelectionModel().select(tab);
                    break;
                }
            }
        } else {
            // Create new tab
            Tab tab = new Tab(symbol);
            HBox hBox = new HBox();
            VBox vBox = new VBox();
            HBox.setHgrow(vBox, Priority.ALWAYS);
            VBox.setVgrow(hBox, Priority.ALWAYS);

            // Create plot displaying the stock data in real time
            QuoteInfo info = this.controller.getQuoteInfo(symbol);
            if (info != null){
                tab.onClosedProperty().set((event) -> {
                    this.controller.removeDisplayedChart(info);
                    tab.contentProperty().set(null);
                    info.setPlotted(false);
                });
                CandlestickPlot plot = new CandlestickPlot();
                plot.onDragOverProperty().set(event -> this.controller.onDragOverChartTabPane(event));
                plot.onDragDroppedProperty().set(event -> this.controller.onDragDroppedChartTabPane(event));
                VBox.setVgrow(plot, Priority.ALWAYS);
                HBox.setHgrow(plot, Priority.ALWAYS);
                tab.contentProperty().set(plot);
                this.chartsTabPane.getTabs().add(tab);
                this.controller.addDisplayedChart(info, plot);
                this.controller.getQuoteInfo(symbol).setPlotted(true);
                this.chartsTabPane.getSelectionModel().select(tab);
            }
        }
    }

    /**
     * Update stock info from received request
     * If it is new, it is created
     * @param symbol The symbol of the stock to update
     * @param lastAccessTime The time the data was last accessed
     * @param price The current price of the stock
     */
    public void updateStockInfo(String symbol, long lastAccessTime, double price){
        // Exit if the quote is being removed
        if (this.controller.isQuoteInfoRemoved(symbol)) return;
        // Retrieve existing QuoteInfo in the list view
        QuoteInfo info = this.controller.getQuoteInfo(symbol);
        if (info == null){
            if (!symbol.equals(StrCst.INVALID_QUOTE)){
                info = new QuoteInfo(symbol, price, lastAccessTime);
                Updater.update(this.controller, EnumCst.ListViewOps.ADD, info);
            }
        } else {
            Updater.update(info, price);
        }


        // Update candleStick if necessary
        if (info.isPlotted()){
            CandlestickPlot plot = this.controller.getDisplayedChart(info);
            if (plot != null){
                CandleData lastCandle = plot.getLastCandleData();
                SimpleDateFormat simpleDateFormat = plot.getSimpleDateFormat();
                boolean sameDisplayedDate = lastCandle != null && simpleDateFormat.format(new Date(lastAccessTime))
                                                                                  .equals(simpleDateFormat.format(lastCandle.getDateTime().getTime()));
                if (sameDisplayedDate){
                    Updater.update(plot, price);
                } else {
                    CandleData candleData = new CandleData(new Date(lastAccessTime), price, price, price, price, 0);
                    Updater.update(plot, candleData);
                }
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

    /*****************************************
     *
     * Load / Save
     *
     *****************************************/
    /**
     * Map the view to json format
     *
     * @return The mapping between object fields and json fields
     */
    @Override
    public Map<String, Object> viewToJsonMap() {
        // Initialization
        Map<String, Object> brace = new HashMap<>();

        // Store quote info displayed
        Map<String, Object> infos = new HashMap<>();
        for (QuoteInfo info : this.stockDisplayedListView.getItems()){
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put(StrCst.JSON_KEY_PRICE, info.getPrice());
            infoMap.put(StrCst.JSON_KEY_LAST_ACCESSED_TIME, info.getLastAccessedTime());
            infos.put(info.getSymbol(), infoMap);
        }
        brace.put(StrCst.JSON_KEY_QUOTE_INFOS, infos);

        return brace;
    }

    /**
     * Cast the json object stored in the fields
     * to a view
     */
    @Override
    public void jsonToView(Map<String, Object> map) {
        super.jsonToView(map);
    }

    /**
     * Read the fields stored in the json
     */
    @Override
    protected void readFields(Map<String, Object> fields) {
        // Loop over the fields
        for (String field : fields.keySet()){
            // Build the quote infos
            if (field.equals(StrCst.JSON_KEY_QUOTE_INFOS)){
                Map<String, Object> infos = (Map<String, Object>) fields.get(field);
                this.buildQuoteInfos(infos);
            }
        }
    }

    /**
     * Create quote infos from saved ones
     * The keys are the symbols of the saved quotes
     * @param infos The infos to create
     */
    private void buildQuoteInfos(Map<String, Object> infos){
        // Loop over all the symbols
        for (String symbol : infos.keySet()){
            double price = 0;
            long lastAccessedTime = 0L;
            Map<String, Object> data = (Map<String, Object>) infos.get(symbol);
            JSONObject o = new JSONObject(data);
            // Loop over the data
            price = o.getDouble(StrCst.JSON_KEY_PRICE);
            lastAccessedTime = o.getLong(StrCst.JSON_KEY_LAST_ACCESSED_TIME);
            // Build info from read fields
            QuoteInfo info = new QuoteInfo(symbol, price, lastAccessedTime);
            this.addQuoteInfo(info);
        }
    }

}
