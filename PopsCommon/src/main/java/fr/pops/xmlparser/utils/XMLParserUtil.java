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
 * Name: XMLParserUtil.java
 *
 * Description: Abstract class to read and write common XML blocks
 *
 * Author: Charles MERINO
 *
 * Date: 11/11/2020
 *
 ******************************************************************************/
package fr.pops.xmlparser.utils;

import fr.pops.nn.ndarray.BaseNDArray;
import fr.pops.nn.ndarray.INDArray;
import fr.pops.nn.ndarray.Shape;
import fr.pops.cst.StrCst;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.util.StringTokenizer;

public abstract class XMLParserUtil {

    /*****************************************
     *
     * Writing Methods
     *
     *****************************************/
    /**
     * Write the type of the given object
     */
    public static void writeObjectType(XMLStreamWriter xmlsw, Object obj) throws XMLStreamException {
        xmlsw.writeStartElement(StrCst.DATA_TYPE);
        xmlsw.writeCharacters(XMLParserUtil.extractTypeFromObj(obj));
        xmlsw.writeEndElement();
    }

    /**
     * Write the type of the given object
     */
    public static void writeObjectValue(XMLStreamWriter xmlsw, Object obj) throws XMLStreamException {
        xmlsw.writeStartElement(StrCst.VALUE);
        xmlsw.writeCharacters(String.valueOf(obj));
        xmlsw.writeEndElement();
    }

    /**
     * Write a standard line
     * @param xmlsw The XML Stream writer to write on
     * @param tagName The tag name
     * @param value The value to write between the tags
     */
    public static void writeBaseLine(XMLStreamWriter xmlsw, String tagName, String value) throws XMLStreamException {
        xmlsw.writeStartElement(tagName);
            xmlsw.writeCharacters(value);
        xmlsw.writeEndElement();
    }

    /**
     * Write a standard line with namespace
     * @param xmlsw The XML Stream writer to write on
     * @param DataName The name of the data to write
     * @param data The data to write
     */
    public static void writeTypedData(XMLStreamWriter xmlsw, String DataName, Object data) throws XMLStreamException {
        xmlsw.writeStartElement(DataName);
        XMLParserUtil.writeObjectType(xmlsw, data);
        XMLParserUtil.writeObjectValue(xmlsw, data);
        xmlsw.writeEndElement();
    }

    /**
     * Write a NDArray
     * @param xmlsw The XML Stream writer to write on
     * @param arr The INDArray to write
     */
    public static void writeNDArrayElement(XMLStreamWriter xmlsw, String arrName, INDArray arr) throws XMLStreamException {
        // Write shape
        xmlsw.writeStartElement(arrName);
        xmlsw.writeStartElement(StrCst.SHAPE);
            XMLParserUtil.writeTypedData(xmlsw, StrCst.X_AXIS_LENGTH, arr.getShape().getXAxisLength());
            XMLParserUtil.writeTypedData(xmlsw, StrCst.Y_AXIS_LENGTH, arr.getShape().getYAxisLength());
            XMLParserUtil.writeTypedData(xmlsw, StrCst.Z_AXIS_LENGTH, arr.getShape().getZAxisLength());
        xmlsw.writeEndElement();
        // Write data
        xmlsw.writeStartElement(StrCst.DATA);
        int count = 0;
        for (double val : arr.getData()){
            xmlsw.writeCharacters(count < arr.getShape().getSize() - 1 ? val + " " : String.valueOf(val));
            count++;
        }
        xmlsw.writeEndElement();
        xmlsw.writeEndElement();
    }

    /*****************************************
     *
     * Reading Methods
     *
     *****************************************/
    /**
     * Read a standard line
     * @param xmlsr The XML Stream Reader to read from
     * @param tagName The tag name
     * @return The value between the specified tags in a string format
     */
    public static String readBaseLine(XMLStreamReader xmlsr, String tagName) throws XMLStreamException {
        // Retrieve the type of tag read
        int xmlsrType = 0;
        // Retrieve localName
        String localName = "";
        // Value returned
        String res = "";
        // xmlsr.hasNext() condition avoid reading a line that doesn't exist due to corruption of the file
        while (xmlsr.hasNext() && (xmlsrType != XMLStreamReader.END_ELEMENT || !localName.equals(tagName))){
            xmlsrType = xmlsr.next();
            switch (xmlsrType)
            {
                case XMLStreamReader.CHARACTERS:
                    res = xmlsr.getText();
                    break;
                case XMLStreamReader.END_ELEMENT:
                    localName = xmlsr.getLocalName();
                    break;
            }
        }
        return res;
    }

    /**
     * Read a standard line
     * @param xmlsr The XML Stream Reader to read from
     * @param tagName The tag name
     * @return The value between the specified tags in a string format
     */
    public static Object readTypedData(XMLStreamReader xmlsr, String tagName) throws XMLStreamException {
        // Retrieve the type of tag read
        int xmlsrType = 0;
        // Retrieve localName
        String localName = "";
        // Value returned
        Object res = "";
        // Type of data
        String type = "";
        // xmlsr.hasNext() condition avoid reading a line that doesn't exist due to corruption of the file
        while (xmlsr.hasNext() && (xmlsrType != XMLStreamReader.END_ELEMENT || localName != tagName)){
            xmlsrType = xmlsr.next();
            switch (xmlsrType)
            {
                case XMLStreamReader.START_ELEMENT:
                    localName = xmlsr.getLocalName();
                    xmlsr.next();
                    switch (localName)
                    {
                        case StrCst.DATA_TYPE:
                            type = xmlsr.getText();
                            break;
                        case StrCst.VALUE:
                            switch (type)
                            {
                                case StrCst.INTEGER:
                                    res = readInt(xmlsr);
                                    break;
                                case StrCst.DOUBLE:
                                    res = readDouble(xmlsr);
                                    break;
                                case StrCst.BASE_NDARRAY:
                                    res = readNDArrayElement(xmlsr, tagName);
                                    break;
                            }
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    localName = xmlsr.getLocalName();
                    break;
            }
        }
        return res;
    }

    /**
     * Read a NDArray
     * @param xmlsr The XML Stream Reader read to read from
     * @return The read array
     */
    public static INDArray readNDArrayElement(XMLStreamReader xmlsr, String arrName) throws XMLStreamException {
        // Initialize values
        double[] data = new double[0];
        Shape shape = null;
        // Retrieve the type of tag read
        int xmlsrType = 0;
        // Retrieve localName
        String localName = "";
        // xmlsr.hasNext() condition avoid reading a line that doesn't exist due to corruption of the file
        // </Data> is the last tag of the NDArray, no matter the array's name
        while (xmlsr.hasNext() && (xmlsrType != XMLStreamReader.END_ELEMENT || localName != arrName)){
            xmlsrType = xmlsr.next();
            switch (xmlsrType)
            {
                case XMLStreamReader.START_ELEMENT:
                    localName = xmlsr.getLocalName();
                    switch (localName)
                    {
                        case StrCst.SHAPE:
                            shape = XMLParserUtil.readShape(xmlsr);
                            break;
                        case StrCst.DATA:
                            data = XMLParserUtil.readDoubleArray(xmlsr);
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    localName = xmlsr.getLocalName();
                    break;
            }
        }
        return new BaseNDArray.BaseNDArrayBuilder().withData(data).withShape(shape).build();
    }

    /**
     * Read a Shape for INDArray
     * @param xmlsr The XML Stream Reader read to read from
     * @return The read shape
     */
    public static Shape readShape(XMLStreamReader xmlsr) throws XMLStreamException {
        // Initialize values
        int xAxisLength = 1;
        int yAxisLength = 1;
        int zAxisLength = 1;
        // Retrieve the type of tag read
        int xmlsrType = 0;
        // Retrieve localName
        String localName = "";
        // xmlsr.hasNext() condition avoid reading a line that doesn't exist due to corruption of the file
        // </Data> is the last tag of the NDArray, no matter the array's name
        while (xmlsr.hasNext() && (xmlsrType != XMLStreamReader.END_ELEMENT || localName != StrCst.SHAPE)){
            xmlsrType = xmlsr.next();
            switch (xmlsrType)
            {
                case XMLStreamReader.START_ELEMENT:
                    localName = xmlsr.getLocalName();
                    switch (localName)
                    {
                        case StrCst.X_AXIS_LENGTH:
                            xAxisLength = (int) XMLParserUtil.readTypedData(xmlsr, StrCst.X_AXIS_LENGTH);
                            break;
                        case StrCst.Y_AXIS_LENGTH:
                            yAxisLength = (int) XMLParserUtil.readTypedData(xmlsr, StrCst.Y_AXIS_LENGTH);
                            break;
                        case StrCst.Z_AXIS_LENGTH:
                            zAxisLength = (int) XMLParserUtil.readTypedData(xmlsr, StrCst.Z_AXIS_LENGTH);
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    localName = xmlsr.getLocalName();
                    break;
            }
        }
        return new Shape(xAxisLength, yAxisLength, zAxisLength);
    }

    /**
     * Read a double data array for INDArray
     * @param xmlsr The XML Stream Reader read to read from
     * @return The read data
     */
    public static double[] readDoubleArray(XMLStreamReader xmlsr) throws XMLStreamException {
        // Initialize values
        double[] res = new double[0];
        String arrString;
        // Retrieve the type of tag read
        int xmlsrType = 0;
        // Retrieve localName
        String localName = "";
        // xmlsr.hasNext() condition avoid reading a line that doesn't exist due to corruption of the file
        // </Data> is the last tag of the NDArray, no matter the array's name
        while (xmlsr.hasNext() && (xmlsrType != XMLStreamReader.END_ELEMENT || localName != StrCst.DATA)){
            xmlsrType = xmlsr.next();
            switch (xmlsrType)
            {
                case XMLStreamReader.CHARACTERS:
                    arrString = xmlsr.getText();
                    StringTokenizer tokenizer = new StringTokenizer(arrString);
                    int index = 0;
                    res = new double[tokenizer.countTokens()];
                    while(tokenizer.hasMoreElements()){
                        res[index] = Double.parseDouble((String) tokenizer.nextElement());
                        index++;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    localName = xmlsr.getLocalName();
                    break;
            }
        }
        return res;
    }

    /**
     * @param xmlsr The XML Stream Reader read to read from
     * @return The int value read
     */
    public static int readInt(XMLStreamReader xmlsr){
        return Integer.parseInt(xmlsr.getText());
    }

    /**
     * @param xmlsr The XML Stream Reader read to read from
     * @return The double value read
     */
    public static double readDouble(XMLStreamReader xmlsr){
        return Double.parseDouble(xmlsr.getText());
    }

    /*****************************************
     *
     * Misc
     *
     *****************************************/
    /**
     * With custom types the full path of the class is retrieved
     * @param obj The object to retrieve the type from
     * @return The data of the obj
     */
    public static String extractTypeFromObj(Object obj){
        return XMLParserUtil.extractTypeFromString(String.valueOf(obj.getClass()));
    }

    /**
     * With custom types the full path of the class is retrieved
     * @param type The string to extract the type from
     * @return The type of the data
     */
    public static String extractTypeFromString(String type){
        return type.substring(type.lastIndexOf(".")+1);
    }

}
