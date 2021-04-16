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
 * Name: ServerRequestHandler.java
 *
 * Description: Class inheriting from PopsCommon.RequestHandler describing
 *              how to handle requests coming from to the server
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.server;

import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.AuthenticateRequest;
import fr.pops.sockets.resquest.GetServerInfoRequest;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquesthandler.RequestHandler;

import java.nio.channels.SelectionKey;

public class ServerRequestHandler extends RequestHandler {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private SelectionKey currentKey;
    private Server server;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private ServerRequestHandler(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param server  Reference to the server to communicate with it
     */
    public ServerRequestHandler(Server server){
        this.server = server;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Handle the request
     * Surcharge includes SelectionKey because the request
     * might need to be transferred
     * @param key The SelectionKey of the client sending the request
     * @param request The request to be handled
     */
    protected  void handle(SelectionKey key, Request request){
        this.currentKey = key;
        this.handle(request);
    }

    /**
     * Process the given request
     * Specific to the server
     * @param request The request to process
     */
    @Override
    protected void process(Request request) {

        switch (request.getType()){
            case AUTHENTICATE:
                long id = ((AuthenticateRequest) request).getClientId();
                this.server.addClient(id, this.currentKey);
                System.out.println(EnumCst.ClientTypes.getType(id).name() + " client is connected.");
                break;
            case GET_SERVER_INFO:
                GetServerInfoRequest serverInfoRequest = (GetServerInfoRequest) request;
                serverInfoRequest.setFrequency(this.server.getFrequency());
                serverInfoRequest.setClientTypes(this.server.getConnectedClientsId());
                break;

        }

    }
}
