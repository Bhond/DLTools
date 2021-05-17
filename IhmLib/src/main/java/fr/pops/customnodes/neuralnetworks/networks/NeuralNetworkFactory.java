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
 * Name: NeuralNetworkFactory.java
 *
 * Description: Factory to retrieve a neural network of a given type
 *
 * Author: Charles MERINO
 *
 * Date: 16/05/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.networks;

import fr.pops.commoncst.EnumCst;

public abstract class NeuralNetworkFactory {

    /**
     * Build a neural network given the type
     * @param type The type of neural network to create
     * @return The neural network scene to create
     */
    public static NeuralNetwork get(EnumCst.NeuralNetworkTypes type){
        // Select which neural network to get
        switch (type){
            case CLASSIFIER:
                return new Classifier();
            default:
                System.out.println("Unknown type of neural network.");
                return null;
        }
    }

}
