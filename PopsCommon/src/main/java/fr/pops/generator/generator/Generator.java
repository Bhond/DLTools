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
 * Name: Generator.java
 *
 * Description: Class generating bean classes from xml files
 *              Singleton
 *
 * Author: Charles MERINO
 *
 * Date: 20/02/2021
 *
 ******************************************************************************/
package fr.pops.generator.generator;

import fr.pops.commoncst.GeneratorCst;
import fr.pops.commoncst.StrCst;
import fr.pops.generator.beanfileconfig.BeanFileConfig;
import fr.pops.xmlparser.xmlparser.XMLParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Generator {

    /*****************************************
     *
     * Static Attributes
     *
     *****************************************/
    private static Generator instance = new Generator();

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private final XMLParser xmlParser = new XMLParser();
    private List<Path> files = new ArrayList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Singleton
     */
    private Generator(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Retrieve the xml files to parse
     * @param startingPathStr The string representing the path to
     *                        the "beans" folder
     */
    public void retrieveXMLFiles(String startingPathStr){
        // Build path
        Path startingPath = Paths.get(startingPathStr);

        // Retrieve the files
        try {
            this.files = Files.find(startingPath, Integer.MAX_VALUE, (path, basicFileAttributes) -> path.toFile().getName().matches(".*.xml") )
                              .collect(Collectors.toList());
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Parse the XML files
     */
    public void parseXMLFiles(){
        // Loop over all the files
        for (Path filePath : files){
            this.xmlParser.parse(filePath.toString());
        }
    }

    /**
     * Generate the files
     */
    public void generate(){
        // Loop over all the files
        for (int i = 0; i < files.size(); i++){
            this.writeFile(this.xmlParser.getFileConfigs(i));
        }
    }

    /**
     * Parse the XML files
     */
    private void writeFile(BeanFileConfig config){

        // Create a new file
        try {
            File file = new File(config.getFilePath());
            boolean fileExists = file.exists();
            boolean fileDeleted = true;
            if (fileExists){
                fileDeleted = file.delete();
            }
            boolean fileCreated = file.createNewFile();
            if (fileDeleted && fileCreated){
                FileWriter fw = new FileWriter(file);

                // Package line
                fw.write(GeneratorCst.BEAN_PACKAGE_DECLARATION_ROOT + config.getPackageName() + GeneratorCst.BEAN_END_LINE);
                fw.write(GeneratorCst.BEAN_SKIP_LINE);
                fw.write(GeneratorCst.BEAN_SKIP_LINE);

                // Import mandatory packages
                fw.write(GeneratorCst.BEAN_IMPORT_BEAN_PACKAGE);
                fw.write(GeneratorCst.BEAN_SKIP_LINE);
                fw.write(GeneratorCst.BEAN_IMPORT_PROPERTY_PACKAGE);
                fw.write(GeneratorCst.BEAN_SKIP_LINE);
                fw.write(GeneratorCst.BEAN_SKIP_LINE);

                // Class declaration
                fw.write(GeneratorCst.BEAN_CLASS_DECLARATION
                        + ' '
                        + config.getFilename()
                        + ' '
                        + GeneratorCst.BEAN_EXTENDS_BEAN_DECLARATION
                        + ' '
                        + GeneratorCst.BEAN_OPENING_BRACKET);
                fw.write(GeneratorCst.BEAN_SKIP_LINE);
                fw.write(GeneratorCst.BEAN_SKIP_LINE);

                // Write Attributes part
                this.writeAttributesPart(fw, config);

                // Write ctor part
                this.writeCtorPart(fw, config);

                // Write getter par
                this.writeGettersPart(fw, config);

                // Write setter part
                this.writeSettersPart(fw, config);

                // Close class
                fw.write(GeneratorCst.BEAN_CLOSING_BRACKET);

                // Close file
                fw.close();

            } else {
                System.out.println("Error creating bean: " + file);
            }

        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Print the "attributes" part
     * @param fw The file write
     * @param config The config holding the properties
     * @throws IOException Throws this exception when fw could not write in the file
     */
    private void writeAttributesPart(FileWriter fw, BeanFileConfig config) throws IOException{
        // Header
        fw.write(GeneratorCst.BEAN_PROPERTIES_HEADER);
        fw.write(GeneratorCst.BEAN_SKIP_LINE);

        // Loop over all properties
        for (int i = 0;  i < config.getNbProperties(); i++){
            String propertyStr = config.getPropertiesAttributes(i);
            String[] fields = this.retrievePropertyFields(propertyStr);

            // Mandatory fields
            // Name
            String name = fields[0];
            // Type
            String type = fields[1];
            // Default value
            String defaultValueStr = fields[2];

            // Write attributes
            // Property class
            fw.write(GeneratorCst.BEAN_DECLARATION_INDENTATION
                    + GeneratorCst.BEAN_PRIVATE
                    + ' '
                    + GeneratorCst.PROPERTY
                    + ' '
                    + name
                    + GeneratorCst.BEAN_END_LINE);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);
            // Default value
            this.writeTypedAttribute(fw, name, type, defaultValueStr);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);
        }
    }

    /**
     * Write a typed attribute
     * @param fw The file writer
     * @param name The name of the attribute
     * @param type The type of the attribute
     * @param defaultValueStr The default value of the attribute in a string format
     * @throws IOException The exception is thrown if the file write could not write a line
     */
    private void writeTypedAttribute(FileWriter fw, String name, String type, String defaultValueStr) throws IOException{

        // Initialize line
        String line = GeneratorCst.BEAN_DECLARATION_INDENTATION
                + GeneratorCst.BEAN_PRIVATE
                + ' ';

        // Build type
        switch (type.toLowerCase()){
            case "string":
                line += StrCst.STRING;
                break;
            case "double":
                line += StrCst.DOUBLE;
                break;
            case "boolean":
                line += StrCst.BOOLEAN;
                break;
        }

        // Continue
        line += ' '
              + name + GeneratorCst.PROPERTY_DEFAULT
              + " = ";

        // Build default value
        line += decorateParam(type, defaultValueStr);

        fw.write(line
                + GeneratorCst.BEAN_END_LINE);
        fw.write(GeneratorCst.BEAN_SKIP_LINE);
    }

    /**
     * Write ctor part
     * @param fw The file write
     * @param config The config holding the properties
     * @throws IOException Throws this exception when fw could not write in the file
     */
    private void writeCtorPart(FileWriter fw, BeanFileConfig config) throws IOException{

        // Header
        fw.write(GeneratorCst.BEAN_CTOR_HEADER);
        fw.write(GeneratorCst.BEAN_SKIP_LINE);

        // Declaration
        String declaration = GeneratorCst.BEAN_DECLARATION_INDENTATION
                + ' '
                + GeneratorCst.BEAN_PUBLIC
                + ' '
                + config.getFilename()
                + GeneratorCst.BEAN_CTOR_BRACKETS
                + GeneratorCst.BEAN_OPENING_BRACKET;
        fw.write(declaration);
        fw.write(GeneratorCst.BEAN_SKIP_LINE);

        // Call parent ctor
        fw.write(GeneratorCst.BEAN_CORPUS_INDENTATION
                + GeneratorCst.BEAN_SUPER_CTOR);
        fw.write(GeneratorCst.BEAN_SKIP_LINE);

        // Loop over all properties
        for (int i = 0;  i < config.getNbProperties(); i++){
            String propertyStr = config.getPropertiesAttributes(i);
            String[] fields = this.retrievePropertyFields(propertyStr);

            // Mandatory fields
            // Name
            String name = fields[0];
            // Type
            String type = fields[1];
            // Default value
            String defaultValueStr = fields[2];

            // Property class
            String propertyBuilderLine = GeneratorCst.BEAN_CORPUS_INDENTATION
                    + GeneratorCst.BEAN_THIS_DOT
                    + name
                    + " = "
                    + GeneratorCst.PROPERTY_BUILDER_DECLARATION
                    + GeneratorCst.PROPERTY_BUILDER_WITH_NAME
                    + GeneratorCst.BEAN_METHOD_OPENING_BRACKET + decorateParam(StrCst.STRING, name) + GeneratorCst.BEAN_METHOD_CLOSING_BRACKET
                    + GeneratorCst.PROPERTY_BUILDER_WITH_TYPE
                    + GeneratorCst.BEAN_METHOD_OPENING_BRACKET + decorateParam(StrCst.STRING, type) + GeneratorCst.BEAN_METHOD_CLOSING_BRACKET
                    + GeneratorCst.PROPERTY_BUILDER_WITH_DEFAULT_VALUE
                    + GeneratorCst.BEAN_METHOD_OPENING_BRACKET + decorateParam(type, defaultValueStr) + GeneratorCst.BEAN_METHOD_CLOSING_BRACKET
                    + GeneratorCst.PROPERTY_BUILDER_BUILD
                    + GeneratorCst.BEAN_END_LINE
                    + GeneratorCst.BEAN_SKIP_LINE;
            fw.write(propertyBuilderLine);

        }

        // End ctor
        fw.write(GeneratorCst.BEAN_DECLARATION_INDENTATION + GeneratorCst.BEAN_CLOSING_BRACKET);
        fw.write(GeneratorCst.BEAN_SKIP_LINE);
        fw.write(GeneratorCst.BEAN_SKIP_LINE);

    }

    /**
     * Write getter part
     * @param fw The file write
     * @param config The config holding the properties
     * @throws IOException Throws this exception when fw could not write in the file
     */
    private void writeGettersPart(FileWriter fw, BeanFileConfig config) throws IOException{
        // Header
        fw.write(GeneratorCst.BEAN_GETTER_HEADER);
        fw.write(GeneratorCst.BEAN_SKIP_LINE);

        // Loop over all properties
        for (int i = 0;  i < config.getNbProperties(); i++){
            String propertyStr = config.getPropertiesAttributes(i);
            String[] fields = this.retrievePropertyFields(propertyStr);

            // Mandatory fields
            // Name
            String name = fields[0];
            // Type
            String type = fields[1];
            // Default value
            String defaultValueStr = fields[2];

            // Method declaration
            String declaration = GeneratorCst.BEAN_DECLARATION_INDENTATION
                    + GeneratorCst.BEAN_PUBLIC
                    + ' '
                    + type
                    + ' '
                    + GeneratorCst.BEAN_GET + name.substring(0,1).toUpperCase() + name.substring(1)
                    + GeneratorCst.BEAN_METHOD_OPENING_BRACKET + GeneratorCst.BEAN_METHOD_CLOSING_BRACKET + GeneratorCst.BEAN_OPENING_BRACKET;
            fw.write(declaration);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);

            // Corpus
            String getterLine = GeneratorCst.BEAN_CORPUS_INDENTATION
                    + GeneratorCst.BEAN_RETURN
                    + ' '
                    + GeneratorCst.BEAN_METHOD_OPENING_BRACKET
                    + type
                    + GeneratorCst.BEAN_METHOD_CLOSING_BRACKET
                    + GeneratorCst.BEAN_THIS_DOT
                    + name
                    + GeneratorCst.BEAN_GET_VALUE;
            fw.write(getterLine);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);

            // Close method
            fw.write(GeneratorCst.BEAN_DECLARATION_INDENTATION + GeneratorCst.BEAN_CLOSING_BRACKET);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);
        }
    }

    /**
     * Write setter part
     * @param fw The file write
     * @param config The config holding the properties
     * @throws IOException Throws this exception when fw could not write in the file
     */
    private void writeSettersPart(FileWriter fw, BeanFileConfig config) throws IOException{
        // Header
        fw.write(GeneratorCst.BEAN_SETTER_HEADER);
        fw.write(GeneratorCst.BEAN_SKIP_LINE);

        // Loop over all properties
        for (int i = 0;  i < config.getNbProperties(); i++){
            String propertyStr = config.getPropertiesAttributes(i);
            String[] fields = this.retrievePropertyFields(propertyStr);

            // Mandatory fields
            // Name
            String name = fields[0];
            // Type
            String type = fields[1];
            // Default value
            String defaultValueStr = fields[2];

            // Method declaration
            String declaration = GeneratorCst.BEAN_DECLARATION_INDENTATION
                    + GeneratorCst.BEAN_PUBLIC
                    + ' '
                    + GeneratorCst.BEAN_VOID
                    + ' '
                    + GeneratorCst.BEAN_SET + name.substring(0,1).toUpperCase() + name.substring(1)
                    + GeneratorCst.BEAN_METHOD_OPENING_BRACKET
                    + type
                    + ' '
                    + GeneratorCst.BEAN_NEW_VALUE
                    + GeneratorCst.BEAN_METHOD_CLOSING_BRACKET
                    + GeneratorCst.BEAN_OPENING_BRACKET;
            fw.write(declaration);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);

            // Corpus
            String setterLine = GeneratorCst.BEAN_CORPUS_INDENTATION
                    + GeneratorCst.BEAN_THIS_DOT
                    + name
                    + GeneratorCst.BEAN_SET_VALUE
                    + GeneratorCst.BEAN_METHOD_OPENING_BRACKET
                    + GeneratorCst.BEAN_NEW_VALUE
                    + GeneratorCst.BEAN_METHOD_CLOSING_BRACKET
                    + GeneratorCst.BEAN_END_LINE;
            fw.write(setterLine);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);

            // Close method
            fw.write(GeneratorCst.BEAN_DECLARATION_INDENTATION + GeneratorCst.BEAN_CLOSING_BRACKET);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);
            fw.write(GeneratorCst.BEAN_SKIP_LINE);
        }
    }

    /*****************************************
     *
     * Tools
     *
     *****************************************/
    /**
     * Retrieve the property fields in a string array
     * @param propertyStr The property fields parsed by the xml parser and found in the config
     * @return The array containing all the property's fields in a string format
     */
    private String[] retrievePropertyFields(String propertyStr) {
        // Separate the fields
        StringTokenizer tokenizer = new StringTokenizer(propertyStr, GeneratorCst.PROPERTY_TOKENIZER_DELIMITER);
        int index = 0;
        String[] res = new String[tokenizer.countTokens()];
        while (tokenizer.hasMoreElements()) {
            res[index] = (String) tokenizer.nextElement();
            index++;
        }
        return res;
    }

    public String decorateParam(String type, String param){

        String res = "";
        switch (type.toLowerCase()) {
            case "string":
                res += "\"" + param + "\"";
                break;
            case "double":
                res += param + "d";
                break;
            default:
                res += param;
                break;
        }
        return res;
    }


    /*****************************************
     *
     * Getter
     *
     *****************************************/
    public static Generator getInstance() {
        return instance;
    }
}
