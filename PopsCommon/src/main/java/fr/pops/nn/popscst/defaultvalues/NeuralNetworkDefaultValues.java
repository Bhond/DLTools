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
 * Name: NeuralNetworkDefaultValues.java
 *
 * Description: Default values for neural networks
 *
 * Author: Charles MERINO
 *
 * Date: 24/11/2020
 *
 ******************************************************************************/
package fr.pops.nn.popscst.defaultvalues;

import fr.pops.nn.datareader.DataReader;
import fr.pops.nn.popscst.cst.EnumCst;

public abstract class NeuralNetworkDefaultValues {

    // Dbl
    public static final double DEFAULT_LEARNING_RATE_VALUE = 0.01d;
    public static final double DEFAULT_LEARNING_RATE_MOMENTUM = 0.01d;
    public static final double DEFAULT_L1_LEARNING_RATE_VALUE = 0.01d;
    public static final double DEFAULT_L2_LEARNING_RATE_VALUE = 0.01d;

    // Int
    public static final int DEFAULT_BATCH_SIZE = 1;
    public static final int DEFAULT_NB_ITERATIONS = 1;

    // Boolean
    public static final boolean DEFAULT_REGULARISATION_ON = false;

    // Enum
    public final static EnumCst.Optimizer DEFAULT_OPTIMIZER = EnumCst.Optimizer.NONE;

    // Data reader
    public static final DataReader DEFAULT_DATA_READER = null;

}
