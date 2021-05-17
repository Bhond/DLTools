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

import fr.pops.math.ArrayUtil;
import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;
import fr.pops.nn.bias.Bias;
import fr.pops.nn.weights.weight.StandardWeight;
import fr.pops.nn.weights.weight.Weight;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.popscst.defaultvalues.LayerDefaultValues;
import fr.pops.popsmath.PopsMath;

public class LSTMCell extends Layer {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    /*
     * The names are not that explicit but
     * they are pretty common to all definition of LSTMs.
     */
    // States
    private BaseNDArray cellState; // C_{t-1}
    private BaseNDArray hiddenState; // h_{t-1}

    // Weights
    private StandardWeight forgetWeights;    // W_f
    private StandardWeight inputWeights;     // W_i
    private StandardWeight candidateWeights; // W_c
    private StandardWeight outputWeights;    // W_o

    // Biases
    protected Bias forgetBias; // b_f
    protected Bias inputBias; // b_i
    protected Bias candidateBias; // b_c
    protected Bias outputBias; // b_o

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    private LSTMCell(int nOut, EnumCst.WeightsInitMethod weightsInitMethod){
        this.nOut = nOut;
        this.weightsInitMethod = weightsInitMethod;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Compute the value of neurons to activate
     * @param previousLayer Previous layer
     */
    @Override
    public void computeZ(Layer previousLayer) {
        // Concatenate hidden previous state and and the activations of th previous layer
        INDArray previousActivation = previousLayer.getActivations();
        this.z = previousActivation.concatenate(this.hiddenState);
    }

    /**
     * Apply the selected non-linear function to the previously computed z
     * TODO: change comment
     */
    @Override
    public void activate() {
        // ForgetGate
        BaseNDArray forgetState = this.computeForgetGate();

        // Update gate
        BaseNDArray updateState = this.computeUpdateGate();

        // Update cell state
        INDArray cellStateBufferForget = ArrayUtil.dot(forgetState, this.cellState);
        this.cellState = (BaseNDArray) (ArrayUtil.add(cellStateBufferForget, updateState));

        // Output gate
        BaseNDArray output = this.computeOutputGate();

        // Fill in structures
        this.activations = new BaseNDArray(output);
        this.hiddenState = new BaseNDArray(output);
    }

    /**
     * Compute the forget gate
     */
    private BaseNDArray computeForgetGate(){
        INDArray forgetState = ArrayUtil.dot(this.forgetWeights.getValue(), this.z);
        forgetState = ArrayUtil.add(forgetState, this.forgetBias.getValue());
        return (BaseNDArray) ArrayUtil.apply(PopsMath.sigmoid, forgetState);
    }

    /**
     * Compute the update gate
     */
    private BaseNDArray computeUpdateGate(){
        // Input
        INDArray inputBuffer = ArrayUtil.dot(this.inputWeights.getValue(), this.z);
        inputBuffer = ArrayUtil.add(inputBuffer, this.inputBias.getValue());
        INDArray input = ArrayUtil.apply(PopsMath.sigmoid, inputBuffer);

        // Candidate
        INDArray candidateBuffer = ArrayUtil.dot(this.candidateWeights.getValue(), this.z);
        candidateBuffer = ArrayUtil.add(candidateBuffer, this.candidateBias.getValue());
        INDArray candidate = ArrayUtil.apply(PopsMath.tanh, candidateBuffer);

        return (BaseNDArray) ArrayUtil.dot(input, candidate);
    }

    /**
     * Compute the output gate
     */
    private BaseNDArray computeOutputGate(){
        // Output
        INDArray outputBuffer = ArrayUtil.dot(this.outputWeights.getValue(), this.z);
        outputBuffer = ArrayUtil.add(outputBuffer, this.outputBias.getValue());
        INDArray output = ArrayUtil.apply(PopsMath.sigmoid, outputBuffer);

        // Hidden state
        INDArray hiddenBuffer = ArrayUtil.apply(PopsMath.tanh, this.cellState);

        return (BaseNDArray) ArrayUtil.dot(output, hiddenBuffer);
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
     * @param batchSize The size of the batch
     * @param optimizer The optimizer to use
     */
    @Override
    public void updateInternalState(double baseLearningRate, int batchSize, EnumCst.Optimizer optimizer) {

    }

    /**
     * @return The weights between the previous and this layer
     * The type of the data needs to be set when implementing
     * the layer
     * T is INDArray for DenseLayers, Recurrent Layers, LSTM
     * T is List<INDArray> for Convolution layers
     */
    @Override
    public Weight getWeight() {
        return null;
    }

    /**
     * @return The bias of the layer
     */
    @Override
    public Bias getBias() {
        return null;
    }

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class LSTMCellBuilder {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        private int nOut = LayerDefaultValues.DEFAULT_N_OUT;
        private EnumCst.WeightsInitMethod weightsInitMethod = LayerDefaultValues.DEFAULT_WEIGHT_INIT_METHOD;

        /*****************************************
         *
         * Methods
         *
         *****************************************/
        /**
         * @param nOut The size of the hidden state "h_t"
         * @return The builder itself
         */
        public LSTMCellBuilder withNOut(int nOut){
            this.nOut = nOut;
            return this;
        }

        /**
         * @param method The method to use to initialize the weight array
         * @return The builder itself
         */
        public LSTMCellBuilder withWeightInit(EnumCst.WeightsInitMethod method){
            this.weightsInitMethod = method;
            return this;
        }

        /**
         * Build new LSTMCell
         * @return A new LSTMCell with the input specifications
         */
        public LSTMCell build(){
            return new LSTMCell(this.nOut,
                                this.weightsInitMethod);
        }
    }
}
