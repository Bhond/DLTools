package fr.pops.sockets.streamhandler;

import fr.pops.sockets.cst.IdCst;
import fr.pops.sockets.resquesthandler.Request;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

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
    private Queue<Request> requestToSendQueue = new LinkedList<>();
    private Queue<String> tmpToSend = new LinkedList<>();

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
    protected OutputStreamHandler(Socket socket) {
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
    protected void write(Request request){
//        this.outputStream.print(request.toString());
        this.outputStream.print("Id: " + IdCst.ID_STOCK);
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
                // Write new messages
                String msg = this.tmpToSend.poll();

                if (msg != null) {
                    this.write(msg);
                    System.out.println("done");
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

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    public void addRequest(String msg){
        this.tmpToSend.offer(msg);
    }
}
