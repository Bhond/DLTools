package fr.pops.beans.properties;

import fr.pops.commoncst.EnumCst;

public class StringProperty extends Property<String> {

    /**
     * Standard ctor
     * @param beanId     The id of the bean using this property
     * @param name       The name of the property
     * @param type       The type of the property
     * @param value      The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    public StringProperty(int beanId, String name, EnumCst.PropertyTypes type, String value, boolean isComputed, boolean isInternal) {
        super(beanId, name, type, value, isComputed, isInternal);
    }
}
