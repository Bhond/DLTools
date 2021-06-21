package fr.pops.customnodes.beanproperties;

import javafx.scene.control.TextField;

public class StringPropertyNode extends PropertyNode<String>{

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public StringPropertyNode(String name, String defaultValue, boolean isComputed){
        super(name, defaultValue, isComputed);
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the value node
     */
    @Override
    protected void onInit() {
        super.onInit();
        this.valueNode = new TextField(this.defaultValue);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The displayed value of the property
     */
    @Override
    public String getValue() {
        return ((TextField) this.valueNode).getText();
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Sets the property's value
     * @param value The new property's value
     */
    @Override
    public void setValue(String value) {
        ((TextField) this.valueNode).setText(value);
    }
}
