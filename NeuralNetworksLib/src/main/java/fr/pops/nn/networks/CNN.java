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
 * Name: CNN.java
 *
 * Description: Class inheriting from NeuralNetwork defining
 *              Convolution Neural Networks
 *
 * Author: Charles MERINO
 *
 * Date: 03/10/2020
 *
 ******************************************************************************/
package fr.pops.nn.networks;

import fr.pops.guesser.FeedForward;
import fr.pops.math.ndarray.INDArray;
import fr.pops.math.ndarray.Shape;
import fr.pops.nn.layers.FlattenLayer;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.solver.StochasticGradientDescent;
import fr.pops.trainer.StandardTrainer;

import java.io.Serializable;

public class CNN extends NeuralNetwork implements Serializable {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/

    /**
     * Standard ctor, nothing to be done
     */
    private CNN(){
        // Nothing to be done
    }

    /**
     * Ctor to call
     * @param neuralNetworkConfiguration The neural network configuration
     */
    public CNN(NeuralNetworkConfiguration neuralNetworkConfiguration){
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

        // Fill in variables common to all classifiers
        this.type = EnumCst.NeuralNetworkTypes.CNN;

        // TODO: to be adjusted...
        this.guesser = new FeedForward();
        this.solver = new StochasticGradientDescent();
        this.trainer = new StandardTrainer();

        /* *
         * Initialize the different structures not ready yet
         * The first layer is the input layer. It is always already ready at this point.
         * Nothing as to be done for it, for now...
         * */
        for (int i = 1; i < this.neuralNetworkConfiguration.getNbLayers(); i++){
            /* Slide a flatten layer if when necessary
             * If the layer at index i is a Dense layer which is flat and the previous layer
             * is either a Convolution layer or a Pooling layer which contain volumetric data
             * Data should flattened to make the join between feature recognition and
             * classification parts in the CNN.
             */
            if (this.neuralNetworkConfiguration.getLayers().get(i).getType() == EnumCst.LayerTypes.DENSE){
                if (this.neuralNetworkConfiguration.getLayers().get(i-1).getType() == EnumCst.LayerTypes.CONVOLUTION
                ||  this.neuralNetworkConfiguration.getLayers().get(i-1).getType() == EnumCst.LayerTypes.POOLING){
                    this.neuralNetworkConfiguration.getLayers().add(i, new FlattenLayer(this.neuralNetworkConfiguration.getLayers()
                                                                                                                       .get(i-1)
                                                                                                                       .getActivations()
                                                                                                                       .getShape()));
                }
            }

            // Other parameters of the layer
            if (!this.neuralNetworkConfiguration.getLayers().get(i).isReady()){
                this.neuralNetworkConfiguration.getLayers().get(i).controlLayerConfiguration(this.neuralNetworkConfiguration.getLayers().get(i-1));
            }
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
        INDArray data = this.neuralNetworkConfiguration.getDataReader().getSample(epoch);
        Shape inputShape = this.neuralNetworkConfiguration.getLayers().get(0).getActivations().getShape();
        if (data.getShape() != inputShape){
//            System.out.println("The shape of the data to be recognized is not the same as the shape of the input layer. The data has been reshaped. Be careful with the results.");
//            System.out.println("The shape of the data: " + data.getShape());
//            System.out.println("The shape of the input: " + inputShape);
            data.reshape(inputShape);
        }
        this.neuralNetworkConfiguration.getLayers().get(0).setActivations(data);
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
