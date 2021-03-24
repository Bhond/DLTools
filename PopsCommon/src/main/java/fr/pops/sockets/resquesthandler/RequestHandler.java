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

import fr.pops.sockets.streamhandler.InputStreamHandler;
import fr.pops.sockets.streamhandler.OutputStreamHandler;

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

    private Queue<String> tmpStrQueue = new LinkedList<>();

    protected InputStreamHandler inputStreamHandler;
    protected OutputStreamHandler outputStreamHandler;
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
     * @param outputStreamHandler The output stream handler to send messages
     */
    protected RequestHandler(InputStreamHandler inputStreamHandler, OutputStreamHandler outputStreamHandler){
        this.inputStreamHandler = inputStreamHandler;
        this.outputStreamHandler = outputStreamHandler;

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

    public void send(String msg){
        this.outputStreamHandler.addRequest(msg);
    }

    /*****************************************
     *
     * Methods to override
     *
     *****************************************/
    /**
     * Handle the requests
     */
    protected abstract void handle(String request);

    /*****************************************
     *
     * Parent method
     *
     *****************************************/
    @Override
    public void run() {
        // Loop to handle requests
        while (this.isRunning){

            // Priority to the request created
            String msg = tmpStrQueue.poll();
            if (msg != null){
                this.handle(msg);
            }

            // Retrieve new msg
            this.tmpStrQueue.offer(this.inputStreamHandler.pull());

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
