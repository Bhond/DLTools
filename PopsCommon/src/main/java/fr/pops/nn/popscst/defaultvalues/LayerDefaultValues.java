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
 * Name: LayerDefaultValues.java
 *
 * Description: Default values for the layers in neural networks
 *
 * Author: Charles MERINO
 *
 * Date: 10/01/2021
 *
 ******************************************************************************/
package fr.pops.nn.popscst.defaultvalues;

import fr.pops.nn.popscst.cst.EnumCst;

public abstract class LayerDefaultValues {

    // Int
    public final static int DEFAULT_N_IN = 1;
    public final static int DEFAULT_N_OUT = 1;
    public final static int DEFAULT_KERNEL_STRIDE = 1;
    public final static int DEFAULT_PADDING = 0;
    public final static int DEFAULT_KERNEL_FILTER_WIDTH = 3;
    public final static int DEFAULT_KERNEL_FILTER_HEIGHT = 3;

    // Boolean
    public final static boolean DEFAULT_FLAG_N_IN_SPECIFIED = false;
    public final static boolean DEFAULT_FLAG_N_OUT_SPECIFIED = false;

    // Enum
    public final static EnumCst.ActivationFunction DEFAULT_ACTIVATION_FUNCTION_DENSE = EnumCst.ActivationFunction.SIGMOID;
    public final static EnumCst.ActivationFunction DEFAULT_ACTIVATION_FUNCTION_CONVOLUTION = EnumCst.ActivationFunction.RELU;
    public final static EnumCst.WeightsInitMethod DEFAULT_WEIGHT_INIT_METHOD = EnumCst.WeightsInitMethod.XAVIER;

}
