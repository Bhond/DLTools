package fr.pops.sockets.streamhandler;

import fr.pops.sockets.resquesthandler.RequestHandler;

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

    // Input / output streams
    protected RequestHandler requestHandler;
    protected PrintWriter outputStream;

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
    protected OutputStreamHandler(Socket socket, RequestHandler requestHandler) {
        // Initialize the socket
        onInit(socket, requestHandler);
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
    private void onInit(Socket socket, RequestHandler requestDispatcher){
        // Store the socket
        this.socket = socket;

        // Store the request dispatcher
        this.requestHandler = requestDispatcher;

        // Build input / output streams
        try {
            // Build the output stream
            this.outputStream = new PrintWriter(this.socket.getOutputStream(), true);

            // Run
            this.isRunning = true;

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
    protected void write(String msg){
        this.outputStream.print(msg);
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
                // Write new messages
                this.write("Test");
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

    /*****************************************
     *
     * Setter
     *
     *****************************************/
}
