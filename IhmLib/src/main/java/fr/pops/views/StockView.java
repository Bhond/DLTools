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
import fr.pops.customnodes.plot.BasePlot;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StockView extends BaseView {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private BasePlot stockDataPlot;

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

        // Create plot displaying the stock data in real time
        this.stockDataPlot = new BasePlot();
        VBox.setVgrow(this.stockDataPlot, Priority.ALWAYS);
        HBox.setHgrow(this.stockDataPlot, Priority.ALWAYS);

        // Build hierarchy
        this.rootLayout.getChildren().addAll(this.stockDataPlot);
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    public void addCurrentPrice(double value){
        XYChart.Series<Number, Number> series = this.stockDataPlot.getSeries();
        Updater.update(series, series.getData().size(), value);
    }
}
