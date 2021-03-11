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
 * Name: ToolController.java
 *
 * Description: Class defining the parent controller for tool views
 *
 * Author: Charles MERINO
 *
 * Date: 10/03/2021
 *
 ******************************************************************************/
package fr.pops.controllers.toolcontrolers;

import fr.pops.views.View;

public abstract class ToolController {

    private View view;

    /*****************************************
     *
     * Ctors
     *
     *****************************************/
    /**
     * Standard ctor
     */
    protected ToolController(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param view The view to control
     */
    protected ToolController(View view){
        this.view = view;
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialization
     */
    protected void onInit(){

    }

}
