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
 * Name: Standard trainer.java
 *
 * Description: Class to train the neural networks
 *
 * Author: Charles MERINO
 *
 * Date: 19/05/2019
 *
 * TODO: Make this class more generic with CNN
 *       Refactor the input setting to include more types of data
 *       when CNN will be implemented (Matrices / custom type)
 *
 ******************************************************************************/
package fr.pops.nn.trainer;

import fr.pops.nn.consoledisplay.ConsoleDisplay;
import fr.pops.nn.ndarray.INDArray;
import fr.pops.nn.neuralnetworks.networks.NeuralNetwork;
import fr.pops.nn.popsmath.ArrayUtil;
import fr.pops.nn.popsmath.PopsMath;
import fr.pops.nn.popsmath.Vector;
import fr.pops.nn.updater.Updater;
import fr.pops.nn.scorer.Error;

import java.util.Arrays;

public class StandardTrainer implements ITrainer {

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    @Override
    public void run(NeuralNetwork neuralNetwork) {
        // Initialization
        double initTime = System.currentTimeMillis();
        int wellGuessed = 0; // TMP

        // Loop over all the training samples
        while(neuralNetwork.getCurrentEpoch() < neuralNetwork.getNeuralNetworkConfiguration().getNbIterations()) {

            for (int i = 0; i < neuralNetwork.getNeuralNetworkConfiguration().getBatchSize(); i++){

                // Set input : first layer
                neuralNetwork.feedNetwork(neuralNetwork.getCurrentEpoch());
                // Feed forward pass
                neuralNetwork.guess();
                // Compute error
                INDArray error = Error.quadraticError(neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(neuralNetwork.getNeuralNetworkConfiguration().getNbLayers()-1),
                                                    neuralNetwork.getNeuralNetworkConfiguration().getDataReader().getLabel(neuralNetwork.getCurrentEpoch()));

                INDArray dError = Error.dQuadraticError(neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(neuralNetwork.getNeuralNetworkConfiguration().getNbLayers()-1),
                                                        neuralNetwork.getNeuralNetworkConfiguration().getDataReader().getLabel(neuralNetwork.getCurrentEpoch()));
                /* TEMP ? */
                neuralNetwork.setCurrentError(ArrayUtil.average(error));
//                neuralNetwork.getScorer().fillConfusionMatrix(neuralNetwork.getCurrentEpoch(), neuralNetwork.getDataReader(), neuralNetwork.getLayers().get(neuralNetwork.getNbLayers()-1));
//                neuralNetwork.getScorer().computeF1();

                // Back propagate
                neuralNetwork.backpropagate(dError);

                // TMP
                int valueGuessed = PopsMath.indexOfMinMaxValue(Vector.toVector(neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(neuralNetwork.getNeuralNetworkConfiguration().getLayers().size()-1).getActivations().getData()), "max");
                int lbl = PopsMath.indexOfMinMaxValue(Vector.toVector(neuralNetwork.getNeuralNetworkConfiguration().getDataReader().getLabel(neuralNetwork.getCurrentEpoch()).getData()), "max") ;
                if (valueGuessed == lbl){
                    wellGuessed++;
                }

                // Add a progress bar
                double achieved = PopsMath.round( (double)(neuralNetwork.getCurrentEpoch()+1) / (neuralNetwork.getNeuralNetworkConfiguration().getNbIterations()), 2);
                ConsoleDisplay.progressBar("Training in progress ", achieved, Arrays.asList("Error"), Arrays.asList(1 - (double)wellGuessed / (neuralNetwork.getCurrentEpoch()+1)));

                // Increase step to control fr.pops.main loop
                // TODO: Check infinite loop when else
                neuralNetwork.setCurrentEpoch(neuralNetwork.getCurrentEpoch() == neuralNetwork.getNeuralNetworkConfiguration().getDataReader().getNbOfTrainingSamples() ? 0 : neuralNetwork.getCurrentEpoch() + 1);
            }

            // Update the weights and biases
            Updater.update(neuralNetwork);

            // Clear the weights and biases
            Updater.clearGradients(neuralNetwork);

        }

        // Small terminal output
        double finalTime = System.currentTimeMillis();

        System.out.println("\nTraining done in " + (finalTime - initTime) / 1000 + " s.");
        System.out.println("Well guessed = " + wellGuessed + " / " + neuralNetwork.getNeuralNetworkConfiguration().getNbIterations()); // TMP
    }
}
