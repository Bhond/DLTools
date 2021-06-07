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
package fr.pops.nn.bias;

import fr.pops.jsonparser.IRecordable;
import fr.pops.math.ArrayUtil;
import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;
import fr.pops.math.ndarray.Shape;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Bias implements IRecordable {

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
    /**
     * @return The values of the bias
     */
    public INDArray getValue(){return this.value;}

    /**
     * @return The gradient of the bias
     */
    public INDArray getGradient(){return this.gradient;}

    /**
     * @return The learning rates of the bias
     */
    public INDArray getLearningRates(){return this.learningRates;}

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Set the value of the bias
     * @param arr The values of the bias
     */
    public void setValue(INDArray arr){this.value = arr;}

    /*****************************************
     *
     * Load / save
     *
     *****************************************/
    /**
     * Cast the instance of the object into a JSONObject
     */
    @Override
    public JSONObject record() {
        // Initialization
        Map<String, Object> brace = new HashMap<>();

        // Fields
        brace.put("type", this.getClass());
        brace.put("value", this.value);

        return new JSONObject(brace);
    }

    /**
     * Load JSONObject
     *
     * @param jsonObject
     */
    @Override
    public void load(JSONObject jsonObject) {

    }

}
