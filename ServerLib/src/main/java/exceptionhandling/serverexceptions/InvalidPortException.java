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
 * Name: InvalidPortException.java
 *
 * Description: Class defining an error thrown when an invalid port has been selected
 *
 * Author: Charles MERINO
 *
 * Date: 11/02/2021
 *
 ******************************************************************************/
package exceptionhandling.serverexceptions;

public class InvalidPortException extends Exception {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
        public InvalidPortException(int port){
            super("Invalid port " + port);
    }
}
