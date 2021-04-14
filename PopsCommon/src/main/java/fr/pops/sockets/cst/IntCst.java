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
 * Name: IntCst.java
 *
 * Description: Abstract class storing Constant Integer values.
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.cst;

public class IntCst {

    /*****************************************
     *
     * Encoder / decoder
     *
     *****************************************/
    public static final int BOOLEAN_BYTE_SIZE = 1;

    public static final int BUFFER_8k_SIZE = 8192;
    public static final int BUFFER_16k_SIZE = 16384;
    public static final int BUFFER_32k_SIZE = 32768;
    public static final int BUFFER_65k_SIZE = 65536;

    public static final int BUFFER_SIZE = BUFFER_65k_SIZE;

}
