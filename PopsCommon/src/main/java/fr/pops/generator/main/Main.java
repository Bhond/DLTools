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
 * Name: Main.java
 *
 * Description: Main class used to generate beans automatically given xml file
 *
 * Author: Charles MERINO
 *
 * Date: 20/02/2021
 *
 ******************************************************************************/
package fr.pops.generator.main;

import fr.pops.generator.generator.Generator;

public class Main {

    public static void main(String[] args) {
        // Retrieve the instance of the generator
        Generator generator = Generator.getInstance();

        // Retrieve all the of the xml files to parse
        generator.retrieveXMLFiles(args[0]);

        // Parse the XML the files
        generator.parseXMLFiles();

        // Generate the files
        generator.generate();
    }

}
