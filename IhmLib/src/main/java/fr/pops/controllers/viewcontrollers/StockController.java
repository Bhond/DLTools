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

import fr.pops.customnodes.plot.candlestickplot.CandlestickPlot;
import fr.pops.viewmodels.StockModel;
import fr.pops.views.stock.QuoteInfo;
import fr.pops.views.stock.StockView;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.input.*;


public class StockController extends BaseController<StockView, StockModel>{

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
        // Nothing to be done
    }

    /**
     * Ctor
     * @param view The view to control
     */
    public StockController(StockView view){
        // Parent
        super(view, new StockModel());
    }

    /*****************************************
     *
     * Actions
     *
     *****************************************/
    /**
     * Add stock data to the screen
     * @param actionEvent Action event triggering the method
     */
    public void onAddStock(ActionEvent actionEvent){
        System.out.println("Adding stock");
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
     * @param candlestickPlot The candlestick plot to display the quote data on
     */
    public void onDragOverChart(DragEvent dragEvent, CandlestickPlot candlestickPlot){
        if (dragEvent.getDragboard().hasString()){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Add current stock price to the chart displaying it
     */
    public void addCurrentStockPrice(long lastAccessTime, double value){
        this.view.addCurrentPrice(lastAccessTime, value);
    }

}
