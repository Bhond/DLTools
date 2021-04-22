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
 * Name: CommunicationPipeline.java
 *
 * Description: Class defining the communication pipeline
 *              used by a client to communicate with the server
 *
 * Author: Charles MERINO
 *
 * Date: 11/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.communicationpipeline;

import fr.pops.sockets.client.BaseClient;
import fr.pops.sockets.resquest.AuthenticateRequest;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquest.RequestFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CommunicationPipeline {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Channel parameters
    private Selector selector;
    private SocketChannel channel;
    private ByteBuffer buffer;

    // Communication
    protected BaseClient client;
    private RequestFactory requestFactory = new RequestFactory();
    private Queue<Request> outputBucket = new LinkedList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public CommunicationPipeline(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param client The client using this communication pipeline
     * @param channel The channel used by the client
     *                to communicate with the server
     * @param buffer The buffer used to communicate
     */
    public CommunicationPipeline(BaseClient client, SocketChannel channel, ByteBuffer buffer){
        // Initialize pipeline
        this.onInit(client, channel, buffer);
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize pipeline
     * @param client The client using this communication pipeline
     * @param channel The channel used by the client
     *                to communicate with the server
     * @param buffer The buffer used to communicate
     */
    public void onInit(BaseClient client, SocketChannel channel, ByteBuffer buffer){
        this.client = client;
        this.channel = channel;
        try {
            this.selector = Selector.open();
            this.channel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            this.buffer = buffer;
        } catch (IOException ignored) {}
    }

    /*****************************************
     *
     * Loop
     *
     *****************************************/
    /**
     * Main loop
     * to communicate with the server
     */
    public void run(){

        this.onConnectionOpened();

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                this.communicate();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * Send an authenticate request when the connection is started
     */
    private void onConnectionOpened() {
        // Send authenticate request to server to inform it which ones are connected
        this.send(new AuthenticateRequest(this.client.getType().getId()));
    }

    /**
     * Communicate with the server
     *
     * TODO: Allow sending request after processing
     *
     * Handles 2 types of operation:
     *      - Read: Read input
     *      - Write: Write output
     *
     * @throws Throwable
     */
    private void communicate() throws Throwable {
        // Select the keys
        this.selector.selectNow();
        Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();

        // Loop over the keys
        for (; keys.hasNext();) {
            SelectionKey key = keys.next();
            // Skip key if not valid
            if (!key.isValid()) continue;

            // Specific operation for the key
            this.specificOp(key);

            // Read request and perform specific operation
            if (key.isReadable()){
                Request request = this.read();
                if (request != null){
                    // Handle request
                    this.client.handle(request);

                    // Specific operation
                    this.specificOp(key, request);
                }
            }

            // Write output
            if (key.isWritable()){
                this.write();
            }
        }
    }

    /**
     * Perform specific operation for the given key
     * @param key Client's selection key
     */
    protected void specificOp(SelectionKey key){}

    /**
     * Perform specific operation for the given key
     * and the given request
     * @param key Client's selection key
     * @param request The request to process
     */
    protected void specificOp(SelectionKey key, Request request){}



    /*****************************************
     *
     * Request handling
     *
     *****************************************/
    /**
     * Read request received from the server
     */
    private Request read(){
        Request request = null;
        try{
            // Put data in the buffer
            int amountRead = this.channel.read(buffer.clear());

            // If something has been read, fill in the returned request
            // TODO: Check if in the case of amountRead == 0, should the client be disconnected?
            if (amountRead != -1 && amountRead != 0){
                // Build request
                request = this.requestFactory.getRequest(this.buffer.array());
            }

            // Clean buffer
            this.cleanBuffer();

        } catch (Throwable t){
            t.printStackTrace();
        }
        return request;
    }

    /**
     * Write output
     */
    private void write(){
        // Check if a request is ready to be sent
        if (!this.outputBucket.isEmpty()){
            // Get request
            Request requestToSend = this.outputBucket.poll();

            // Encode it
            requestToSend.encode();

            // Put the request in the buffer
            this.buffer.put(requestToSend.getRawRequest());
            this.buffer.flip();

            // Send the request
            try{
                this.channel.write(this.buffer);
                this.cleanBuffer();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Write a request to send it to the server
     * @param request The request to send
     */
    public void send(Request request) {
        this.outputBucket.add(request);
    }

    /**
     * Clean buffer of all previous data
     */
    private void cleanBuffer(){
        // Replace all the values in the byte array by 0
        java.util.Arrays.fill(this.buffer.array(), (byte) 0);
        // Reset cursor, limit and mark
        this.buffer.clear();
    }
}

