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
     * @return The integer value contained in the input byte array
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
     * @param dbl The double value to encode in a byte array
     * @return A byte array representation of the input double
     */
    public static byte[] encodeDouble(double dbl){
        return ByteBuffer.allocate(Double.BYTES).putDouble(dbl).array();
    }

    /**
     * Decode double value from string
     * @param rawParam Byte array representation of a double
     * @return The double value contained in the input array
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
     * @return A byte array representation of the input boolean
     */
    public static byte[] encodeBoolean(boolean b){
        return b ? EncoderDecoder.encodeInt32(1) : EncoderDecoder.encodeInt32(0);
    }

    /**
     * Decode boolean from string
     * @param rawParam Byte array representation of a boolean
     * @return The boolean contained in the input array
     */
    public static boolean decodeBoolean(byte[] rawParam){
        return EncoderDecoder.decodeInt32(rawParam) == 1;
    }

    /*****************************************
     *
     * String
     *
     *****************************************/
    /**
     * Encode String to byte array representation
     * @param str The String to encode in a byte array
     * @return A byte array representation of the input byte array
     */
    public static byte[] encodeString(String str){
        return str.getBytes();
    }

    /**
     * Decode String from string
     * @param rawParam Byte array representation of a string
     * @return The String contained in the input array
     */
    public static String decodeString(byte[] rawParam){
        return new String(rawParam);
    }

    /*****************************************
     *
     * ASCII
     *
     *****************************************/
    /**
     * Encode String to byte array representation
     * @param ascii The ASCII to encode in a byte array
     * @return A byte array representation of the input byte array
     */
    public static byte[] encodeASCII(String ascii){
        return ascii.getBytes();
    }

    /**
     * Decode ASCII from string
     * @param rawParam Byte array representation of a ASCII
     * @return The ASCII contained in the input array
     */
    public static String decodeASCII(byte[] rawParam){
        return new String(rawParam);
    }
}
