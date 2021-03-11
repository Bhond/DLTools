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
package fr.pops.nn.solver;

import fr.pops.nn.ndarray.INDArray;
import fr.pops.nn.neuralnetworks.networks.CNN;
import fr.pops.nn.neuralnetworks.networks.Classifier;
import fr.pops.nn.neuralnetworks.networks.RNN;

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
