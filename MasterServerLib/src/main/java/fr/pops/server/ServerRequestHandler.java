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
        // Process the request depending on its type
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

    /**
     * Select the next operation to perform with the request
     * @param request The request to handle
     * @return The operation to perform next.
     *         Either NONE, WRITE_BACK or TRANSFER
     */
    public EnumCst.RequestOperations selectNextOperation(Request request){
        // Init
        EnumCst.RequestOperations operation;

        // Select next operation
        switch (request.getType()){
            case PING:
                operation = EnumCst.RequestOperations.WRITE_BACK;
                break;
            case GET_SERVER_INFO:
                operation = EnumCst.RequestOperations.WRITE_BACK;
                break;
            case GET_CURRENT_STOCK_INFO:
                operation = EnumCst.RequestOperations.TRANSFER;
                break;
            default:
                operation = EnumCst.RequestOperations.NONE;
                break;
        }
        return operation;
    }

    /**
     * Select the next receiver when the request is transferred
     * amongst the clients
     * @param request The request to transfer
     * @param from The client's id who sent the request
     * @return The receiver's id
     */
    public long selectReceiver(Request request, long from){
        long toId;
        switch (request.getType()) {
            case GET_CURRENT_STOCK_INFO:
                toId = getOtherStockOrIhm(from);
                break;
            default:
                toId = EnumCst.ClientTypes.DEFAULT.getId();
                System.out.println("Unknown request type: " + request.getType() + ". Unable to select receiver.");
                break;
        }
        return toId;
    }

    /*****************************************
     *
     * Request exchange handling
     *
     *****************************************/
    /**
     * Returns the appropriate receiver
     * between the IHM and the Stock clients
     * @param from Ihm client's id or Stock client's id
     * @return The other id:
     *          If from.id == Ihm.id return Stock.id
     *          If from.id == Stock.id return Ihm.id
     */
    private long getOtherStockOrIhm(long from){
        long toId;
        switch (EnumCst.ClientTypes.getType(from)){
            case IHM:
                toId = EnumCst.ClientTypes.STOCK.getId();
                break;
            case STOCK:
                toId = EnumCst.ClientTypes.IHM.getId();
                break;
            default:
                toId = EnumCst.ClientTypes.DEFAULT.getId();
                System.out.println("Input sender is neither Ihm nor Stock client. Unable to transfer request.");
                break;
        }
        return toId;
    }
}
