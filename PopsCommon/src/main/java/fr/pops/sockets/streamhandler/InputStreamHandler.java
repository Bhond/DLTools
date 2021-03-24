package fr.pops.sockets.streamhandler;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
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

    // Input stream
    protected Scanner inputStream;
    protected Queue<String> incomingMsg = new LinkedList<>();

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
    protected InputStreamHandler(Socket socket) {
        // Initialize the socket
        onInit(socket);
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
    private void onInit(Socket socket){
        // Store the socket
        this.socket = socket;

        // Store the request dispatcher
        //this.requestHandler = requestDispatcher;

        // Build input / output streams
        try {
            // Build the input stream
            this.inputStream = new Scanner(this.socket.getInputStream());

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
        if (this.inputStream.hasNextLine()) {
            System.out.println("Msg received");
            String msg = this.inputStream.nextLine();
            this.incomingMsg.offer(msg.toUpperCase());
            System.out.println("Response sent");
        }
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
     * Getter
     *
     *****************************************/
    public String pull(){
        return this.incomingMsg.poll();
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
