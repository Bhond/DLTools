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
 * Name: RequestHandler.java
 *
 * Description: Abstract class describing the basics of a request handler that handles
 *              requests between clients and servers
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquesthandler;

import fr.pops.sockets.resquest.Request;

public abstract class RequestHandler {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    protected RequestHandler(){}

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Handle the request
     * @param request The request to handle
     */
    public final void handle(Request request){
        // Decode request
        request.decode();

        // Internal process of the request
        request.process();

        // Process the request
        this.process(request);
    }

    /**
     * Process the request
     * Not every request has to be processed depending on the client
     * @param request The request to process
     */
    protected abstract void process(Request request);

}
