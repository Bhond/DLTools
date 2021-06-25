package fr.pops.customnodes.beanproperties;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class PropertyNode<T> extends HBox {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    protected static final String VALUE_LISTENER_TAG = "valueListenerTag";

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected String name;
    private Label nameLbl;
    protected Node valueNode;
    protected T value;
    protected boolean isComputed;
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    protected PropertyNode(String name, T value, boolean isComputed){
        // Fields
        this.name = name;
        this.value = value;
        this.isComputed = isComputed;

        // Initialisation
        this.onInit();

        // Build hierarchy
        this.buildHierarchy();

        // Build controls
        this.buildControls();
    }

    /**
     * Build the controls
     */
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

        // Listener
        this.addPropertyChangeListener(this::onValueChanged);
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
     * Listeners
     *
     *****************************************/
    /**
     * Add a property change listener
     * @param listener The listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * Add a property change listener
     * @param listener The listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener){
        this.support.removePropertyChangeListener(listener);
    }

    /**
     * Method called when the value has changed
     */
    private void onValueChanged(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(VALUE_LISTENER_TAG)){
            System.out.println( propertyChangeEvent.getPropertyName() + " has changed from " + propertyChangeEvent.getOldValue() + " to " + propertyChangeEvent.getNewValue());
        }
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
