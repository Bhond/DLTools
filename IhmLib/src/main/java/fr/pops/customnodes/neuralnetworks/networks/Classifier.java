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
 * Name: Classifier.java
 *
 * Description: Class defining the display of classifier neural network
 *
 * Author: Charles MERINO
 *
 * Date: 16/05/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.networks;

import fr.pops.commoncst.EnumCst;
import fr.pops.customnodes.neuralnetworks.layers.DenseLayer;

import java.util.LinkedList;
import java.util.List;

public class Classifier extends NeuralNetwork {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Classifier objects
    private List<DenseLayer> layers;

    // Drawing parameters
    private final int distanceBetweenLayers = 200; // TODO: correct this

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    protected Classifier() {
        // Parent
        super(EnumCst.NeuralNetworkTypes.CLASSIFIER);

        // Initialization
        this.onInit();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the scene
     */
    @Override
    protected void onInit() {
        // Parent
        super.onInit();

        // Fields
        this.layers = new LinkedList<>();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Build the neural network
     */
    @Override
    public void build() {
        // If map is empty, return
        int nbLayers = this.layersConf.size();
        if (nbLayers == 0) return;

        // Initialize the parameters
        int dx = this.widthNeuralNetwork / nbLayers;
        int startPos = - ((nbLayers / 2) * dx) + (nbLayers % 2 == 0 ?  dx / 2 : 0);
        int pos = startPos;

        // Loop over all the layers
        for (int i = 0; i < nbLayers; i++){
            DenseLayer layer = new DenseLayer(this.heightNeuralNetwork, this.layersConf.get(i));
            if (i > 0){
                layer.buildNeurons();
                layer.buildWeights(this.layers.get(i-1), dx);
                this.neuralNetworkGroup.getChildren().addAll(layer.getWeights());
            } else {
                layer.buildNeurons();
            }
            this.neuralNetworkGroup.getChildren().addAll(layer.getNeurons());
            this.layers.add(layer);
            pos += dx;
        }

        /* Bring the neurons of all the layers back to the front
         *  and translate the layer to the right location */
        pos = startPos;
        for (DenseLayer layer : this.layers){
            // Translate the neurons
            layer.translateX(pos);
            // Put the neurons at the front
            layer.neuronsToFront();
            pos += dx;
        }
    }

    /**
     * Clear the neural network
     */
    @Override
    public void clear() {
        this.neuralNetworkGroup.getChildren().clear();
        this.layers.clear();
    }
}
