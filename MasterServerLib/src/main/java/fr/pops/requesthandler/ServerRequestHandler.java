package fr.pops.requesthandler;

import fr.pops.client.ServerInputStreamHandler;
import fr.pops.client.ServerOutputStreamHandler;
import fr.pops.sockets.resquesthandler.RequestHandler;

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
     * @param outputStreamHandler The output stream handler to send messages
     */
    public ServerRequestHandler(ServerInputStreamHandler inputStreamHandler, ServerOutputStreamHandler outputStreamHandler){
        // Parent
        super(inputStreamHandler, outputStreamHandler);
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
    public void handle(String request) {
        super.send(request);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
}
