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
 * Name: BeanModel.java
 *
 * Description: Abstract class defining the Entity pattern to setup / update / dispose
 *              the models during the bean loop
 *
 * Author: Charles MERINO
 *
 * Date: 24/02/2021
 *
 ******************************************************************************/
package fr.pops.models;

import fr.pops.beans.bean.Bean;

public abstract class BeanModel<T extends Bean> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected T bean;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    protected BeanModel(){}


    /**
     * Standard ctor
     */
    protected BeanModel(T bean){
        this.bean = bean;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Called once at the beginning of the loop
     * to initialize the models
     */
    public void setup(){}

    /**
     * Called every step to update the models
     */
    public abstract void update(double dt);

    /**
     * Called at the end of the loop
     * when it is no longer running
     */
    public void dispose(){}

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Set the bean used by the model
     * @param bean The bean used by the model
     */
    public final void setBean(T bean){
        this.bean = bean;
    }
}
