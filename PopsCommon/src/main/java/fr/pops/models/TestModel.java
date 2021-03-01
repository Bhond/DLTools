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
 * Name: TestModel.java
 *
 * Description: Test model to implement bean -> server -> ihm communication
 *              and the beans
 *
 * Author: Charles MERINO
 *
 * Date: 28/02/2021
 *
 ******************************************************************************/
package fr.pops.models;

import fr.pops.beans.test.TestBean;

public class TestModel extends BeanModel<TestBean> {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public TestModel(){
        super();
    }

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * Update method used to update the model
     * @param dt The time step
     */
    @Override
    public void update(double dt){

        // Update position
        bean.setPositionX(bean.getSpeed() * bean.getDirectionX() * dt);
        bean.setPositionY(bean.getSpeed() * bean.getDirectionY() * dt);

        System.out.println("Test position X: " + bean.getPositionX());
        System.out.println("Test position Y: " + bean.getPositionY());

    }

}
