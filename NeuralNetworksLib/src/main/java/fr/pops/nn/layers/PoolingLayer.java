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
 * Name: PoolingLayer.java
 *
 * Description: Class defining the Pooling Layers in
 *              Convolutional Neural Networks.
 *
 * Author: Charles MERINO
 *
 * Date: 01/10/2020
 *
 ******************************************************************************/
package fr.pops.nn.layers;

import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;
import fr.pops.math.ndarray.Shape;
import fr.pops.nn.weights.weight.Weight;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.popscst.defaultvalues.LayerDefaultValues;
import fr.pops.math.ArrayUtil;
import fr.pops.math.PopsMath;
import java.util.AbstractMap;

public class PoolingLayer extends Layer {

    /*****************************************
     *
     * Ctors
     *
     *****************************************/
    private int stride;
    private int padding;
    private Shape kernelShape;
    private Integer[] mask;
    private Shape previousLayerShape;

    /*****************************************
     *
     * Ctors
     *
     *****************************************/
    /**
     * Standard ctor, nothing to be done
     */
    private PoolingLayer(){
        // Nothing to be done
    }

    /**
     * @param kernel2DShape The shape to give to the kernel filter in 2D, the depth is defined by nOut
     * @param stride The step to slide the kernel filters across the activation array in the previous layer
     */
    private PoolingLayer(Shape kernel2DShape, int stride, int padding){
        this.type = EnumCst.LayerTypes.POOLING;
        this.kernelShape = kernel2DShape;
        this.stride = stride;
        this.padding = padding;
    }

    /*****************************************
     *
     * Parent methods
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
     */
    @Override
    public void controlLayerConfiguration(Layer previousLayer) {
        // Fill in some variables
        // A pooling layer keeps the shape of activation array in the previous layer
        int nIn = previousLayer.getNOut();
        this.nIn = nIn;
        this.nOut = nIn;
        this.kernelShape = new Shape(this.kernelShape.getXAxisLength(), this.kernelShape.getYAxisLength(), this.nOut);

        /*
         * Previous layer values used to compute the shape of 1 channel of this layer
         * Retrieve the shape of the first kernel filter, there is at least one
         */
        Shape previousLayerActivationsShape = previousLayer.getActivations().getShape();
        this.previousLayerShape = previousLayerActivationsShape;
        int xAxisLength = 1;
        int yAxisLength = 1;
        if (previousLayerActivationsShape.getXAxisLength() > 1 && previousLayerActivationsShape.getXAxisLength() > 1){
            xAxisLength = ((previousLayerActivationsShape.getXAxisLength() - this.kernelShape.getXAxisLength()) / this.stride) + 1;
            yAxisLength = ((previousLayerActivationsShape.getYAxisLength() - this.kernelShape.getYAxisLength()) / this.stride) + 1;
        } else {
            System.out.println("Can't initialize a convolution layer from something else than a 2D+ array in the previous layer.");
        }

        /*
         * The shape of the layer has not been fully configured yet
         * It is finally done here, using the previous layer
         * along with the bias
         */
        this.z = new BaseNDArray.BaseNDArrayBuilder().zeros(xAxisLength, yAxisLength, nOut).build();
        this.activations = new BaseNDArray.BaseNDArrayBuilder().zeros(xAxisLength, yAxisLength, nOut).build();

        // The layer is now fully configured
        this.isReady = true;
    }

    /**
     * @param previousLayer Previous layer
     */
    @Override
    public void computeZ(Layer previousLayer){
        AbstractMap.SimpleEntry<Integer[], INDArray> pairMaskZ = ArrayUtil.maskedMaxPool(previousLayer.getActivations(), this.kernelShape, this.stride);
        this.mask = pairMaskZ.getKey();
        this.z = pairMaskZ.getValue();
    }

    /**
     * Apply the selected non-linear function to the previously computed z
     */
    @Override
    public void activate(){
        this.activations = ArrayUtil.apply(PopsMath.identity, this.z);
    }

    /**
     * Compute the error passing through this layer
     * For pooling layers the sigma is the values of the previous sigma
     * filtered by the mask
     * It reshapes the layer of the next layer is flat like a dense layer
     * @param weight The weight to update
     * @param sigma The error passed through the previous layers
     * @return The new array passing through this layer
     */
    @Deprecated
    @Override
    public INDArray computeSigmaUnsafe(Weight weight, INDArray sigma){
        if (sigma.getShape().getSize() != this.activations.getShape().getSize()){
            System.out.println("Invalid shape for sigma in PoolingLayer. Must have the same number of components");
            return new BaseNDArray.BaseNDArrayBuilder().zeros(1).build();
        } else if (sigma.getShape().getYAxisLength() == 1 && sigma.getShape().getZAxisLength() == 1){
            sigma.reshape(this.activations.getShape());
        }

        // Store sigma coming from the next layer
        this.sigma = ArrayUtil.invPool(sigma, this.mask, this.previousLayerShape);

        // Reverse pooling
        return this.sigma;
    }

    /**
     * Transmit the sigma to the designated layer
     * @param layer The previous layer to transmit the to
     */
    @Override
    public void transmitSigma(Layer layer) {
        // Reverse pooling
        INDArray newSigma = ArrayUtil.invPool(sigma, this.mask, this.previousLayerShape);
        layer.setSigma(newSigma);
    }

    /**
     * For pooling layers nothing has been updated
     * @param baseLearningRate The learning rate to use to update the internal values
     * @param optimizer The optimizer to use
     */
    @Override
    public void updateInternalState(double baseLearningRate,  int batchSize, EnumCst.Optimizer optimizer){
        // Nothing to be done
    }

    /**
     * Clear the internal states
     * For pooling layers nothing has been updated
     * Hence, nothing gets to be cleared
     */
    @Override
    public void clearInternalState(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Parent getters
     *
     *****************************************/
    /**
     * @return Null, Pooling don't have any weights
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
    public static class PoolingLayerBuilder {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        private int stride = LayerDefaultValues.DEFAULT_KERNEL_STRIDE;
        private int padding = LayerDefaultValues.DEFAULT_PADDING;
        private Shape kernel2DShape = new Shape(LayerDefaultValues.DEFAULT_KERNEL_FILTER_HEIGHT, LayerDefaultValues.DEFAULT_KERNEL_FILTER_WIDTH);

        /*****************************************
         *
         * Ctor
         *
         *****************************************/
        /**
         * Standard ctor, nothing to be done
         */
        public PoolingLayerBuilder() {
            // Nothing to be done
        }

        /*****************************************
         *
         * With methods
         *
         *****************************************/
        /**
         * The depth of the filter is given by nOut
         * @param width The width of the kernel filters
         * @param height The height of the kernel filters
         * @return The builder itself
         */
        public PoolingLayerBuilder withKernel2DShape(int width, int height){
            this.kernel2DShape = new Shape.ShapeBuilder().withShape(width, height).build();
            return this;
        }

        /**
         * @param stride The stride used to slide the kernel filters for the convolution
         * @return The builder itself
         */
        public PoolingLayerBuilder withStride(int stride){
            this.stride = stride;
            return this;
        }

        /**
         * Padding is not a working feature yet
         * @param padding The padding to add around the activations
         * @return The builder itself
         */
        @Deprecated
        public PoolingLayerBuilder withPaddingUnsafe(int padding){
            this.padding = padding;
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
        public PoolingLayer build(){
            return new PoolingLayer(this.kernel2DShape, this.stride, this.padding);
        }
    }
}
