package fr.pops.sockets.encodedecoder;

import fr.pops.sockets.cst.IntCst;
import fr.pops.sockets.cst.StrCst;

public abstract class EncoderDecoder {

    /*****************************************
     *
     * Integer
     *
     *****************************************/
    /**
     * Encode int32 value to binary string
     * @param int32 The integer value to encode in a binary string
     * @return A binary string representation of the input integer
     */
    public static String encodeInt32(int int32){
        String rawParam = Integer.toBinaryString(int32);
        return String.format(StrCst.INT32_STRING_FORMAT, rawParam).replaceAll(" ", "0");
    }

    /**
     * Decode int32 value from string
     * @param rawParam Binary string representation of an integer
     * @return The integer value contained in the input string
     */
    public static int decodeInt32(String rawParam){
        return Integer.parseInt(rawParam, IntCst.RADIX_BINARY);
    }

    /*****************************************
     *
     * Double
     *
     *****************************************/
    /**
     * Encode double value to binary string
     * TODO: describe cast to long then to dbl
     * @param dbl The double value to encode in a binary string
     * @return A binary string representation of the input double
     */
    public static String encodeDouble(double dbl){
        return Long.toBinaryString(Double.doubleToLongBits(dbl));
    }

    /**
     * Decode double value from string
     * @param rawParam Binary string representation of a double
     * @return The double value contained in the input string
     */
    public static double decodeDouble(String rawParam){
        return Double.longBitsToDouble(Long.parseLong(rawParam, IntCst.RADIX_BINARY));
    }

    /*****************************************
     *
     * Boolean
     *
     *****************************************/
    /**
     * Encode boolean to binary string representation
     * @param b The boolean to encode in a binary string
     * @return A binary string representation of the input double
     */
    public static String encodeBoolean(boolean b){
        return b ? StrCst.BOOLEAN_BINARY_TRUE : StrCst.BOOLEAN_BINARY_FALSE;
    }

    /**
     * Decode boolean from string
     * @param rawParam Binary string representation of a boolean
     * @return The boolean contained in the input string
     */
    public static boolean decodeBoolean(String rawParam){
        return rawParam.equals(StrCst.BOOLEAN_BINARY_TRUE);
    }

}
