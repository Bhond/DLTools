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
 * Name: FeedForward.java
 *
 * Description: Class implementing Guesser for FeedForward networks.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.guesser;

import fr.pops.nn.neuralnetworks.networks.NeuralNetwork;

public class FeedForward implements IGuesser {

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * Run the forward pass
     * @param neuralNetwork<T> the Neural network through the forward pass is computed
     */
    @Override
    public void run(NeuralNetwork neuralNetwork){
        // Loop over all layers
        int nbLayers = neuralNetwork.getNeuralNetworkConfiguration().getNbLayers();
        for (int i = 1; i < nbLayers; i++){
            // Compute Z
            neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(i).computeZ(neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(i-1));

            // Compute the activation : activationFunction( layer.z )
            neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(i).activate();
        }
    }
}
