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
 * Name: WeightInitUtil.java
 *
 * Description: Class used to redirect the initialisation of the weights
 *              depending on the method chosen
 *
 * Author: Charles MERINO
 *
 * Date: 14/01/2021
 *
 ******************************************************************************/
package fr.pops.nn.weights.weightInit;

import fr.pops.ndarray.INDArray;
import fr.pops.ndarray.Shape;
import fr.pops.popscst.cst.EnumCst;

public abstract class WeightInitUtil {

    /**
     * Initialize the weights
     * @param initMethod The method to use to initialize the weights
     * @param shape The shape of the value array
     * @param nIn Some int -> Check its description in the layer calling the method
     * @param nOut Some int -> Check its description in the layer calling the method
     * @return A new INDArray with the input shape and the values initialized according to the given method
     */
    public static INDArray init(EnumCst.WeightsInitMethod initMethod, Shape shape, int nIn, int nOut){

        IWeightInit weightInit;
        switch (initMethod){
            case XAVIER:
                weightInit = new WeightInitXavier();
                break;
            case UNIFORM:
                weightInit = new WeightInitUniform();
                break;
            case RELU:
                weightInit = new WeightInitRelu();
                break;
            default:
                System.out.println("The method to use to initialize the weights has not been specified. Check layer configuration");
                return null;
        }
        return weightInit.initialize(nIn, nOut, shape);
    }
}
