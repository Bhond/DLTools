package fr.pops.popscst.cst;

public abstract class XMLStrCst {

    /*****************************************
     *
     * Header
     *
     *****************************************/
    public static final String VERSION = "1.0";
    public static final String ENCODING = "UTF-8";

    /*****************************************
     *
     * Data types and value
     *
     *****************************************/
    // Types
    public static final String DATA_TYPE = "DataType";
    // "Native" types
    public final static String DOUBLE = "Double";
    public final static String INTEGER = "Integer";
    public final static String STRING = "String";
    // Custom types
    public final static String BASE_NDARRAY = "BaseNDArray";

    // INDArray attributes
    public final static String SHAPE = "Shape";
    public final static String X_AXIS_LENGTH = "xAxisLength";
    public final static String Y_AXIS_LENGTH = "yAxisLength";
    public final static String Z_AXIS_LENGTH = "zAxisLength";
    public final static String DATA = "Data";

    // Value
    public static final String VALUE = "Value";

    /*****************************************
     *
     * Neural Network info
     *
     *****************************************/
    public static final String CONFIG = "Config";
    public static final String NN_TYPE = "NNType";
    public static final String CLASSIFIER = "CLASSIFIER";
    public static final String CNN = "CNN";
    public static final String RNN = "RNN";
    public static final String LSTM = "LSTM";

    /*****************************************
     *
     * Neural Network architecture
     *
     *****************************************/
    // Common
    public static final String ID = "Id";

    // Layers
    public static final String LAYERS = "Layers";
    public static final String LAYER = "Layer";
    public static final String LAYER_TYPE = "LayerType";
    public static final String ACTIVATION_FUNCTION = "ActivationFunction";
    public static final String ACTIVATIONS = "Activations";
    // Layer type
    public static final String DENSE_LAYER = "DenseLayer";
    public static final String RECURRENT_DENSE_LAYER = "RecurrentDenseLayer";
    public static final String CONVOLUTION_LAYER = "ConvolutionLayer";
    public static final String POOLING_LAYER = "PoolingLayer";

    //Weights
    public static final String WEIGHTS = "Weights";
    public static final String WEIGHT = "Weight";
    public static final String WEIGHT_VALUES = "WeightValues";

    // Biases
    public static final String BIASES = "Biases";
    public static final String BIAS = "Bias";
    public static final String BIAS_VALUES = "BiasValues";
}
