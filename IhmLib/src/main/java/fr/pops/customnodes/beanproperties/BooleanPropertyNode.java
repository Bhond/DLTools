package fr.pops.customnodes.beanproperties;

import javafx.scene.control.CheckBox;

public class BooleanPropertyNode extends PropertyNode<Boolean> {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public BooleanPropertyNode(String name, boolean value, boolean isComputed){
        super(name, value, isComputed);
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
        this.valueNode = new CheckBox();
        ((CheckBox)this.valueNode).setSelected(this.value);
        ((CheckBox)this.valueNode).setAllowIndeterminate(false);
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
    public Boolean getValue() {
        return this.valueNode.isPressed();
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
    public void setValue(Boolean value) {
        ((CheckBox)this.valueNode).setSelected(value);
    }
}
