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
import io.vertx.core.json.JsonObject;

public class DeleteBeanRequest extends Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String componentId;
    private int beanId;
    private boolean isBeanDeleted;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Create a request that will be handle by the requestHandler
     * @param componentId The id of the component related to the bean
     * @param beanId The id of the bean to delete
     */
    public DeleteBeanRequest(String componentId, int beanId) {
        // Parent
        super(EnumCst.RequestTypes.DELETE_BEAN);

        // Fields
        this.componentId = componentId;
        this.beanId = beanId;
    }

    /**
     * Ctor used to initialize the request with
     * its content
     * @param requestBody The request's body
     */
    public DeleteBeanRequest(JsonObject requestBody) {
        super(EnumCst.RequestTypes.DELETE_BEAN, requestBody);
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param rawParams The raw Request in binary
     * @param length    The length of the request
     */
    public DeleteBeanRequest(byte[] rawParams, int length) {
        super(EnumCst.RequestTypes.DELETE_BEAN, rawParams, length);
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

        // Encode component id
        this.encoderDecoderHelper.encodeBoolean(this.isBeanDeleted);
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

        // Encode bean deleted
        this.isBeanDeleted = this.encoderDecoderHelper.decodeBoolean();
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
     * @return The bean's id determined by the server at creation
     */
    public int getBeanId() {
        return this.beanId;
    }

    /**
     * @return True if the bean has been deleted by the server
     */
    public boolean isBeanDeleted() {
        return isBeanDeleted;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set true or false depending on the deletion of the bean by the server
     * @param b The bean to delete
     * @return True if the bean has bean deleted by the server
     */
    public boolean setBeanDeleted(boolean b) {
        return this.isBeanDeleted;
    }

    /**
     * Set request's length
     */
    @Override
    protected void setRequestLength() {
        super.setRequestLength(Integer.BYTES,                                // Bean ID
                               this.componentId.length() * Character.BYTES, // Component id
                               Integer.BYTES); // Bean deleted
    }
}
