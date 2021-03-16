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
 * Name: Common.java
 *
 * Description: Common values to Pops.
 *              Constants and enums.
 *
 * Author: Charles MERINO
 *
 * Date: 02/11/2019
 *
 ******************************************************************************/
package fr.pops.nn.nncst.cst;

public abstract class EnumCst {

    /*****************************************
     *
     * Enums for neural nets
     *
     *****************************************/
    /**
     * Type of Neural Network
     */
    public enum NeuralNetworkTypes { UNKNOWN, CLASSIFIER, CNN, RNN, LSTM }

    /**
     * Type of layer
     */
    public enum LayerTypes { UNKNOWN, INPUT, FLATTEN, DENSE, RECURRENT_DENSE, CONVOLUTION, POOLING }

    /**
     * File format
     */
    public enum FileFormat { IDX1, IDX3, WAVE }

    /**
     * These are the known features that can be parsed:
     * MNIST: Mnist image
     * TEXT: Raw text
     * IMAGE2D: 2D image with gray scale values
     * IMAGE3D; 3D image with RGB channels
     */
    public enum Feature { MNIST, TEXT, IMAGE2D, IMAGE3D }

    /**
     * Known features
     */
    // MNIST
    public enum MnistData { MNIST_TRAINING, MNIST_TESTING }
    // CIFAR
    public enum CifarData { CIFAR_TRAINING, CIFAR_TESTING }
    public enum CifarLabels { AIRPLANE, AUTOMOBILE, BIRD, CAT, DEER, DOG, FROG, HORSE, SHIP, TRUCK }

    /**
     * Running mode
     */
    public enum RunningMode { TRAINING, TEST }

    /**
     * Activation functions
     */
    public enum ActivationFunction { UNKNOWN, SIGMOID, RELU, SOFTMAX, TANH, IDENTITY }

    /**
     * Derivative of the activation functions
     */
    public enum DActivationFunction { dSIGMOID, dRELU, dSOFTMAX, dTANH, dIDENTITY }

    /**
     * Method to initialize the weights
     */
    public enum WeightsInitMethod { XAVIER, RELU, UNIFORM }

    /**
     * Optimizer
     */
    public enum Optimizer { NONE, ADAGRAD }

    /*****************************************
     *
     * Misc Enums
     *
     *****************************************/
    /**
     * Value type
     */
    public enum DataTypes { INTEGER, DOUBLE }

    /**
     * Colors of an image
     */
    public enum ImageTypes { COLORIZED, GREYSCALE }

}
