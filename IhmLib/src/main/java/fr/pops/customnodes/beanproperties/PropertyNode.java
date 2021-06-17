package fr.pops.customnodes.beanproperties;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public abstract class PropertyNode<T> extends HBox {

    protected Label nameLbl;
    protected Node valueNode;

    /**
     * Standard ctor
     * Nothing to be done
     */
    protected PropertyNode(){
    }

    protected abstract void onInit();

    /**
     * @return The property's name
     */
    public String getName() {
        return this.nameLbl.getText();
    }

    /**
     * @return The displayed value of the property
     */
    public abstract T getValue();

}
