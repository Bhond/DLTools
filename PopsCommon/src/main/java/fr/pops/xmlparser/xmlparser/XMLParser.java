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
            List<String> propertiesAttributes = new ArrayList<>();

            // Loop over all lines
            while (xmlsr.hasNext()){
                // Retrieve the type of tag read
                int xmlsrType = xmlsr.next();

                // For now: only start element is useful.
                if (xmlsrType == XMLStreamReader.START_ELEMENT){

                    // Switch local names
                    switch (xmlsr.getLocalName()){

                        case GeneratorCst.PROPERTY:
                            propertiesAttributes.add(readProperty(xmlsr));
                            break;
                    }
                }
            }

            // Add new bean config
            this.fileConfigs.add(new BeanFileConfig(filepath, propertiesAttributes));

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
     * @param xmlsr The xml stream reader
     * @throws XMLStreamException
     */
    private String readProperty(XMLStreamReader xmlsr) throws XMLStreamException{
        /**
         *
         * TODO: Find a way to read optional values
         *
         */

        // Mandatory values
        // Read name
        String property = XMLParserUtil.readBaseLine(xmlsr, GeneratorCst.PROPERTY_NAME) + GeneratorCst.PROPERTY_TOKENIZER_DELIMITER;
        // Read type
        property += XMLParserUtil.readBaseLine(xmlsr, GeneratorCst.PROPERTY_TYPE) + GeneratorCst.PROPERTY_TOKENIZER_DELIMITER;
        // Read default value
        property += XMLParserUtil.readBaseLine(xmlsr, GeneratorCst.PROPERTY_DEFAULT) + GeneratorCst.PROPERTY_TOKENIZER_DELIMITER;

        // Return the generated string
        return property;

    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return Get the generate configs
     */
    public BeanFileConfig getFileConfigs(int i) {
        return this.fileConfigs.get(i);
    }
}
