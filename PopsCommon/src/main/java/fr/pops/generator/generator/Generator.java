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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
     * Generate all the beans
     */
    public void generate(){

        // Loop over all the files
        for (Path path : files){
            System.out.println(path);
        }
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
