/*******************************************************************************
 *
 *                         PPPP     OOOO     PPPP    SSSS
 *                        PP  PP   OO  OO   PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP   SSSS
 *                        PPPP    OO    OO  PPPP        SS
 *                        PP       OO  OO   PP          SS
 *                        PP        OOOO    PP       SSSS
 *
 * Name: ComponentIcon.java
 *
 * Description: Class defining the components' icons in the library
 *              It is built from the enum defined in EnumCst
 *
 * Author: Charles MERINO
 *
 * Date: 21/06/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.cst.EnumCst;
import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ComponentIcon extends VBox {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private EnumCst.ComponentTypes type;
    private Pane icon;
    private Label label = new Label();
    private Point2D dragOffset = new Point2D (0.0, 0.0);

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public ComponentIcon(){
        this.type = EnumCst.ComponentTypes.DRAG;
        this.onInit();
    }

    /**
     * Ctor
     * @param type the type of the icon
     */
    public ComponentIcon(EnumCst.ComponentTypes type){
        this.type = type;
        this.onInit();
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialisation
     */
    private void onInit(){
        // Root
        this.setAlignment(Pos.CENTER);

        // Icon
        this.icon = new Pane();
        this.icon.setMinSize(30,30);
        this.icon.setMaxSize(30,30);

        // Label
        this.label = new Label();
        this.label.setText(this.type.toString());

        // Style
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_ICON_CSS));
        this.selectStyle();

        // Hierarchy
        this.getChildren().addAll(this.icon, this.label);
    }

    public void relocateToPoint (Point2D p) {
        //relocates the object to a point that has been converted to
        //scene coordinates
        Point2D localCoords = this.getParent().sceneToLocal(p);
        this.relocate (
                (int) (localCoords.getX() - this.dragOffset.getX()),
                (int) (localCoords.getY() - this.dragOffset.getY())
        );
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Select the style depending of the component's category
     */
    private void selectStyle(){
        this.icon.getStyleClass().clear();
        switch (this.type.getCategory()){
            case INPUTS:
                this.icon.getStyleClass().add(StrCst.STYLE_CLASS_SHAPE_SERVER);
                break;
            case LAYERS:
                this.icon.getStyleClass().add(StrCst.STYLE_CLASS_SHAPE_BEZIER);
                break;
        }
        this.icon.getStyleClass().add("icn");
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The type of the component to create
     */
    public EnumCst.ComponentTypes getType() {
        return type;
    }

    /**
     * @return The displayed text
     */
    public String getText(){
        return this.label.getText();
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the type of the icon
     * @param type The type of the icon
     */
    public void setType(EnumCst.ComponentTypes type) {
        this.type = type;
        this.label.setText(type.toString());
        this.selectStyle();
    }
}
