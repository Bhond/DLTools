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
 * Name: AuthenticateRequest.java
 *
 * Description: Class inheriting from PopsCommon.Request
 *              Describes the authenticate request sent by clients when
 *              connecting to the server to authenticate the identity of the client
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquest;

import fr.pops.sockets.cst.EnumCst;

public class GetCurrentStockInfoRequest extends Request{

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Stock info
    private double currentPrice = 0.0d;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private GetCurrentStockInfoRequest(){
        super(EnumCst.RequestTypes.AUTHENTICATE);
        // Nothing to be done
    }

    /**
     * Ctor used to initialize the request with
     * an id
     * @param clientId The id of the client to authenticate
     */
    public GetCurrentStockInfoRequest(long clientId) {
        super(EnumCst.RequestTypes.GET_CURRENT_STOCK_INFO);
    }

    /**
     * Ctor used to create a request when receiving one
     * @param rawParams The raw parameters to decode
     */
    public GetCurrentStockInfoRequest(byte[] rawParams) {
        super(EnumCst.RequestTypes.GET_CURRENT_STOCK_INFO, rawParams);
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
        // Parent
        super.encode();

        // Encode client id

        // Retrieve raw request
        this.rawRequest = this.encoderDecoderHelper.getRawParams();
    }

    /**
     * Decode request from a binary representation
     */
    public void decode(){
        // Parent
        super.decode();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The current stock price
     */
    public double getCurrentPrice() {
        return this.currentPrice;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set request's length
     */
    @Override
    protected void setRequestLength() {
        //            Request ID
        this.length = Integer.BYTES;
    }

    /**
     * Set the current price
     * @param currentPrice The current stock price
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
