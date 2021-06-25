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
 * Name: XMLParser.java
 *
 * Description: Parse xml files to write bean files
 *
 * Author: Charles MERINO
 *
 * Date: 20/02/2021
 *
 ******************************************************************************/
package fr.pops.xmlparser.xmlparser;

import fr.pops.commoncst.GeneratorCst;
import fr.pops.generator.beanfileconfig.BeanFileConfig;
import fr.pops.xmlparser.utils.XMLParserUtil;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XMLParser {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    List<BeanFileConfig> fileConfigs = new ArrayList<>();
    private XMLInputFactory factory;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public XMLParser(){
        this.onInit();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    private void onInit(){
        // Initialize reader
        this.factory = XMLInputFactory.newInstance();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Parse the file to a structure that will be used to build the bean
     * @param filepath The file to parse
     */
    public void parse(String filepath){
        // Build stream
        File file;
        XMLStreamReader xmlsr;

        try {
            file = new File(filepath);
            xmlsr = factory.createXMLStreamReader(new FileReader(file));
            List<HashMap<String, String>> properties = new ArrayList<>();
            // Loop over all lines
            while (xmlsr.hasNext()){
                // Retrieve the type of tag read
                int xmlsrType = xmlsr.next();

                // For now: only start element is useful.
                if (xmlsrType == XMLStreamReader.START_ELEMENT){
                    switch (xmlsr.getLocalName()){
                        case "Entry":
                            // TODO: Implement this this
                            //HashMap<String, String> entry = new HashMap<>().put(xmlsr.getAttributeValue(0), XMLParserUtil.readEntry(xmlsr));
                            break;

                        case "Group":
                            if (xmlsr.getAttributeValue(0).equals(GeneratorCst.PROPERTY)){
                                properties.add(this.readProperty(xmlsr));
                            }
                            break;
                    }
                }
            }

            // Add new bean config
            this.fileConfigs.add(new BeanFileConfig(filepath, properties));

        } catch (XMLStreamException | FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Parse a property defined between <Property></Property> tags
     * Mandatory values:
     *  - Name
     *  - Type
     *  - Default value
     * Facultative values:
     *  - isComputed
     *  - isInternal
     * @param xmlsr The xml stream reader
     * @throws XMLStreamException
     */
    private HashMap<String, String> readProperty(XMLStreamReader xmlsr) throws XMLStreamException {
        HashMap<String, String> property = new HashMap<>();
        int xmlsrType;
        while(xmlsr.hasNext()){
            xmlsrType = xmlsr.next();
            if (xmlsrType == XMLStreamReader.END_ELEMENT && xmlsr.getLocalName().equals(GeneratorCst.GROUP)){
                break;
            } else if (xmlsrType == XMLStreamReader.START_ELEMENT && xmlsr.getLocalName().equals(GeneratorCst.ENTRY)) {
                property.put(xmlsr.getAttributeValue(0), XMLParserUtil.readEntry(xmlsr));
            }
        }
        // Return the generated string
        return checkFacultativeFields(property);
    }

    /**
     * Check the facultative fields entered
     * If they are present, do nothing.
     * Else add them with their default value
     * @param property The hashmap of the property to complete if necessary
     */
    private HashMap<String, String> checkFacultativeFields(HashMap<String, String> property){
        // Is computed
        if (!property.containsKey(GeneratorCst.PROPERTY_IS_COMPUTED)) property.put(GeneratorCst.PROPERTY_IS_COMPUTED, "false");

        // Is internal
        if (!property.containsKey(GeneratorCst.PROPERTY_IS_INTERNAL)) property.put(GeneratorCst.PROPERTY_IS_INTERNAL, "false");

        return property;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return Get the generateBean configs
     */
    public BeanFileConfig getFileConfigs(int i) {
        return this.fileConfigs.get(i);
    }
}
