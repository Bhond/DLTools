package fr.pops.customnodes.neuralnetworks.component.link;

import fr.pops.cst.StrCst;
import fr.pops.customnodes.neuralnetworks.component.component.Component;
import fr.pops.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;

import java.util.UUID;

public class Link extends AnchorPane {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private CubicCurve nodeLink;
    private LinkHandle startHandle;
    private LinkHandle endHandle;
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

        // Build the node link
        this.nodeLink = new CubicCurve();
        this.getChildren().add(this.nodeLink);

        // Set offsets
        this.controlOffsetX.set(100.0d);
        this.controlOffsetY.set(50.0d);

        // Bind properties
        // Direction
        this.controlDirectionX1.bind(new When(this.nodeLink.startXProperty().greaterThan(this.nodeLink.endXProperty())).then(-1.0d).otherwise(1.0d));
        this.controlDirectionX2.bind(new When(this.nodeLink.startXProperty().greaterThan(this.nodeLink.endXProperty())).then(1.0d).otherwise(-1.0d));

        // Control points
        this.nodeLink.controlX1Property().bind(Bindings.add(this.nodeLink.startXProperty(), this.controlOffsetX.multiply(this.controlDirectionX1)));
        this.nodeLink.controlX2Property().bind(Bindings.add(this.nodeLink.endXProperty(), this.controlOffsetX.multiply(this.controlDirectionX2)));
        this.nodeLink.controlY1Property().bind(Bindings.add(this.nodeLink.startYProperty(), this.controlOffsetY.multiply(this.controlDirectionY1)));
        this.nodeLink.controlY2Property().bind(Bindings.add(this.nodeLink.endYProperty(), this.controlOffsetY.multiply(this.controlDirectionY2)));

        // Style
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_LINK_CSS));
        this.getStyleClass().add(Utils.getResource(StrCst.STYLE_CLASS_ROOT));
        this.nodeLink.getStyleClass().add(Utils.getResource(StrCst.STYLE_CLASS_LINK));
        this.setFocusTraversable(true);
        this.nodeLink.setFocusTraversable(true);
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
        LinkHandle sourceLinkHandle = source.getRightLinkHandle();
        this.nodeLink.startXProperty().bind(Bindings.add(source.layoutXProperty(), sourceLinkHandle.getLayoutX() + (sourceLinkHandle.getWidth() / 2.0)));
        this.nodeLink.startYProperty().bind(Bindings.add(source.layoutYProperty(), sourceLinkHandle.getParent().getLayoutY() + (sourceLinkHandle.getHeight() /2.0d)));
        sourceLinkHandle.setState(LinkHandle.States.LINKED);
        this.startHandle = sourceLinkHandle;
        // End
        LinkHandle targetLinkHandle = target.getLeftLinkHandle();
        this.nodeLink.endXProperty().bind(Bindings.add(target.layoutXProperty(), targetLinkHandle.getLayoutX() + (targetLinkHandle.getWidth() /2.0d)));
        this.nodeLink.endYProperty().bind(Bindings.add(target.layoutYProperty(), targetLinkHandle.getParent().getLayoutY() + (targetLinkHandle.getHeight() /2.0d)));
        targetLinkHandle.setState(LinkHandle.States.LINKED);
        this.endHandle = targetLinkHandle;
        // Register
        source.registerLink(this.getId());
        target.registerLink(this.getId());
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The right Link handle which this link is connected to
     */
    public LinkHandle getStartHandle() {
        return this.startHandle;
    }

    /**
     * @return The left Link handle which this link is connected to
     */
    public LinkHandle getEndHandle() {
        return this.endHandle;
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
        this.nodeLink.setStartX(startPoint.getX());
        this.nodeLink.setStartY(startPoint.getY());
    }

    /**
     * Set the ending point
     * @param endPoint The ending position of the curve
     */
    public void setEnd(Point2D endPoint){
        this.nodeLink.setEndX(endPoint.getX());
        this.nodeLink.setEndY(endPoint.getY());
    }
}
