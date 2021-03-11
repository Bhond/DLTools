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
 * Name: SequenceGenerator.java
 *
 * Description: Class defining the sequence generator to generate a sequence of data
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.scorer;

import fr.pops.nn.networks.NeuralNetwork;
import fr.pops.ndarray.INDArray;
import fr.pops.popsmath.PopsMath;
import fr.pops.popsmath.Vector;

public abstract class SequenceGenerator {

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    /**
     * Generate a sequence from given seed and length
     * @param neuralNetwork
     * @param seed
     * @param seqLength
     * @return
     */
    public static String generateStringSequence(NeuralNetwork neuralNetwork, INDArray seed, int seqLength){
        // TODO: Correct this line
        String message = "";// + neuralNetwork.getDataReader().getFromDictionary(PopsMath.indexOfMinMaxValue(seed, "max"));
        // Set first input
        neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(0).setActivations(seed);
        // Loop over seqLength
        for(int i = 0; i < seqLength; i++){
            // Forward pass
            neuralNetwork.guess();
            // Retrieve predicted value
            char val = neuralNetwork.getNeuralNetworkConfiguration().getDataReader().getFromDictionary(PopsMath.indexOfMinMaxValue((Vector) neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(neuralNetwork.getNeuralNetworkConfiguration().getNbLayers()-1).getActivations(), "max"));
            message += val;
            // Set predicted output as a new input
            neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(0).setActivations(neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(neuralNetwork.getNeuralNetworkConfiguration().getNbLayers()-1).getActivations());
        }
        return message;
    }
}
