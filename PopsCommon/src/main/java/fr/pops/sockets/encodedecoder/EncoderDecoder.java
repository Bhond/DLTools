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
 * Name: EncoderDecoder.java
 *
 * Description: Abstract class used to encode and decode typed values
 *              Should be used with the EncodeDecoderHelper
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.encodedecoder;

import java.nio.ByteBuffer;

public abstract class EncoderDecoder {

    /*****************************************
     *
     * Integer
     *
     *****************************************/
    /**
     * Encode int32 value to byte array
     * @param int32 The integer value to encode in a byte array
     * @return A byte array representation of the input integer
     */
    public static byte[] encodeInt32(int int32){
        return ByteBuffer.allocate(Integer.BYTES).putInt(int32).array();
    }

    /**
     * Decode int32 value from string
     * @param rawParam Byte array representation of an integer
     * @return The integer value contained in the input string
     */
    public static int decodeInt32(byte[] rawParam){
        return ByteBuffer.wrap(rawParam).getInt();
    }

    /*****************************************
     *
     * Integers
     *
     *****************************************/
    /**
     * Concatenate a binary representation
     * of the given long and the buffer
     * @param long64 The integer to stack in the buffer
     */
    public static byte[] encodeLong64(long long64){
        return ByteBuffer.allocate(Long.BYTES).putLong(long64).array();
    }

    /**
     * Decode the given binary representation
     * of an long in the buffer starting from the cursor position
     * @return The long value parsed from the buffer
     */
    public static long decodeLong64(byte[] rawParam){
        return ByteBuffer.wrap(rawParam).getLong();
    }

    /*****************************************
     *
     * Double
     *
     *****************************************/
    /**
     * Encode double value to byte array
     * TODO: describe cast to long then to dbl
     * @param dbl The double value to encode in a byte array
     * @return A byte array representation of the input double
     */
    public static byte[] encodeDouble(double dbl){
        return ByteBuffer.allocate(Double.BYTES).putDouble(dbl).array();
    }

    /**
     * Decode double value from string
     * @param rawParam Byte array representation of a double
     * @return The double value contained in the input string
     */
    public static double decodeDouble(byte[] rawParam){
        return ByteBuffer.wrap(rawParam).getDouble();
    }

    /*****************************************
     *
     * Boolean
     *
     *****************************************/
    /**
     * Encode boolean to byte array representation
     * @param b The boolean to encode in a byte array
     * @return A byte array representation of the input double
     */
    public static byte[] encodeBoolean(boolean b){
        return b ? EncoderDecoder.encodeInt32(1) : EncoderDecoder.encodeInt32(0);
    }

    /**
     * Decode boolean from string
     * @param rawParam Byte array representation of a boolean
     * @return The boolean contained in the input string
     */
    public static boolean decodeBoolean(byte[] rawParam){
        return EncoderDecoder.decodeInt32(rawParam) == 1;
    }

}
