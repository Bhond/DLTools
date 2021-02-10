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
 * Name: SequentialTrainer.java
 *
 * Description: Class to train neural networks with
 *              time series using sequences.
 *
 * Author: Charles MERINO
 *
 * Date: 19/05/2019
 *
 ******************************************************************************/
package fr.pops.trainer;

import fr.pops.datareader.Sequence;
import fr.pops.neuralnetworks.networks.NeuralNetwork;
import fr.pops.ndarray.INDArray;
import fr.pops.scorer.Error;
import fr.pops.scorer.SequenceGenerator;
import fr.pops.updater.Updater;

public class SequentialTrainer implements ITrainer {

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    @Override
    public void run(NeuralNetwork neuralNetwork) {
        // Initialization
        double initTime = System.currentTimeMillis();
        int toto = 0;
        // Loop over all the training samples
        while(neuralNetwork.getCurrentEpoch() < neuralNetwork.getNeuralNetworkConfiguration().getNbIterations()){
            // Set the sequence to run the training
            Sequence sequence = (Sequence) neuralNetwork.getNeuralNetworkConfiguration().getDataReader().getSample(neuralNetwork.getCurrentEpoch());
            int sequenceLength = sequence.size();

            for (int i = 0; i < sequenceLength; i++){

                // Set input : first layer
                neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(0).setActivations(sequence.get(i));
                // Feed forward pass
                neuralNetwork.guess();
                // Compute error
                INDArray error = Error.dQuadraticError(neuralNetwork.getNeuralNetworkConfiguration().getLayers().get(neuralNetwork.getNeuralNetworkConfiguration().getNbLayers()-1),
                        ((Sequence) neuralNetwork.getNeuralNetworkConfiguration().getDataReader().getLabel(neuralNetwork.getCurrentEpoch())).get(i));
                // Back propagate
                neuralNetwork.backpropagate(error);
            }

            // Update the weights and biases
            Updater.update(neuralNetwork);

            // Clear the weights and biases
            Updater.clearGradients(neuralNetwork);

            if (neuralNetwork.getCurrentEpoch() % 100 == 0){
                System.out.println("Epoch : " + neuralNetwork.getCurrentEpoch());
                System.out.println(SequenceGenerator.generateStringSequence(neuralNetwork, ((Sequence) neuralNetwork.getNeuralNetworkConfiguration().getDataReader().getSample(neuralNetwork.getCurrentEpoch())).get(0), 50));
                System.out.println("-------------------------------------------------------------------------" + "\n");
            }

            neuralNetwork.setCurrentEpoch(neuralNetwork.getCurrentEpoch() == neuralNetwork.getNeuralNetworkConfiguration().getDataReader().getNbOfTrainingSamples() ? 0 : neuralNetwork.getCurrentEpoch() + 1);

        }

        // Small terminal output
        double finalTime = System.currentTimeMillis();

        System.out.println("\nTraining done in " + (finalTime - initTime) / 1000 + " s.");
        System.out.println("Well guessed = " + toto);
    }

}
