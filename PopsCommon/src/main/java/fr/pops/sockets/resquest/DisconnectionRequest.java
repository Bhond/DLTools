package fr.pops.sockets.resquest;

import fr.pops.sockets.cst.EnumCst;

public class DisconnectionRequest extends Request {

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
     */
    private DisconnectionRequest() {
        super(EnumCst.RequestTypes.DISCONNECTION);
    }

    /**
     * Ctor used to initialize the request with
     * an id
     * @param clientId The id of the client to authenticate
     */
    public DisconnectionRequest(long clientId) {
        super(EnumCst.RequestTypes.DISCONNECTION);
        this.clientId = clientId;
    }

    /**
     * Ctor used to create a request when receiving one
     * @param rawParams The raw parameters to decode
     */
    public DisconnectionRequest(byte[] rawParams) {
        super(EnumCst.RequestTypes.DISCONNECTION, rawParams);
    }

    /**
     * Ctor used to create a request when receiving one
     * @param rawParams The raw parameters to decode
     * @param length The request's length
     */
    public DisconnectionRequest(byte[] rawParams, int length) {
        super(EnumCst.RequestTypes.DISCONNECTION, rawParams, length);
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
        this.rawRequest = this.encoderDecoderHelper.getRawParams();
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
