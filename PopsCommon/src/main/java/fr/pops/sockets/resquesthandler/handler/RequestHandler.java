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
 * Description: Class dispatching requests from a client
 *              Singleton
 *
 * Author: Charles MERINO
 *
 * Date: 17/02/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquesthandler.handler;

import fr.pops.sockets.resquesthandler.request.Request;
import fr.pops.sockets.resquesthandler.request.RequestQueue;

import java.util.LinkedList;
import java.util.Queue;

public abstract class RequestHandler extends Thread implements Runnable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private boolean isRunning;
    private Thread thread = new Thread(this);
    private Queue<Request> requestQueue = new LinkedList<>();

    protected RequestQueue inputRequestQueue;
    protected RequestQueue outputRequestQueue;
    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    protected RequestHandler(){
        // Nothing to be done
    }

    /**
     * Ctor to call
     * @param inputRequestQueue The input request queue to receive request from
     * @param outputRequestQueue The output request queue to send request to
     */
    protected RequestHandler(RequestQueue inputRequestQueue, RequestQueue outputRequestQueue){
        this.inputRequestQueue = inputRequestQueue;
        this.outputRequestQueue = outputRequestQueue;
        this.isRunning = true;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Enqueue the given request to be processed
     * @param request The request to enqueue
     */
    public void enqueueRequest(Request request){
        this.requestQueue.add(request);
    }

    /*****************************************
     *
     * Methods to override
     *
     *****************************************/
    /**
     * Handle the requests
     */
    protected void handle(Request request){
        request.toUpper();
    }

    /*****************************************
     *
     * Parent method
     *
     *****************************************/
    @Override
    public void run() {
        // Loop to handle requests
        while (this.isRunning){

            /*
             * TODO: Might have to refactor this
             */
            // Receive new message
            for (Request request = this.inputRequestQueue.receive();
                 request != null;
                 request = this.inputRequestQueue.receive()){

                // Write new messages
                this.handle(request);
                System.out.println("Handling: " + request.toString());

                // Send request to the output stream
                this.outputRequestQueue.send(request);
            }
        }
    }
}
