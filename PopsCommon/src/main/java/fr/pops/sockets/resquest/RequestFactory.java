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
 * Name: RequestFactory.java
 *
 * Description: Class using the Factory pattern to create on the fly request
 *              depending on its type encode directly in its raw representation
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquest;

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
     *
     * TODO: Avoid reading the request and fill in the new request class with
     *       all of the parameters which leads to reading the id again
     *       Cloning the buffer array is mandatory right now due to the
     *       lack of idea for another solution
     *
     * @param rawRequest The raw representation of the request
     *                   in binary
     * @return A request filled with the right information and casted to the proper class
     */
    public Request getRequest(byte[] rawRequest){

        // Retrieve the request id
        this.encoderDecoderHelper.reset(rawRequest.clone());
        int id = this.encoderDecoderHelper.decodeInt32();
        int length = this.encoderDecoderHelper.decodeInt32();
        byte[] rawParams = this.encoderDecoderHelper.getRawParams();
        EnumCst.RequestTypes requestType = EnumCst.RequestTypes.values()[id];

        // Create the correct type of request
        switch (requestType){
            case AUTHENTICATE:
                return new AuthenticateRequest(rawParams, length);
            case DISCONNECTION:
                return new DisconnectionRequest(rawParams, length);
            case PING:
                return new PingRequest(rawParams, length);
            case GET_NETWORK_INFO:
                return new GetNetworkInfoRequest(rawParams, length);
            case GET_CURRENT_STOCK_INFO:
                return new GetCurrentStockInfoRequest(rawParams, length);
            case GET_MNIST_IMAGE:
                return new GetMNISTImageRequest(rawParams, length);
            case GET_MNIST_CONFIGURATION:
                return new GetMNISTConfiguration(rawParams, length);
            default:
                return null;
        }

    }







}
