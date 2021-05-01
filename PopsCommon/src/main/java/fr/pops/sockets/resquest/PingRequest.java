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
 * Name: PingRequest.java
 *
 * Description: Class inheriting from PopsCommon.Request
 *              Describes the ping request
 *              It is simply used to ping the server or a client to know
 *              the response delay between both end points
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquest;

import fr.pops.sockets.cst.EnumCst;

public class PingRequest extends Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private double t0 = 0.0d;
    private double t1 = 0.0d;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Create a request that will be handled by the requestHandler
     */
    public PingRequest() {
        // Parent
        super(EnumCst.RequestTypes.PING);
        // Params
        this.t0 = System.currentTimeMillis();
    }

    /**
     * Create a request that will be handled by the requestHandler
     * @param rawParams The raw parameters to decode
     */
    public PingRequest(byte[] rawParams) {
        // Parent
        super(EnumCst.RequestTypes.PING, rawParams);
    }

    /**
     * Create a request that will be handled by the requestHandler
     * @param rawParams The raw parameters to decode
     * @param length The request's length
     */
    public PingRequest(byte[] rawParams, int length) {
        // Parent
        super(EnumCst.RequestTypes.PING, rawParams, length);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Information format:
     *  - The state
     *  - The epoch of creation of the ping request
     */
    @Override
    public void encode() {
        // Parent
        super.encode();

        // t0
        this.encoderDecoderHelper.encodeDouble(this.t0);

        // Get encoded request
        this.rawRequest = this.encoderDecoderHelper.getRawParams();
    }

    /**
     *  Decode the ping request
     */
    @Override
    public void decode() {
        // Parent
        super.decode();

        // t0
        this.t0 = this.encoderDecoderHelper.decodeDouble();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The response delay
     */
    public double getResponseDelay() {
        return this.t1 - this.t0;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the request's length
     */
    @Override
    protected void setRequestLength() {
        super.setRequestLength(Double.BYTES); // Delay
    }

    /**
     * Set the response delay
     * @param t1 The new time to compute the response delay
     */
    public void setT1(double t1) {
        this.t1 = t1;
    }
}
