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
 * Name: StockCommunicationPipeline.java
 *
 * Description: Class defining the communication pipeline
 *              used by the stock client to communicate with the server
 *
 * Author: Charles MERINO
 *
 * Date: 11/04/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.sockets.communicationpipeline.CommunicationPipeline;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.Request;

import java.nio.channels.SelectionKey;

public class StockCommunicationPipeline extends CommunicationPipeline {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public StockCommunicationPipeline(){
        // Parent
        super();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Perform specific operation for the given key
     * and the given request
     * @param key Client's selection key
     * @param request The request to process
     */
    @Override
    protected void specificOp(SelectionKey key, Request request) {
        // Parent
        super.specificOp(key, request);

        // Select next operation depending on the request's type
        EnumCst.RequestOperations operation = ((StockRequestHandler) this.client.getRequestHandler()).selectNextOperation(request);

        // Perform next operation
        if (operation == EnumCst.RequestOperations.WRITE_BACK){
            this.client.send(request);
        }
    }
}
