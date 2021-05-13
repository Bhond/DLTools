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
 * Name: StochasticGradientDescent.java
 *
 * Description: Class implementing Solver. It defines the classical SGD
 *              to train neural networks.
 *
 * Author: Charles MERINO
 *
 * Date: 19/05/2019
 *
 ******************************************************************************/
package fr.pops.solver;

import fr.pops.nn.bias.Bias;
import fr.pops.nn.networks.CNN;
import fr.pops.nn.networks.Classifier;
import fr.pops.nn.networks.NeuralNetworkConfiguration;
import fr.pops.math.ndarray.INDArray;
import fr.pops.nn.weights.weight.Weight;

public class StochasticGradientDescent implements ISolver {

    // TODO: Correct this class

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * Compute the stochastic gradient descent
     * @param classifier
     * @param error
     */
    @Override
    public void run(Classifier classifier, INDArray error) {
        // Initialization
        // Begin backward pass with the output layer

//        this.fillConfusionMatrix(classifier.getCurrentEpoch(), classifier.getDataReader(), classifier.getLayers().get(classifier.getLayers().size()-1));

//        if (classifier.isRegularisationOn()){
//            Vector l1Vector = PopsMath.apply(x -> x * classifier.getL1LearningRate(), this.computeL1(classifier.getWeights().get(classifier.getWeights().size()-1)));
//            Vector l2Vector = PopsMath.apply(x -> x * this.l2LearningRate, this.computeL2(classifier.getWeights().get(classifier.getWeights().size()-1)));
//            error = PopsMath.apply(x -> 2 * x, error); // TODO : Check that "2" here ...
//            error = PopsMath.add(PopsMath.add(error, l1Vector), l2Vector);
//        }

        NeuralNetworkConfiguration configuration = classifier.getNeuralNetworkConfiguration();

        configuration.getLayers().get(configuration.getNbLayers()-1).initializeSigma(error);
        configuration.getLayers().get(configuration.getNbLayers()-1).getWeight().computeWeightsIndividualGradient(configuration.getLayers().get(configuration.getNbLayers()-1).getSigma(),
                                                                                                                  configuration.getLayers().get(configuration.getNbLayers()-2).getActivations()); // Last weight
        configuration.getLayers().get(configuration.getNbLayers()-1).getBias().computeBiasesIndividualGradient(configuration.getLayers().get(configuration.getNbLayers()-1).getSigma()); // Last bias

        // Loop over the remaining layers minus the input
        for(int d = configuration.getLayers().size() - 2; d > 0; d--){
            // Compute sigma
            configuration.getLayers().get(d+1).transmitSigma(configuration.getLayers().get(d));

            // Complete each gradients
            Weight weight = configuration.getLayers().get(d).getWeight();
            if (weight != null){
                weight.computeWeightsIndividualGradient(configuration.getLayers().get(d).getSigma(), configuration.getLayers().get(d-1).getActivations());
            }
            Bias bias = configuration.getLayers().get(d).getBias();
            if (bias != null){
                bias.computeBiasesIndividualGradient(configuration.getLayers().get(d).getSigma());
            }
        }
    }

    /**
     * Compute the stochastic gradient descent
     * @param cnn
     * @param error
     */
    @Override
    public void run(CNN cnn, INDArray error) {
        // Initialization
        NeuralNetworkConfiguration configuration = cnn.getNeuralNetworkConfiguration();

        configuration.getLayers().get(configuration.getNbLayers()-1).initializeSigma(error);
        configuration.getLayers().get(configuration.getNbLayers()-1).getWeight().computeWeightsIndividualGradient(configuration.getLayers().get(configuration.getNbLayers()-1).getSigma(),
                configuration.getLayers().get(configuration.getNbLayers()-2).getActivations()); // Last weight
        configuration.getLayers().get(configuration.getNbLayers()-1).getBias().computeBiasesIndividualGradient(configuration.getLayers().get(configuration.getNbLayers()-1).getSigma()); // Last bias

        // Loop over the remaining layers minus the input
        for(int d = configuration.getLayers().size() - 2; d > 0; d--){
            // Compute sigma
            configuration.getLayers().get(d+1).transmitSigma(configuration.getLayers().get(d));

            // Complete each gradients
            Weight weight = configuration.getLayers().get(d).getWeight();
            if (weight != null){
                weight.computeWeightsIndividualGradient(configuration.getLayers().get(d).getSigma(), configuration.getLayers().get(d-1).getActivations());
            }
            Bias bias = configuration.getLayers().get(d).getBias();
            if (bias != null){
                bias.computeBiasesIndividualGradient(configuration.getLayers().get(d).getSigma());
            }
        }
    }
}
