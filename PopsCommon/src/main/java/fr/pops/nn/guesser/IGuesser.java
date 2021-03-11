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
 * Name: Guesser.java
 *
 * Description: Interface allowing Neural Nets to propagate
 *              the input to the final layer and guess a feature.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.guesser;

import fr.pops.nn.neuralnetworks.networks.NeuralNetwork;

public interface IGuesser {

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    default void run(NeuralNetwork neuralNetwork){}

}
