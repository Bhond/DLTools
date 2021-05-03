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
 * Name: StockRequestHandler.java
 *
 * Description: Class inheriting from PopsCommon.RequestHandler describing
 *              how to handle requests coming from from the server
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.GetCurrentStockInfoRequest;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquesthandler.RequestHandler;
import fr.pops.stockinfo.StockInfoManager;

public class StockRequestHandler extends RequestHandler {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public StockRequestHandler() {
        // Parent
        super();
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Process input request
     * Specific to the stock client
     * @param request The request to process
     */
    @Override
    protected void process(Request request) {

        // Process request
        switch (request.getType()){
            case GET_CURRENT_STOCK_INFO:
                this.getCurrentStockInfoHandling((GetCurrentStockInfoRequest) request);
                break;
        }
    }

    /**
     * Select the next operation to perform with the request
     * @param request The request to handle
     * @return The operation to perform next:
     *         Either NONE, WRITE_BACK
     */
    public EnumCst.RequestOperations selectNextOperation(Request request){
        // Init
        EnumCst.RequestOperations operation;

        // Select next operation
        switch (request.getType()){
            case GET_CURRENT_STOCK_INFO:
                operation = EnumCst.RequestOperations.WRITE_BACK;
                break;
            default:
                operation = EnumCst.RequestOperations.NONE;
                break;
        }
        return operation;
    }

    /**
     * Handle GetCurrentStockInfo request
     * @param request The request to handle
     */
    private void getCurrentStockInfoHandling(GetCurrentStockInfoRequest request){
        StockInfoManager.getInstance().getStockInfo().updateStockInfo();
        long lastAccessTime = StockInfoManager.getInstance().getStockInfo().getLastAccessedTime();
        request.setAccessTime(lastAccessTime); // currentPrice
        double currentPrice = StockInfoManager.getInstance().getStockInfo().getPrice();
        request.setCurrentStockPrice(currentPrice);
    }
}
