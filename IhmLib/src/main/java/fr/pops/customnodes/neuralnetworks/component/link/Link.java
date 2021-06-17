package fr.pops.customnodes.neuralnetworks.component.link;

import fr.pops.customnodes.neuralnetworks.component.component.Component;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.CubicCurve;

import java.util.UUID;

public class Link extends CubicCurve {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private final DoubleProperty controlOffsetX = new SimpleDoubleProperty();
    private final DoubleProperty controlOffsetY = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionX1 = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionY1 = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionX2 = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionY2 = new SimpleDoubleProperty();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public Link(){
        this.onInit();
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the curve
     */
    private void onInit() {
        // Set a unique id
        this.setId(UUID.randomUUID().toString());

        // Set offsets
        this.controlOffsetX.set(100.0d);
        this.controlOffsetY.set(50.0d);

        // Bind properties
        // Direction
        this.controlDirectionX1.bind(new When(this.startXProperty().greaterThan(this.endXProperty())).then(-1.0d).otherwise(1.0d));
        this.controlDirectionX2.bind(new When(this.startXProperty().greaterThan(this.endXProperty())).then(1.0d).otherwise(-1.0d));
        // Control points
        this.controlX1Property().bind(Bindings.add(this.startXProperty(), this.controlOffsetX.multiply(this.controlDirectionX1)));
        this.controlX2Property().bind(Bindings.add(this.endXProperty(), this.controlOffsetX.multiply(this.controlDirectionX2)));
        this.controlY1Property().bind(Bindings.add(this.startXProperty(), this.controlOffsetY.multiply(this.controlDirectionY1)));
        this.controlY2Property().bind(Bindings.add(this.endYProperty(), this.controlOffsetY.multiply(this.controlDirectionY2)));

        // Style
        this.getStyleClass().add("link");
        this.setFocusTraversable(true);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Bind both curve's ends to source and target nodes
     * @param source The source to bind the starting point to
     * @param target The target to bind the ending point to
     */
    public void bindEnds(Component source, Component target){
        // Start
        LinkHandler sourceLinkHandle = source.getRightLinkHandle();
        this.startXProperty().bind(Bindings.add(sourceLinkHandle.layoutXProperty(), (sourceLinkHandle.getWidth() /2.0d)));
        this.startYProperty().bind(Bindings.add(sourceLinkHandle.layoutYProperty(), (sourceLinkHandle.getHeight() /2.0d)));
        // End
        LinkHandler targetLinkHandle = source.getLeftLinkHandle();
        this.endXProperty().bind(Bindings.add(targetLinkHandle.layoutXProperty(), (targetLinkHandle.getWidth() /2.0d)));
        this.endYProperty().bind(Bindings.add(targetLinkHandle.layoutYProperty(), (targetLinkHandle.getHeight() /2.0d)));
        // Register
        source.registerLink(this.getId());
        target.registerLink(this.getId());
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the starting point
     * @param startPoint The starting position of the curve
     */
    public void setStart(Point2D startPoint){
        this.setStartX(startPoint.getX());
        this.setStartY(startPoint.getY());
    }

    /**
     * Set the ending point
     * @param endPoint The ending position of the curve
     */
    public void setEnd(Point2D endPoint){
        this.setEndX(endPoint.getX());
        this.setEndY(endPoint.getY());
    }
}
