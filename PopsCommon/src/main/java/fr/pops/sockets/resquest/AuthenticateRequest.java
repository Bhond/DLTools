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
 * Name: AuthenticateRequest.java
 *
 * Description: Class inheriting from PopsCommon.Request
 *              Describes the authenticate request sent by clients when
 *              connecting to the server to authenticate the identity of the client
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquest;

import fr.pops.sockets.cst.EnumCst;
import io.vertx.core.json.JsonObject;

public class AuthenticateRequest extends Request{

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private long clientId;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private AuthenticateRequest(){
        super(EnumCst.RequestTypes.AUTHENTICATE);
        // Nothing to be done
    }

    /**
     * Ctor used to initialize the request with
     * an id
     * @param clientId The id of the client to authenticate
     */
    public AuthenticateRequest(long clientId) {
        super(EnumCst.RequestTypes.AUTHENTICATE);
        this.clientId = clientId;
    }

    /**
     * Ctor used to initialize the request with
     * its content
     * @param requestBody The request's body
     */
    public AuthenticateRequest(JsonObject requestBody) {
        super(EnumCst.RequestTypes.AUTHENTICATE, requestBody);
    }

    /**
     * Ctor used to create a request when receiving one
     * @param rawParams The raw parameters to decode
     */
    public AuthenticateRequest(byte[] rawParams, int length) {
        super(EnumCst.RequestTypes.AUTHENTICATE, rawParams, length);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Encode request to a binary representation
     */
    public void encode(){
        // Parent
        super.encode();

        // Encode client id
        this.encoderDecoderHelper.encodeLong64(this.clientId);
    }

    /**
     * Decode request from a binary representation
     */
    public void decode(){
        // Parent
        super.decode();

        // Decode client id
        this.clientId = this.encoderDecoderHelper.decodeLong64();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The client id
     */
    public long getClientId() {
        return this.clientId;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set request's length
     */
    @Override
    protected void setRequestLength() {
        super.setRequestLength(Long.BYTES); // Client ID
    }
}
