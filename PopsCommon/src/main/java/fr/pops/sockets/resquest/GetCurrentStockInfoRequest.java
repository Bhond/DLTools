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
    private long accessTime = 0L;
    private double currentPrice = 0.0d;
    private String stockName = "";
    private boolean invalid = false;

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
     * Ctor
     */
    public GetCurrentStockInfoRequest(String symbol){
        super(EnumCst.RequestTypes.GET_CURRENT_STOCK_INFO);
        this.stockName = symbol;
    }

    /**
     * Ctor used to create a request when receiving one
     * @param rawParams The raw parameters to decode
     */
    public GetCurrentStockInfoRequest(byte[] rawParams) {
        super(EnumCst.RequestTypes.GET_CURRENT_STOCK_INFO, rawParams);
    }

    /**
     * Ctor used to create a request when receiving one
     * @param rawParams The raw parameters to decode
     * @param length The request's length
     */
    public GetCurrentStockInfoRequest(byte[] rawParams, int length) {
        super(EnumCst.RequestTypes.GET_CURRENT_STOCK_INFO, rawParams, length);
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

        // Access time
        this.encoderDecoderHelper.encodeLong64(this.accessTime);

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

        // Access time
        this.accessTime = this.encoderDecoderHelper.decodeLong64();

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
    public String getSymbol() {
        return this.stockName;
    }

    /**
     * @return The last time the data was accessed
     */
    public long getAccessTime() {
        return this.accessTime;
    }

    /**
     * @return The current stock price
     */
    public double getCurrentStockPrice() {
        return this.currentPrice;
    }

    /**
     * @return True is the symbol is invalid
     */
    public boolean isInvalid() {
        return this.invalid;
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
        super.setRequestLength(Integer.BYTES,                             // Stock name's length
                               this.stockName.length() * Character.BYTES, // Stock name
                               Long.BYTES,                                // Access time
                               Double.BYTES);                             // Current stock price
    }

    /**
     * Invalidate this request
     */
    public void invalidate(){
        this.stockName = "invalid";
        this.invalid = true;
    }

    /**
     * Set the access time
     * @param accessTime The time the data retrieved was last accessed
     */
    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }

    /**
     * Set the current price
     * @param currentPrice The current stock price
     */
    public void setCurrentStockPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

}
