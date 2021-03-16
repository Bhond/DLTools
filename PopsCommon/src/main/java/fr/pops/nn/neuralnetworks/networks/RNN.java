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
 * Name: RNN.java
 *
 * Description: Class inheriting from NeuralNetwork defining
 *              Recurrent Neural Networks.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.neuralnetworks.networks;

import fr.pops.nn.guesser.FeedForward;
import fr.pops.nn.ndarray.INDArray;
import fr.pops.nn.nncst.cst.EnumCst;
import fr.pops.nn.solver.StochasticGradientDescentThroughTime;
import fr.pops.nn.trainer.SequentialTrainer;

public class RNN extends NeuralNetwork {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    private RNN(){
        // Nothing to be done
    }

    /**
     * Ctor to call
     * @param neuralNetworkConfiguration The neural network configuration
     */
    private RNN(NeuralNetworkConfiguration neuralNetworkConfiguration){
        this.neuralNetworkConfiguration = neuralNetworkConfiguration;
    }

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * Initialize the neural network:
     *  - Setup the general parameters
     *  - Setup the weights
     *  - Control the configuration of the neural network
     */
    @Override
    public void init() {

        // Fill in variables common to all RNN
        this.type = EnumCst.NeuralNetworkTypes.RNN;
        this.guesser = new FeedForward();
        this.solver = new StochasticGradientDescentThroughTime();
        this.trainer = new SequentialTrainer();

        // Initialize the weights
        for (int i = 0; i < this.neuralNetworkConfiguration.getNbLayers() - 1; i++){
            this.neuralNetworkConfiguration.getLayers().get(i).initializeWeights(this.neuralNetworkConfiguration.getLayers().get(i-1).getNIn());
        }
    }

    /**
     * Feed the network with a data from the DataReader
     * specified in the NeuralNetworkConfiguration at the given epoch
     * @param epoch The index of the feature used to feed the neural Network
     */
    @Override
    public void feedNetwork(int epoch) {
        // Set the values of the input layer
        this.neuralNetworkConfiguration.getLayers().get(0).setActivations(this.neuralNetworkConfiguration.getDataReader().getSample(epoch));
    }

    /**
     *  Guess the given input
     */
    @Override
    public void guess(){
        this.guesser.run(this);
    }

    /**
     * Propagate the error backwards through the neural network
     * @param error The error to propagate backwards
     */
    @Override
    public void backpropagate(INDArray error){
        this.solver.run(this, error);
    }

    /**
     * Method to call to train the neural network
     */
    @Override
    public void train() {
        this.trainer.run(this);
    }

}
