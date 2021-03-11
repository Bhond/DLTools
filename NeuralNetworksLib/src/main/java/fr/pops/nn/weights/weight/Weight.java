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
 * Name: Weight.java
 *
 * Description: Class defining the weights between layers.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.weights.weight;

import fr.pops.ndarray.INDArray;
import fr.pops.popscst.cst.EnumCst;

public abstract class Weight {

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * @param initMethod The method used to initialize the weights
     * @param nIn The number of neurons in the first layer to connect to
     * @param nOut The number of neurons in the second layer to connect to
     */
    protected abstract void initializeValues(EnumCst.WeightsInitMethod initMethod, int nIn, int nOut);

    /**
     * Compute individual gradients of the weights
     * @param sigma The error transmitted through the layers
     * @param activations The activations of the previous layer
     */
    public abstract void computeWeightsIndividualGradient(INDArray sigma, INDArray activations);

    /**
     * @param arr The array containing new gradient values to add to the current gradient
     */
    public void completeGradient(INDArray arr){};

    /**
     * @param arr The array containing new gradient values to add to the current gradient
     * @param i The index of the filter int values list
     */
    public void completeGradient(INDArray arr, int i){}

    /**
     * Clear the gradient
     */
    public abstract void clearGradient();

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public INDArray getValue(){  return null; }

    public INDArray getValue(int i){ return null; }

    public INDArray getGradient(){ return null; }

    public INDArray getGradient(int i){ return null; }

    public INDArray getLearningRates(){ return null; }

    public INDArray getLearningRates(int i){ return null; }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    public void setValue(INDArray arr){}

    public void setValue(INDArray arr, int i){}

}
