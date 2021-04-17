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
package fr.pops.sockets.resquest;

import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.encodedecoder.EncoderDecoderHelper;

import java.util.Arrays;

@SuppressWarnings("unused")
public abstract class Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected final EncoderDecoderHelper encoderDecoderHelper = new EncoderDecoderHelper();
    protected boolean needResponse = false;
    protected boolean needDispatch = false;

    protected EnumCst.RequestTypes type;
    protected int length;
    protected byte[] rawParams;

    protected byte[] rawRequest;

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
     * @param type The type of the request, defined in EnumCst
     */
    protected Request(EnumCst.RequestTypes type){
        // Initialize the request
        this.type = type;
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param rawParams The raw Request in binary
     * @param type The type of the request, defined in EnumCst
     */
    protected Request(EnumCst.RequestTypes type, byte[] rawParams){
        // Initialize the request
        this.rawParams = rawParams;
        this.type = type;
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
    public void encode(){
        // Encode request type
        this.setRequestLength();
        this.encoderDecoderHelper.reset(this.length);
        this.encoderDecoderHelper.encodeInt32(this.type.ordinal());
    }

    /**
     * Decode request from a binary representation
     */
    public void decode(){
        // Reset helper
        this.encoderDecoderHelper.reset(this.rawParams);
        encoderDecoderHelper.decodeInt32();
    }

    /**
     * Process the request
     */
    public abstract void process();

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The type of the request, defined in EnumCst
     */
    public EnumCst.RequestTypes getType() {
        return this.type;
    }

    public byte[] getRawRequest() {
        return this.rawRequest;
    }

    /**
     * @return True if this request needs a response
     */
    public boolean needResponse(){
        return this.needResponse;
    }

    /**
     * @return True if the request's answer needs to be dispatched
     */
    public boolean needDispatch() { return this.needDispatch; }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the request length
     */
    protected abstract void setRequestLength();

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * @return String version of the class
     */
    @Override
    public String toString(){
        return Arrays.toString(this.rawParams);
    }

}