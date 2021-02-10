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
 * Name: Xavier.java
 *
 * Description: Class used to initialize the weights with the Xavier method
 *
 * Author: Charles MERINO
 *
 * Date: 10/01/2021
 *
 ******************************************************************************/
package fr.pops.neuralnetworks.weights.weightInit;

import fr.pops.ndarray.BaseNDArray;
import fr.pops.ndarray.INDArray;
import fr.pops.ndarray.Shape;
import fr.pops.popsmath.ArrayUtil;
import fr.pops.popsmath.PopsMath;

public class WeightInitXavier implements IWeightInit {


    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done...
     */
    public WeightInitXavier(){
        // Nothing to be done...
    }

    /*****************************************
     *
     * Parent method
     *
     *****************************************/
    /**
     * Uniform distribution between - sqrt(6/(nIn + nOut)) and sqrt(6/(nIn + nOut))
     * @param nIn Some int -> Check its description in the layer calling the method
     * @param nOut Some int -> Check its description in the layer calling the method
     * @param shape The shape of output INDArray
     * @return An INDArray corresponding to the weight array initialized with the XAVIER method
     */
    @Override
    public INDArray initialize(int nIn, int nOut, Shape shape){
        double bound = Math.sqrt(6) / Math.sqrt(nIn + nOut);
        return ArrayUtil.apply(x -> x + PopsMath.rand(-bound, bound), new BaseNDArray.BaseNDArrayBuilder().zeros(shape).build());
    }
}
