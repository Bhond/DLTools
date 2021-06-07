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
 * Name: MainController.java
 *
 * Description: Abstract class describing the basic parameters of the view controllers
 *
 * Author: Charles MERINO
 *
 * Date: 20/04/2021
 *
 ******************************************************************************/
package fr.pops.controllers.viewcontrollers;

import fr.pops.controllers.controllermanager.ControllerManager;
import fr.pops.viewmodels.BaseModel;
import fr.pops.views.base.BaseView;

public abstract class BaseController<viewT extends BaseView, modelT extends BaseModel<?>> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected viewT view;
    protected modelT model;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    protected BaseController(){
        ControllerManager.getInstance().addController(this);
    }

    /**
     * Ctor
     * @param view The view controlled by this controller
     * @param model The model to update
     */
    protected BaseController(viewT view, modelT model){
        // Initialize the controller
        this.onInit(view, model);
    }

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Initiliaze the controller
     * @param view The view controlled by this controller
     * @param model The model to update
     */
    protected void onInit(viewT view, modelT model){
        ControllerManager.getInstance().addController(this);
        this.view = view;
        this.model = model;
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * @return The view controlled by this controller
     */
    public viewT getView() {
        return this.view;
    }

    /**
     * @return The model updating this controller's view
     */
    public modelT getModel() {
        return this.model;
    }

}
