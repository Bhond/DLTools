package fr.pops.customnodes.beanproperties;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class IntegerPropertyNode extends PropertyNode<Integer>{

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public IntegerPropertyNode(String name, int defaultValue, boolean isComputed){
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
        this.valueNode = new TextField(this.value.toString());
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
    public Integer getValue() {
        return Integer.parseInt(((Label) this.valueNode).getText());
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
    public void setValue(Integer value) {
        ((TextField) this.valueNode).setText(String.valueOf(value));
    }
}
