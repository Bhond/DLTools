package fr.pops.customnodes.beanproperties;

import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class BeanProperties extends VBox {

    private final Label titleLabel = new Label("Properties");
    private final Label beanNameLabel = new Label();
    private ObservableList<PropertyNode<?>> propertyNodes = FXCollections.observableArrayList();;

    /**
     * Standard ctor
     */
    public BeanProperties(String beanName){
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_CSS_DIRECTORY + "BeanProperties.css"));
        this.titleLabel.getStyleClass().add("title");
        this.beanNameLabel.getStyleClass().add("title");
        this.getStyleClass().add("root");
        this.beanNameLabel.setText(beanName);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.spacingProperty().set(10);
        this.getChildren().addAll(this.titleLabel);
    }

    public void reset(BeanProperties beanProperties){
        this.getChildren().removeAll(this.propertyNodes);
        this.propertyNodes.clear();
        this.propertyNodes.addAll(beanProperties.getPropertiesNodes());
        this.getChildren().addAll(beanProperties.getPropertiesNodes());
    }

    /**
     * @return The property nodes displayed
     */
    public ObservableList<PropertyNode<?>> getPropertiesNodes() {
        return this.propertyNodes;
    }

    public void addNodes(PropertyNode<?>... nodes){
        propertyNodes.addAll(nodes);
        //this.getChildren().addAll(propertyNodes);
    }
}
