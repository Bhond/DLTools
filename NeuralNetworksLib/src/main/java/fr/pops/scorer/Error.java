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
 * Name: Error.java
 *
 * Description: Class defining the error over the guessed result to train models.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.scorer;

import fr.pops.nn.layers.Layer;
import fr.pops.math.ArrayUtil;
import fr.pops.math.ndarray.INDArray;

public abstract class Error {

    /*****************************************
     *
     * Algebraic ops
     *
     *****************************************/
    /**
     * Quadratic error
     * @param layer
     * @param label
     * @return
     */
    public static INDArray quadraticError(Layer layer, INDArray label){
        INDArray error = ArrayUtil.add(layer.getActivations(), ArrayUtil.negate(label));
        return ArrayUtil.apply(x -> Math.pow(x, 2), error);
    }

    /**
     * Derivative of the quadratic error
     * @param layer
     * @param label
     * @return
     */
    public static INDArray dQuadraticError(Layer layer, INDArray label){
        INDArray error = ArrayUtil.add( layer.getActivations(), ArrayUtil.negate(label));
        return ArrayUtil.apply(x -> 2 * x, error);
    }
}
