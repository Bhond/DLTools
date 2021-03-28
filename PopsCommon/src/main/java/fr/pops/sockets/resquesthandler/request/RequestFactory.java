package fr.pops.sockets.resquesthandler.request;

import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.encodedecoder.EncoderDecoderHelper;

public class RequestFactory {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private final EncoderDecoderHelper encoderDecoderHelper = new EncoderDecoderHelper();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public RequestFactory(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * Build request from the given raw representation
     * @param rawRequest The raw representation of the request
     *                   in binary
     * @return A request filled with the right information and casted to the proper class
     */
    public Request getRequest(String rawRequest){

        // Retrieve the request id
        this.encoderDecoderHelper.reset(rawRequest);
        int id = this.encoderDecoderHelper.decodeInt32();
        EnumCst.RequestTypes requestType = EnumCst.RequestTypes.values()[id];

        // Create the correct type of request
        switch (requestType){
            case PING:
                return new PingRequest();
            default:
                return null;
        }

    }







}
