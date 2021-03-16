package fr.pops.client;

import fr.pops.sockets.resquesthandler.Request;
import fr.pops.sockets.resquesthandler.RequestHandler;

public class IhmRequestHandler extends RequestHandler {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private static final IhmRequestHandler instance = new IhmRequestHandler();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    private IhmRequestHandler(){
        // Parent
        super();
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
    protected void handle(Request request) {

    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    public static IhmRequestHandler getInstance() {
        return instance;
    }
}
