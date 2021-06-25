package fr.pops.beans.properties;

import fr.pops.commoncst.EnumCst;

public class StringProperty extends Property<String> {

    /**
     * Standard ctor
     * @param name       The name of the property
     * @param type       The type of the property
     * @param value      The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    public StringProperty(String name, EnumCst.PropertyTypes type, String value, boolean isComputed, boolean isInternal) {
        super(name, type, value, isComputed, isInternal);
    }
}
