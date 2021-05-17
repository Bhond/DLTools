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
 * Name: DenseLayer.java
 *
 * Description: Class defining the visualisation of a dense layer
 *
 * Author: Charles MERINO
 *
 * Date: 16/05/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.layers;

import fr.pops.math.PopsMath;
import fr.pops.utils.Utils;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Sphere;

import java.util.LinkedList;
import java.util.List;

public class DenseLayer {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Objects
    private Group group;
    private List<Sphere> neurons;
    private List<CubicCurve> weights;

    // Parameters
    private final static int neuronRadius = 1; // TODO: correct this
    private final static int distanceBetweenNeurons = 50; // TODO: correct this

    // Graphics
    private final static String startColorRamp = "ff5000";
    private final static String endColorRamp = "2dc8c8";

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public DenseLayer(){
        // Initialize the layer
        this.onInit();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the layer
     */
    private void onInit(){
        // Initialize the displayed objects
        this.group = new Group();
        this.neurons = new LinkedList<>();
        this.weights = new LinkedList<>();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Build the layer
     */
    public void build(DenseLayer previousLayer, double dx){
        // Draw the neurons
        this.buildNeurons();
        // Draw the weights
        if (previousLayer != null) {
            this.buildWeights(previousLayer, dx);
        }
    }

    /**
     * Build the neurons
     */
    public void buildNeurons(){
        // Loop over the neurons
        int nbNeurons = (int) PopsMath.rand(10, 30);
        int dy = distanceBetweenNeurons + 2 * neuronRadius;
        int pos = - ((nbNeurons+1) / 2 * dy) + (nbNeurons % 2 == 0 ? dy/2: 0);

        for (int i = 0; i < nbNeurons; i++){
            Sphere neuron = new Sphere();
            PhongMaterial mat = new PhongMaterial();
            mat.setDiffuseColor(Color.BLACK);
            neuron.setRadius(neuronRadius);
            neuron.setMaterial(mat);
            neuron.translateYProperty().set(pos);
            this.neurons.add(neuron);
            pos += dy;
        }
    }

    /**
     * Draw the weights between the layers
     * @param previousLayer The previous layer to connect to
     * @param dx The difference of x coordinate between the layers
     */
    public void buildWeights(DenseLayer previousLayer, double dx) {
        double startX = (int) -dx;
        double endX = 0;
        double controlX1 = 0;
        double controlY1 = 0;
        double controlX2 = 0;
        double controlY2 = 0;

        double[] orange = Utils.hexToDoubleColor(startColorRamp);
        double[] blue = Utils.hexToDoubleColor(endColorRamp);

        // Loop over the neurons in the previous layer
        for (int i = 0; i < previousLayer.getNbNeurons(); i++){
            double startY = previousLayer.getNeurons().get(i).translateYProperty().get();
            // Loop over the neurons in this layer
            for (int j = 0; j < this.getNbNeurons(); j++){
                double endY = this.neurons.get(j).translateYProperty().get();
                controlX1 = -dx/2;
                controlY1 = startY;
                controlX2 = -dx/2;
                controlY2 = endY;
                CubicCurve weight = new CubicCurve(startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);
                weight.setStrokeWidth(1);
                double[] col = PopsMath.rand(0,1) < .5d ? orange : blue;
                weight.setStroke(new Color(col[0], col[1], col[2], PopsMath.rand(0,1)));
                weight.setEffect(new Bloom());
                weight.setFill(Color.TRANSPARENT);
                this.weights.add(weight);
            }
        }
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The group containing all the  displayed components
     */
    public Group getGroup() {
        return this.group;
    }

    /**
     * @return The distance between
     */
    public static int getNeuronRadius() {
        return neuronRadius;
    }

    /**
     * @return The number of neurons in the layer
     */
    public int getNbNeurons(){
        return this.neurons.size();
    }

    /**
     * @return The neurons of the layer
     */
    public List<Sphere> getNeurons() {
        return this.neurons;
    }

    /**
     * @return The weights of the layer
     */
    public List<CubicCurve> getWeights() {
        return this.weights;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Translate the neurons on the x-axis coordinate
     * @param value The amount of translation
     */
    public void translateX(double value){
        // Translate the neurons
        for (Sphere neuron : this.neurons){
            neuron.translateXProperty().set(value);
            neuron.toFront();
        }

        // Translate the weights
        for (CubicCurve weight : this.weights){
            // Start/end
            weight.translateXProperty().set(value);
            // Control points
            double startX = weight.startXProperty().get();
            double endX = weight.endXProperty().get();
            double x = PopsMath.lerp(startX, endX, 0.5);
            weight.controlX1Property().set(x);
            weight.controlX2Property().set(x);
        }
    }

    /**
     *  Bring the neurons to the front
     */
    public void neuronsToFront(){
        for (Sphere neuron : this.neurons){
            neuron.toFront();
        }
    }
}
