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

import fr.pops.viewmodels.StockModel;
import fr.pops.views.StockView;
import javafx.event.ActionEvent;

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
     * @param actionEvent Action event triggering the event
     */
    public void onAddStock(ActionEvent actionEvent){
        System.out.println("Adding stock");
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Add current stock price to the chart displaying it
     */
    public void addCurrentStockPrice(double value){
        this.view.addCurrentPrice(value);
    }

}
