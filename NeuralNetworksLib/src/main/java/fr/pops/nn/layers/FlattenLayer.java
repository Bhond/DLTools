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
 * Name: Flatten.java
 *
 * Description: Class defining the flatten layer to flatten the data to make the join
 *              between feature recognition and classification parts in the CNN.
 *
 * Author: Charles MERINO
 *
 * Date: 17/11/2020
 *
 ******************************************************************************/
package fr.pops.nn.layers;

import fr.pops.activator.Activator;
import fr.pops.ndarray.BaseNDArray;
import fr.pops.ndarray.INDArray;
import fr.pops.ndarray.Shape;
import fr.pops.nn.weights.weight.StandardWeight;
import fr.pops.nn.weights.weight.Weight;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.popsmath.ArrayUtil;
import fr.pops.popsmath.PopsMath;

public class FlattenLayer extends Layer {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private Shape previousLayerShape;
    private StandardWeight weight;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     *  Standard ctor, nothing to be done
     */
    private FlattenLayer(){
        // Nothing to be done
    }

    /**
     * @param shapeIn The shape of the previous layer for
     *                defining the size of the flatten layer and to reshape sigma during
     *                the backward phase
     */
    public FlattenLayer(Shape shapeIn){
        this.type = EnumCst.LayerTypes.FLATTEN;
        this.previousLayerShape = shapeIn;
        this.nOut = shapeIn.getSize();
        this.activationFunction = EnumCst.ActivationFunction.IDENTITY;
        this.dActivationFunction = EnumCst.DActivationFunction.dIDENTITY;
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
     * Flatten the previous layer
     * @param previousLayer Previous layer
     */
    @Override
    public void computeZ(Layer previousLayer) {
        INDArray dataToFlatten = new BaseNDArray(previousLayer.getActivations());
        dataToFlatten.reshape(previousLayer.getActivations().getShape().getSize());
        this.z = dataToFlatten;
    }

    /**
     * Activate the layer by applying the identity function to z
     */
    @Override
    public void activate() {
        this.activations = ArrayUtil.apply(PopsMath.identity, this.z);
    }

    /**
     * @param weight The weight to update
     * @param sigma The error passed through the previous layers
     */
    @Deprecated
    @Override
    public INDArray computeSigmaUnsafe(Weight weight, INDArray sigma) {
        INDArray eta = Activator.dActivate(this.dActivationFunction, this.z);
        INDArray weightsT =  ArrayUtil.transpose(weight.getValue());
        INDArray tmp = ArrayUtil.dot(weightsT, sigma);
        this.sigma = ArrayUtil.hadamard(eta, tmp);
        this.sigma.reshape(this.previousLayerShape);
        return this.sigma;
    }

    /**
     * Transmit the sigma to the designated layer
     * For a flatten layer sigma is not modified because this
     * type of layer doesn't modify the input, it only reshapes it
     * into a flattened layer
     * It is just a transition layer between a Convolution or a Pooling layer
     * to a Dense layer
     * @param layer The previous layer to transmit the to
     */
    @Override
    public void transmitSigma(Layer layer) {
        INDArray sigmaToTransmit = new BaseNDArray(this.sigma);
        sigmaToTransmit.reshape(this.previousLayerShape);
        layer.setSigma(sigmaToTransmit);
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
}
