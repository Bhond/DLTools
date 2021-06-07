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
import fr.pops.controllers.viewcontrollers.MNISTController;
import fr.pops.controllers.viewcontrollers.NetworkInfoController;
import fr.pops.controllers.viewcontrollers.StockController;
import fr.pops.sockets.resquest.*;

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
            case GET_MNIST_IMAGE:
                dispatchGetMNISTImageRequest((GetMNISTImageRequest) request);
                break;
            case GET_MNIST_CONFIGURATION:
                dispatchGetMNISTConfigurationRequest((GetMNISTConfiguration) request);
                break;
        }
    }

    /**
     * Dispatch ping Request
     * @param request The ping request to dispatch
     */
    private static void dispatchPingRequest(PingRequest request) {
        BaseController<?, ?> controller = ControllerManager.getInstance().getFirst(NetworkInfoController.class);
        if (controller != null){
            ((NetworkInfoController) controller).setPingValue(request.getResponseDelay());
        }
    }

    /**
     * Dispatch the request
     * @param request The request to dispatch
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
     * @param request The request to dispatch
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

    /**
     * Dispatch GetMNISTImageRequest
     * @param request The request to dispatch
     */
    private static void dispatchGetMNISTImageRequest(GetMNISTImageRequest request) {
        BaseController<?, ?> controller = ControllerManager.getInstance().getFirst(MNISTController.class);
        if (controller != null){
            ((MNISTController) controller).setLabel(request.getLabel());
            ((MNISTController) controller).setImage(request.getImage());
        }
    }

    /**
     * Dispatch GetMNISTConfiguration
     * @param request The request to dispatch
     */
    private static void dispatchGetMNISTConfigurationRequest(GetMNISTConfiguration request) {
        BaseController<?, ?> controller = ControllerManager.getInstance().getFirst(MNISTController.class);
        if (controller != null){
            ((MNISTController) controller).setConfiguration(request.getNbLayers(),
                    request.getLayers(),
                    request.getLearningRate(),
                    request.isRegularisationOn(),
                    request.getL1(),
                    request.getL2());
        }
    }
}
