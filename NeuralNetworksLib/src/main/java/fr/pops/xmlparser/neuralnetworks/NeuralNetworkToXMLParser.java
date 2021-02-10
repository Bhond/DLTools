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
 * Name: NeuralNetworkXMLParserUtil.java
 *
 * Description: Class used to save and load a Neural Network object
 *              to and from a XML file
 *
 * Author: Charles MERINO
 *
 * Date: 11/11/2020
 *
 * TODO: Complete the load and save methods with the missing attributes
 *
 ******************************************************************************/
package fr.pops.xmlparser.neuralnetworks;

import fr.pops.popscst.cst.EnumCst;
import fr.pops.popscst.cst.XMLStrCst;
import fr.pops.ndarray.INDArray;
import fr.pops.neuralnetworks.bias.Bias;
import fr.pops.neuralnetworks.layers.Layer;
import fr.pops.neuralnetworks.networks.NeuralNetwork;
import fr.pops.neuralnetworks.weights.weight.StandardWeight;
import fr.pops.xmlparser.utils.XMLParserUtil;

import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkToXMLParser {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // None

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Basic ctor
     */
    private NeuralNetworkToXMLParser(){}

    /*****************************************
     *
     * Saving Method
     *
     *****************************************/
    /**
     * Save a neural network in a XML format
     * @param fullpath The fullpath of the file to save the network to
     * @param nn The neural network to save
     * @throws IOException
     * @throws XMLStreamException
     */
    public static void save(String fullpath, NeuralNetwork nn) throws IOException, XMLStreamException {
        // Initialize
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        FileWriter output = new FileWriter(fullpath);
        XMLStreamWriter xmlsw = outputFactory.createXMLStreamWriter(output);

        // Start file
        xmlsw.writeStartDocument(XMLStrCst.ENCODING, XMLStrCst.VERSION);

        // Root
        xmlsw.writeStartElement(XMLStrCst.CONFIG);

        // General info
        XMLParserUtil.writeBaseLine(xmlsw, XMLStrCst.NN_TYPE, nn.getType().toString());

        // Layers
        xmlsw.writeStartElement(XMLStrCst.LAYERS);
        for (Layer layer : nn.getNeuralNetworkConfiguration().getLayers()){
            xmlsw.writeStartElement(XMLStrCst.LAYER);
            XMLParserUtil.writeBaseLine(xmlsw, XMLStrCst.LAYER_TYPE, XMLParserUtil.extractTypeFromObj(layer));
            XMLParserUtil.writeBaseLine(xmlsw, XMLStrCst.ACTIVATION_FUNCTION, String.valueOf(layer.getActivationFunction()));
            XMLParserUtil.writeNDArrayElement(xmlsw, XMLStrCst.BIAS, layer.getBias().getValue());
            xmlsw.writeEndElement();
        }
        xmlsw.writeEndElement();

        // Weights
        xmlsw.writeStartElement(XMLStrCst.WEIGHTS);
//        for (IWeight weight : nn.getWeights()){
//            xmlsw.writeStartElement(XMLStrCst.WEIGHT);
//            XMLParserUtil.writeNDArrayElement(xmlsw, XMLStrCst.WEIGHT_VALUES, weight.getValue());
//            xmlsw.writeEndElement();
//        }
        xmlsw.writeEndElement();

        //Close root
        xmlsw.writeEndElement();

        // End file
        xmlsw.writeEndDocument();

        // Close file
        xmlsw.flush();
        xmlsw.close();
    }

    /*****************************************
     *
     * Loading Method
     *
     *****************************************/
    /**
     * Read a neural network in a XML format
     * @param fullpath The path where the neural network to retrieve is stored
     * @return The neural network
     * @throws IOException
     * @throws XMLStreamException
     */
    public static NeuralNetwork load(String fullpath) throws IOException, XMLStreamException {
        // Initialize
        EnumCst.NeuralNetworkTypes type = EnumCst.NeuralNetworkTypes.UNKNOWN;
        List<Layer> layersList = new ArrayList<>();
        List<StandardWeight> weightsList = new ArrayList<>();
        List<Bias> biasesList = new ArrayList<>();
        // Initialize reader
        XMLInputFactory factory = XMLInputFactory.newInstance();
        File file = new File(fullpath);
        XMLStreamReader xmlsr = factory.createXMLStreamReader(new FileReader(file));

        // Read the file
        while (xmlsr.hasNext()) {
            // Retrieve the type of tag read
            int xmlsrType = xmlsr.next();

            // For now: only start element is useful.
            if (xmlsrType == XMLStreamReader.START_ELEMENT){
                // Check localName
                String localName = xmlsr.getLocalName();
                switch (localName){
                    case XMLStrCst.NN_TYPE:
                        type = NeuralNetworkToXMLParser.readNeuralNetworkType(xmlsr);
                        break;
                    case XMLStrCst.LAYER:
                        layersList.add((Layer) NeuralNetworkToXMLParser.readLayer(xmlsr));
                        break;
                    case XMLStrCst.WEIGHT:
                        weightsList.add((StandardWeight) NeuralNetworkToXMLParser.readWeight(xmlsr));
                        break;
                    case XMLStrCst.BIAS:
                        biasesList.add((Bias) NeuralNetworkToXMLParser.readBias(xmlsr));
                        break;
                }
            }
        }

        // Close file
        xmlsr.close();

        NeuralNetwork nn = null;
//        switch (type){
//            case CLASSIFIER:
//                nn = new Classifier();
//                break;
//            case RNN:
//                nn = new RNN();
//                break;
//            case LSTM:
//                nn = new RNN();
//                break;
//            case CNN:
//                nn = new CNN();
//                break;
//        }
//        // Add layers to the neural network
//        for (Layer layer : layersList){
//            nn.withLayer(layer);
//        }
//        // Add the weights to the neural network
//        for (Weight weight : weightsList){
//            nn.withWeight(weight);
//        }
//        // Add the biasess to the neural network
//        for (Bias bias : biasesList){
//            nn.withBias(bias);
//        }
        return nn;
    }

    /*****************************************
     *
     * Misc
     *
     *****************************************/
    /**
     * Extract a layer from the xml file
     */
    private static Layer readLayer(XMLStreamReader xmlsr) throws XMLStreamException {
        // Initialize
        EnumCst.LayerTypes layerType = EnumCst.LayerTypes.UNKNOWN;
        Integer id = 0;
        EnumCst.ActivationFunction activationFunctions = EnumCst.ActivationFunction.UNKNOWN;
        INDArray activations = null;

        // Retrieve the type of tag read
        int xmlsrType = 0;
        // Retrieve localName
        String localName = "";
        // xmlsr.hasNext() condition avoid reading a line that doesn't exist due to corruption of the file
        while(xmlsr.hasNext() && (xmlsrType != XMLStreamReader.END_ELEMENT || localName != XMLStrCst.LAYER)){
            xmlsrType = xmlsr.next();
            switch (xmlsrType)
            {
                case XMLStreamReader.START_ELEMENT:
                    localName = xmlsr.getLocalName();
                    switch (localName)
                    {
                        case XMLStrCst.LAYER_TYPE:
                            layerType = NeuralNetworkToXMLParser.readLayerType(xmlsr);
                            break;

                        case XMLStrCst.ACTIVATION_FUNCTION:
                            activationFunctions = NeuralNetworkToXMLParser.readActivationFunction(xmlsr);
                            break;

                        case XMLStrCst.ACTIVATIONS:
                            activations = XMLParserUtil.readNDArrayElement(xmlsr, XMLStrCst.ACTIVATIONS);
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    localName = xmlsr.getLocalName();
                    break;
            }
        }

        // Build the layers
        // TODO: Complete here for every type of layer
        //       Missing the internal weights for recurrent layer
        //       Missing the nb of channels for convolution layer
        //       Missing the nb of channels for pooling layer
        Layer layer = null;
//            switch (layerType){
//                case DENSE:
//                    layer = new DenseLayer(activations, activationFunctions);
//                    break;
//                case RECURRENT_DENSE:
//                    layer = new DenseLayer(activations, activationFunctions);
//                    break;
//                case CONVOLUTION:
//                    layer = new DenseLayer(activations, activationFunctions);
//                    break;
//                case POOLING:
//                    layer = new DenseLayer(activations, activationFunctions);
//                    break;
//            }
        return layer;
    }

    /**
     * Retrieve layer type
     */
    private static EnumCst.NeuralNetworkTypes readNeuralNetworkType(XMLStreamReader xmlsr) throws XMLStreamException {
        // Initialize
        EnumCst.NeuralNetworkTypes type = EnumCst.NeuralNetworkTypes.UNKNOWN;
        // Read string value
        xmlsr.next();
        String typeStr = xmlsr.getText();
        // Convert it
        switch(typeStr)
        {
            case XMLStrCst.CLASSIFIER:
                type = EnumCst.NeuralNetworkTypes.CLASSIFIER;
                break;
            case XMLStrCst.RNN:
                type = EnumCst.NeuralNetworkTypes.RNN;
                break;
            case XMLStrCst.LSTM:
                type = EnumCst.NeuralNetworkTypes.LSTM;
                break;
            case XMLStrCst.CNN:
                type = EnumCst.NeuralNetworkTypes.CNN;
                break;
        }
        return type;
    }

    /**
     * Retrieve layer type
     */
    private static EnumCst.LayerTypes readLayerType(XMLStreamReader xmlsr) throws XMLStreamException {
        // Initialize
        EnumCst.LayerTypes type = EnumCst.LayerTypes.UNKNOWN;
        // Read string value
        xmlsr.next();
        String typeStr = xmlsr.getText();
        // Convert it
        switch(typeStr)
        {
            case XMLStrCst.DENSE_LAYER:
                type = EnumCst.LayerTypes.DENSE;
                break;
            case XMLStrCst.RECURRENT_DENSE_LAYER:
                type = EnumCst.LayerTypes.RECURRENT_DENSE;
                break;
            case XMLStrCst.CONVOLUTION_LAYER:
                type = EnumCst.LayerTypes.CONVOLUTION;
                break;
            case XMLStrCst.POOLING_LAYER:
                type = EnumCst.LayerTypes.POOLING;
                break;
        }
        return type;
    }

    /**
     * Retrieve the activation function
     */
    private static EnumCst.ActivationFunction readActivationFunction(XMLStreamReader xmlsr) throws XMLStreamException {
        xmlsr.next();
        String typeStr = xmlsr.getText();
        return EnumCst.ActivationFunction.valueOf(typeStr);
    }

    /**
     * Extract a weight from the xml file
     */
    private static StandardWeight readWeight(XMLStreamReader xmlsr) throws XMLStreamException {
        // Initialize
        Integer id = 0;
        INDArray weightsValues = null;
        // Retrieve the type of tag read
        int xmlsrType = 0;
        // Retrieve localName
        String localName = "";
        // xmlsr.hasNext() condition avoid reading a line that doesn't exist due to corruption of the file
        while(xmlsr.hasNext() && (xmlsrType != XMLStreamReader.END_ELEMENT || localName != XMLStrCst.WEIGHT)){
            xmlsrType = xmlsr.next();
            // For now: only start element is useful.
            switch (xmlsrType)
            {
                case XMLStreamReader.START_ELEMENT:
                    localName = xmlsr.getLocalName();
                    switch (localName)
                    {
                        case XMLStrCst.WEIGHT_VALUES:
                            weightsValues = XMLParserUtil.readNDArrayElement(xmlsr, XMLStrCst.WEIGHT_VALUES);
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    localName = xmlsr.getLocalName();
                    break;
            }
        }
        return new StandardWeight(weightsValues);
    }

    /**
     * Extract a bias from the xml file
     */
    private static Bias readBias(XMLStreamReader xmlsr) throws XMLStreamException {
        // Initialize
        Integer id = 0;
        INDArray biasValues = null;
        // Retrieve the type of tag read
        int xmlsrType = 0;
        // Retrieve localName
        String localName = "";
        // xmlsr.hasNext() condition avoid reading a line that doesn't exist due to corruption of the file
        while(xmlsr.hasNext() && (xmlsrType != XMLStreamReader.END_ELEMENT || localName != XMLStrCst.BIAS)){
            xmlsrType = xmlsr.next();
            // For now: only start element is useful.
            switch (xmlsrType)
            {
                case XMLStreamReader.START_ELEMENT:
                    localName = xmlsr.getLocalName();
                    switch (localName)
                    {
                        case XMLStrCst.BIAS_VALUES:
                            biasValues = XMLParserUtil.readNDArrayElement(xmlsr, XMLStrCst.BIAS_VALUES);
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    localName = xmlsr.getLocalName();
                    break;
            }
        }
        Bias bias =  new Bias(biasValues.getShape().getSize());
        bias.setValue(biasValues);
        return bias;
    }
}

