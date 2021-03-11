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
 * Name: BeanObservable.java
 *
 * Description: Class defining listeners for a class with observable attributes
 *
 * Author: Charles MERINO
 *
 * Date: 04/03/2021
 *
 ******************************************************************************/
package fr.pops.beanobservable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class BeanObservable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Listeners
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
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * Add a property change listener
     * @param listener The listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        this.support.removePropertyChangeListener(listener);
    }
}
