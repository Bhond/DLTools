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
 * Name: Trainer.java
 *
 * Description: Interface defining the trainer for training neural networks.
 *
 * Author: Charles MERINO
 *
 * Date: 19/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.trainer;

import fr.pops.nn.neuralnetworks.networks.NeuralNetwork;

public interface ITrainer {

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
     void run(NeuralNetwork neuralNetwork);
}
