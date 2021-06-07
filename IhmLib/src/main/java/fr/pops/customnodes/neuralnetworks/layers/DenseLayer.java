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
    private int height = 0;
    private int nbNeurons = 0;
    private final static int nbMaxNeuronsToDraw = 30;
    private final static int neuronRadius = 1;

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
    public DenseLayer(int height, int nbNeuron){
        // Initialize the layer
        this.onInit(height, nbNeuron);
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the layer
     * @param i
     * @param height The height of the layer to draw
     */
    private void onInit(int height, int nbNeurons){
        // Initialize the displayed objects
        this.group = new Group();
        this.neurons = new LinkedList<>();
        this.weights = new LinkedList<>();

        // Initialize the parameters
        this.height = height;
        this.nbNeurons = nbNeurons;
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
        // Initialize the bounds and increments
        int nbNeurons = this.nbNeurons;
        boolean skipNeurons = nbNeurons > nbMaxNeuronsToDraw;
        int nbNeuronsToDraw = skipNeurons ? (nbMaxNeuronsToDraw + 2) : nbNeurons;
        int dy = this.height / nbNeuronsToDraw;
        int pos =  - (dy * nbNeuronsToDraw) / 2 - (skipNeurons ? 0 : dy/2);

        // Loop over the neurons to draw
        for (int i = 0; i < nbNeurons; i++){
            // Create objects
            Sphere neuron = new Sphere();
            PhongMaterial mat = new PhongMaterial();
            // Setup the objects
            mat.setDiffuseColor(Color.BLACK);
            neuron.setRadius(neuronRadius);
            neuron.setMaterial(mat);
            neuron.translateYProperty().set(pos);

            // Store the neuron
            this.neurons.add(neuron);

            // Adjust increment
            if (skipNeurons && i == ((nbMaxNeuronsToDraw / 2) - 1)){
                pos += 4 * dy;
                i = nbNeurons - (nbMaxNeuronsToDraw / 2);
            } else {
                pos += dy;
            }
        }
    }

    /**
     * Draw the weights between the layers
     * @param previousLayer The previous layer to connect to
     * @param dx The difference of x coordinate between the layers
     */
    public void buildWeights(DenseLayer previousLayer, double dx) {
        // Initialize the positions
        double startX = (int) -dx;
        double endX = 0;
        double controlX1;
        double controlY1;
        double controlX2;
        double controlY2;

        double[] orange = Utils.hexToDoubleColor(startColorRamp);
        double[] blue = Utils.hexToDoubleColor(endColorRamp);

        // Loop over the neurons in the previous layer
        for (int i = 0; i < previousLayer.getNbNeurons(); i++){
            double startY = previousLayer.getNeurons().get(i).translateYProperty().get();

            // Loop over the neurons in this layer
            for (int j = 0; j < this.getNbNeurons(); j++){

                // Setup the coordinates
                double endY = this.neurons.get(j).translateYProperty().get();
                controlX1 = -dx/2;
                controlY1 = startY;
                controlX2 = -dx/2;
                controlY2 = endY;

                // Create the weight
                CubicCurve weight = new CubicCurve(startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);

                // Setup the weights
                weight.setStrokeWidth(1);
                double[] col = PopsMath.rand(0,1) < .5d ? orange : blue;
                weight.setStroke(new Color(col[0], col[1], col[2], PopsMath.rand(0,1)));
                weight.setEffect(new Bloom());
                weight.setFill(Color.TRANSPARENT);

                // Store the weight
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
