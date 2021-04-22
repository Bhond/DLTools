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
 * Name: EncoderDecoderHelper.java
 *
 * Description: Class used to encode and decode typed values
 *              It interfaces with EncoderDecoder to make Reading values from
 *              a byte array easier
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.encodedecoder;

import fr.pops.sockets.cst.IntCst;

public class EncoderDecoderHelper {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private byte[] buffer;
    private int cursor;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Instantiate helper from scratch
     */
    public EncoderDecoderHelper(){
        this.buffer = new byte[0];
        this.cursor = 0;
    }

    /**
     * Ctor to instantiate non empty request
     * @param rawParams The raw params to decode
     */
    public EncoderDecoderHelper(byte[] rawParams){
        this.buffer = rawParams;
        this.cursor = 0;
    }

    /*****************************************
     *
     * Reset
     *
     *****************************************/
    /**
     * Reset the helper
     * to encode a new message
     */
    public void reset(){
        this.buffer = new byte[0];
        this.cursor = 0;
    }

    /**
     * Reset the helper
     * to encode a new message
     */
    public void reset(int length){
        this.buffer = new byte[length];
        this.cursor = 0;
    }

    /**
     * Reset the helper to decode a new message
     * @param rawParams The raw parameters to decode
     */
    public void reset(byte[] rawParams){
        this.buffer = rawParams;
        this.cursor = 0;
    }

    /*****************************************
     *
     * Buffer methods
     *
     *****************************************/
    /**
     * Put given array in the buffer
     * @param arr The array to add
     * @param size The size of the array which also increments the cursor position
     */
    private void put(byte[] arr, int size){
        int pos = this.cursor;
        for (byte b : arr){
            this.buffer[pos] = b;
            pos++;
        }
        this.cursor += size;
    }

    /**
     * Pull an array from the buffer
     * @param size The size of the array to extract
     */
    private byte[] pull(int size){
        int pos = 0;
        byte[] arr = new byte[size];
        for (int i = this.cursor; i < this.cursor + size; i++){
            //System.out.println("i: " + i +" ; buff: " + buffer[i]);
            arr[pos] = this.buffer[i];
            pos++;
        }
        this.cursor += size;
        return arr;
    }

    /*****************************************
     *
     * Integers
     *
     *****************************************/
    /**
     * Concatenate a binary representation
     * of the given integer and the buffer
     * @param int32 The integer to stack in the buffer
     */
    public void encodeInt32(int int32){
        this.put(EncoderDecoder.encodeInt32(int32), Integer.BYTES);
    }

    /**
     * Decode the given binary representation
     * of an integer in the buffer starting from the cursor position
     * @return The integer value parsed from the buffer
     */
    public int decodeInt32(){
        return EncoderDecoder.decodeInt32(this.pull(Integer.BYTES));
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
    public void encodeLong64(long long64){
        this.put(EncoderDecoder.encodeLong64(long64), Long.BYTES);
    }

    /**
     * Decode the given binary representation
     * of an long in the buffer starting from the cursor position
     * @return The long value parsed from the buffer
     */
    public long decodeLong64(){
        return EncoderDecoder.decodeLong64(this.pull(Long.BYTES));
    }

    /*****************************************
     *
     * Double
     *
     *****************************************/
    /**
     * Concatenate a binary representation
     * of the given double and the buffer
     * @param dbl The double to stack in the buffer
     */
    public void encodeDouble(double dbl){
        this.put(EncoderDecoder.encodeDouble(dbl), Double.BYTES);
    }

    /**
     * Decode the given binary representation
     * of a double in the buffer starting from the cursor position
     * @return The double value parsed from the buffer
     */
    public double decodeDouble(){
        return EncoderDecoder.decodeDouble(this.pull(Double.BYTES));
    }

    /*****************************************
     *
     * Boolean
     *
     *****************************************/
    /**
     * Concatenate a binary representation
     * of the given boolean and the buffer
     * @param b Boolean to encode
     */
    public void encodeBoolean(boolean b){
        this.put(EncoderDecoder.encodeBoolean(b), IntCst.BOOLEAN_BYTE_SIZE);
    }

    /**
     * Decode the given binary representation
     * of a boolean in the buffer starting from the cursor position
     * @return The boolean parsed from the buffer
     */
    public boolean decodeBoolean(){
        return EncoderDecoder.decodeBoolean(this.pull(IntCst.BOOLEAN_BYTE_SIZE));
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The raw parameters encoded
     */
    public byte[] getRawParams() {
        return this.buffer;
    }
}
