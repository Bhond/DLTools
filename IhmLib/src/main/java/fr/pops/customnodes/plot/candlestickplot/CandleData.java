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
 * Name: CandleData.java
 *
 * Description: Class holdings the data contained in a candle
 *
 * Author: Charles MERINO
 *
 * Date: 27/04/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.plot.candlestickplot;

import fr.pops.cst.DblCst;

import java.util.Date;

public class CandleData {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected double open = DblCst.CANDLE_DATA_OPEN_DEFAULT;
    protected double high = DblCst.CANDLE_DATA_HIGH_DEFAULT;
    protected double low = DblCst.CANDLE_DATA_LOW_DEFAULT;
    protected double close = DblCst.CANDLE_DATA_CLOSE_DEFAULT;
    protected long volume = (long) DblCst.CANDLE_DATA_VOLUME_DEFAULT; // TODO: Remove cast
    protected Date dateTime;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private CandleData() {
        // Nothing to be done
    }

    /**
     * Ctor
     * @param dateTime The date time of this bar
     * @param open The open price of this bar
     * @param high The high price of this bar
     * @param low The low price of this bar
     * @param close The close price of this bar
     * @param volume The volume of this bar
     */
    public CandleData(Date dateTime, double open, double high, double low, double close, long volume) {
        this.dateTime = dateTime;
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.volume = volume;
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Updates the last price, adjusting the high and low
     * @param close The last price
     */
    public void update( double close ) {
        if( close > high ) {
            high = close;
        }

        if( close < low ) {
            low = close;
        }
        this.close = close;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The current date time
     */
    public Date getDateTime() {
        return this.dateTime;
    }

    /**
     * @return The open price of this bar
     */
    public double getOpen() {
        return this.open;
    }

    /**
     * @return The High price of this bar
     */
    public double getHigh() {
        return this.high;
    }

    /**
     * @return The Low price of this bar
     */
    public double getLow() {
        return this.low;
    }

    /**
     * @return The close price for this bar
     */
    public double getClose() {
        return this.close;
    }

    /**
     * @return The Volume for this bar
     */
    public long getVolume() {
        return this.volume;
    }


    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the current date time
     * @param dateTime The current date time
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Sets the open price for this bar
     * @param open The open price for this bar
     */
    public void setOpen(double open) {
        this.open = open;
    }

    /**
     * Sets the high price for this bar
     * @param high The high price for this bar
     */
    public void setHigh(double high) {
        this.high = high;
    }

    /**
     * Sets the low price for this bar
     * @param low The low price for this bar
     */
    public void setLow(double low) {
        this.low = low;
    }

    /**
     * Sets the closing price for this bar
     * @param close The closing price for this bar
     */
    public void setClose(double close) {
        this.close = close;
    }

    /**
     * Sets the volume for this bar
     * @param volume Sets the volume for this bar
     */
    public void setVolume(long volume) {
        this.volume = volume;
    }

}
