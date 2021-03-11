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
 * Name: Activator.java
 *
 * Description: Abstract class to distribute the activation function
 *              depending on each layer's setup.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.activator;

import fr.pops.nn.ndarray.INDArray;
import fr.pops.nn.popscst.cst.EnumCst;
import fr.pops.nn.popsmath.ArrayUtil;
import fr.pops.nn.popsmath.PopsMath;

public abstract class Activator {

    /**
     * @param activationFunction The activation function to apply to the input array
     * @param arr The array to apply the activation function
     * @return The same array with the given function applied to the input array
     */
    public static INDArray activate(EnumCst.ActivationFunction activationFunction, INDArray arr){
        INDArray res;
        if (activationFunction == EnumCst.ActivationFunction.SIGMOID){
            res = ArrayUtil.apply(PopsMath.sigmoid, arr);
        } else if (activationFunction == EnumCst.ActivationFunction.RELU){
            res =  ArrayUtil.apply(PopsMath.ReLu, arr);
        } else if (activationFunction == EnumCst.ActivationFunction.SOFTMAX){
            res =  PopsMath.softmax.apply(arr);
        } else if (activationFunction == EnumCst.ActivationFunction.TANH){
            res =  ArrayUtil.apply(PopsMath.tanh, arr);
        } else if (activationFunction == EnumCst.ActivationFunction.IDENTITY) {
            res = ArrayUtil.apply(PopsMath.identity, arr);
        } else {
            res = arr;
            System.out.println("Unknown activation function. Please check input.");
        }
        return res;
    }

    /**
     * @param dActivationFunction The dActivation function to apply to the input array
     * @param arr The array to apply the dActivation function
     * @return The same array with the given function applied to the input array
     */
    public static INDArray dActivate(EnumCst.DActivationFunction dActivationFunction, INDArray arr){
        INDArray res;
        if (dActivationFunction == EnumCst.DActivationFunction.dSIGMOID){
            res = ArrayUtil.apply(PopsMath.dsigmoid, arr);
        } else if (dActivationFunction == EnumCst.DActivationFunction.dRELU) {
            res = ArrayUtil.apply(PopsMath.dRelu, arr);
        } else if (dActivationFunction == EnumCst.DActivationFunction.dSOFTMAX){
            res =  PopsMath.dsoftmax.apply(arr);
        } else if (dActivationFunction == EnumCst.DActivationFunction.dTANH) {
            res = ArrayUtil.apply(PopsMath.dtanh, arr);
        } else if (dActivationFunction == EnumCst.DActivationFunction.dIDENTITY) {
            res = ArrayUtil.apply(PopsMath.identity, arr);
        } else {
            res = arr;
            System.out.println("Unknown derivative of the activation function. Please check input.");
        }
        return res;
    }

}
