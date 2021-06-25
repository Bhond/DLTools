package fr.pops.customnodes.beanproperties;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class StringPropertyNode extends PropertyNode<String>{

    /**
     *
     * TODO: Sent request when new value entered
     *
     */


    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public StringPropertyNode(String name, String value, boolean isComputed){
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
        this.valueNode = new TextField(this.value);
        this.valueNode.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                this.setValue(((TextField)this.valueNode).getText());
            }
        });
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
        String oldValue = this.value;
        this.value = value;
        ((TextField) this.valueNode).setText(value);
        this.support.firePropertyChange(VALUE_LISTENER_TAG, oldValue, value);
    }
}
