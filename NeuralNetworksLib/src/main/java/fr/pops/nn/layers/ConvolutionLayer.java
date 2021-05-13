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
 * Name: ConvolutionLayer.java
 *
 * Description: Class defining the Convolution Layers in
 *              Convolutional Neural Networks.
 *
 * Author: Charles MERINO
 *
 * Date: 01/10/2020
 *
 ******************************************************************************/
package fr.pops.nn.layers;

import fr.pops.activator.Activator;
import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;
import fr.pops.math.ndarray.Shape;
import fr.pops.nn.bias.Bias;
import fr.pops.nn.weights.weight.KernelFilter;
import fr.pops.nn.weights.weight.Weight;
import fr.pops.optimizer.Optimizer;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.popscst.defaultvalues.LayerDefaultValues;
import fr.pops.math.ArrayUtil;

@SuppressWarnings("unused")
public class ConvolutionLayer extends Layer {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Temp
    private KernelFilter weight;
    private Shape kernelShape;
    private int stride;
    private int padding;
    private int sigmaPadding;
    private Shape transmitterKernelShape;
    private Shape previousLayerActivationsShape;
    private boolean nInSpecified;

    /*****************************************
     *
     * Ctors
     *
     *****************************************/
    /**
     * Standard ctor, nothing to be done
     */
    private ConvolutionLayer(){
        // Nothing to be done
    }

    /**
     * @param nIn The zAxisLength / depth of the filter. Coming from the previous layer (its nOut).
     * @param nInSpecified The user can specify nIn. This prevents Pops from overriding it
     * @param nOut The zAxisLength / depth of the next layer. It is also the number of filter to apply
     * @param stride The step to slide the kernel filters across the activation array in the previous layer
     * @param kernel2DShape The shape to give to the kernel filter in 2D, the depth is defined by nOut
     * @param activationFunction The function used to activate the neurons. By default, and one should normally
     *                           use it, ReLu
     */
    private ConvolutionLayer(int nIn, boolean nInSpecified, int nOut, int stride, int padding, Shape kernel2DShape, EnumCst.ActivationFunction activationFunction, EnumCst.WeightsInitMethod weightsInitMethod){
        this.isReady = false;
        this.type = EnumCst.LayerTypes.CONVOLUTION;
        this.nIn = nIn;
        this.nInSpecified = nInSpecified;
        this.nOut = nOut;
        this.stride = stride;
        this.padding = padding;
        this.kernelShape = kernel2DShape;
        this.activationFunction = activationFunction;
        this.dActivationFunction = EnumCst.DActivationFunction.valueOf("d" + activationFunction.name());
        this.weightsInitMethod = weightsInitMethod;
    }

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * This method initialize the weights
     * @param nIn nOut of the previous layer
     */
    @Override
    public void initializeWeights(int nIn){
        this.kernelShape = new Shape(this.kernelShape.getXAxisLength(), this.kernelShape.getYAxisLength(), nIn);
        this.transmitterKernelShape = new Shape(this.kernelShape.getXAxisLength(), this.kernelShape.getYAxisLength(), this.nOut);
        this.weight = new KernelFilter(this.weightsInitMethod, this.kernelShape, nIn, this.nOut);
    }

    /**
     * Control the layer configuration
     * This method is called inside a Neural network init method
     * if the layer is not ready.
     * For instance with Convolution Layers, some parameters are not mandatory
     * or need information coming from structures not ready yet when instantiated
     * like nIn.
     */
    @Override
    public void controlLayerConfiguration(Layer previousLayer){
        /*
         * nIn is configured here if it has not been specified earlier
         * to avoid overriding the user's configuration
         */
        if (!this.nInSpecified){
            this.nIn = previousLayer.getNOut();
        }

        // Cache the previous layer's activations shape
        this.previousLayerActivationsShape = previousLayer.getActivations().getShape();

        /*
         * Initialize the weights
         * Need to be done here since nIn might have not been properly defined
         */
        this.initializeWeights(this.nIn);
        /*
         * Previous layer values used to compute the shape of 1 channel of this layer
         * Retrieve the shape of the first kernel filter, there is at least one
         * The shape is the same for all of the kernel filters
         */
        int xAxisLength = 1;
        int yAxisLength = 1;
        if (previousLayerActivationsShape.getXAxisLength() > 1 && this.previousLayerActivationsShape.getXAxisLength() > 1){
            xAxisLength = ((this.previousLayerActivationsShape.getXAxisLength() - this.weight.getValue(0).getShape().getXAxisLength()) / this.stride) + 1;
            yAxisLength = ((this.previousLayerActivationsShape.getYAxisLength() - this.weight.getValue(0).getShape().getYAxisLength()) / this.stride) + 1;
        } else {
            System.out.println("Can't initialize a convolution layer from something else than a 2D+ array in the previous layer.");
        }

        // Cache the padding to add to sigma for backward propagation
        // TODO: make computation possible with non squared padding
        int buffer = this.stride * (this.previousLayerActivationsShape.getXAxisLength() - 1) + this.kernelShape.getXAxisLength() - xAxisLength;
        this.sigmaPadding = buffer % 2 == 0 ? (int)(0.5 * buffer) : 0;

        /*
         * The shape of the layer has not been fully configured yet
         * It is finally done here, using the previous layer
         * along with the bias
         */
        this.z = new BaseNDArray.BaseNDArrayBuilder().zeros(xAxisLength, yAxisLength, nOut).build();
        this.activations = new BaseNDArray.BaseNDArrayBuilder().zeros(xAxisLength, yAxisLength, nOut).build();
        this.bias = new Bias(xAxisLength, yAxisLength, nOut);

        // The layer is now fully configured
        this.isReady = true;
    }

    /**
     * @param previousLayer Previous layer
     */
    @Override
    public void computeZ(Layer previousLayer){
        // Loop over the kernel filter values
        INDArray buffer = new BaseNDArray.BaseNDArrayBuilder().zeros(this.activations.getShape()).build();
        for (int i = 0; i < this.nOut; i++){
            INDArray convolution = ArrayUtil.convolve(previousLayer.getActivations(), this.weight.getValue(i), this.stride);
            buffer.merge(convolution, i);
        }
        // Add the bias, this should be a list of values and should be added in the loop
        this.z = ArrayUtil.add(buffer, this.bias.getValue());
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
     * @param weight The weight to update
     * @param sigma The error passed through the previous layers
     * @return The new array passing through this layer
     */
    @Deprecated
    @Override
    public INDArray computeSigmaUnsafe(Weight weight, INDArray sigma){
        // TODO: To be be implemented with retro-propagation or not...
        return null;
    }

    /**
     * Transmit the sigma to the designated layer
     * @param layer The previous layer to transmit the to
     */
    @Override
    public void transmitSigma(Layer layer) {
        // Add padding to sigma per every channel
        INDArray sigmaToConvolve = ArrayUtil.addPadding(this.sigma, this.sigmaPadding);

        // Loop over every previous layer channels
        INDArray buffer = new BaseNDArray.BaseNDArrayBuilder().zeros(this.previousLayerActivationsShape).build();
        for (int i = 0; i < this.previousLayerActivationsShape.getZAxisLength(); i++){
            // Loop over all kernel filters of this layer to extract 1 channel
            // to be used to convolve with sigma
            INDArray transmitterKernel = new BaseNDArray.BaseNDArrayBuilder().zeros(this.transmitterKernelShape).build();
            for (int w = 0; w < this.nOut; w++){
                transmitterKernel.merge(this.weight.getValue(w).extractChannel(i).rot180(), w);
            }
            // Make a convolution between padded sigma and each i-th channel of the kernel filters flipped 180deg
            buffer.merge(ArrayUtil.convolve(sigmaToConvolve, transmitterKernel, this.stride), i);
        }
        // Compute the Hadamard product between the computation above and the previous layer's z array
        INDArray sigmaToTransmit = ArrayUtil.hadamard(buffer, layer.getZ());
        layer.setSigma(sigmaToTransmit);
    }

    /**
     * Update the internal values such as internal weights and/or bias
     * @param baseLearningRate The learning rate to use to update the internal values
     * @param optimizer The optimizer to use
     */
    @Override
    public void updateInternalState(double baseLearningRate,  int batchSize, EnumCst.Optimizer optimizer){
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
        // Clear weight's gradient
        this.weight.clearGradient();
        // Clear bias's gradient
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
            lambdaWeights = ArrayUtil.apply(x -> x * eta, new BaseNDArray.BaseNDArrayBuilder().ones(this.weight.getValue(0).getShape()).build());
        }
        // Actual update
        for (int w = 0; w < this.nOut; w++){
            INDArray weightsGrad = ArrayUtil.hadamard(lambdaWeights, this.weight.getGradient(w));
            INDArray weightsUpdate = ArrayUtil.add(this.weight.getValue(w), weightsGrad);
            this.weight.setValue(w, weightsUpdate);
        }
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
    public KernelFilter getWeight() {
        return this.weight;
    }

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class ConvolutionLayerBuilder {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        private int nIn = LayerDefaultValues.DEFAULT_N_IN;
        private int nOut = LayerDefaultValues.DEFAULT_N_OUT;
        private int stride = LayerDefaultValues.DEFAULT_KERNEL_STRIDE;
        private int padding = LayerDefaultValues.DEFAULT_PADDING;
        private Shape kernel2DShape = new Shape(LayerDefaultValues.DEFAULT_KERNEL_FILTER_HEIGHT, LayerDefaultValues.DEFAULT_KERNEL_FILTER_WIDTH);
        private EnumCst.ActivationFunction activationFunction = LayerDefaultValues.DEFAULT_ACTIVATION_FUNCTION_CONVOLUTION;
        private EnumCst.WeightsInitMethod weightsInitMethod = LayerDefaultValues.DEFAULT_WEIGHT_INIT_METHOD;

        /*****************************************
         *
         * Flags
         *
         *****************************************/
        private boolean nInSpecified = LayerDefaultValues.DEFAULT_FLAG_N_IN_SPECIFIED;

        /*****************************************
         *
         * Ctor
         *
         *****************************************/
        /**
         * Standard ctor, nothing to be done
         */
        public ConvolutionLayerBuilder() {
            // Nothing to be done
        }

        /*****************************************
         *
         * With methods
         *
         *****************************************/
        /**
         * @param nIn The number of input from the previous layer
         *            Also defines the depth of the kernel filters
         *            nOut of the previous layer
         * @return The builder itself
         */
        public ConvolutionLayerBuilder withNIn(int nIn){
            this.nIn = nIn;
            this.nInSpecified = true;
            return this;
        }

        /**
         * @param nOut The number of channels of the next layer
         *             Defines the number of kernel filters in the layer
         *             nIn of the next layer
         * @return The builder itself
         */
        public ConvolutionLayerBuilder withNOut(int nOut){
            this.nOut = nOut;
            return this;
        }

        /**
         * The depth of the filter is given by nOut
         * @param width The width of the kernel filters
         * @param height The height of the kernel filters
         * @return The builder itself
         */
        public ConvolutionLayerBuilder withKernel2DShape(int width, int height){
            this.kernel2DShape = new Shape.ShapeBuilder().withShape(width, height).build();
            return this;
        }

        /**
         * @param stride The stride used to slide the kernel filters for the convolution
         * @return The builder itself
         */
        public ConvolutionLayerBuilder withStride(int stride){
            this.stride = stride;
            return this;
        }

        /**
         * Padding is not a working feature yet
         * @param padding The padding to add around the activations
         * @return The builder itself
         */
        @Deprecated
        public ConvolutionLayerBuilder withPaddingUnsafe(int padding){
            this.padding = padding;
            return this;
        }

        public ConvolutionLayerBuilder withActivationFunction(EnumCst.ActivationFunction activationFunction){
            this.activationFunction = activationFunction;
            return this;
        }

        /**
         * @param method The method to use to initialize the weight array
         * @return The builder itself
         */
        public ConvolutionLayerBuilder withWeightInit(EnumCst.WeightsInitMethod method){
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
        public ConvolutionLayer build(){
            return new ConvolutionLayer(this.nIn, this.nInSpecified,
                                        this.nOut,
                                        this.stride,
                                        this.padding,
                                        this.kernel2DShape,
                                        this.activationFunction,
                                        this.weightsInitMethod);
        }
    }
}
