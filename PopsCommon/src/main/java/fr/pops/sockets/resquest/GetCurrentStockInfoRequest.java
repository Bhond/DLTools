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
 * Name: GetCurrentStockInfoRequest.java
 *
 * Description: Class inheriting from PopsCommon.Request
 *              Describes the GetCurrentStockInfo request sent by clients
 *              to update the stock view
 *
 * Author: Charles MERINO
 *
 * Date: 22/04/2021
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
    private String stockName = "TSLA";

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public GetCurrentStockInfoRequest(){
        super(EnumCst.RequestTypes.GET_CURRENT_STOCK_INFO);
        // Nothing to be done
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

        // Stock name
        this.encoderDecoderHelper.encodeString(this.stockName);

        // Current price
        this.encoderDecoderHelper.encodeDouble(this.currentPrice);

        // Retrieve raw request
        this.rawRequest = this.encoderDecoderHelper.getRawParams();
    }

    /**
     * Decode request from a binary representation
     */
    public void decode(){
        // Parent
        super.decode();

        // Stock name
        this.stockName = this.encoderDecoderHelper.decodeString();

        // Current price
        this.currentPrice = this.encoderDecoderHelper.decodeDouble();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The stock name
     */
    public String getStockName() {
        return this.stockName;
    }

    /**
     * @return The current stock price
     */
    public double getCurrentStockPrice() {
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
        //            Request ID                 Stock Name                                        Current stock price
        this.length = Integer.BYTES +  Integer.BYTES + this.stockName.length() * Character.BYTES + Double.BYTES;
    }

    /**
     * Set the current price
     * @param currentPrice The current stock price
     */
    public void setCurrentStockPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
