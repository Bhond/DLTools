package fr.pops.customnodes.beanproperties;

import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class BeanProperties extends VBox {

    private final Label title = new Label("Settings");
    private ObservableList<PropertyNode<?>> propertiyNodes;

    /**
     * Standard ctor
     */
    public BeanProperties(){
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_CSS_DIRECTORY + "BeanProperties.css"));
        this.title.getStyleClass().add("title");
        this.getStyleClass().add("root");
        VBox.setVgrow(this, Priority.ALWAYS);
        this.getChildren().add(this.title);
    }

    /**
     * @return The property nodes displayed
     */
    public ObservableList<PropertyNode<?>> getPropertiesNodes() {
        return this.propertiyNodes;
    }
}
