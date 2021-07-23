package fr.pops.sockets.resquest;

import fr.pops.sockets.cst.EnumCst;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;

public class GetMNISTConfiguration extends Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private fr.pops.commoncst.EnumCst.NeuralNetworkTypes neuralNetworkType = fr.pops.commoncst.EnumCst.NeuralNetworkTypes.CLASSIFIER;
    private int nbLayers;
    private HashMap<Integer, Integer> layers = new HashMap<>();
    private double learningRate;
    private boolean regularisationOn;
    private double l1;
    private double l2;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Create a request that will be handled by the requestHandler
     */
    public GetMNISTConfiguration() {
        // Parent
        super(EnumCst.RequestTypes.GET_MNIST_CONFIGURATION);
    }

    /**
     * Ctor used to initialize the request with
     * its content
     * @param requestBody The request's body
     */
    public GetMNISTConfiguration(JsonObject requestBody) {
        super(EnumCst.RequestTypes.GET_MNIST_CONFIGURATION, requestBody);
    }

    /**
     * Create a request that will be handled by the requestHandler
     * @param rawParams The raw parameters to decode
     * @param length The request's length
     */
    public GetMNISTConfiguration(byte[] rawParams, int length) {
        // Parent
        super(EnumCst.RequestTypes.GET_MNIST_CONFIGURATION, rawParams, length);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Information format:
     *  - The state
     *  - The epoch of creation of the ping request
     */
    @Override
    public void encode() {
        // Parent
        super.encode();

        // Type
        this.encoderDecoderHelper.encodeInt32(this.neuralNetworkType.ordinal());

        // Nb layers
        this.encoderDecoderHelper.encodeInt32(this.nbLayers);

        // Layers
        for (int i = 0; i < this.nbLayers; i++ ){
            this.encoderDecoderHelper.encodeInt32(this.layers.get(i));
        }

        // Learning rate
        this.encoderDecoderHelper.encodeDouble(this.learningRate);

        // Regularisation on
        this.encoderDecoderHelper.encodeBoolean(this.regularisationOn);

        // l1
        this.encoderDecoderHelper.encodeDouble(this.l1);

        // l2
        this.encoderDecoderHelper.encodeDouble(this.l2);
    }

    /**
     *  Decode the ping request
     */
    @Override
    public void decode() {
        // Parent
        super.decode();

        // Type
        int neuralNetworkType = this.encoderDecoderHelper.decodeInt32();
        this.neuralNetworkType = fr.pops.commoncst.EnumCst.NeuralNetworkTypes.values()[neuralNetworkType];

        // Nb layers
        this.nbLayers = this.encoderDecoderHelper.decodeInt32();

        // Layers
        for (int i = 0; i < this.nbLayers; i++ ){
            this.layers.put(i, this.encoderDecoderHelper.decodeInt32());
        }

        // Learning rate
        this.learningRate = this.encoderDecoderHelper.decodeDouble();

        // Regularisation on
        this.regularisationOn = this.encoderDecoderHelper.decodeBoolean();

        // l1
        this.l1 = this.encoderDecoderHelper.decodeDouble();

        // l2
        this.l2 = this.encoderDecoderHelper.decodeDouble();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The type of the neural network
     */
    public fr.pops.commoncst.EnumCst.NeuralNetworkTypes getNeuralNetworkType() {
        return this.neuralNetworkType;
    }

    /**
     * @return The number of layers
     */
    public int getNbLayers() {
        return this.nbLayers;
    }

    /**
     * @return The map containing the pair Id, nOut
     */
    public HashMap<Integer, Integer> getLayers() {
        return this.layers;
    }

    /**
     * @return The learning rate
     */
    public double getLearningRate() {
        return this.learningRate;
    }

    /**
     * @return True if the regularisation is on
     */
    public boolean isRegularisationOn() {
        return this.regularisationOn;
    }

    /**
     * @return The l1 learning rate
     */
    public double getL1() {
        return this.l1;
    }

    /**
     * @return The l2 learning rate
     */
    public double getL2() {
        return this.l2;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the request's length
     */
    @Override
    protected void setRequestLength() {
        super.setRequestLength(Integer.BYTES, // neural network type
                Integer.BYTES, // Nb layers
                2 * this.nbLayers * Integer.BYTES, // Layers
                Double.BYTES, // Learning rate
                Integer.BYTES, // Regularisation on
                Double.BYTES, // l1
                Double.BYTES); // l2
    }

    /**
     * Set the type of the neural network
     * @param neuralNetworkType The type of the neural network
     */
    public void setNeuralNetworkType(fr.pops.commoncst.EnumCst.NeuralNetworkTypes neuralNetworkType) {
        this.neuralNetworkType = neuralNetworkType;
    }

    /**
     * Set the number of layers in the neural network
     * @param nbLayers The number of layers in the neural network
     */
    public void setNbLayers(int nbLayers) {
        this.nbLayers = nbLayers;
    }

    /**
     * Set the map of the layers containing the pairs Id, nOut
     * @param layers The map of the layers containing the pairs Id, nOut
     */
    public void setLayers(HashMap<Integer, Integer> layers) {
        this.layers = layers;
    }

    /**
     * Set the learning rate
     * @param learningRate The learning rate of the neural network
     */
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    /**
     * Set the regularisation
     * @param regularisationOn The regularisation parameter
     */
    public void setRegularisationOn(boolean regularisationOn) {
        this.regularisationOn = regularisationOn;
    }

    /**
     * Set the l1 learning rate
     * @param l1 The l1 learning rate
     */
    public void setL1(double l1) {
        this.l1 = l1;
    }

    /**
     * Set the l2 learning rate
     * @param l2 The l2 learning rate
     */
    public void setL2(double l2) {
        this.l2 = l2;
    }
}
