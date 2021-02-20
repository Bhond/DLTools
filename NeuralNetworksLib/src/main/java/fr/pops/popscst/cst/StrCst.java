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
 * Name: StrCst.java
 *
 * Description: Abstract class storing String Constants like path or
 *              GUI displayed messages.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.popscst.cst;

public abstract class StrCst {

    /*****************************************
     *
     * Name of the whole project
     *
     *****************************************/
    public final static String ZE_NAME = "Pops";

    /*****************************************
     *
     * String format
     *
     *****************************************/
    public final static String INDARRAY_VALUES_FORMAT = "%6.3f";

    /*****************************************
     *
     * Network types
     *
     *****************************************/
    public static final String CLASSIFIER = "classifier";
    public static final String CNN = "CNN";
    public static final String RNN = "RNN";
    public static final String LSTM = "LSTM";

    /*****************************************
     *
     * Activation functions
     *
     *****************************************/
    public static final String SIGMOID = "sigmoid";
    public static final String RELU = "relu";
    public static final String SOFTMAX = "softmax";
    public static final String TANH = "tanh";

    /*****************************************
     *
     * dActivation functions
     *
     *****************************************/
    public static final String DSIGMOID = "dsigmoid";
    public static final String DRELU = "drelu";
    public static final String DSOFTMAX = "dsoftmax";
    public static final String DTANH = "dtanh";

    /*****************************************
     *
     * Choice Box Value
     *
     *****************************************/
    // Networks
    public static final String MNIST = "MNIST";

    // Optimizer
    public static final String ADAGRAD = "adagrad";

    // Data
    public static final String MNIST_TRAIN = "MNIST Train";
    public static final String MNIST_TEST = "MNIST Test";

    // Mode
    public static final String GUESSING = "Guessing";
    public static final String TRAINING = "Training";

    /*****************************************
     *
     * Paths
     *
     *****************************************/
    // TODO: remove unnecessary csts
    // FXML
    public static final String PATH_MAIN_PANE_FXML = "/fxml/MainApp.fxml";
    public static final String PATH_MAIN_ICON = "/media/images/icon.jpg";
    public static final String PATH_MNIST_IMAGE_FXML = "/fxml/MnistImage.fxml";
    public static final String PATH_TEXT_DISPLAY_FXML = "/fxml/TextDisplay.fxml";

    // CSS
    public static final String PATH_CIRCLE_PROGRESS_INDICATOR_CSS = "/css/CircleProgressIndicator.css";
    public static final String PATH_RING_PROGRESS_INDICATOR_CSS = "/css/RingProgressIndicator.css";

    // Resources
    public static final String PATH_NETWORK_CONFIG = "../../../config/";

    // MNIST
    public static final String PATH_MNIST_NETWORK = "MNIST_Classifier.def";
    public static final String PATH_MNIST = "/data/MNIST/";
    public static final String PATH_MNIST_TRAINING_IMAGES = "train-images.idx3-ubyte";
    public static final String PATH_MNIST_TRAINING_LABELS = "train-labels.idx1-ubyte";
    public static final String PATH_MNIST_TESTING_IMAGES = "train-images.idx3-ubyte"; // To be changed
    public static final String PATH_MNIST_TESTING_LABELS = "train-labels.idx1-ubyte"; // To be changed

    // CIFAR
    public static final String PATH_CIFAR = "/data/CIFAR/";
    public static final String PATH_CIFAR_TRAINING_IMAGES = "data_batch_1.bin"; // To be completed

}
