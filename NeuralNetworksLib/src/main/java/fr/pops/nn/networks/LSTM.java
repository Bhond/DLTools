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
 * Name: LSTM.java
 *
 * Description: Class defining the LSTM neural network.
 *
 * Author: Charles MERINO
 *
 * Date: 12/05/2021
 *
 ******************************************************************************/
package fr.pops.nn.networks;

import fr.pops.math.ndarray.INDArray;

public class LSTM extends NeuralNetwork {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private LSTM(){
        // Nothing to be done
    }

    /**
     * Ctor to call
     * @param neuralNetworkConfiguration The neural network configuration
     */
    public LSTM(NeuralNetworkConfiguration neuralNetworkConfiguration){
        this.neuralNetworkConfiguration = neuralNetworkConfiguration;
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the neural network
     */
    @Override
    public void init() {

    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Feed the network with a data from the DataReader
     * specified in the NeuralNetworkConfiguration at the given epoch
     *
     * @param epoch The index of the feature used to feed the neural Network
     */
    @Override
    public void feedNetwork(int epoch) {

    }

    /**
     * Guess the given input
     */
    @Override
    public void guess() {

    }

    /**
     * Propagate the error backwards through the neural network
     *
     * @param error The error to propagate backwards
     */
    @Override
    public void backpropagate(INDArray error) {

    }

    /**
     * Method to call to train the neural network
     */
    @Override
    public void train() {

    }
}
