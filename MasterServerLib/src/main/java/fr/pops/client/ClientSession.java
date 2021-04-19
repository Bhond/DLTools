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
 * Name: ClientSession.java
 *
 * Description: Class defining the client session on the server
 *              to be able to communicate with the client
 *
 * Author: Charles MERINO
 *
 * Date: 11/04/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.sockets.cst.IntCst;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquest.RequestFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;

public class ClientSession {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private SelectionKey selectionKey;
    private SocketChannel channel;
    private ByteBuffer buffer;

    private RequestFactory requestFactory = new RequestFactory();
    private Queue<Request> outputBucket = new LinkedList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param selectionKey The selection key used by the client
     * @param channel The channel used by the client
     */
    public ClientSession(SelectionKey selectionKey, SocketChannel channel){
        // Initialize client
        this.onInit(selectionKey, channel);
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize client session
     * @param selectionKey The selection key used by the client
     * @param channel The channel used by the client
     */
    private void onInit(SelectionKey selectionKey, SocketChannel channel){
        this.selectionKey = selectionKey;
        try{
            this.channel = (SocketChannel) channel.configureBlocking(false);
            this.buffer = ByteBuffer.allocate(IntCst.BUFFER_SIZE);
        } catch (Throwable ignored){}
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Disconnect client
     */
    public void disconnect(){
        try{
            if (this.selectionKey != null) this.selectionKey.cancel();
            if (this.channel == null) return;
            System.out.println("Disconnecting: " + this.channel.getRemoteAddress());
            this.channel.close();
        } catch (Throwable ignored){}
    }

    /*****************************************
     *
     * Handling requests
     *
     *****************************************/
    /**
     * Read input from buffer
     */
    public Request read(){
        Request request = null;
        try{
            // Put data in the buffer
            int amountRead = this.channel.read(this.buffer);
            // If something has been read, fill in the returned request
            // TODO: Check if in the case of amountRead == 0, should the client be disconnected?
            if (amountRead != -1 && amountRead != 0){
                // Build request
                request = this.requestFactory.getRequest(this.buffer.array());
                this.cleanBuffer();
            }

        } catch (Throwable t){
            t.printStackTrace();
            this.disconnect();
        }
        return request;
    }

    public void write(){
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
            } catch (IOException ignored){}
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
