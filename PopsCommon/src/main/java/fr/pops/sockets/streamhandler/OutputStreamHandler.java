package fr.pops.sockets.streamhandler;

import fr.pops.sockets.resquesthandler.request.Request;
import fr.pops.sockets.resquesthandler.request.RequestQueue;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class OutputStreamHandler extends Thread implements Runnable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Socket
    protected Socket socket;
    protected int id;

    // thread
    private Thread thread;
    protected boolean isRunning;

    // Output stream
    protected PrintWriter outputStream;

    // FIFO
    private RequestQueue requestToSendQueue;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done...
     */
    protected OutputStreamHandler(){
        // Nothing to be done...
    }

    /**
     * Instantiate a new fr.pops.client thread
     * @param socket The socket used by the fr.pops.client
     */
    protected OutputStreamHandler(Socket socket, RequestQueue requestQueue) {
        // Initialize the socket
        onInit(socket, requestQueue);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Initialize the client thread
     * @param socket The socket used by the client
     */
    private void onInit(Socket socket, RequestQueue requestQueue){
        // Store the socket
        this.socket = socket;

        // Store request queue
        this.requestToSendQueue = requestQueue;

        // Run
        this.isRunning = true;

        // Build Stream
        try {
            // Build the output stream
            this.outputStream = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error: " + this.socket);
            e.printStackTrace();
        }
    }

    /**
     * Start thread
     */
    public final void start(){
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Write message to output stream
     */
    protected void write(Request request){
        this.outputStream.println(request.toString());
    }

    /**
     * Write message to output stream
     */
    protected void write(String request){
        this.outputStream.println(request);
    }


    /*****************************************
     *
     * Abstract methods
     *
     *****************************************/
    /**
     * Initialize the connection
     */
    protected abstract void onConnectionOpened();

    protected abstract void setup();

    /*****************************************
     *
     * Parent method
     *
     *****************************************/
    @Override
    public void run() {
        try {
            // Do something when the connection starts
            this.onConnectionOpened();
            while (this.isRunning){
                /*
                 * TODO: Might have to refactor this
                 */
                for (Request request = this.requestToSendQueue.receive();
                     request != null;
                     request = this.requestToSendQueue.receive()){

                     // Write new messages
                     this.write(request);
                }
            }
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Closed: " + this.socket);
        }
    }
}
