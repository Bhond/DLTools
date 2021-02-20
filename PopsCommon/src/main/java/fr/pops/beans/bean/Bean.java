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
 * Name: Bean.java
 *
 * Description: Abstract class defining Beans which will be serialized and shared
 *              amongst clients
 *
 * Author: Charles MERINO
 *
 * Date: 18/02/2021
 *
 ******************************************************************************/
package fr.pops.beans.bean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public abstract class Bean implements Serializable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /*****************************************
     *
     * Listeners
     *
     *****************************************/
    /**
     * Add a property change listener
     * @param listener The listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    /**
     * Add a property change listener
     * @param listener The listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }

}
