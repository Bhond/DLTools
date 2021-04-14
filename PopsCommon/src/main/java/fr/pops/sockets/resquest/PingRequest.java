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
    private int state = 0;
    private double t0 = 0.0d;
    private double t1 = 0.0d;
    private double responseDelay = 0.0d;

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
        this.needResponse = true;
        this.state = 0;
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

        // Encode raw params
        this.encoderDecoderHelper.encodeInt32(this.state);
        this.encoderDecoderHelper.encodeDouble(this.t0);
        this.rawRequest = this.encoderDecoderHelper.getRawParams();
    }

    /**
     *  Decode the ping request
     */
    @Override
    public void decode() {
        // Parent
        super.decode();

        // Decode raw params
        this.state = this.encoderDecoderHelper.decodeInt32();
        this.t0 = this.encoderDecoderHelper.decodeDouble();
    }

    /**
     * Process the request
     */
    @Override
    public void process(){
        if (this.state == 0){
            this.state++;
            this.needResponse = true;
        } else if (this.state == 1){
            this.needDispatch = true;
            this.t1 = System.currentTimeMillis();
            this.responseDelay = this.t1 - this.t0;
        }
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The state of the ping request
     */
    public int getState() {
        return this.state;
    }

    /**
     * @return The response delay
     */
    public double getResponseDelay() {
        return this.responseDelay;
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
        //            ID              State           Delay
        this.length = Integer.BYTES + Integer.BYTES + Double.BYTES;
    }
}
