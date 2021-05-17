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
 * Name: InputLayer.java
 *
 * Description: Class defining the input layer to push data in the network.
 *
 * Author: Charles MERINO
 *
 * Date: 17/11/2020
 *
 ******************************************************************************/
package fr.pops.nn.layers;

import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;
import fr.pops.nn.bias.Bias;
import fr.pops.nn.weights.weight.Weight;
import fr.pops.popscst.cst.EnumCst;

public class InputLayer extends Layer {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     *  Standard ctor, nothing to be done
     */
    private InputLayer(){
        // Nothing to be done
    }

    /**
     * @param shape Shape of the input layer
     *              If the layer if a flat, 1D array, nIn (storing it is not mandatory) and nOut are the number of neurons in the layer.
     *              Otherwise, it is the depth of the tensor
     */
    public InputLayer(int... shape){
        this.type = EnumCst.LayerTypes.INPUT;
        this.nIn = 1;
        if (shape.length == 3 || shape.length == 2) { // 3D array
            this.nOut = shape[2];
        } else if (shape.length == 1){
            this.nOut = shape[0];
        } else {
            this.nOut = 1; // 4D+ array
        }

        this.activations = new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build();
        this.isReady = true;
    }

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * @param nIn nOut of the previous Layer
     */
    @Override
    public void initializeWeights(int nIn) {
        // Nothing to be done
    }

    /**
     * @param previousLayer Previous layer
     */
    @Override
    public void computeZ(Layer previousLayer) {
        // Nothing to be done
    }

    /**
     * Activate the layer by applying the non-linearity to z
     */
    @Override
    public void activate() {
        // Nothing to be done
    }

    /**
     * @param weight The weight to update
     * @param sigma The error passed through the previous layers
     */
    @Deprecated
    @Override
    public INDArray computeSigmaUnsafe(Weight weight, INDArray sigma) {
        // Nothing to be done
        return null;
    }

    /**
     * Transmit the sigma to the designated layer
     * @param layer The previous layer to transmit the to
     */
    @Override
    public void transmitSigma(Layer layer) {
        // Nothing to be done
    }

    /**
     * @param baseLearningRate The learning rate to use to update the internal values
     * @param batchSize The size of the mini batch for mini-batch training
     * @param optimizer The optimizer to use
     */
    @Override
    public void updateInternalState(double baseLearningRate, int batchSize, EnumCst.Optimizer optimizer) {
        // Nothing to be done
    }

    /**
     * @return The weight
     */
    @Override
    public Weight getWeight() {
        // Nothing to be done
        return null;
    }

    /**
     * @return The bias of the layer
     */
    @Override
    public Bias getBias() {
        return null;
    }
}
