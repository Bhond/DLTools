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
 * Name: BeveledCorner.java
 *
 * Description: Build a beveled corner.
 *
 * Author: Charles MERINO
 *
 * Date: 06/03/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.misc;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class BeveledCorner {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    public enum Position { TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT }

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Drawing info
    private double bevelOffset;
    private double size;
    private Position position;
    private double parentHeight;
    private double parentWidth;

    // Increments determined by the desired position
    private int xIncrement;
    private int yIncrement;

    /*
     * The 3 lines drawing the beveled corner.
     * if Position == TOP_Leff:
     * l1 ->    __
     * l2 ->  /
     * l3 -> |
     */
    private Line l1;
    private Line l2;
    private Line l3;

    // Collection of the lines
    private List<Line> lines = new ArrayList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private BeveledCorner(){
        // Nothing to be done
    }

    /**
     * Create new beveled corner
     * @param bevelOffSet The offset from the origin
     * @param size The length of each corner branch
     */
    public BeveledCorner(double parentHeight, double parentWidth, double bevelOffSet, double size, Position position){
        // Initialize the corner
        this.parentHeight = parentHeight;
        this.parentWidth = parentWidth;
        this.bevelOffset = bevelOffSet;
        this.size = size;
        this.position = position;

        // Build corner
        this.build();

        // Initialize style class
        this.setStyleClass("beveledCorner");
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Build the corner
     */
    private void build(){
        // Origin
        double xOrigin = computeXOrigin();
        double yOrigin = computeYOrigin();

        // Increments
        this.compteIncrements();

        // L1
        double l1StartX = xOrigin + this.xIncrement * this.bevelOffset;
        double l1EndX = l1StartX +  this.xIncrement * this.size;
        double l1StartY = yOrigin;
        double l1EndY = yOrigin;
        this.l1 = new Line(l1StartX, l1StartY, l1EndX, l1EndY);

        // L2
        double l2StartX = l1StartX;
        double l2EndX = xOrigin;
        double l2StartY = l1StartY;
        double l2EndY = yOrigin +  this.yIncrement * this.bevelOffset;
        this.l2 = new Line(l2StartX, l2StartY, l2EndX , l2EndY);

        // L3
        double l3StartX = xOrigin;
        double l3EndX = xOrigin;
        double l3StartY = yOrigin + this.yIncrement *  this.bevelOffset;
        double l3EndY = l3StartY + this.yIncrement * this.size;
        this.l3 = new Line(l3StartX, l3StartY, l3EndX, l3EndY);

        // Build hierarchy
        this.lines.add(this.l1);
        this.lines.add(this.l2);
        this.lines.add(this.l3);
    }

    /**
     * @return The origin of the x coordinate
     */
    private double computeXOrigin(){
        double xOrigin;
        if (this.position == Position.TOP_RIGHT || this.position == Position.BOTTOM_RIGHT){
            xOrigin = this.parentWidth;
        } else {
            xOrigin = 0;
        }
        return xOrigin;
    }

    /**
     * @return The origin of the y coordinate
     */
    private double computeYOrigin(){
        double yOrigin;
        if (this.position == Position.BOTTOM_LEFT || this.position == Position.BOTTOM_RIGHT){
            yOrigin = this.parentHeight;
        } else {
            yOrigin = 0;
        }
        return yOrigin;
    }

    private void compteIncrements(){

        switch (this.position){
            case TOP_LEFT:
                this.xIncrement = 1;
                this.yIncrement = 1;
                break;
            case TOP_RIGHT:
                this.xIncrement = -1;
                this.yIncrement = 1;
                break;
            case BOTTOM_LEFT:
                this.xIncrement = 1;
                this.yIncrement = -1;
                break;
            case BOTTOM_RIGHT:
                this.xIncrement = -1;
                this.yIncrement = -1;
                break;
        }
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Translate the lines to their new positions
     * @param parentHeight The parent height
     * @param parentWidth The parent width
     */
    public void update(int parentWidth, int parentHeight){

        this.l1.translateXProperty().set(this.parentHeight - parentHeight);
        this.l1.translateYProperty().set(this.parentWidth - parentWidth);

        this.l2.translateXProperty().set(this.parentHeight - parentHeight);
        this.l2.translateYProperty().set(this.parentWidth - parentWidth);

        this.l3.translateXProperty().set(this.parentHeight - parentHeight);
        this.l3.translateYProperty().set(this.parentWidth - parentWidth);

        this.parentHeight = parentHeight;
        this.parentWidth = parentWidth;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The lines shaping the beveled corner
     */
    public List<Line> getLines(){
        return this.lines;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the parent height to update the drawing
     * @param parentHeight The parent height
     */
    public void setParentHeight(int parentHeight) {
        this.parentHeight = parentHeight;
    }

    /**
     * Set the parent width to update the drawing
     * @param parentWidth The parent width
     */
    public void setParentWidth(int parentWidth) {
        this.parentWidth = parentWidth;
    }

    /**
     * Set the color of the corner
     * @param paint The color
     */
    public void setStroke(Paint paint){
        for (Line l : this.lines){
            l.setStroke(paint);
        }
    }

    /**
     * Set the stroke width
     * @param v The width
     */
    public void setStrokeWidth(double v){
        for (Line l : this.lines){
            l.setStrokeWidth(v);
        }
    }

    /**
     * Set the style class of the corner
     * @param s The name of the style class
     */
    public void setStyleClass(String s){
        for (Line line : lines){
            line.getStyleClass().add(s);
        }
    }

}
