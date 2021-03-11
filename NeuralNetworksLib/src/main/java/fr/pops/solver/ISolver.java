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
 * Name: Solver.java
 *
 * Description: Interface defining the solvers to train the neural networks.
 *
 * Author: Charles MERINO
 *
 * Date: 19/05/2019
 *
 ******************************************************************************/
package fr.pops.solver;

import fr.pops.nn.networks.CNN;
import fr.pops.nn.networks.Classifier;
import fr.pops.nn.networks.RNN;
import fr.pops.ndarray.INDArray;

public interface ISolver {

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    default void run(Classifier classifier, INDArray error){}

    default void run(RNN rnn, INDArray error){}

    default void run(CNN cnn, INDArray error){}

}
