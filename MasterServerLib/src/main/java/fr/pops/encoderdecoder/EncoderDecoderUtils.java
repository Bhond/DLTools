package fr.pops.encoderdecoder;

public class EncoderDecoderUtils {

    private final static EncoderDecoderUtils instance = new EncoderDecoderUtils();

    /**
     * Standard ctor
     */
    private EncoderDecoderUtils(){

    }

    public static EncoderDecoderUtils getInstance() {
        return instance;
    }
}
