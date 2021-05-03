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
 * Name: BaseClient.java
 *
 * Description: Abstract class defining the the basic description of a client
 *
 * Author: Charles MERINO
 *
 * Date: 11/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.client;

import fr.pops.sockets.communicationpipeline.CommunicationPipeline;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.cst.IntCst;
import fr.pops.sockets.resquest.DisconnectionRequest;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquesthandler.RequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class BaseClient {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // General
    protected EnumCst.ClientTypes type;

    // Client socket parameters
    protected InetSocketAddress socketAddress;
    protected SocketChannel channel;
    protected ByteBuffer buffer;

    // Communication pipeline
    protected CommunicationPipeline communicationPipeline;
    protected RequestHandler requestHandler;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private BaseClient(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param type The client's type
     * @param socketAddress The socket address to connect to
     * @param requestHandler The request handler that will process the requests
     */
    protected BaseClient(EnumCst.ClientTypes type, InetSocketAddress socketAddress, RequestHandler requestHandler){
        // Initialisation
        this.onInit(type, socketAddress, requestHandler);
    }

    /**
     * Ctor
     * @param type The client's type
     * @param socketAddress The socket address to connect to
     * @param requestHandler The request handler that will process the requests
     * @param pipeline The raw communication pipeline not initialized
     */
    protected BaseClient(EnumCst.ClientTypes type, InetSocketAddress socketAddress, RequestHandler requestHandler, CommunicationPipeline pipeline){
        // Initialisation
        this.onInit(type, socketAddress, requestHandler, pipeline);
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize client
     * @param socketAddress The socket address to connect to
     * @param requestHandler The request handler used to handle requests
     *                       It needs to be set because handling might not be the same for all clients
     */
    private void onInit(EnumCst.ClientTypes type, InetSocketAddress socketAddress, RequestHandler requestHandler){
        // Store fields
        this.type = type;
        this.socketAddress = socketAddress;

        // Initialize client
        try{
            this.channel = SocketChannel.open(socketAddress);
            this.channel.configureBlocking(false);
            this.buffer = ByteBuffer.allocate(IntCst.BUFFER_SIZE);
            this.requestHandler = requestHandler;
            this.communicationPipeline = new CommunicationPipeline(this, this.channel, this.buffer);
        } catch (IOException ignored) {}
    }

    /**
     * Initialize client
     * @param socketAddress The socket address to connect to
     * @param requestHandler The request handler used to handle requests
     *                       It needs to be set because handling might not be the same for all clients
     */
    private void onInit(EnumCst.ClientTypes type, InetSocketAddress socketAddress, RequestHandler requestHandler, CommunicationPipeline pipeline){
        // Store fields
        this.type = type;
        this.socketAddress = socketAddress;

        // Initialize client
        try{
            this.channel = SocketChannel.open(socketAddress);
            this.channel.configureBlocking(false);
            this.buffer = ByteBuffer.allocate(IntCst.BUFFER_SIZE);
            this.requestHandler = requestHandler;
            this.communicationPipeline = pipeline;
            this.communicationPipeline.onInit(this, this.channel, this.buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Start the client
     */
    public void start(){
        // Start and run the communication pipeline to enable communication with the server
        this.communicationPipeline.run();
    }

    /**
     * Start the client
     */
    public void start(boolean isStandAlone){
        // Start and run the communication pipeline to enable communication with the server
        if (!isStandAlone){
            this.communicationPipeline.run();
        }
    }

    /**
     * Close the client
     */
    public void close(){
        this.send(new DisconnectionRequest(this.getType().getId()));
        this.communicationPipeline.close();
    }

    /*****************************************
     *
     * Handling requests
     *
     *****************************************/
    public void handle(Request request) { this.requestHandler.handle(request);}

    /**
     * Send the input request
     * @param request The request to send
     *                It needs to be encoded
     */
    public void send(Request request){
        this.communicationPipeline.send(request);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The client's id
     */
    public final EnumCst.ClientTypes getType(){
        return this.type;
    }

    /**
     * @return The request handler
     */
    public RequestHandler getRequestHandler() {
        return this.requestHandler;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
