package fr.pops.sockets.encodedecoder;

import fr.pops.sockets.cst.IntCst;

public class EncoderDecoderHelper {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String buffer;
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
        this.buffer = "";
        this.cursor = 0;
    }

    /**
     * Ctor to instantiate non empty request
     * @param rawParams The raw params to decode
     */
    public EncoderDecoderHelper(String rawParams){
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
        this.buffer = "";
        this.cursor = 0;
    }

    /**
     * Reset the helper to decode a new message
     * @param rawParams The raw parameters to decode
     */
    public void reset(String rawParams){
        this.buffer = rawParams;
        this.cursor = 0;
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
        this.buffer += EncoderDecoder.encodeInt32(int32);
        this.cursor += IntCst.INT32_SIZE;
    }

    /**
     * Decode the given binary representation
     * of an integer in the buffer starting from the cursor position
     * @return The integer value parsed from the buffer
     */
    public int decodeInt32(){
        int out = EncoderDecoder.decodeInt32(buffer.substring(this.cursor, IntCst.INT32_SIZE));
        this.cursor += IntCst.INT32_SIZE;
        return out;
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
        this.buffer += EncoderDecoder.encodeDouble(dbl);
        this.cursor += IntCst.DOUBLE_SIZE;
    }

    /**
     * Decode the given binary representation
     * of a double in the buffer starting from the cursor position
     * @return The double value parsed from the buffer
     */
    public double decodeDouble(){
        double out = EncoderDecoder.decodeDouble(buffer.substring(this.cursor, IntCst.DOUBLE_SIZE));
        this.cursor += IntCst.DOUBLE_SIZE;
        return out;
    }

    /*****************************************
     *
     * Boolean
     *
     *****************************************/
    /**
     * Concatenate a binary representation
     * of the given boolean and the buffer
     * @param b
     */
    public void encodeBoolean(boolean b){
        this.buffer += EncoderDecoder.encodeBoolean(b);
        this.cursor += IntCst.BOOLEAN_SIZE;
    }

    /**
     * Decode the given binary representation
     * of a boolean in the buffer starting from the cursor position
     * @return The boolean parsed from the buffer
     */
    public boolean decodeBoolean(){
        boolean out = EncoderDecoder.decodeBoolean(buffer.substring(this.cursor, IntCst.BOOLEAN_SIZE));
        this.cursor += IntCst.BOOLEAN_SIZE;
        return out;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The raw parameters encoded
     */
    public String getRawParams() {
        return this.buffer;
    }
}
