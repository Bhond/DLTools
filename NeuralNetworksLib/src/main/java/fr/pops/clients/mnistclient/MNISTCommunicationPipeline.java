package fr.pops.clients.mnistclient;

import fr.pops.sockets.communicationpipeline.CommunicationPipeline;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.Request;

import java.nio.channels.SelectionKey;

public class MNISTCommunicationPipeline extends CommunicationPipeline {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public MNISTCommunicationPipeline(){
        // Nothing to be done
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
        EnumCst.RequestOperations operation = ((MNISTRequestHandler) this.client.getRequestHandler()).selectNextOperation(request);

        // Perform next operation
        if (operation == EnumCst.RequestOperations.WRITE_BACK){
            this.client.send(request);
        }
    }
}
