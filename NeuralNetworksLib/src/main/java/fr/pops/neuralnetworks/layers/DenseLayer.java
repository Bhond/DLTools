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
 * Name: DenseLayer.java
 *
 * Description: Class implementing Layers defining the dense layers.
 *              These layers are fully connecting each of its neurons to
 *              each neurons in the previous layer.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.neuralnetworks.layers;

import fr.pops.activator.Activator;
import fr.pops.neuralnetworks.weights.weight.StandardWeight;
import fr.pops.neuralnetworks.weights.weight.Weight;
import fr.pops.optimizer.Optimizer;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.ndarray.BaseNDArray;
import fr.pops.ndarray.INDArray;
import fr.pops.neuralnetworks.bias.Bias;
import fr.pops.popscst.defaultvalues.LayerDefaultValues;
import fr.pops.popsmath.*;

@SuppressWarnings("unused")
public class DenseLayer extends Layer {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected StandardWeight weight;

    /*****************************************
     *
     * Ctors
     *
     *****************************************/
    /**
     * Standard ctor, it cannot be used
     */
    private DenseLayer(){
        // Nothing to be done
    }

    /**
     * @param nOut The number neuron in the layer
     * @param actvFct The activation function the layer
     */
    protected DenseLayer(int nOut, EnumCst.ActivationFunction actvFct, EnumCst.WeightsInitMethod weightsInitMethod){
        this.isReady = false;
        this.type = EnumCst.LayerTypes.DENSE;
        this.nOut = nOut;
        this.activationFunction = actvFct;
        this.dActivationFunction = EnumCst.DActivationFunction.valueOf("d" + actvFct.name());
        this.weightsInitMethod = weightsInitMethod;
    }

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * This method initialize the weights
     * @param nIn The number of neurons in the previous Layer. It is its nOut
     */
    @Override
    public void initializeWeights(int nIn){
        this.weight = new StandardWeight(this.weightsInitMethod, nIn, this.nOut);
    }

    /**
     * @param previousLayer The previous layer which some info can come from
     *                      like nIn for Convolution layers ( = nOut of the previous)
     */
    @Override
    public void controlLayerConfiguration(Layer previousLayer) {
        // Initialize the weights
        initializeWeights(previousLayer.getNOut());

        // Initialize the fr.pops.main structures of the layer
        this.activations = new BaseNDArray.BaseNDArrayBuilder().zeros(nOut).build();
        this.bias = new Bias(nOut);
        this.z = new BaseNDArray.BaseNDArrayBuilder().zeros(nOut).build();

        this.isReady = true;
    }

    /**
     * Reshape the data from the previous layer to fit
     * the shape of the data expected in a dense layer.
     * Turns every INDArray to a flat vectorized one.
     * @param previousLayer Previous layer
     */
    @Override
    public void computeZ(Layer previousLayer){
        // Compute z
        this.z = ArrayUtil.add(ArrayUtil.dot(this.weight.getValue(), previousLayer.getActivations()), this.bias.getValue());
    }

    /**
     * Apply the selected non-linear function to the previously computed z
     */
    @Override
    public void activate(){
        this.activations = Activator.activate(this.activationFunction, this.z);
    }

    /**
     * Compute the error passing through this layer
     * @param sigma The error passed through the next layers
     * @return The new array passing through this layer
     */
    @Deprecated
    @Override
    public INDArray computeSigmaUnsafe(Weight weight, INDArray sigma){
        INDArray eta = Activator.dActivate(this.dActivationFunction, this.z);
        INDArray weightsT =  ArrayUtil.transpose(weight.getValue());
        INDArray tmp = ArrayUtil.dot(weightsT, sigma);
        return ArrayUtil.hadamard(eta, tmp);
    }

    /**
     * Transmit the error to the previous layer
     * @param layer The previous layer to transmit the to
     */
    @Override
    public void transmitSigma(Layer layer) {
        INDArray eta = Activator.dActivate(layer.getDActivationFunction(), layer.getZ());
        INDArray weightsT =  ArrayUtil.transpose(this.weight.getValue());
        INDArray tmp = ArrayUtil.dot(weightsT, this.sigma);
        INDArray sigmaToTransmit = ArrayUtil.hadamard(eta, tmp);
        layer.setSigma(sigmaToTransmit);
    }

    /**
     * Update the internal values such as internal weights and/or bias
     * @param baseLearningRate The learning rate to use to update the internal values
     * @param batchSize The batch size defined for mini-batch training
     * @param optimizer Specify the optimizer to use to update the values:
     *                      -   NONE
     *                      -   Adagrad
     */
    @Override
    public void updateInternalState(double baseLearningRate, int batchSize, EnumCst.Optimizer optimizer){
        // Update the weight array
        updateWeights(baseLearningRate, batchSize, optimizer);

        // Update the bias array
        updateBias(baseLearningRate, batchSize, optimizer);
    }

    /**
     * Clear the internal states
     */
    @Override
    public void clearInternalState(){
        this.weight.clearGradient();
        this.bias.clearGradient();
    }

    /*****************************************
     *
     * Update methods
     *
     *****************************************/
    /**
     * The method is used to update the weight array between the previous layer and this one
     * Computes the following equation:
     *      *  X (t + 1) = X (t) - (alpha / m) * grad(X)
     * @param learningRate The learning rate used to update the bias values
     * @param batchSize The batch size defined for mini-batch training
     * @param optimizer Specify the optimizer to use to update the values:
     *                      -   NONE
     *                      -   Adagrad
     */
    private void updateWeights(double learningRate, int batchSize, EnumCst.Optimizer optimizer){
        // Initialisation
        INDArray lambdaWeights;
        if (optimizer == EnumCst.Optimizer.ADAGRAD){
             lambdaWeights = Optimizer.updateLearningRates(learningRate,
                     this.weight.getLearningRates(),
                    this.weight.getGradient());
        } else {
             double eta = (-1) * learningRate / batchSize;
             lambdaWeights = ArrayUtil.apply(x -> x * eta, new BaseNDArray.BaseNDArrayBuilder().ones(this.weight.getGradient().getShape()).build());
        }
        // Actual update
        INDArray weightsGrad = ArrayUtil.hadamard(lambdaWeights, this.weight.getGradient());
        INDArray weightsUpdate = ArrayUtil.add(this.weight.getValue(), weightsGrad);
        this.weight.setValue(weightsUpdate);
    }

    /**
     * This method is used to update the bias array of this layer
     * Computes the following equation:
     *      *  X (t + 1) = X (t) - (alpha / m) * grad(X)
     * @param learningRate The learning rate used to update the bias values
     * @param batchSize The batch size defined for mini-batch training
     * @param optimizer Specify the optimizer to use to update the values:
     *                      -   NONE
     *                      -   Adagrad
     */
    private void updateBias(double learningRate, int batchSize, EnumCst.Optimizer optimizer){
        // Initialisation
        INDArray lambdaBiases;
        if (optimizer == EnumCst.Optimizer.ADAGRAD){
            lambdaBiases = Optimizer.updateLearningRates(learningRate,
                    this.bias.getLearningRates(),
                    this.bias.getGradient());
        } else {
            double eta = (-1) * learningRate / batchSize;
            lambdaBiases = ArrayUtil.apply(x -> x * eta,  new BaseNDArray.BaseNDArrayBuilder().ones(this.bias.getGradient().getShape()).build());
        }
        // Actual update
        INDArray biasGrad = ArrayUtil.hadamard(lambdaBiases, this.bias.getGradient());
        INDArray biasUpdate = ArrayUtil.add(this.bias.getValue(), biasGrad);
        this.bias.setValue(biasUpdate);
    }

    /*****************************************
     *
     * Parent getters
     *
     *****************************************/
    @Override
    public StandardWeight getWeight() {
        return this.weight;
    }

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class DenseLayerBuilder {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        private int nOut = LayerDefaultValues.DEFAULT_N_OUT;
        private EnumCst.ActivationFunction activationFunction = LayerDefaultValues.DEFAULT_ACTIVATION_FUNCTION_DENSE;
        private EnumCst.WeightsInitMethod weightsInitMethod = LayerDefaultValues.DEFAULT_WEIGHT_INIT_METHOD;

        /*****************************************
         *
         * Ctor
         *
         *****************************************/
        /**
         * Standard ctor, nothing is done
         */
        public DenseLayerBuilder(){
            // Nothing to be done
        }

        /*****************************************
         *
         * With methods
         *
         *****************************************/
        /**
         * @param nOut The number of output neurons
         * @return The builder itself
         */
        public DenseLayerBuilder withNOut(int nOut){
            this.nOut = nOut;
            return this;
        }

        /**
         * @param activationFunction The activation function
         * @return The builder itself
         */
        public DenseLayerBuilder withActivationFunction(String activationFunction){
            this.activationFunction = EnumCst.ActivationFunction.valueOf(activationFunction.toUpperCase());
            return this;
        }

        /**
         * @param activationFunction The activation function
         * @return The builder itself
         */
        public DenseLayerBuilder withActivationFunction(EnumCst.ActivationFunction activationFunction){
            this.activationFunction = activationFunction;
            return this;
        }

        /**
         * @param method The method to use to initialize the weight array
         * @return The builder itself
         */
        public DenseLayerBuilder withWeightInit(EnumCst.WeightsInitMethod method){
            this.weightsInitMethod = method;
            return this;
        }

        /*****************************************
         *
         * Build method
         *
         *****************************************/
        /**
         * Build the layer
         * @return The built layer
         */
        public DenseLayer build(){
            return new DenseLayer(this.nOut, this.activationFunction, this.weightsInitMethod);
        }
    }
}
