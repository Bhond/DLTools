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
 * Description: Class starting Pops server
 *
 * Author: Charles MERINO
 *
 * Date: 10/02/2021
 *
 ******************************************************************************/
package fr.pops.main;

import fr.pops.server.Server;
import fr.pops.serverlibcst.IntCst;

public class Main {
    
    public static void main(String[] args) {
        // Instantiate the server
        Server popsServer = new Server(IntCst.SERVER_PORT);
    }
    
}
