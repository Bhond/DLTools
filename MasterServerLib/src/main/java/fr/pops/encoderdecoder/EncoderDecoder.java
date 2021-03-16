package fr.pops.encoderdecoder;

import fr.pops.serverlibcst.IntCst;

import java.nio.ByteBuffer;

public class EncoderDecoder {

    /*****************************************
     *
     * Static Attributes
     *
     *****************************************/
    private final static EncoderDecoder instance = new EncoderDecoder();

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private ByteBuffer buffer;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    private EncoderDecoder(){

    }

    /*****************************************
     *
     * Reset encoder / decoder
     *
     *****************************************/
    public void reset(Byte[] rawParams){
        this.buffer.reset();
    }

    /*****************************************
     *
     * Encode / decode methods
     *
     *****************************************/
    /**
     * Encode given param to byte array
     * @param i Int value to encode in a 4 bytes array
     */
    public void encode(int i){
        this.buffer = ByteBuffer.allocate(IntCst.BYTE_SIZE_INTEGER);
        this.buffer.putInt(i);
    }

    public void decode(){

    }

    /**
     * Encode given param to byte array
     * @param d Dbl value to encode in a 8 bytes array
     */
    public void encode(double d){
        this.buffer = ByteBuffer.allocate(IntCst.BYTE_SIZE_DOUBLE);
        this.buffer.putDouble(d);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The instance of the EncoderDecoder
     */
    public static EncoderDecoder getInstance() {
        return instance;
    }
}
