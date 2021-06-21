package fr.pops.customnodes.beanproperties;

import fr.pops.math.ndarray.Shape;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ShapePropertyNode extends PropertyNode<Shape>{

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private TextField xAxisField;
    private TextField yAxisField;
    private TextField zAxisField;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public ShapePropertyNode(String name, Shape defaultValue, boolean isComputed){
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
        this.valueNode = new HBox();
        this.xAxisField = new TextField(String.valueOf(this.defaultValue.getXAxisLength()));
        this.yAxisField = new TextField(String.valueOf(this.defaultValue.getYAxisLength()));
        this.zAxisField = new TextField(String.valueOf(this.defaultValue.getZAxisLength()));

        ((HBox)this.valueNode).widthProperty().addListener((observableValue, oldValue, newValue) -> {
            this.xAxisField.setPrefWidth(newValue.doubleValue() / 3);
            this.yAxisField.setPrefWidth(newValue.doubleValue() / 3);
            this.zAxisField.setPrefWidth(newValue.doubleValue() / 3);
        });

        ((HBox)this.valueNode).getChildren().addAll(this.xAxisField, this.yAxisField, this.zAxisField);
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
    public Shape getValue() {
        return null;
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
    public void setValue(Shape value) {
        this.xAxisField.setText(String.valueOf(value));
        this.yAxisField.setText(String.valueOf(value));
        this.zAxisField.setText(String.valueOf(value));
    }
}
