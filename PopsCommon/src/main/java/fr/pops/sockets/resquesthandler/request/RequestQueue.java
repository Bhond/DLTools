package fr.pops.sockets.resquesthandler.request;

import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // True if receiver should wait
    // False if sender should wait
    private boolean transfer = true;
    Queue<Request> requestQueue = new LinkedList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public RequestQueue(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods: sender
     *
     *****************************************/
    public synchronized void send(Request request){
        while (!transfer){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        transfer = false;
        requestQueue.offer(request);
        notifyAll();
    }

    /*****************************************
     *
     * Methods: receiver
     *
     *****************************************/
    public synchronized Request receive(){
        while (transfer){
            try {
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        transfer = true;
        notifyAll();
        return requestQueue.poll();
    }
}
