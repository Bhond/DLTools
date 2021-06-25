package fr.pops.beans.properties;

import fr.pops.commoncst.EnumCst;

public class BooleanProperty extends Property<Boolean> {

    /**
     * Standard ctor
     * @param name       The name of the property
     * @param type       The type of the property
     * @param value      The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    public BooleanProperty(String name, EnumCst.PropertyTypes type, Boolean value, boolean isComputed, boolean isInternal) {
        super(name, type, value, isComputed, isInternal);
    }
}
