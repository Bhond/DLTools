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
package fr.pops.sockets.resquesthandler;

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

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Singleton
     */
    protected RequestHandler(){
        this.isRunning = true;
        this.thread.start();
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
    protected abstract void handle(Request request);

    /*****************************************
     *
     * Parent method
     *
     *****************************************/
    @Override
    public void run() {
        // Loop to handle requests
        while (this.isRunning){
            while (!this.requestQueue.isEmpty()){
                this.handle(requestQueue.remove());
            }
            // TEMP
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // TEMP
        }
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
}
