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
 * Name: Layer.java
 *
 * Description: Interface defining the Layers in Neural Networks.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.layers;

import fr.pops.activator.Activator;
import fr.pops.nn.weights.weight.Weight;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.nn.bias.Bias;
import fr.pops.math.ArrayUtil;
import fr.pops.math.ndarray.INDArray;

import java.io.Serializable;

public abstract class Layer implements Serializable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected EnumCst.LayerTypes type;
    protected EnumCst.ActivationFunction activationFunction;
    protected EnumCst.DActivationFunction dActivationFunction;
    protected EnumCst.WeightsInitMethod weightsInitMethod;
    protected int nIn;
    protected int nOut;
    protected INDArray z;
    protected INDArray activations;
    protected INDArray sigma;
    protected boolean isReady;
    protected Bias bias;


    /*****************************************
     *
     * Methods to override
     *
     *****************************************/
    /**
     * Control the layer configuration
     * This method is called inside a Neural network init method
     * if the layer is not ready.
     * For instance with Convolution Layers, some parameters are not mandatory
     * or need information coming from structures not ready yet when instantiated
     * like nIn.
     * @param previousLayer The previous layer which some info can come from
     *                      like nIn for Convolution layers ( = nOut of the previous)
     */
    public void controlLayerConfiguration(Layer previousLayer){}

    /**
     * Initialize the weights depending on the layer type
     * @param nIn nOut of the previous layer
     */
    public void initializeWeights(int nIn){}

    /**
     * Compute the value of neurons to activate
     * @param previousLayer Previous layer
     */
    public abstract void computeZ(Layer previousLayer);

    /**
     * Apply the selected non-linear function to the previously computed z
     */
    public abstract void activate();

    /**
     * Initialize the error to back propagate
     * @param error The initial error relative to the label
     */
    public void initializeSigma(INDArray error){
        this.sigma =  ArrayUtil.hadamard(Activator.dActivate(this.dActivationFunction, this.z),
                                  error);
    }

    /**
     * Compute the error passing through this layer
     * @param weight The weight to update
     * @param sigma The error passed through the previous layers
     * @return The new array passing through this layer
     */
    @Deprecated
    public abstract INDArray computeSigmaUnsafe(Weight weight, INDArray sigma);

    /**
     * Transmit the sigma to the designated layer
     * @param layer The previous layer to transmit the to
     */
    public abstract void transmitSigma(Layer layer);

    /**
     * Update the internal values such as internal weights and/or bias
     * @param baseLearningRate The learning rate to use to update the internal values
     * @param optimizer The optimizer to use
     */
    public abstract void updateInternalState(double baseLearningRate, int batchSize, EnumCst.Optimizer optimizer);

    /**
     * Clear the internal states
     */
    public void clearInternalState(){}

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * @return True if the layer has been fully initialized
     *         False otherwise
     */
    public boolean isReady(){ return this.isReady; }

    /**
     * @return The type of the layer
     */
    public EnumCst.LayerTypes getType(){ return this.type; }

    /**
     * @return nIn
     */
    public int getNIn(){ return this.nIn; }

    /**
     * @return nOut
     */
    public int getNOut(){ return this.nOut; }

    /**
     * @return The activation function
     */
    public EnumCst.ActivationFunction getActivationFunction(){ return this.activationFunction; }

    /**
     * @return The derivative of the activation function
     */
    public EnumCst.DActivationFunction getDActivationFunction(){ return this.dActivationFunction; }

    /**
     * @return The activation of the neurons
     */
    public INDArray getActivations(){ return this.activations; }

    /**
     * @return The bias of the layer
     */
    public Bias getBias() { return this.bias; }

    /**
     * @return The z value of the layer: The pre-activations
     *         before being applied to the non-linear function
     */
    public INDArray getZ(){ return this.z; }

    /**
     * @return The propagated error through this layer
     */
    public INDArray getSigma(){ return this.sigma; }

    /**
     * @return The weights between the previous and this layer
     *         The type of the data needs to be set when implementing
     *         the layer
     *         T is INDArray for DenseLayers, Recurrent Layers
     *         T is List<INDArray> for Convolution layers
     */
    public abstract Weight getWeight();

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    public void setActivations(INDArray activations){ this.activations = activations; }

    public void setZ(INDArray z){
        this.z = z;
    }

    public void setSigma(INDArray sigma){ this.sigma = sigma; }
}
