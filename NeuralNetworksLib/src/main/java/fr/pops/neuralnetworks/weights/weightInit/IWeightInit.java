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
 * Name: WeightInit.java
 *
 * Description: Interface used to initialize the weights depending on the chosen method
 *
 * Author: Charles MERINO
 *
 * Date: 10/01/2021
 *
 ******************************************************************************/
package fr.pops.neuralnetworks.weights.weightInit;

import fr.pops.ndarray.INDArray;
import fr.pops.ndarray.Shape;

public interface IWeightInit {

    /*****************************************
     *
     * Method to override
     *
     *****************************************/
    public INDArray initialize(int nIn, int nOut, Shape shape);

}
