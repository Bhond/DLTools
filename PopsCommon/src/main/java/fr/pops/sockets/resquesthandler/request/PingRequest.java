package fr.pops.sockets.resquesthandler.request;

import fr.pops.sockets.cst.StrCst;

import java.net.Socket;

public class PingRequest extends Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private int state;
    private double t0;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Create a request that will be handled by the requestHandler
     */
    public PingRequest() {
        // Parent
        super(StrCst.PING);
        // Params
        this.state = 0;
        this.t0 = System.currentTimeMillis();
    }

    /**
     * Create a request that will be handled by the requestHandler
     */
    public PingRequest(String msg) {
        // Parent
        super(msg, StrCst.PING);
    }

    /**
     * Create a request that will be handled by the requestHandler
     * TODO: check utility...
     * @param socket  The socket calling the request
     */
    public PingRequest(Socket socket) {
        super(socket, StrCst.PING);
        this.state = 0;
        this.t0 = System.currentTimeMillis();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Information format:
     *  - The state
     *  - The epoch of creation of the ping request
     */
    @Override
    protected void encode() {
        // Parent
        super.encode();

        // Encode raw params
        this.encoderDecoderHelper.encodeInt32(this.state);
        this.encoderDecoderHelper.encodeDouble(this.t0);
    }

    /**
     *  Decode the ping request
     * @param rawParams The raw params
     */
    @Override
    protected void decode(String rawParams) {
        // Parent
        super.decode(rawParams);

        // Decode raw params
        this.state = this.encoderDecoderHelper.decodeInt32();
        this.t0 = this.encoderDecoderHelper.decodeDouble();
    }

}
