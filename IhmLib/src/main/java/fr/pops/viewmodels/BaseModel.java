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
 * Name: BaseModel.java
 *
 * Description: Abstract class defining the basic structure of the views' models
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.viewmodels;

import fr.pops.controllers.viewcontrollers.BaseController;
import fr.pops.cst.EnumCst;

public abstract class BaseModel<controllerT extends BaseController<?,?>> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected EnumCst.ModelSteppingFamily modelSteppingFamily = EnumCst.ModelSteppingFamily.FAMILY_1_ON_1;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    protected BaseModel(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Update the model
     * @param dt The time delay between frames
     */
    public abstract void update(double dt);

    /**
     * Dispose the model
     */
    public final void dispose(){

    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The update rate of the model
     */
    public EnumCst.ModelSteppingFamily getModelSteppingFamily() {
        return this.modelSteppingFamily;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the model stepping family
     * which controls the update rate of the model
     * using the ihm loop frequency as a reference
     * @param modelSteppingFamily The update rate of the model
     */
    public void setModelSteppingFamily(EnumCst.ModelSteppingFamily modelSteppingFamily) {
        this.modelSteppingFamily = modelSteppingFamily;
    }
}
