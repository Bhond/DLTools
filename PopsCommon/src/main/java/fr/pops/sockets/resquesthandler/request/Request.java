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
 * Name: Request.java
 *
 * Description: Class defining request exchange between the server and a client
 *
 * Author: Charles MERINO
 *
 * Date: 17/02/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquesthandler.request;

import fr.pops.sockets.encodedecoder.EncoderDecoderHelper;

import java.net.Socket;

@SuppressWarnings("unused")
public abstract class Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected final EncoderDecoderHelper encoderDecoderHelper = new EncoderDecoderHelper();
    /*
     * TODO: change idx to id
     */
    protected String id;
    protected int idx;

    private Socket socket;
    private String rawRequest;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private Request(){
        // Nothing to be done
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param id The id of the request, defined in StrCst
     */
    protected Request(String id){
        // Initialize the request
        this.id = id;
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param socket The socket calling the request
     * @param id The id of the request, defined in StrCst
     */
    protected Request(Socket socket, String id){
        // Initialize the request
        this.socket = socket;
        this.id = id;
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param rawRequest The raw Request in binary
     * @param id The id of the request, defined in StrCst
     */
    protected Request(String rawRequest, String id){
        // Initialize the request
        this.rawRequest = rawRequest;
        this.id = id;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     *
     */
    private void computeIdLength(){
        System.out.println("Not implemented yet");
    }

    /**
     * Encode request to a binary representation
     */
    protected void encode(){
        // Encode request type
        this.encoderDecoderHelper.reset();
        this.encoderDecoderHelper.encodeInt32(this.idx); // tmp change idx to id
    }

    /**
     * Decode request from a binary representation
     */
    protected void decode(String rawParams){
        // Reset the helper
        this.encoderDecoderHelper.reset(rawParams);
    }

    /**
     * Build the request using the factory pattern
     */
    public void build(){

    }

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * @return Tmp handling
     */
    public void toUpper(){
        this.rawRequest = this.rawRequest.toUpperCase();
    }

    /**
     * @return String version of the class
     */
    @Override
    public String toString(){
        return this.rawRequest;
    }

}
