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
 * Name: NeuralNetworkConfiguration.java
 *
 * Description: Class defining the configuration for Neural Networks:
 *                  - Hyper parameters
 *                  - Layers
 *
 * Author: Charles MERINO
 *
 * Date: 17/11/2020
 *
 ******************************************************************************/
package fr.pops.nn.networks;

import fr.pops.datareader.DataReader;
import fr.pops.nn.layers.InputLayer;
import fr.pops.nn.layers.Layer;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.popscst.cst.StrCst;
import fr.pops.popscst.defaultvalues.NeuralNetworkDefaultValues;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class NeuralNetworkConfiguration {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Hyper-parameters
    private double learningRate;
    private double l1LearningRate;
    private double l2LearningRate;
    private int batchSize;
    private int nbIterations;

    // Flags
    private boolean inTraining = false;
    protected boolean regularisationOn;

    // Enum
    private EnumCst.Optimizer optimizer;

    // Data reader
    private DataReader dataReader;

    // Layers
    private List<Layer> layers;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor, it cannot be used
     */
    private NeuralNetworkConfiguration(double learningRate,
                                       double l1LearningRate,
                                       double l2LearningRate,
                                       int batchSize,
                                       int nbIterations,
                                       boolean regularisationOn,
                                       EnumCst.Optimizer optimizer,
                                       List<Layer> layers,
                                       DataReader dataReader) {

        // Hyper parameters
        this.learningRate = learningRate;
        this.l1LearningRate = l1LearningRate;
        this.l2LearningRate = l2LearningRate;
        this.batchSize = batchSize;
        this.nbIterations = nbIterations;
        this.regularisationOn = regularisationOn;
        this.optimizer = optimizer;
        this.layers = layers;
        this.dataReader = dataReader;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return Learning rate used to update the weights and biases
     */
    public double getLearningRate() { return this.learningRate; }

    /**
     * @return Mini-batch size for mini-batch training feature
     */
    public int getBatchSize() { return this.batchSize; }

    /**
     * @return L1 learning rate
     */
    public double getL1LearningRate() { return this.l1LearningRate; }

    /**
     * @return L2 learning rate
     */
    public double getL2LearningRate() { return this.l2LearningRate; }

    /**
     * @return Nb of iteration
     */
    public int getNbIterations(){ return this.nbIterations; }

    /**
     * @return The data reader used
     */
    public DataReader getDataReader() { return this.dataReader; }

    /**
     * @return The optimizer used during training
     */
    public EnumCst.Optimizer getOptimizer() { return this.optimizer; }

    /**
     * @return The list of layers
     */
    public List<Layer> getLayers() { return this.layers; }

    /**
     * @return The number of layers
     */
    public int getNbLayers(){ return this.layers.size(); }

    /*****************************************
     *
     * States
     *
     *****************************************/
    /**
     * @return The state of the regularization option
     */
    public boolean isRegularisationOn() { return this.regularisationOn; }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    public void setLearningRate(double learningRate){ this.learningRate = learningRate; }

    public void setNbIterations(int nbIterations){ this.nbIterations = nbIterations; }

    public void setBatchSize(int size){ this.batchSize = size; }

    public void setL1(double l1){ this.l1LearningRate = l1; }

    public void setL2(double l2){ this.l2LearningRate = l2; }

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class NeuralNetworkConfigurationBuilder {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        // Hyper-parameters
        private double learningRate = NeuralNetworkDefaultValues.DEFAULT_LEARNING_RATE_VALUE;
        private double l1LearningRate = NeuralNetworkDefaultValues.DEFAULT_L1_LEARNING_RATE_VALUE;
        private double l2LearningRate = NeuralNetworkDefaultValues.DEFAULT_L2_LEARNING_RATE_VALUE;
        private int batchSize = NeuralNetworkDefaultValues.DEFAULT_BATCH_SIZE;
        private int nbIterations = NeuralNetworkDefaultValues.DEFAULT_NB_ITERATIONS;

        // Flags
        private boolean regularisationOn = NeuralNetworkDefaultValues.DEFAULT_REGULARISATION_ON;

        // Enum
        private EnumCst.Optimizer optimizer = NeuralNetworkDefaultValues.DEFAULT_OPTIMIZER;

        // Layers
        List<Layer> layers = new ArrayList<>();

        // Data reader
        private DataReader dataReader = NeuralNetworkDefaultValues.DEFAULT_DATA_READER;

        /*****************************************
         *
         * Ctor
         *
         *****************************************/
        public NeuralNetworkConfigurationBuilder(){
            // Nothing to be done
        }

        /*****************************************
         *
         * With methods
         *
         *****************************************/
        /**
         * Initialize the learning rate
         * @param delta The initial learning rate
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withLearningRate(double delta){
            this.learningRate = delta;
            return this;
        }

        /**
         * Allow regularisation
         * @param regOn Turn one regularization
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withRegularisation(boolean regOn){
            this.regularisationOn = regOn;
            return this;
        }

        /**
         * Initialize custom L1 learning rate
         * @param alpha1 The initial L1 value
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withL1(double alpha1){
            this.l1LearningRate = alpha1;
            return this;
        }

        /**
         * Initialize custom L1 learning rate
         * @param alpha2 The initial L2 learning rate
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withL2(double alpha2){
            this.l2LearningRate = alpha2;
            return this;
        }

        /**
         * Initialize the batch size for the training
         * @param s The batch size
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withBatchSize(int s){
            this.batchSize = s;
            return this;
        }

        /**
         * Initialize the number of iterations of the training loops
         * @param nbIterations The number of iterations
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withNbIterations(int nbIterations){
            this.nbIterations = nbIterations;
            return this;
        }

        /**
         * Add an input layer to the neural network
         * It always add it at the first position of the list
         * @param shape The shape of the input layer
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withInputLayer(int... shape) {
            this.layers.add(0, new InputLayer(shape));
            return this;
        }

        /**
         * Add a layer to the neural network
         * @param layer The layer to add to the network
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withLayer(Layer layer) {
            this.layers.add(layer);
            return this;
        }

        /**
         * Configure an optimizer to be used during the training
         * @param optimizerStr The name of the optimizer
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withOptimizer(String optimizerStr){
            if (optimizerStr.equalsIgnoreCase(StrCst.ADAGRAD)){
                this.optimizer = EnumCst.Optimizer.ADAGRAD;
            } else {
                System.out.println("Unknown given optimizer. No optimizer is used instead.");
            }
            return this;
        }

        /**
         * Initialize data reader
         * @param dataReader The data reader
         * @return The builder itself
         */
        public NeuralNetworkConfigurationBuilder withDataReader(DataReader dataReader){
            this.dataReader = dataReader;
            return this;
        }

        /*****************************************
         *
         * Build method
         *
         *****************************************/
        /**
         * Build the neural network configuration
         * @return The neural network configuration
         */
        public NeuralNetworkConfiguration build() {
            return new NeuralNetworkConfiguration(this.learningRate,
                                                  this.l1LearningRate,
                                                  this.l2LearningRate,
                                                  this.batchSize,
                                                  this.nbIterations,
                                                  this.regularisationOn,
                                                  this.optimizer,
                                                  this.layers,
                                                  this.dataReader);
        }
    }
}
