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
package fr.pops.beans.beanmodels;

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

        bean.setSpeed(bean.getSpeed() + .01d * dt);

    }

}
