package fr.pops.client;

import fr.pops.sockets.resquesthandler.RequestHandler;

public class IhmRequestHandler extends RequestHandler {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public IhmRequestHandler(IhmOutputStreamHandler ihmOutputStreamHandler){
        // Parent
        super(ihmOutputStreamHandler);
    }

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * Handle the given request
     * @param request The request to handle
     */
    @Override
    protected void handle(String request) {

    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
}
