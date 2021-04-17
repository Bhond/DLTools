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
 * Name: GetServerInfoRequest.java
 *
 * Description: Class inheriting from PopsCommon.Request
 *              Describes the "get server info" request
 *              It retrieves the server information
 *              to display it with the server info view
 *
 * Author: Charles MERINO
 *
 * Date: 17/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquest;

import fr.pops.sockets.cst.EnumCst;

import java.util.ArrayList;
import java.util.List;

public class GetServerInfoRequest extends Request{

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private int state = 0;

    private double frequency = 0.0d;
    private int nbClients = 0;
    private List<EnumCst.ClientTypes> clientTypes = new ArrayList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public GetServerInfoRequest(){
        // Parent
        super(EnumCst.RequestTypes.GET_SERVER_INFO);
    }

    /**
     * Ctor
     */
    public GetServerInfoRequest(byte[] rawParams){
        // Parent
        super(EnumCst.RequestTypes.GET_SERVER_INFO, rawParams);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    @Override
    public void encode() {
        // Parent
        super.encode();

        // State
        this.encoderDecoderHelper.encodeInt32(this.state);

        /*
         * Info
         * Must concatenate the server info
         * filled in by the server
         */
        if (this.state != 0){
            // Frequency
           this.encoderDecoderHelper.encodeDouble(this.frequency);

            // Nb clients
            this.encoderDecoderHelper.encodeInt32(this.nbClients);

            // Clients" types
            for (EnumCst.ClientTypes clientType : this.clientTypes) {
                this.encoderDecoderHelper.encodeInt32(clientType.ordinal());
            }
        }

        // Raw request
        this.rawRequest = this.encoderDecoderHelper.getRawParams();
    }

    @Override
    public void decode() {
        // Parent
        super.decode();

        // State
        this.state = this.encoderDecoderHelper.decodeInt32();

        // Decode info if it is the response
        if (this.state != 0){
            // Frequency
            this.frequency = this.encoderDecoderHelper.decodeDouble();

            // Nb clients
            this.nbClients = this.encoderDecoderHelper.decodeInt32();

            // Clients" types
            for (int i = 0; i < this.nbClients; i++){
                this.clientTypes.add(EnumCst.ClientTypes.getType(this.encoderDecoderHelper.decodeInt32()));
            }
        }
    }

    /**
     * Process the request
     */
    @Override
    public void process() {
        if (this.state == 0){
            this.needResponse = true;
            this.state++;
        } else {
            this.needDispatch = true;
        }
    }

    /**
     * Set the request length to reset the helper properly
     */
    @Override
    protected void setRequestLength() {
        this.length = Integer.BYTES               // ID
                + Integer.BYTES                   // State
                + Double.BYTES                    // Frequency
                + Integer.BYTES                   // Nb clients
                + this.nbClients * Integer.BYTES; // Clients' types
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * @return The server's update rate
     */
    public double getFrequency() {
        return this.frequency;
    }
    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Retrieve the server frequency / update rate
     */
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    /**
     * Retrieve the clients' types
     * And store the number of clients at the same time
     * @param clientTypeIds The client type id connected to the server
     */
    public void setClientTypes(Long... clientTypeIds) {
        for (Long id : clientTypeIds){
            this.clientTypes.add(EnumCst.ClientTypes.getType(id));
        }
        this.nbClients = clientTypeIds.length;
    }
}
