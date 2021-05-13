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
 * Name: KernelFilter.java
 *
 * Description: Class defining Convolution filters for Convolution layers
 *
 * Author: Charles MERINO
 *
 * Date: 03/10/2020
 *
 ******************************************************************************/
package fr.pops.nn.weights.weight;

import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;
import fr.pops.math.ndarray.Shape;
import fr.pops.nn.weights.weightInit.WeightInitUtil;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.math.ArrayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KernelFilter extends Weight {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected Shape kernelShape;
    protected List<INDArray> values = new ArrayList<>();
    protected List<INDArray> gradients = new ArrayList<>();
    protected List<INDArray> learningRatesPerElements = new ArrayList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor, nothing to be done
     */
    private KernelFilter(){
        // Nothing to be done
    }

    /**
     * @param initMethod The method used to initialize the weights
     * @param shape The shape to give to the filters
     */
    public KernelFilter(EnumCst.WeightsInitMethod initMethod, Shape shape, int nIn, int nOut){
        this.kernelShape = shape;
        this.initializeValues(initMethod, nIn, nOut);
    }

    /**
     * @param values The value to give to the weights
     */
    private KernelFilter(List<INDArray> values){
        this.values = values;
        // Initialize the gradient arrays
        this.gradients = values.stream()
                             .map(indArray -> new BaseNDArray.BaseNDArrayBuilder().zeros(indArray.getShape()).build())
                             .collect(Collectors.toList());
        // Initialize the gradient arrays
        this.learningRatesPerElements = values.stream()
                                             .map(indArray -> new BaseNDArray.BaseNDArrayBuilder().zeros(indArray.getShape()).build())
                                             .collect(Collectors.toList());
    }

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    /**
     * @param initMethod The method used to initialize the weights
     * @param nIn nOut of the previous layer
     * @param nOut nOut of the layer storing these weights
     */
    @Override
    protected void initializeValues(EnumCst.WeightsInitMethod initMethod, int nIn, int nOut) {
        for (int i = 0; i < nOut; i++) {
            // Initialize the values
            this.values.add(WeightInitUtil.init(initMethod, this.kernelShape, nIn, nOut));
            // Initialize the gradient as an empty matrix
            this.gradients.add(new BaseNDArray.BaseNDArrayBuilder().zeros(this.kernelShape).build());
            // Initialize the learning rates per element as an empty matrix
            this.learningRatesPerElements.add(new BaseNDArray.BaseNDArrayBuilder().zeros(this.kernelShape).build());
        }
    }

    /**
     * Convolution between sigma and the activations of the previous layer
     * @param sigma The error transmitted through the layers
     * @param activations The activations of the previous layer
     */
    @Override
    public void computeWeightsIndividualGradient(INDArray sigma, INDArray activations) {
        // TODO: First draft, optimize the computation to avoid recreating arrays when it is not necessary
        // Loop over all filters
        for (int w = 0; w < this.gradients.size(); w++){
            INDArray gradient = new BaseNDArray.BaseNDArrayBuilder().zeros(this.kernelShape).build();
            // Loop over every channels of the activations
            for (int c = 0; c < activations.getShape().getZAxisLength(); c++){
                INDArray activationChannel = activations.extractChannel(c);
                INDArray sigmaChannel = sigma.extractChannel(w);
                INDArray gradientChannel = ArrayUtil.convolve(activationChannel, sigmaChannel, 1); // Need to compute the stride
                gradient.merge(gradientChannel, c);
            }
            this.completeGradient(gradient, w);
        }
    }

    /**
     * @param arr The array containing new gradient values to add to the current gradient
     * @param i The index of the filter int values list
     */
    @Override
    public void completeGradient(INDArray arr, int i) {
        if (this.gradients.size() < i){
            this.gradients.add(arr);
        } else {
            this.gradients.set(i, ArrayUtil.add(this.gradients.get(i), arr));
        }
    }

    /**
     * Clear the gradient
     */
    @Override
    public void clearGradient() {
        this.gradients.clear();
        for (int i = 0; i < this.values.size(); i++) {
            this.gradients.add(new BaseNDArray.BaseNDArrayBuilder().zeros(this.kernelShape).build());
        }
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    @Override
    public INDArray getValue(int i){ return this.values.get(i); }

    @Override
    public INDArray getGradient(int i){ return this.gradients.get(i); }

    @Override
    public INDArray getLearningRates(int i){ return this.learningRatesPerElements.get(i); }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    public void setValue(int i, INDArray arr){ this.values.set(i, arr);}
}
