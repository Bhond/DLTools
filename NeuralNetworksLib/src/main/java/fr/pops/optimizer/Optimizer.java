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
 * Name: Optimizer.java
 *
 * Description: Class defining optimizers.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.optimizer;

import fr.pops.math.ArrayUtil;
import fr.pops.math.ndarray.INDArray;

@SuppressWarnings("unused")
public class Optimizer {

    // Parameter to avoid dividing by 0
    private final static float EPSILON = 1E-8f;


    /*****************************************
     *
     * TODO: Restore this part

    // Compute L1 factor
    private Vector computeL1(Weight weight){
        return PopsMath.times(PopsMath.apply(x -> Math.abs(x), weight.getValue()), Vector.ones(weight.getValue().getNbRows()));
    }

   // Compute L2 factor
    private Vector computeL2(Weight weight){
        return PopsMath.times(PopsMath.apply(x -> Math.pow(x, 2), weight.getValue()), Vector.ones(weight.getValue().getNbRows()));
    }

     *
     *
     *****************************************/

    /*****************************************
     *
     * Adagrad
     *
     *****************************************/
    /**
     * Method for the learning rates of the biases
     * @param baseLearningRate
     * @param previousLearningRates
     * @param gradient
     * @return
     */
    private static INDArray Adagrad(double baseLearningRate, INDArray previousLearningRates, INDArray gradient){
        // w^2
        INDArray squaredGradient = fr.pops.math.ArrayUtil.hadamard(gradient, gradient);
        // v(t+1) = v(t) + w^2
        INDArray updatedLearningRates = fr.pops.math.ArrayUtil.add(previousLearningRates, squaredGradient);
        // res = eta / sqrt(v(t+1) + epsilon)
        INDArray toto = ArrayUtil.apply(x -> ((-1) * baseLearningRate * Math.pow(x + EPSILON, -(1/Math.sqrt(2)))), updatedLearningRates);
        return toto;
    }

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    /**
     * For weights
     * @param baseLearningRate
     * @param previousLearningRates
     * @param gradient
     * @return
     */
    public static INDArray updateLearningRates(double baseLearningRate, INDArray previousLearningRates, INDArray gradient){
        return Adagrad(baseLearningRate, previousLearningRates, gradient);
    }

}
