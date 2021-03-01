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
package fr.pops.beans.bean;

import fr.pops.cst.EnumCst;
import fr.pops.cst.GeneratorDefaultValues;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Property  implements Serializable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Listeners
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // Info
    private int id;
    private String name;
    private EnumCst.PropertyTypes type;
    private Object value;

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
     */
    private Property(String name, EnumCst.PropertyTypes type, Object value){
        this.name= name;
        this.type = type;
        this.value = value;
    }

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

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The value
     */
    public Object getValue() {
        return this.value;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the value to the given input
     * Fires a property changed listener
     * @param value The new value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class PropertyBuilder {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        private String name = GeneratorDefaultValues.NAME_DEFAULT;
        private EnumCst.PropertyTypes type = GeneratorDefaultValues.TYPE_DEFAULT;
        private Object value = GeneratorDefaultValues.VALUE_DEFAULT;

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
        public PropertyBuilder withName(String name){
            this.name = name;
            return this;
        }

        /**
         *
         * @param type The type of the property
         * @return The builder itself
         */
        public PropertyBuilder withType(String type){
            this.type = EnumCst.PropertyTypes.valueOf(type.toUpperCase());
            return this;
        }

        /**
         *
         * @param defaultValue The default value of the property
         * @return The builder itself
         */
        public PropertyBuilder withDefaultValue(Object defaultValue){
            this.value = defaultValue;
            return this;
        }

        /**
         * Build the property
         * @return A property object built with the parameters
         *         defined by the with methods
         */
        public Property build() {
            return new Property(this.name, this.type, this.value);
        }

    }

}
