package fr.pops.customnodes.beanproperties;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DoublePropertyNode extends PropertyNode<Double>{

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public DoublePropertyNode(String name, double defaultValue, boolean isComputed){
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
        this.valueNode = new TextField(this.defaultValue.toString());
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
    public Double getValue() {
        return Double.parseDouble(((Label) this.valueNode).getText());
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
    public void setValue(Double value) {
        ((TextField)this.valueNode).setText(String.valueOf(value));
    }
}
