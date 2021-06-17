package fr.pops.customnodes.beanproperties;

import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BeanProperties extends VBox {

    private ObservableList<PropertyNode> propertiyNodes;

    /**
     * Standard ctor
     */
    public BeanProperties(){
        this.setStyle("-fx-background-color:red;");
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    /**
     * @return The property nodes displayed
     */
    public ObservableList<PropertyNode> getPropertiyNodes() {
        return this.propertiyNodes;
    }
}
