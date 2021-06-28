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
 * Name: CreateBeanBean.java
 *
 * Description: Class inheriting from PopsCommon.Request
 *              Describes the CreateBean request
 *              It asks the server to create a new bean
 *              of the beanTypeId type
 *              It is sent back to inform the client
 *              what is the id of the bean created
 *
 * Author: Charles MERINO
 *
 * Date: 25/05/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquest.beanrequests;

import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.Request;

public class CreateBeanRequest extends Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String componentId;
    private String beanTypeId;
    private int beanId;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Create a request that will be handle by the requestHandler
     * @param componentId The id of the component related to the bean
     * @param beanTypeId The type id of the bean to create
     */
    public CreateBeanRequest(String componentId, String beanTypeId) {
        // Parent
        super(EnumCst.RequestTypes.CREATE_BEAN);

        // Fields
        this.componentId = componentId;
        this.beanTypeId = beanTypeId;
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param rawParams The raw Request in binary
     * @param length    The length of the request
     */
    public CreateBeanRequest(byte[] rawParams, int length) {
        super(EnumCst.RequestTypes.CREATE_BEAN, rawParams, length);
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

        // Encode bean id
        this.encoderDecoderHelper.encodeInt32(this.beanId);

        // Encode component id
        this.encoderDecoderHelper.encodeString(this.componentId);

        // Encode bean type id
        this.encoderDecoderHelper.encodeString(this.beanTypeId);

        this.rawRequest = this.encoderDecoderHelper.getRawParams();
    }

    /**
     * Decode request from a binary representation
     */
    public void decode(){
        // Parent
        super.decode();

        // Encode bean id
        this.beanId = this.encoderDecoderHelper.decodeInt32();

        // Encode bean type id
        this.componentId = this.encoderDecoderHelper.decodeString();

        // Encode bean type id
        this.beanTypeId = this.encoderDecoderHelper.decodeString();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The type id of the bean to create it by reflection
     */
    public String getComponentId() {
        return this.componentId;
    }

    /**
     * @return The type id of the bean to create it by reflection
     */
    public String getBeanTypeId() {
        return this.beanTypeId;
    }

    /**
     * @return The bean's id determined by the server at creation
     */
    public int getBeanId() {
        return this.beanId;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Sets the bean id determined by the bean manager at creation
     * @param beanId The bean's id
     */
    public void setBeanId(int beanId) {
        this.beanId = beanId;
    }

    /**
     * Set request's length
     */
    @Override
    protected void setRequestLength() {
        super.setRequestLength(Integer.BYTES,                               // Bean ID
                               this.componentId.length() * Character.BYTES, // Component id
                               this.beanTypeId.length() * Character.BYTES); // Bean type id
    }
}
