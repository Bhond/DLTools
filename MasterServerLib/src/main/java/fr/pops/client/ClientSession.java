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

import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.cst.IntCst;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquest.RequestFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ClientSession {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private EnumCst.ClientTypes type;
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
     * TODO: Check if in the case of amountRead == 0, should the client be disconnected?
     */
    public List<Request> read(){
        List<Request> requests = new LinkedList<>();
        try{
            // If something has been read, fill in the returned request
            int amountRead = this.channel.read(this.buffer.clear());
            boolean hasRemaining = amountRead > 0;

            // While requests are still in the buffer
            while (hasRemaining) {
                Request request = this.requestFactory.getRequest(this.buffer.array());
                if (request != null) {
                    requests.add(request);
                    // Update position to read next request if there is any
                    if (this.buffer.position() != request.getLength()) {
                        this.buffer.position(request.getLength());
                        this.buffer.compact();
                        this.buffer.rewind();
                    } else {
                        hasRemaining = false;
                    }
                } else {
                    hasRemaining = false;
                }
            }
        } catch (Throwable ignored){}

        return requests;
    }

    public void write(){
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
     * @return The type of the client bound to this session
     */
    public EnumCst.ClientTypes getType() {
        return this.type;
    }

    /**
     * Set the client's type
     * Coming from the authenticate request
     * @param type The type of the client
     */
    public void setType(EnumCst.ClientTypes type) {
        this.type = type;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The client's channel
     */
    public SocketChannel getChannel() {
        return this.channel;
    }
}
