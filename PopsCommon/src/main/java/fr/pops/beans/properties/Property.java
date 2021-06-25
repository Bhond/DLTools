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
    private int id;
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
     * @param name The name of the property
     * @param type The type of the property
     * @param value The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    protected Property(String name, EnumCst.PropertyTypes type, T value, boolean isComputed, boolean isInternal){
        // Initialisation
        onInit(name, type, value, isComputed, isInternal);
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
    private void onInit(String name, EnumCst.PropertyTypes type, T value, boolean isComputed, boolean isInternal){
        // Attributes
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
            System.out.println("New value: " + event.getNewValue() + " for: " + this.name);
        }
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
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

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class PropertyBuilder<T> {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        private String name = "";
        private EnumCst.PropertyTypes type = EnumCst.PropertyTypes.OBJECT;
        private T value = null;
        private boolean isComputed = false;
        private boolean isInternal = false;

        /*****************************************
         *
         * Ctor
         *
         *****************************************/
        /**
         * Standard ctor
         * Nothing to be done
         */
        public PropertyBuilder(){
            // Nothing to be done
        }

        /*****************************************
         *
         * With methods
         *
         *****************************************/
        /**
         * Name of the property
         * @param name The name of the property
         * @return The builder itself
         */
        public PropertyBuilder<T> withName(String name){
            this.name = name;
            return this;
        }

        /**
         * Type of the property
         * @param type The type of the property
         * @return The builder itself
         */
        public PropertyBuilder<T> withType(String type){
            this.type = EnumCst.PropertyTypes.valueOf(type.toUpperCase());
            return this;
        }

        /**
         *
         * @param defaultValue The default value of the property
         * @return The builder itself
         */
        public PropertyBuilder<T> withDefaultValue(T defaultValue){
            this.value = defaultValue;
            return this;
        }

        /**
         *
         * Set the parameter isComputed of the property
         * @param isComputed True if this property is only available for the model
         * @return The builder itself
         */
        public PropertyBuilder<T> isComputed(boolean isComputed){
            this.isComputed = isComputed;
            return this;
        }

        /**
         * Set the parameter isInternal of the property
         * @param isInternal True if this property is only available for the model
         * @return The builder itself
         */
        public PropertyBuilder<T> isInternal(boolean isInternal){
            this.isInternal = isInternal;
            return this;
        }

        /**
         * Build the property
         * @return A property object built with the parameters
         *         defined by the with methods
         */
        public Property<T> build() {
            //return new Property(this.name, this.type, this.value, this.isComputed, this.isInternal);
            return null;
        }

    }

}
