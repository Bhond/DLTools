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
 * Name: HexImage.java
 *
 * Description: Class defining an image with each pixel is an hexadecimal value
 *
 * Author: Charles MERINO
 *
 * Date: 16/05/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.heximage;

import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class HexImage extends GridPane {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Objects
    private INDArray image;
    private List<Pane> pixels;

    // Parameters
    private int resolutionX;
    private int resolutionY;
    private boolean greyScale;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private HexImage(){
        this(new BaseNDArray.BaseNDArrayBuilder().zeros(1,1,1).build());
    }

    /**
     * Ctor
     * @param image The image to draw
     */
    public HexImage(INDArray image){
        // Fields
        this.image = image;
        this.resolutionX = image.getShape().getXAxisLength();
        this.resolutionY = image.getShape().getYAxisLength();
        this.greyScale = image.getShape().getZAxisLength() == 1;

        // Initialization
        this.onInit();
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the image
     */
    private void onInit(){
        // Pixels
        this.pixels = new LinkedList<>();

        // Create the image
        this.buildImage();
    }

    /**
     * Create the image
     */
    private void buildImage() {
        // Create the panes representing the pixels
        int nbPixels = resolutionX * resolutionY;
        int rowIdx = 0;
        int colIdx = 0;
        for (int i = 0; i < nbPixels; i++){
            Pane pxl = new Pane();
            pxl.setPrefHeight(50);
            pxl.setPrefWidth(50);
            double[] col = buildColorFromImage(i);
            Background bg = new Background(new BackgroundFill(new Color(col[0], col[1], col[2], 1), null, null));
            pxl.setBackground(bg);
            this.add(pxl, colIdx, rowIdx);
            rowIdx += colIdx != 0 && colIdx % (resolutionX - 1) == 0 ? 1 : 0;
            colIdx += colIdx != 0 && colIdx % (resolutionX - 1) == 0 ? -colIdx : 1;
        }
    }

    /**
     * Create color from pixel values in the image
     * The result changes according to the greyscale boolean
     * and the shape of the image
     * @return The rgb color values in a length 3 double array
     */
    private double[] buildColorFromImage(int pixelIdx){
        double[] color = new double[3];
        this.greyScale = this.image.getShape().getZAxisLength() == 1;
        if (this.greyScale){
            color[0] = this.image.getData()[pixelIdx];
            color[1] = this.image.getData()[pixelIdx];
            color[2] = this.image.getData()[pixelIdx];
        } else {
            color[0] = this.image.getData()[pixelIdx];
            color[1] = this.image.getData()[pixelIdx + this.resolutionX * this.resolutionY];
            color[2] = this.image.getData()[pixelIdx + 2 * this.resolutionX * this.resolutionY];
        }
        return color;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Display the input image
     * @param image The image to display
     */
    public void setImage(BaseNDArray image) {
        this.image = image;
        this.getChildren().removeAll(this.getChildren());
        this.buildImage();
    }
}
