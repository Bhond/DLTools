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
 * Name: BorderedPane.java
 *
 * Description: Class defining custom node for javafx: BorderedPane.
 *              AnchorPane with custom corners
 *
 * Author: Charles MERINO
 *
 * Date: 06/03/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.panes;

import fr.pops.customnodes.misc.BeveledCorner;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class CorneredPane extends AnchorPane {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private BeveledCorner topLeftCorner;
    private BeveledCorner topRightCorner;
    private BeveledCorner bottomLeftCorner;
    private BeveledCorner bottomRightCorner;

    private double height;
    private double width;
    private double bevelOffset;
    private double size;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public CorneredPane(double height, double width, double bevelOffset, double size){
        // Parent
        super();

        // Initialisation
        this.onInit(height, width, bevelOffset, size);
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialisation
     */
    private void onInit(double height, double width, double bevelOffset, double size){
        // Initialize attributes
        this.bevelOffset = bevelOffset;
        this.size = size;

        // Draw the corners
        this.addCorners();
    }

    /*****************************************
     *
     * Draw methods
     *
     *****************************************/
    /**
     * Add the corners
     */
    private void addCorners(){

        // Top left
        this.topLeftCorner = new BeveledCorner(this.height, this.width, this.bevelOffset, this.size, BeveledCorner.Position.TOP_LEFT);
        this.setAnchor(this.topLeftCorner); // In progress ....................
        this.getChildren().addAll(this.topLeftCorner.getLines());

        // Top right
        this.topRightCorner = new BeveledCorner(this.height, this.width, this.bevelOffset, this.size, BeveledCorner.Position.TOP_RIGHT);
        this.getChildren().addAll(this.topRightCorner.getLines());

        // Bottom left
        this.bottomLeftCorner = new BeveledCorner(this.height, this.width, this.bevelOffset, this.size, BeveledCorner.Position.BOTTOM_LEFT);
        this.getChildren().addAll(this.bottomLeftCorner.getLines());

        // Bottom right
        this.bottomRightCorner = new BeveledCorner(this.height, this.width, this.bevelOffset, this.size, BeveledCorner.Position.BOTTOM_RIGHT);
        this.getChildren().addAll(this.bottomRightCorner.getLines());

    }

    private void setAnchor(BeveledCorner corner){
        for (Line line : this.topLeftCorner.getLines()){
            AnchorPane.setLeftAnchor(line, 0d);
            AnchorPane.setTopAnchor(line, 0d);
        }
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Update the size of the main pane and the children
     * @param height The new height
     * @param width The new width
     */
    public void updateSize(double height, double width){
        // Update sizes
        this.height = height;
        this.width = width;

        // Update children
        this.getChildren().clear();
        this.addCorners();
    }

}
