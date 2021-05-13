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

import fr.pops.math.PopsMath;
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
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
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

    // Server loop parameters
    private final long initialDelay = 0;
    private final long timeDelay = PopsMath.convertDoubleToLong(1E-2, 1E-2);

    // Communication
    protected ScheduledFuture<?> service;
    protected BaseClient client;
    private RequestFactory requestFactory = new RequestFactory();
    private ConcurrentLinkedQueue<Request> outputBucket = new ConcurrentLinkedQueue<>();

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
        this.service = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                this.communicate();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }, this.initialDelay, this.timeDelay, TimeUnit.MILLISECONDS);
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
     * @throws Throwable Exception thrown when an error occurs
     *                  when selecting the keys.
     *                  Check {@link java.nio.channels.Selector::selectNow} method
     *                  for more info
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
                List<Request> requests = this.read();
                for (Request request : requests){
                    // Handle the request
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
    private List<Request> read(){
        List<Request> requests = new LinkedList<>();
        try{
            // If something has been read, fill in the returned request
            int amountRead = this.channel.read(this.buffer.clear());
            int remaining = amountRead;

            // While requests are still in the buffer
            while (remaining > 0) {
                Request request = this.requestFactory.getRequest(this.buffer.array());
                if (request != null) {
                    requests.add(request);
                    // Update position to read next request if there is any
                    if (remaining > request.getLength()) {
                        this.buffer.position(request.getLength());
                        this.buffer.compact();
                        remaining -= request.getLength();
                    } else if (remaining == request.getLength()){
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (Throwable t){
            t.printStackTrace();
        }
        return requests;
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
            this.buffer.clear();
            this.buffer.put(requestToSend.getRawRequest());
            this.buffer.flip();

            // Send the request
            try{
                this.channel.write(this.buffer);
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

    /*****************************************
     *
     * Connection handling
     *
     *****************************************/
    /**
     * Send an authenticate request when the connection is started
     */
    private void onConnectionOpened() {
        // Send authenticate request to server to inform it which ones are connected
        this.send(new AuthenticateRequest(this.client.getType().getId()));
    }

    /**
     * Close communication between server and client
     */
    public void close(){
        try {
            this.channel.close();
            this.buffer = null;
            this.service.cancel(true);
        } catch (IOException ignored){}
    }

}

