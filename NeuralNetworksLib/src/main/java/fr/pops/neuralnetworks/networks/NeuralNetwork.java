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
 * Name: NeuralNetwork.java
 *
 * Description: Abstract class defining the Neural Networks.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.neuralnetworks.networks;

import fr.pops.guesser.IGuesser;
import fr.pops.ndarray.INDArray;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.solver.ISolver;
import fr.pops.trainer.ITrainer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

@SuppressWarnings({"unused"})
public abstract class NeuralNetwork implements Serializable {


    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Configuration
    protected NeuralNetworkConfiguration neuralNetworkConfiguration;

    // Type
    protected EnumCst.NeuralNetworkTypes type;

    // Current training epoch
    protected int currentEpoch = 0;

    // Current error
    protected double currentError;

    // Interfaces
    protected IGuesser guesser;
    protected ISolver solver;
    protected ITrainer trainer;

    // Listeners
    protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    /*****************************************
     *
     * Main methods
     *
     *****************************************/
    /**
     * Initialize the neural network
     */
    abstract public void init();

    /**
     * Feed the network with a data from the DataReader
     * specified in the NeuralNetworkConfiguration at the given epoch
     * @param epoch The index of the feature used to feed the neural Network
     */
    abstract public void feedNetwork(int epoch);

    /**
     *  Guess the given input
     */
    abstract public void guess();

    /**
     * Propagate the error backwards through the neural network
     * @param error The error to propagate backwards
     */
    abstract public void backpropagate(INDArray error);

    /**
     * Method to call to train the neural network
     */
    abstract public void train();

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * @return The neural network configuration
     */
    public NeuralNetworkConfiguration getNeuralNetworkConfiguration(){ return this.neuralNetworkConfiguration; }

    /**
     * @return The type of neural network
     */
    public EnumCst.NeuralNetworkTypes getType() { return this.type; }

    /**
     * @return Current step
     */
    public int getCurrentEpoch(){ return this.currentEpoch; }

    /**
     * @return Current error
     */
    public double getCurrentError(){ return this.currentError; }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    public void setCurrentEpoch(int currentEpoch){ this.currentEpoch = currentEpoch; }

    public void setCurrentError(double currentError){ this.currentError = currentError; }

    /*****************************************
     *
     * Listeners handling
     *
     *****************************************/
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
