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
package fr.pops.resquesthandler;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class RequestDispatcher extends Thread implements Runnable {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    private static RequestDispatcher instance = new RequestDispatcher();
    private Thread thread = new Thread(this);

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private boolean isRunning;
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
    private RequestDispatcher(){
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

    /**
     * Handle the requests
     */
    public void dispatch(Request request){
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
            while (!this.requestQueue.isEmpty()){
                dispatch(requestQueue.remove());
            }
            // TEMP
            try {
                sleep(1);
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
    /**
     * This class is a singleton
     * @return The only instance of the object
     */
    public static RequestDispatcher getInstance() {
        return instance;
    }
}
