package fr.pops.customnodes.beanproperties;

import fr.pops.beans.bean.Bean;
import fr.pops.beans.properties.Property;
import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class BeanProperties extends VBox {

    private FileChooser chooser;
    private final Label titleLabel = new Label("Properties");
    private final Label beanNameLabel = new Label();
    protected ObservableList<Node> extraNodes = FXCollections.observableArrayList();
    protected ObservableList<PropertyNode<?>> propertyNodes = FXCollections.observableArrayList();

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
        this.addExtraNodes(this.titleLabel, this.beanNameLabel);
    }

    /**
     * Reset the bean properties
     * Used with the one displaying the selected component
     * @param beanProperties
     */
    public void reset(BeanProperties beanProperties){
        this.getChildren().removeAll(this.extraNodes);
        this.getChildren().removeAll(this.propertyNodes);
        this.propertyNodes.clear();
        this.extraNodes.clear();
        this.extraNodes.addAll(beanProperties.getExtraNodes());
        this.getChildren().addAll(beanProperties.getExtraNodes());
        this.propertyNodes.addAll(beanProperties.getPropertiesNodes());
        this.getChildren().addAll(beanProperties.getPropertiesNodes());
    }

    /**
     * Add extra node
     * @param nodes
     */
    public void addExtraNodes(Node... nodes){
        this.extraNodes.addAll(nodes);
        this.getChildren().addAll(nodes);
    }

    /**
     * Build the property nodes
     * @param bean The bean used to create the properties
     */
    public <T extends Bean> void build(T bean){
        // Loop over the property
        // TODO: Try doing this without the "type" parameter
        for (Property<?> property : bean.getProperties()){
            if (property.isInternal()) continue;
            switch (property.getType()){
                case DOUBLE:
                    this.propertyNodes.add(new DoublePropertyNode(property.getName(), (double) property.getValue(), property.isComputed()));
                    break;
                case BOOLEAN:
                    this.propertyNodes.add(new BooleanPropertyNode(property.getName(), (boolean) property.getValue(), property.isComputed()));
                    break;
                case INT:
                    this.propertyNodes.add(new IntegerPropertyNode(property.getName(), (int) property.getValue(), property.isComputed()));
                    break;
                case STRING:
                    this.propertyNodes.add(new StringPropertyNode(property.getName(), (String) property.getValue(), property.isComputed()));
                    break;
            }
        }
    }

    /**
     * @return The extra nodes displayed
     */
    public ObservableList<Node> getExtraNodes() {
        return this.extraNodes;
    }

    /**
     * @return The property nodes displayed
     */
    public ObservableList<PropertyNode<?>> getPropertiesNodes() {
        return this.propertyNodes;
    }
}
