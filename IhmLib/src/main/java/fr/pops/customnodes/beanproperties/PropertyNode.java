package fr.pops.customnodes.beanproperties;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public abstract class PropertyNode<T> extends HBox {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected String name;
    private Label nameLbl;
    protected Node valueNode;
    protected T defaultValue;
    protected boolean isComputed;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    protected PropertyNode(String name, T defaultValue, boolean isComputed){
        // Fields
        this.name = name;
        this.defaultValue = defaultValue;
        this.isComputed = isComputed;

        // Initialisation
        this.onInit();

        // Build hierarchy
        this.buildHierarchy();

        // Build controls
        this.buildControls();
    }

    private void buildControls() {
        // Check modification
        this.valueNode.disableProperty().setValue(isComputed);

        // Correct width
        this.widthProperty().addListener((observableValue, oldWidth, newWidth) -> {
            this.nameLbl.setPrefWidth(newWidth.doubleValue() / 2);
            this.valueNode.prefWidth(newWidth.doubleValue() / 2);
        });
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the node
     */
    protected void onInit(){
        // Name label
        this.nameLbl = new Label(this.name);
    }

    /**
     * Build hierarchy
     */
    private void buildHierarchy(){
        // Add all nodes
        this.getChildren().addAll(this.nameLbl, this.valueNode);
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
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

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Sets the property's value
     * @param value The new property's value
     */
    public abstract void setValue(T value);
}
