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
 * Name: RequestDispatcher.java
 *
 * Description: Abstract class used to dispatch incoming requests
 *              across the controllers
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.controllers.controllermanager.ControllerManager;
import fr.pops.controllers.viewcontrollers.BaseController;
import fr.pops.controllers.viewcontrollers.NetworkInfoController;
import fr.pops.controllers.viewcontrollers.StockController;
import fr.pops.sockets.resquest.GetCurrentStockInfoRequest;
import fr.pops.sockets.resquest.GetNetworkInfoRequest;
import fr.pops.sockets.resquest.PingRequest;
import fr.pops.sockets.resquest.Request;

public abstract class RequestDispatcher {

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Dispatch the request across the controllers
     * @param request The request to dispatch
     */
    public static void dispatch(Request request){

        // Dispatch the request in the controllers
        switch (request.getType()){
            case PING:
                dispatchPingRequest((PingRequest) request);
                break;
            case GET_NETWORK_INFO:
                dispatchGetNetworkInfoRequest((GetNetworkInfoRequest) request);
                break;
            case GET_CURRENT_STOCK_INFO:
                dispatchGetCurrentStockInfoRequest((GetCurrentStockInfoRequest) request);
                break;
        }
    }


    /**
     * Dispatch ping Request
     * @param request The ping request
     */
    private static void dispatchPingRequest(PingRequest request) {
        BaseController<?, ?> controller = ControllerManager.getInstance().getFirst(NetworkInfoController.class);
        if (controller != null){
            ((NetworkInfoController) controller).setPingValue(request.getResponseDelay());
        }
    }

    /**
     * Dispatch the request
     * @param request The request
     */
    private static void dispatchGetNetworkInfoRequest(GetNetworkInfoRequest request) {
        BaseController<?, ?> controller =  ControllerManager.getInstance().getFirst(NetworkInfoController.class);
        if (controller != null){
            ((NetworkInfoController) controller).setFrequency(request.getFrequency());
            ((NetworkInfoController) controller).setConnectedClients(request.getClientTypes());
        }
    }

    /**
     * Dispatch the request
     * @param request The request
     */
    private static void dispatchGetCurrentStockInfoRequest(GetCurrentStockInfoRequest request) {
        BaseController<?, ?> controller =  ControllerManager.getInstance().getFirst(StockController.class);
        if (controller != null){
            long lastAccessTime = request.getAccessTime();
            double price = request.getCurrentStockPrice();
            String symbol = request.getSymbol();
            ((StockController) controller).addCurrentStockPrice(symbol, lastAccessTime, price);
        }
    }
}
