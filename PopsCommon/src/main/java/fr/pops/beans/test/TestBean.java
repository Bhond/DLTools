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
 * Name: TestBean.java
 *
 * Description: Test class to implement beans properly
 *
 * Author: Charles MERINO
 *
 * Date: 18/02/2021
 *
 ******************************************************************************/
package fr.pops.beans.test;

import fr.pops.beans.bean.Bean;

public class TestBean extends Bean {

    /*****************************************
     *
     * Properties
     *
     *****************************************/
    private String name = "Toto";

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public TestBean(){
        // Nothing to be done...
    }


    /*****************************************
     *
     * Properties' getters and setters
     *
     *****************************************/
    public String getName() {
        return this.name;
    }

    public void setName(String newValue){
        String oldValue = this.name;
        this.name = newValue;
        this.support.firePropertyChange("name", oldValue, newValue);
    }

}
