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
 * Name: Bias.java
 *
 * Description: Class defining the biases used by layers in Neural Networks.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.neuralnetworks.bias;

import fr.pops.ndarray.BaseNDArray;
import fr.pops.ndarray.INDArray;
import fr.pops.ndarray.Shape;
import fr.pops.popsmath.*;

public class Bias {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private INDArray value;
    private INDArray gradient;
    private INDArray learningRates;

    /*****************************************
     *
     * Ctors
     *
     *****************************************/
    /**
     * Standard ctor, nothing to be done
     */
    private Bias(){
        // Nothing to be done
    }

    /**
     * @param nbNeuron The number of neurons to attach a bias to
     */
    public Bias(int nbNeuron){
        this.initializeValues(nbNeuron);
    }

    /**
     * @param shape The shape of the bias values
     */
    public Bias(int... shape){
        this.initializeValues(shape);
    }

    /**
     * @param shape The shape of the bias values
     */
    public Bias(Shape shape){
        this.initializeValues(shape);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * @param nbNeuron The number of neurons to add a bias to
     */
    private void initializeValues(int nbNeuron){
        this.value = new BaseNDArray.BaseNDArrayBuilder().ones(nbNeuron).build();
        this.gradient = new BaseNDArray.BaseNDArrayBuilder().zeros(nbNeuron).build();
        this.learningRates = new BaseNDArray.BaseNDArrayBuilder().zeros(nbNeuron).build();
    }

    /**
     * @param shape The shape of the bias values
     */
    private void initializeValues(int... shape){
        this.value = new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build();
        this.gradient = new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build();
        this.learningRates = new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build();
    }

    /**
     * @param shape The shape of the bias values
     */
    private void initializeValues(Shape shape){
        this.value = new BaseNDArray.BaseNDArrayBuilder().ones(shape).build();
        this.gradient = new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build();
        this.learningRates = new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build();
    }

    /**
     * Compute individual gradients of the biases
     * @param sigma The error from the from the previous layer
     */
    public void computeBiasesIndividualGradient(INDArray sigma){
        completeGradient(sigma);
    }

    /**
     * Complete the gradient for training with mini batches
     * @param gradient The gradient to add to already computed gradients
     */
    public void completeGradient(INDArray gradient){
        this.gradient =  ArrayUtil.add(this.gradient, gradient);
    }

    /**
     * Clear the gradient
     */
    public void clearGradient(){
        this.gradient = new BaseNDArray.BaseNDArrayBuilder().zeros(this.value.getShape().getSize()).build();
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public INDArray getValue(){return this.value;}

    public INDArray getGradient(){return this.gradient;}

    public INDArray getLearningRates(){return this.learningRates;}

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    public void setValue(INDArray arr){this.value = arr;}

}
