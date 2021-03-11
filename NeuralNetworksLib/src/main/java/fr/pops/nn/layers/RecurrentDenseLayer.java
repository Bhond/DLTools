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
 * Description: Class implementing Layers defining the recurrent dense layers.
 *              These layers are fully connecting each of its neurons to
 *              each neurons in the previous layer. They are also recursively
 *              connecting their current state to their previous state.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.layers;

import fr.pops.activator.Activator;
import fr.pops.nn.weights.weight.Weight;
import fr.pops.nn.weights.weight.StandardWeight;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.ndarray.BaseNDArray;
import fr.pops.ndarray.INDArray;
import fr.pops.optimizer.Optimizer;
import fr.pops.popscst.defaultvalues.LayerDefaultValues;
import fr.pops.popsmath.*;

@SuppressWarnings("unused")
public class RecurrentDenseLayer extends DenseLayer {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private INDArray previousActivations;
    private StandardWeight weightsBetweenTimeSteps;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    private RecurrentDenseLayer(int nOut, EnumCst.ActivationFunction actvFct, EnumCst.WeightsInitMethod weightsInitMethod) {
        // Invoke parent
        super(nOut, actvFct, weightsInitMethod);
        this.type = EnumCst.LayerTypes.RECURRENT_DENSE;
        // Add a recurrent state with its own weights
        this.previousActivations = new BaseNDArray.BaseNDArrayBuilder().zeros(this.weightsBetweenTimeSteps.getValue().getShape()).build();
        this.weightsBetweenTimeSteps = new StandardWeight(weightsInitMethod, this.nOut, this.nOut);
    }

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * Computes the element z = weight between this layer and the previous one * activations of the previous layer
     *                        + internal weight * activations of this layer at the previous time step
     *                        + bias
     * @param previousLayer The previous layer
     */
    @Override
    public void computeZ(Layer previousLayer){
        INDArray wa  = ArrayUtil.dot(this.weight.getValue(), previousLayer.getActivations());
        INDArray wh = ArrayUtil.dot(this.weightsBetweenTimeSteps.getValue(), this.activations);
        INDArray z = ArrayUtil.add(ArrayUtil.add(wa, wh), this.bias.getValue());
        this.z = z;
    }

    /**
     * Apply the selected non-linear function to the previously computed z
     */
    @Override
    public void activate(){
        this.previousActivations = this.activations;
        this.activations = Activator.activate(this.activationFunction, this.z);
    }

    /**
     * Compute the error passing through this layer
     * @param weight The weight to update
     * @param sigma The error passed through the previous layers
     * @return The new array passing through this layer
     */
    @Deprecated
    @Override
    public INDArray computeSigmaUnsafe(Weight weight, INDArray sigma){
        // Compute sigma
        INDArray eta = Activator.dActivate(this.dActivationFunction, this.z);
        INDArray weightsT =  ArrayUtil.transpose(weight.getValue());
        INDArray tmp = ArrayUtil.dot(eta, weightsT);
        INDArray newSigma = ArrayUtil.dot(tmp, sigma);

        // Complete internal weight gradient
        this.weightsBetweenTimeSteps.computeWeightsIndividualGradient(newSigma, this.previousActivations);

        // Return the new Sigma
        return newSigma;
    }

    /**
     * Transmit the error to the previous layer
     * @param layer The previous layer to transmit the to
     */
    @Deprecated
    @Override
    public void transmitSigma(Layer layer) {
        // TODO: To be be implemented with retro-propagation
    }

    /**
     * Update the internal values such as internal weights and/or bias
     * @param baseLearningRate The learning rate to use to update the internal values
     * @param optimizer The optimizer to use
     */
    @Override
    public void updateInternalState(double baseLearningRate, int batchSize, EnumCst.Optimizer optimizer){
        // Weights
        // Initialisation
        INDArray lambda;
        if (optimizer == EnumCst.Optimizer.ADAGRAD){
            lambda = Optimizer.updateLearningRates(baseLearningRate,
                    this.weightsBetweenTimeSteps.getLearningRates(),
                    this.weightsBetweenTimeSteps.getGradient());
        } else {
            lambda = ArrayUtil.apply(x -> x * (-1) * baseLearningRate, new BaseNDArray.BaseNDArrayBuilder().zeros(this.weightsBetweenTimeSteps.getValue().getShape()).build());
        }

        INDArray weightGrad = ArrayUtil.dot(lambda, this.weightsBetweenTimeSteps.getGradient());
        INDArray weightUpdate = ArrayUtil.add(this.weightsBetweenTimeSteps.getValue(), weightGrad);
        this.weightsBetweenTimeSteps.setValue(weightUpdate);

        // Biases
        INDArray lambdaBiases;
        // Initialisation
        if (optimizer == EnumCst.Optimizer.ADAGRAD){
            lambdaBiases = Optimizer.updateLearningRates(baseLearningRate,
                    this.bias.getLearningRates(),
                    this.bias.getGradient());
        } else {
            double eta = (-1) * baseLearningRate / batchSize;
            lambdaBiases = ArrayUtil.apply(x -> x * eta,  new BaseNDArray.BaseNDArrayBuilder().ones(this.bias.getGradient().getShape()).build());
        }

        INDArray biasGrad = ArrayUtil.hadamard(lambdaBiases, this.bias.getGradient());
        INDArray biasUpdate = ArrayUtil.add(this.bias.getValue(), biasGrad);
        this.bias.setValue(biasUpdate);
    }

    /**
     * Clear the internal states
     */
    @Override
    public void clearInternalState(){
        this.previousActivations = new BaseNDArray.BaseNDArrayBuilder().zeros(this.previousActivations.getShape()).build();
        this.weightsBetweenTimeSteps.clearGradient();
    }

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class RecurrentDenseLayerBuilder {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        private int nOut = LayerDefaultValues.DEFAULT_N_OUT;
        private EnumCst.ActivationFunction activationFunction = LayerDefaultValues.DEFAULT_ACTIVATION_FUNCTION_DENSE;
        EnumCst.WeightsInitMethod weightsInitMethod = LayerDefaultValues.DEFAULT_WEIGHT_INIT_METHOD;

        /*****************************************
         *
         * Ctor
         *
         *****************************************/
        /**
         * Standard ctor, nothing is done
         */
        public RecurrentDenseLayerBuilder(){
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
        public RecurrentDenseLayerBuilder withNOut(int nOut){
            this.nOut = nOut;
            return this;
        }

        /**
         * @param activationFunction The activation function
         * @return The builder itself
         */
        public RecurrentDenseLayerBuilder withActivationFunction(EnumCst.ActivationFunction activationFunction){
            this.activationFunction = activationFunction;
            return this;
        }

        /**
         * @param method The method used to initialize the both inter-layer & internal weights
         * @return The builder itself
         */
        public RecurrentDenseLayerBuilder withWeightInit(EnumCst.WeightsInitMethod method){
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
        public RecurrentDenseLayer build() {
            return new RecurrentDenseLayer(this.nOut, this.activationFunction, this.weightsInitMethod);
        }
    }
}
