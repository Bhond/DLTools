package fr.pops.client;

import fr.pops.sockets.resquesthandler.handler.RequestHandler;

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
    public IhmRequestHandler(IhmInputStreamHandler inputStreamHandler, IhmOutputStreamHandler outputStreamHandler){
        // Parent
        super(inputStreamHandler, outputStreamHandler);
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
