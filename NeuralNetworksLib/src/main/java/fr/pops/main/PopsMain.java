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
 * Name: PopsMain.java
 *
 * Description: Main class to run the whole soft
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.main;

import fr.pops.datareader.CifarDataset;
import fr.pops.datareader.DataReader;
import fr.pops.datareader.MNISTDataset;
import fr.pops.neuralnetworks.layers.ConvolutionLayer;
import fr.pops.neuralnetworks.layers.DenseLayer;
import fr.pops.neuralnetworks.layers.PoolingLayer;
import fr.pops.neuralnetworks.networks.CNN;
import fr.pops.neuralnetworks.networks.Classifier;
import fr.pops.neuralnetworks.networks.NeuralNetworkConfiguration;
import fr.pops.popscst.cst.EnumCst;
import fr.pops.popscst.cst.StrCst;
import fr.pops.xmlparser.neuralnetworks.NeuralNetworkToXMLParser;

import java.io.File;
import java.io.InputStream;

public class PopsMain {

    public static void main(String[] args) throws Exception {

        /*****************************************
         *
         * Tests
         *
         *****************************************/
//        // Convolution
//        INDArray toto = new BaseNDArray.BaseNDArrayBuilder().withData(new double[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25}).withShape(5,5,3).build();
//        System.out.println( "Toto \n" + toto.toString() + " \n");
//
//        INDArray kernel = new BaseNDArray.BaseNDArrayBuilder().withData(new double[]{1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1}).withShape(3,3,3).build();
//        System.out.println( "kernel \n" + kernel.toString() + " \n");
//
//        INDArray res = ArrayUtil.convolve(toto, kernel, 1);
//        System.out.println("res \n" + res.toString() + " \n");
//
//        INDArray test = ArrayUtil.apply(x -> x / 3, res);
//        System.out.println("test \n" + test.toString() + " \n");
//
//        DataReader dr = new MNISTDataset(EnumCst.RunningMode.TRAINING);
//        INDArray test = dr.getSample(0);
//        test.reshape(28,28,1);
//        INDArray filter = new BaseNDArray.BaseNDArrayBuilder().ones(5,5,1).build();
//
//        double t0 = System.currentTimeMillis();
//        INDArray res = ArrayUtil.convolve(test, filter, 1);
//
//        System.out.println("dt = " + (System.currentTimeMillis() - t0) / 1000 + " s");
//
//        System.out.println("shape: " + res.getShape());
//        System.out.println("res: " + res);
//
//        // Reshape
//        INDArray toto = new BaseNDArray.BaseNDArrayBuilder().withData(new double[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18}).withShape(3,3,2).build();
//        System.out.println("toto before reshaping: \n" + toto.toString() + " \n");
//
//        toto.reshape(toto.getShape().getSize());
//        System.out.println("toto after reshaping: \n" + toto.toString() + " \n");

//        // Pooling
//        INDArray toto = new BaseNDArray.BaseNDArrayBuilder().withData(new double[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25}).withShape(5,5,3).build();
//        System.out.println( "Toto \n" + toto.toString() + " \n");
//
//        Integer[] mask = ArrayUtil.maskedMaxPool(toto, new Shape.ShapeBuilder().withShape(2,2,3).build(), 1).getKey();
//        INDArray res = ArrayUtil.maxPool(toto, new Shape.ShapeBuilder().withShape(2,2,3).build(), 1);
//        System.out.println("res \n " + res);
//        System.out.println("mask " + "(" + mask.length + ")\n" + Arrays.toString(mask) + " \n");
//
//        INDArray invpool = ArrayUtil.invPool(res, mask, toto.getShape());
//        System.out.println("Inv: \n" + invpool);
//
//        // Rotation
//        INDArray toto = new BaseNDArray.BaseNDArrayBuilder().withData(new double[]{1,2,3,4,5,6,7,8,9}).withShape(3,3,1).build();
//        System.out.println("Toto \n" + toto);
//        INDArray rotatedToto = toto.rot180();
//        System.out.println("Rotated Toto \n" + rotatedToto);

        /*****************************************
         *
         * Classifier
         *
         *****************************************/
//        DataReader dr = new MNISTDataset(EnumCst.RunningMode.TRAINING);
//
//        NeuralNetworkConfiguration conf = new NeuralNetworkConfiguration.NeuralNetworkConfigurationBuilder()
//                .withLearningRate(0.1)
//                .withBatchSize(10)
//                .withNbIterations(6000)
//                .withRegularisation(false)
//                .withDataReader(dr)
//                .withInputLayer(784)
//                .withLayer(new DenseLayer.DenseLayerBuilder().withNOut(100).withActivationFunction("relu").withWeightInit(EnumCst.WeightsInitMethod.XAVIER).build())
//                .withLayer(new DenseLayer.DenseLayerBuilder().withNOut(100).withActivationFunction("relu").withWeightInit(EnumCst.WeightsInitMethod.XAVIER).build())
//                .withLayer(new DenseLayer.DenseLayerBuilder().withNOut(10).withActivationFunction("softmax").withWeightInit(EnumCst.WeightsInitMethod.XAVIER).build())
//                .build();
//
//        Classifier cl = new Classifier(conf);
//        cl.init();
//        cl.train();

//        NeuralNetworkToXMLParser.save("src/resources/config/MNIST_Classifier.xml", cl);

        /*****************************************
         *
         * CNN
         *
         *****************************************/
//        DataReader dr = new MNISTDataset(EnumCst.RunningMode.TRAINING);
//        NeuralNetworkConfiguration conf = new NeuralNetworkConfiguration.NeuralNetworkConfigurationBuilder()
//                .withLearningRate(0.1)
//                .withBatchSize(1)
//                .withRegularisation(false)
//                .withNbIterations(600)
//                .withDataReader(dr)
//                .withInputLayer(28,28,1)
//                .withLayer(new ConvolutionLayer.ConvolutionLayerBuilder().withNOut(5).withKernel2DShape(2,2).withStride(1).withWeightInit(EnumCst.WeightsInitMethod.XAVIER).build())
//                .withLayer(new PoolingLayer.PoolingLayerBuilder().withKernel2DShape(3,3).withStride(1).build())
//                .withLayer((new DenseLayer.DenseLayerBuilder().withNOut(100).withActivationFunction("relu").withWeightInit(EnumCst.WeightsInitMethod.XAVIER).build()))
//                .withLayer((new DenseLayer.DenseLayerBuilder().withNOut(10).withActivationFunction("softmax").withWeightInit(EnumCst.WeightsInitMethod.XAVIER).build()))
//                .build();
//
//        CNN cnn = new CNN(conf);
//        cnn.init();
//        cnn.train();

    }

}