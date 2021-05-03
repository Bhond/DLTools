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
 * Name: DblCst.java
 *
 * Description: Abstract class storing Double Constants
 *
 * Author: Charles MERINO
 *
 * Date: 12/02/2021
 *
 ******************************************************************************/
package fr.pops.cst;

public abstract class DblCst {

    /*****************************************
     *
     * Main View
     *
     *****************************************/
    public final static double SIZE_ANCHOR_ZERO = 0.0d;

    /*****************************************
     *
     * Network view
     *
     *****************************************/
    public final static double EARTH_SPHERE_RADIUS = 200d;

    /*****************************************
     *
     * Candlestick plot
     *
     *****************************************/
    public static final double CANDLE_LINE_WIDTH_DEFAULT = 1d;
    public static final double CANDLE_BODY_WIDTH_DEFAULT = 20d;

    /*****************************************
     *
     * Candle data default values
     *
     *****************************************/
    public static final double CANDLE_DATA_OPEN_DEFAULT = 0.0d;
    public static final double CANDLE_DATA_CLOSE_DEFAULT = 0.0d;
    public static final double CANDLE_DATA_LOW_DEFAULT = 0.0d;
    public static final double CANDLE_DATA_HIGH_DEFAULT = 0.0d;
    public static final double CANDLE_DATA_VOLUME_DEFAULT = 0.0d;

}
