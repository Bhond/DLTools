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
 * Name: PlotController.java
 *
 * Description: Class defining the controller for plot views
 *
 * Author: Charles MERINO
 *
 * Date: 10/03/2021
 *
 ******************************************************************************/
package fr.pops.controllers.viewcontrollers;

import fr.pops.viewmodels.ServerInfoModel;
import fr.pops.views.PlotView;

import java.util.List;

public class PlotController extends BaseController<PlotView, ServerInfoModel>{

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Data
    private List<Object> data;

    // TODO: move some of this in the view class

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    private PlotController(){
        // Call parent
        super();
    }

    /**
     * Ctor
     * @param plotView The view to control
     */
    public PlotController(PlotView plotView){
        // Call parent
        super(plotView);

        // Initialization
        this.onInit(null);
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the controller
     */
    @Override
    protected void onInit(ServerInfoModel model) {// TODO: Change the model
        // Parent
        super.onInit(model);

        // Initialize axis
        this.buildAxis();
    }

    /*****************************************
     *
     * Main pane building
     *
     *****************************************/
    /**
     * Build the axis
     */
    private void buildAxis(){
    }

    /**
     * Compute the x and y origins
     */
    private void computeOrigins(){

    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the data to plot
     * @param data The data to plot
     */
    private void setData(List<Object> data){
        this.data = data;
    }

}
