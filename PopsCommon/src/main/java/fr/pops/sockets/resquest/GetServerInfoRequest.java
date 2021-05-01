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
     * Ctor used to create a request when receiving one
     * @param rawParams The raw parameters to decode
     */
    public GetServerInfoRequest(byte[] rawParams){
        // Parent
        super(EnumCst.RequestTypes.GET_SERVER_INFO, rawParams);
    }

    /**
     * Ctor used to create a request when receiving one
     * @param rawParams The raw parameters to decode
     * @param length The request's length
     */
    public GetServerInfoRequest(byte[] rawParams, int length){
        // Parent
        super(EnumCst.RequestTypes.GET_SERVER_INFO, rawParams, length);
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

        // Frequency
        this.encoderDecoderHelper.encodeDouble(this.frequency);

        // Nb clients
        this.encoderDecoderHelper.encodeInt32(this.nbClients);

        // Clients' types
        for (EnumCst.ClientTypes clientType : this.clientTypes) {
            this.encoderDecoderHelper.encodeLong64(clientType.getId());
        }

        // Raw request
        this.rawRequest = this.encoderDecoderHelper.getRawParams();
    }

    @Override
    public void decode() {
        // Parent
        super.decode();

        // Frequency
        this.frequency = this.encoderDecoderHelper.decodeDouble();

        // Nb clients
        this.nbClients = this.encoderDecoderHelper.decodeInt32();

        // Clients' types
        for (int i = 0; i < this.nbClients; i++){
            long clientTypeId = this.encoderDecoderHelper.decodeLong64();
            EnumCst.ClientTypes clientType = EnumCst.ClientTypes.getType(clientTypeId);
            if (clientType != null) this.clientTypes.add(clientType);
        }
    }

    /**
     * Set the request length to reset the helper properly
     */
    @Override
    protected void setRequestLength() {
        super.setRequestLength(Double.BYTES,                 // Frequency
                               Integer.BYTES,                // Nb clients
                               this.nbClients * Long.BYTES); // Clients' types
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

    /**
     * @return The clients connected to the server
     */
    public List<EnumCst.ClientTypes> getClientTypes() {
        return clientTypes;
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
        // Store the client types
        for (Long id : clientTypeIds){
            this.clientTypes.add(EnumCst.ClientTypes.getType(id));
        }
        this.nbClients = clientTypeIds.length;

        // Update request length
        this.setRequestLength();
    }
}
