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

    /**
     * Create a request that will be handle by the requestHandler
     * @param rawParams The raw Request in binary
     * @param type The type of the request, defined in EnumCst
     * @param length The length of the request
     */
    protected Request(EnumCst.RequestTypes type, byte[] rawParams, int length){
        // Initialize the request
        this.rawParams = rawParams;
        this.type = type;
        this.length = length;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Encode request to a binary representation
     */
    public void encode(){
        // Encode request type
        this.setRequestLength();
        this.encoderDecoderHelper.reset(this.length);
        // Request type
        this.encoderDecoderHelper.encodeInt32(this.type.ordinal());
        // Request length
        this.encoderDecoderHelper.encodeInt32(this.length);
    }

    /**
     * Decode request from a binary representation
     */
    public void decode(){
        // Reset helper
        this.encoderDecoderHelper.reset(this.rawParams);
        // Request type
        this.type = EnumCst.RequestTypes.values()[encoderDecoderHelper.decodeInt32()];
        // Request length
        encoderDecoderHelper.decodeInt32();
    }

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

    /**
     * @return The number of bytes encoded in the array
     */
    public int getLength() {
        return this.length;
    }

    /**
     * @return The raw request encoded to be sent to server or a client
     */
    public byte[] getRawRequest() {
        return this.rawRequest;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the request length
     */
    protected void setRequestLength(){
        //            Id              Length
        this.length = Integer.BYTES + Integer.BYTES;
    }

    /**
     * Set the request length
     * @param length The length of the request without the header
     *               composed of the id and the length of the whole request
     */
    protected void setRequestLength(int... length){
        //            Id              Length
        this.length = Integer.BYTES + Integer.BYTES + Arrays.stream(length).sum();
    }

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
