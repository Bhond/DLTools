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
 * Name: Updater.java
 *
 * Description: Abstract class used to update the weights and biases
 *              when training neural networks.
 *
 * Author: Charles MERINO
 *
 * Date: 19/05/2019
 *
 ******************************************************************************/
package fr.pops.updater;

import fr.pops.nn.networks.NeuralNetwork;
import fr.pops.nn.layers.Layer;

public abstract class Updater {

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    /**
     * Retrieve all gradients previously computed and compute the following equation:
     *  X (t + 1) = X (t) - (alpha / m) * grad(X)
     * @param nn The neural network to update
     */
    public static void update(NeuralNetwork nn){
        for(Layer l : nn.getNeuralNetworkConfiguration().getLayers()){
            l.updateInternalState(nn.getNeuralNetworkConfiguration().getLearningRate(), nn.getNeuralNetworkConfiguration().getBatchSize(), nn.getNeuralNetworkConfiguration().getOptimizer());
        }
    }

    /**
     * Clear the gradients of the model
     * @param nn
     */
    public static void clearGradients(NeuralNetwork nn){
        // Clear internal states
        for (Layer l : nn.getNeuralNetworkConfiguration().getLayers()){
            l.clearInternalState();
        }
    }
}
