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
 * Name: Uniform.java
 *
 * Description: Class used to initialize the weights with the Uniform method
 *
 * Author: Charles MERINO
 *
 * Date: 10/01/2021
 *
 ******************************************************************************/
package fr.pops.nn.weights.weightInit;

import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.math.ndarray.INDArray;
import fr.pops.math.ndarray.Shape;
import fr.pops.math.ArrayUtil;
import fr.pops.math.PopsMath;

public class WeightInitUniform implements IWeightInit {


    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done...
     */
    public WeightInitUniform(){
        // Nothing to be done...
    }

    /*****************************************
     *
     * Parent method
     *
     *****************************************/
    /**
     * Uniform distribution between - 1 / sqrt(nIn) and - 1 / sqrt(nIn)
     * @param nIn Some int -> Check its description in the layer calling the method
     * @param nOut Some int -> Check its description in the layer calling the method
     * @param shape The shape of output INDArray
     * @return An INDArray corresponding to the weight array initialized with the XAVIER method
     */
    @Override
    public INDArray initialize(int nIn, int nOut, Shape shape){
        return ArrayUtil.apply(x -> x + PopsMath.rand(PopsMath.rand(1 / Math.sqrt(nIn))), new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build());
    }
}
