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
 * Name: GeneratorCst.java
 *
 * Description: Abstract class defining the constant values used by the generator
 *              to generateBean bean classes
 *
 * Author: Charles MERINO
 *
 * Date: 25/02/2021
 *
 ******************************************************************************/
package fr.pops.commoncst;

public abstract class GeneratorCst {

    /*****************************************
     *
     * Beans
     *
     *****************************************/
    // Path
    public static final String BEAN_IHM_PATH_PREFIX = "IhmLib\\src\\main\\java";
    public static final String BEAN_PROPERTIES_IHM_PATH_SUFFIX = "\\fr\\pops\\customnodes\\beanproperties\\";

    // General
    public static final String BEAN_EXTENSION = ".java";
    public static final String BEAN_END_LINE = ";";
    public static final String BEAN_COMMA = ",";
    public static final String BEAN_DOT = ".";
    public static final String BEAN_SKIP_LINE = "\n";
    public static final String BEAN_OPENING_BRACKET = "{";
    public static final String BEAN_CLOSING_BRACKET = "}";
    public static final String BEAN_CTOR_BRACKETS = "()";
    public static final String BEAN_METHOD_OPENING_BRACKET = "(";
    public static final String BEAN_METHOD_CLOSING_BRACKET = ")";
    public static final String BEAN_PACKAGE_DECLARATION_ROOT = "package fr.pops.beans.";
    public static final String BEAN_IMPORT_BEAN_PACKAGE = "import fr.pops.beans.bean.Bean;";
    public static final String BEAN_IMPORT_PROPERTY_PACKAGE = "import fr.pops.beans.properties.Property;";
    public static final String BEAN_CLASS_DECLARATION = "public class";
    public static final String BEAN_EXTENDS_BEAN_DECLARATION = "extends Bean";
    public static final String BEAN_SUPER_CTOR = "super";
    public static final String BEAN_DECLARATION_INDENTATION = "\t";
    public static final String BEAN_CORPUS_INDENTATION = "\t\t";
    public static final String BEAN_THIS_DOT = "this.";
    public static final String BEAN_GET = "get";
    public static final String BEAN_GET_VALUE = ".getValue();";
    public static final String BEAN_RETURN = "return";
    public static final String BEAN_SET = "set";
    public static final String BEAN_VOID = "void";
    public static final String BEAN_NEW_VALUE = "newValue";
    public static final String BEAN_SET_VALUE = ".setValue";

    // Parts
    public static final String BEAN_PROPERTIES_HEADER =  "\t/*****************************************\n\t*\n\t * Properties\n\t *\n\t *****************************************/";
    public static final String BEAN_CTOR_HEADER =  "\t/*****************************************\n\t*\n\t * Ctor\n\t *\n\t *****************************************/";
    public static final String BEAN_GETTER_HEADER =  "\t/*****************************************\n\t*\n\t * Getters\n\t *\n\t *****************************************/";
    public static final String BEAN_SETTER_HEADER =  "\t/*****************************************\n\t*\n\t * Setters\n\t *\n\t *****************************************/";

    // Identifiers
    public static final String BEAN_PUBLIC = "public";
    public static final String BEAN_PRIVATE = "private";

    /*****************************************
     *
     * Models
     *
     *****************************************/
    public static final String BEAN_BEAN = "Bean";
    public static final String BEAN_MODEL = "Model";
    public static final String BEAN_ROOT_PACKAGE = "fr.pops.beans.";
    public static final String BEAN_MODELS_PACKAGE_DOT = "fr.pops.beans.beanmodels.";

    /*****************************************
     *
     * Property
     *
     *****************************************/
    // Strings
    public static final String PROPERTY = "Property";
    public static final String GROUP = "Group";
    public static final String ENTRY = "Entry";
    public static final String PROPERTY_NAME = "Name";
    public static final String PROPERTY_TYPE = "Type";
    public static final String PROPERTY_DEFAULT = "Default";
    public static final String PROPERTY_VALUE = "value";
    public static final String PROPERTY_IS_COMPUTED = "isComputed";
    public static final String PROPERTY_IS_INTERNAL = "isInternal";
    public static final String PROPERTY_TOKENIZER_DELIMITER = ";";
    public static final String PROPERTY_BUILDER_DECLARATION = "new Property.PropertyBuilder";
    public static final String PROPERTY_CREATE_PROPERTY_DECLARATION = "this.createProperty";
    public static final String PROPERTY_BUILDER_WITH_NAME = ".withName";
    public static final String PROPERTY_BUILDER_WITH_TYPE = ".withType";
    public static final String PROPERTY_BUILDER_WITH_DEFAULT_VALUE = ".withDefaultValue";
    public static final String PROPERTY_BUILDER_IS_COMPUTED = ".isComputed";
    public static final String PROPERTY_BUILDER_IS_INTERNAL = ".isInternal";
    public static final String PROPERTY_BUILDER_BUILD = ".build()";

}
