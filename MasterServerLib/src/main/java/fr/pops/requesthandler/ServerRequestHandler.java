package fr.pops.requesthandler;

import fr.pops.sockets.resquesthandler.Request;
import fr.pops.sockets.resquesthandler.RequestHandler;

public class ServerRequestHandler extends RequestHandler {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private static final ServerRequestHandler instance = new ServerRequestHandler();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public ServerRequestHandler(){
        // Parent
        super();
    }

    /*****************************************
     *
     * Parent Methods
     *
     *****************************************/
    /**
     * Handle the request
     * @param request The request to handle
     */
    @Override
    public void handle(Request request) {

    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The instance of ServerRequestHandler
     */
    public static ServerRequestHandler getInstance() {
        return instance;
    }
}
