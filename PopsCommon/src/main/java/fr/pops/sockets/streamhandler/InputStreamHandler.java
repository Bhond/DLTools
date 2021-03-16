package fr.pops.sockets.streamhandler;

import fr.pops.sockets.resquesthandler.RequestHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public abstract class InputStreamHandler extends Thread implements Runnable {

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
    protected Scanner inputStream;
    //protected PrintWriter outputStream;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done...
     */
    protected InputStreamHandler(){
        // Nothing to be done...
    }

    /**
     * Instantiate a new fr.pops.client thread
     * @param socket The socket used by the fr.pops.client
     */
    protected InputStreamHandler(Socket socket, RequestHandler requestHandler) {
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
            // Build the input stream
            this.inputStream = new Scanner(this.socket.getInputStream());

            // Build the output stream
            //this.outputStream = new PrintWriter(this.socket.getOutputStream(), true);

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
     * Listen to incoming messages
     */
    protected void listen(){
        System.out.println(this.inputStream);
//        if (this.inputStream.hasNextLine()) {
//            //System.out.println(this.inputStream.nextLine());
//            //this.requestHandler.enqueueRequest(new Request(this.socket, this.inputStream.nextLine()));
//        }
//        else {
//            System.out.println("End listening");
//        }
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
                // Listen for incoming requests
                this.listen();

                System.out.println("Thread running");

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
    /**
     * Set the id of the client
     * @param id The id configured by the ClientStreamHandler
     */
    public void setId(int id){
        this.id = id;
    }
}
