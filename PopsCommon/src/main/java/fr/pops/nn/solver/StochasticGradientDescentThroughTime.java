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
 * Name: StochasticGradientDescentThroughTime.java
 *
 * Description: Class implementing Solver. It defines the classical SGD
 *              through time to train neural networks.
 *
 * Author: Charles MERINO
 *
 * Date: 19/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.solver;

import fr.pops.nn.ndarray.INDArray;
import fr.pops.nn.neuralnetworks.networks.RNN;

public class StochasticGradientDescentThroughTime implements ISolver {

    // TODO: Correct this class

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * Compute the stochastic gradient descent
     * @param rnn
     * @param error
     */
    @Override
    public void run(RNN rnn, INDArray error) {

//        // Compute first sigma
//        INDArray sigma = rnn.getNeuralNetworkConfiguration().getLayers().get(rnn.getNeuralNetworkConfiguration().getNbLayers()-1).initializeSigma(error);
//
//        // The output layer is a classic dense layer, this part is the same as the classic SGD method
//        INDArray weightGrad = StandardWeight.computeWeightsIndividualGradient(sigma, rnn.getNeuralNetworkConfiguration().getLayers().get(rnn.getNeuralNetworkConfiguration().getNbLayers()-2).getActivations());
//        rnn.getWeights().get(rnn.getWeights().size()-1).completeGradient(weightGrad); // Last weight
//
//        INDArray biasGrad = Bias.computeBiasesIndividualGradient(sigma);
//        rnn.getNeuralNetworkConfiguration().getLayers().get(rnn.getNeuralNetworkConfiguration().getNbLayers()-1).getBias().completeGradient(biasGrad); // Last bias
//
//        // Loop over the remaining layers minus the input
//        for(int d = rnn.getNeuralNetworkConfiguration().getNbLayers() - 2; d > 0; d--){
//            // Compute sigma
//            sigma = rnn.getNeuralNetworkConfiguration().getLayers().get(d).computeSigma(rnn.getWeights().get(d), sigma);
//
//            // Complete each gradients
//            weightGrad = StandardWeight.computeWeightsIndividualGradient(sigma, rnn.getNeuralNetworkConfiguration().getLayers().get(d-1).getActivations());
//            rnn.getWeights().get(d-1).completeGradient(weightGrad);
//            biasGrad = Bias.computeBiasesIndividualGradient(sigma);
//            rnn.getNeuralNetworkConfiguration().getLayers().get(d-1).getBias().completeGradient(biasGrad);
//        }
    }
}
