package fr.pops.requesthandler;

import fr.pops.sockets.resquesthandler.handler.RequestHandler;
import fr.pops.sockets.resquesthandler.request.Request;
import fr.pops.sockets.resquesthandler.request.RequestQueue;

public class ServerRequestHandler extends RequestHandler {

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
    private ServerRequestHandler(){
        // Parent
        super();
    }

    /**
     * Ctor to call
     * @param inputRequestQueue The input request queue to receive request from
     * @param outputRequestQueue The output request queue to send request to
     */
    public ServerRequestHandler(RequestQueue inputRequestQueue, RequestQueue outputRequestQueue){
        // Parent
        super(inputRequestQueue, outputRequestQueue);
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
        super.handle(request);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
}
