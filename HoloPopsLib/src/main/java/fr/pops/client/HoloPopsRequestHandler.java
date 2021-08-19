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
 * Name: HoloPopsRequestHandler.java
 *
 * Description: Class defining the request handler used by HOLOPOPS.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquesthandler.RequestHandler;

public class HoloPopsRequestHandler extends RequestHandler {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public HoloPopsRequestHandler(){
        super();
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Process the request
     * For the ihm client, it needs to be dispatched
     * @param request The request to process
     */
    @Override
    protected void process(Request request) {

        // Process request
        switch (request.getType()){

        }
    }

}
