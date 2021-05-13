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
 * Name: LSTMCell.java
 *
 * Description: Class defining the LSTM cell for LSTM Neural nets
 *
 * Author: Charles MERINO
 *
 * Date: 12/05/2021
 *
 ******************************************************************************/
package fr.pops.nn.layers;

import fr.pops.math.ndarray.INDArray;
import fr.pops.nn.weights.weight.Weight;
import fr.pops.popscst.cst.EnumCst;

public class LSTMCell extends Layer {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standar ctor
     */
    private LSTMCell(){

    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Compute the value of neurons to activate
     *
     * @param previousLayer Previous layer
     */
    @Override
    public void computeZ(Layer previousLayer) {

    }

    /**
     * Apply the selected non-linear function to the previously computed z
     */
    @Override
    public void activate() {

    }

    /**
     * Compute the error passing through this layer
     *
     * @param weight The weight to update
     * @param sigma  The error passed through the previous layers
     * @return The new array passing through this layer
     */
    @Override
    public INDArray computeSigmaUnsafe(Weight weight, INDArray sigma) {
        return null;
    }

    /**
     * Transmit the sigma to the designated layer
     *
     * @param layer The previous layer to transmit the to
     */
    @Override
    public void transmitSigma(Layer layer) {

    }

    /**
     * Update the internal values such as internal weights and/or bias
     *
     * @param baseLearningRate The learning rate to use to update the internal values
     * @param batchSize
     * @param optimizer        The optimizer to use
     */
    @Override
    public void updateInternalState(double baseLearningRate, int batchSize, EnumCst.Optimizer optimizer) {

    }

    /**
     * @return The weights between the previous and this layer
     * The type of the data needs to be set when implementing
     * the layer
     * T is INDArray for DenseLayers, Recurrent Layers
     * T is List<INDArray> for Convolution layers
     */
    @Override
    public Weight getWeight() {
        return null;
    }

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class LSTMCellBuilder {

        public LSTMCell build(){
            return new LSTMCell();
        }
    }
}
