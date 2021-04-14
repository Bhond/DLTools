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
 * Name: BeanFileConfig.java
 *
 * Description: Resumes all of the info to write the bean
 *              being generated
 *
 * Author: Charles MERINO
 *
 * Date: 21/02/2021
 *
 ******************************************************************************/
package fr.pops.generator.beanfileconfig;

import fr.pops.commoncst.GeneratorCst;

import java.util.List;

public class BeanFileConfig {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String filePath;
    private String filename;
    private String packageName;
    private List<String> propertiesAttributes;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    public BeanFileConfig(String filePath, List<String> propertiesAttributes){
        this.buildBeanFileName(filePath);
        this.buildBeanFilePath(filePath);
        this.buildBeanPackageName();
        this.propertiesAttributes = propertiesAttributes;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Build the file's name of the bean
     * @param filePath The path of the input xml
     */
    private void buildBeanFileName(String filePath){
        this.filename = filePath.substring(filePath.lastIndexOf('\\')+1, filePath.lastIndexOf('.'));
    }

    /**
     * Build the file's path where the bean will be written
     * @param filePath The path of the input xml
     */
    private void buildBeanFilePath(String filePath){
        String folderPath = filePath.substring(0, filePath.lastIndexOf('\\')+1);
        this.filePath = folderPath + this.filename + GeneratorCst.BEAN_EXTENSION;
    }

    /**
     * Build the file's package name which is the file's name without the word Bean
     */
    private void buildBeanPackageName(){
        this.packageName = this.filename.substring(0, this.filename.lastIndexOf('B')).toLowerCase();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The file path
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * @return The file name
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * @return The package's name
     */
    public String getPackageName() {
        return this.packageName;
    }

    /**
     * @param i The index in the list
     * @return The property attributes in a string format with ";"
     *         as a separator
     */
    public String getPropertiesAttributes(int i) {
        return this.propertiesAttributes.get(i);
    }

    /**
     * @return The number of properties to write
     */
    public int getNbProperties(){
        return this.propertiesAttributes.size();
    }

}
