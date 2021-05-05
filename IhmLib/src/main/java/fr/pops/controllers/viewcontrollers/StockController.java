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
 * Name: StockController.java
 *
 * Description: Class describing the parameters of the stock controller
 *
 * Author: Charles MERINO
 *
 * Date: 20/04/2021
 *
 ******************************************************************************/
package fr.pops.controllers.viewcontrollers;

import fr.pops.client.Client;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.plot.candlestickplot.CandlestickPlot;
import fr.pops.sockets.resquest.GetCurrentStockInfoRequest;
import fr.pops.viewmodels.StockModel;
import fr.pops.views.stock.QuoteInfo;
import fr.pops.views.stock.StockView;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;

import java.util.HashMap;

public class StockController extends BaseController<StockView, StockModel>{

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private HashMap<QuoteInfo, CandlestickPlot> displayedCharts;
    private ObservableValue<ObservableList<QuoteInfo>> displayedQuotes;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private StockController(){
        this.onInit();
    }

    /**
     * Ctor
     * @param view The view to control
     */
    public StockController(StockView view){
        // Parent
        super(view, new StockModel());

        // Initialization
        this.onInit();
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the controller
     */
    private void onInit(){
        // Initialize the displayed chars map
        this.displayedCharts  = new HashMap<>();
    }

    /*****************************************
     *
     * Actions
     *
     *****************************************/
    /**
     * Add stock data to the screen
     */
    public void onAddQuoteInfo(TextField addStockTextField){
        String inputSymbol = addStockTextField.textProperty().get();
        if (!inputSymbol.isEmpty() && !inputSymbol.equals(StrCst.ADD_QUOTE_TEXT_FIELD_DEFAULT)){
            Client.getInstance().send(new GetCurrentStockInfoRequest(inputSymbol));
        }
    }

    /**
     * Remove stock data from the screen
     */
    public void onAddQuoteInfo(){
        System.out.println("Not implemented yet");
    }

    /**
     * Detect drag event
     * @param mouseEvent Mouse event triggering the method
     * @param view The list view from which the item is dragged
     */
    public void onDragDetected(MouseEvent mouseEvent, ListView<QuoteInfo> view){
        QuoteInfo info = view.getSelectionModel().getSelectedItem();
        System.out.println("Dragging: " + info.getSymbol());
        this.onQuoteInfoDragged(mouseEvent, info);
    }

    /**
     * Detect drag event
     * @param mouseEvent Mouse event triggering the method²
     */
    public void onQuoteInfoDragged(MouseEvent mouseEvent, QuoteInfo info){
        Dragboard db = info.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.put(DataFormat.PLAIN_TEXT, info.getSymbol());
        db.setContent(cb);
        mouseEvent.consume();
    }

    /**
     * Drag quote info towards the chart
     * @param dragEvent Drag event triggering the method
     */
    public void onDragOverChartTabPane(DragEvent dragEvent){
        if (dragEvent.getDragboard().hasString()){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    /**
     * Drag dropped quote info towards the chart
     * @param dragEvent Drag dropped event triggering the method
     */
    public void onDragDroppedChartTabPane(DragEvent dragEvent){
        boolean success = false;
        if (dragEvent.getDragboard().hasString()){
            this.view.addCandlestickChart(dragEvent.getDragboard().getString());
            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @param symbol The symbol of the quote info to retrieve
     * @return The Quote info corresponding to the input symbol
     */
    public QuoteInfo getQuoteInfo(String symbol){
        QuoteInfo result = null;
        for (QuoteInfo quoteInfo : this.displayedQuotes.getValue()){
            if (quoteInfo.getSymbol().equals(symbol)){
                result = quoteInfo;
                break;
            }
        }
        return result;
    }

    /**
     * Retrieve the candlestick plot associated to the input quote info
     * @param info The info of the candlestick plot to retrieve
     * @return The candlestick plot corresponding to the given input quote info
     */
    public CandlestickPlot getDisplayedChart(QuoteInfo info) {
        return this.displayedCharts.getOrDefault(info, null);
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Add current stock price to the chart displaying it
     */
    public void addCurrentStockPrice(String symbol, long lastAccessTime, double value){
        this.view.updateStockInfo(symbol, lastAccessTime, value);
    }

    /**
     * Add quote to the quotes' list
     * @param info The info to add
     */
    public void addQuote(QuoteInfo info) {
        this.displayedQuotes.getValue().add(info);
        this.model.addQuoteInfo(info);
    }

    /**
     * Remove quote from the quotes' list
     * @param info The info to remove
     */
    public void removeQuote(QuoteInfo info){
        this.displayedQuotes.getValue().remove(info);
        this.model.removeQuoteInfo(info);
    }

    /**
     * Set the displayed quotes list so that
     * when this one is modified, the list view is aware of the modification
     * @param displayedQuotes The list view items
     */
    public void setDisplayedQuotes(ObservableValue<ObservableList<QuoteInfo>> displayedQuotes) {
        this.displayedQuotes = displayedQuotes;
    }

    /**
     * Add the input pair to the corresponding list
     * @param info The info as the key
     * @param plot The candlestick chart as the value
     */
    public void addDisplayedChart(QuoteInfo info, CandlestickPlot plot){
        this.displayedCharts.put(info, plot);
    }

    /**
     * Remove the input pair to the corresponding list
     * @param info The info as the key to remove
     */
    public void removeDisplayedChart(QuoteInfo info){
        this.displayedCharts.remove(info);
    }
}
