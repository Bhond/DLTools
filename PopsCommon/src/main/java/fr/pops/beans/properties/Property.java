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
 * Name: Property.java
 *
 * Description: Abstract class defining Properties which is a fancy "Attribute"
 *              with more info than a standard one such as a name, a type, a value
 *              Fires listeners when the value is modified
 *
 * Author: Charles MERINO
 *
 * Date: 18/02/2021
 *
 ******************************************************************************/
package fr.pops.beans.properties;

import fr.pops.beans.bean.BeanManager;
import fr.pops.beans.beanobservable.BeanObservable;
import fr.pops.commoncst.EnumCst;
import fr.pops.commoncst.GeneratorCst;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;

public abstract class Property<T> extends BeanObservable implements Serializable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Info
    private int beanId;
    private String name;
    private EnumCst.PropertyTypes type;
    private T value;
    private boolean isComputed;
    private boolean isInternal;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Private
     * @param beanId     The id of the bean using this property
     * @param name       The name of the property
     * @param type       The type of the property
     * @param value      The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    protected Property(int beanId, String name, EnumCst.PropertyTypes type, T value, boolean isComputed, boolean isInternal){
        // Initialisation
        onInit(beanId, name, type, value, isComputed, isInternal);
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialisation
     * @param name The name of the property
     * @param type The type of the property
     * @param value The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    private void onInit(int beanId, String name, EnumCst.PropertyTypes type, T value, boolean isComputed, boolean isInternal){
        // Attributes
        this.beanId = beanId;
        this.name= name;
        this.type = type;
        this.value = value;
        this.isComputed = isComputed;
        this.isInternal = isInternal;

        // Listeners
        this.support.addPropertyChangeListener(this::onListenersChanged);
    }

    /*****************************************
     *
     * Listeners
     *
     *****************************************/
    /**
     * Handles the changes fired by the listeners.
     * One can only change the value of the property,
     * neither the name nor the type.
     * @param event The event
     */
    private void onListenersChanged(PropertyChangeEvent event){
        // Observe changes on the value
        if (event.getPropertyName().equals(GeneratorCst.PROPERTY_VALUE)){
            BeanManager.getInstance().onPropertyUpdate(this);
        }
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The id of the bean using this property
     */
    public int getBeanId() {
        return this.beanId;
    }

    /**
     * @return The name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The type of the property
     */
    public EnumCst.PropertyTypes getType() {
        return this.type;
    }

    /**
     * @return The value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * @return True if the property is computed and not modifiable by the user
     */
    public boolean isComputed() {
        return this.isComputed;
    }

    /**
     * @return True if the property is only used by the models
     */
    public boolean isInternal() {
        return isInternal;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the value to the given input
     * Fires a property changed listener
     * @param newValue The new value
     */
    public void setValue(T newValue) {
        Object oldValue = this.value;
        this.value = newValue;
        this.support.firePropertyChange(GeneratorCst.PROPERTY_VALUE, oldValue, newValue);
    }
}
