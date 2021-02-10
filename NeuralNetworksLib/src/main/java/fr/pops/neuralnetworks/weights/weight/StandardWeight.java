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
 * Name: StandardWeight.java
 *
 * Description: Class defining the weights between dense layers.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.neuralnetworks.weights.weight;

import fr.pops.ndarray.Shape;
import fr.pops.neuralnetworks.weights.weightInit.WeightInitUtil;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.ndarray.BaseNDArray;
import fr.pops.ndarray.INDArray;
import fr.pops.popsmath.*;

public class StandardWeight extends Weight {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private INDArray value;
    private INDArray gradient;
    private INDArray learningRatesPerElements;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor, nothing to be done
     */
    private StandardWeight(){
        // Nothing to be done
    }

    /**
     * @param nIn nOut of the previous layer
     * @param nOut nOut of the layer storing these weights
     * @param initMethod The method used to initialize the weights
     */
    public StandardWeight(EnumCst.WeightsInitMethod initMethod, int nIn, int nOut){
        this.initializeValues(initMethod, nIn, nOut);
    }

    /**
     * @param value The value to give to the weights
     */
    public StandardWeight(INDArray value){
        this.value = value;
        this.gradient = new BaseNDArray.BaseNDArrayBuilder().zeros(value.getShape()).build();
        this.learningRatesPerElements = new BaseNDArray.BaseNDArrayBuilder().zeros(value.getShape()).build();
    }

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * @param nIn The number of neurons in the first layer to connect to
     * @param nOut The number of neurons in the second layer to connect to
     * @param initMethod The method used to initialize the weights
     */
    @Override
    protected void initializeValues(EnumCst.WeightsInitMethod initMethod, int nIn, int nOut){
        // Build the shape of the arrays
        Shape shape = new Shape.ShapeBuilder().withShape(nOut, nIn).build();
        // Initialize the values
        this.value = WeightInitUtil.init(initMethod,shape, nIn, nOut);
        // Initialize the gradient as an empty matrix
        this.gradient =  new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build();
        // Initialize the learning rates per element as an empty matrix
        this.learningRatesPerElements =  new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build();
    }

    /**
     * Compute individual gradients of the weights
     * @param sigma The error transmitted through the layers
     * @param activations The activations of the previous layer
     */
    @Override
    public void computeWeightsIndividualGradient(INDArray sigma, INDArray activations){
        double[][] res = new double[sigma.getShape().getSize()][activations.getShape().getSize()];
        for(int i = 0; i < sigma.getShape().getSize(); i++){
            for(int j = 0; j < activations.getShape().getSize(); j++){
                res[i][j] = activations.getData()[j] * sigma.getData()[i];
            }
        }
        INDArray gradient = new BaseNDArray(res, sigma.getShape().getSize(), activations.getShape().getSize());
        completeGradient(gradient);
    }



    /**
     * @param arr The array containing new gradient values to add to the current gradient
     */
    @Override
    public void completeGradient(INDArray arr){
        this.gradient = ArrayUtil.add(this.gradient, arr);
    }

    /**
     * Clear the gradient
     */
    @Override
    public void clearGradient() {
        this.gradient = new BaseNDArray.BaseNDArrayBuilder().zeros(this.gradient.getShape()).build();
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    @Override
    public INDArray getValue(){ return this.value; }

    @Override
    public INDArray getGradient(){ return this.gradient; }

    @Override
    public INDArray getLearningRates(){ return this.learningRatesPerElements; }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    public void setValue(INDArray arr){ this.value = arr;}
}
