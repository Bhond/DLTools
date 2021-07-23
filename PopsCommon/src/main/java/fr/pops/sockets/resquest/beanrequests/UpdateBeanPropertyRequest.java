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

import fr.pops.beans.properties.Property;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.Request;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("unchecked")
public class UpdateBeanPropertyRequest<T> extends Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String propertyName;
    private int beanId;
    private fr.pops.commoncst.EnumCst.PropertyTypes type;
    private T newValue;
    private int bytesValue;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Create a request that will be handle by the requestHandler
     * @param property The property updated
     */
    public UpdateBeanPropertyRequest(Property<?> property) {
        // Parent
        super(EnumCst.RequestTypes.UPDATE_BEAN_PROPERTY);

        // Fields
        this.beanId = property.getBeanId();
        this.propertyName = property.getName();
        this.type = property.getType();
        this.newValue = (T) property.getValue();
        this.setNbBytesValue();
    }

    /**
     * Ctor used to initialize the request with
     * its content
     * @param requestBody The request's body
     */
    public UpdateBeanPropertyRequest(JsonObject requestBody) {
        super(EnumCst.RequestTypes.UPDATE_BEAN_PROPERTY, requestBody);
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param rawParams The raw Request in binary
     * @param length    The length of the request
     */
    public UpdateBeanPropertyRequest(byte[] rawParams, int length) {
        super(EnumCst.RequestTypes.UPDATE_BEAN_PROPERTY, rawParams, length);
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

        // Encode property name
        this.encoderDecoderHelper.encodeString(this.propertyName);

        // Encode type
        this.encoderDecoderHelper.encodeInt32(this.type.ordinal());

        // Encode value depending on the property's type
        this.encodeValue();
    }

    /**
     * Encode value depending on the property's type
     */
    private void encodeValue() {
        switch (this.type){
            case INT:
                encoderDecoderHelper.encodeInt32((int)this.newValue);
                break;
            case DOUBLE:
                encoderDecoderHelper.encodeDouble((double)this.newValue);
                break;
            case BOOLEAN:
                encoderDecoderHelper.encodeBoolean((boolean)this.newValue);
                break;
            case STRING:
                encoderDecoderHelper.encodeString((String)this.newValue);
                break;
        }
    }

    /**
     * Decode request from a binary representation
     */
    public void decode(){
        // Parent
        super.decode();

        // Decode bean id
        this.beanId = this.encoderDecoderHelper.decodeInt32();

        // Decode property name
        this.propertyName = this.encoderDecoderHelper.decodeString();

        // Decode type
        this.type = fr.pops.commoncst.EnumCst.PropertyTypes.values()[this.encoderDecoderHelper.decodeInt32()];
        this.setNbBytesValue();

        // Decode value
        this.newValue = (T) this.decodeValue();
    }

    /**
     * Decode value
     * @return The decode value depending on the property's type
     */
    private Object decodeValue(){
        switch (this.type){
            case INT:
                return encoderDecoderHelper.decodeInt32();
            case DOUBLE:
                return encoderDecoderHelper.decodeDouble();
            case BOOLEAN:
                return encoderDecoderHelper.decodeBoolean();
            case STRING:
                return encoderDecoderHelper.decodeString();
            default:
                return null;
        }
    }

    /**
     * Set the number of bytes encoding the new value
     */
    private void setNbBytesValue(){
        switch (this.type){
            case INT:
                this.bytesValue = Integer.BYTES;
                break;
            case DOUBLE:
                this.bytesValue = Double.BYTES;
                break;
            case BOOLEAN:
                this.bytesValue = Integer.BYTES;
                break;
            case STRING:
                this.bytesValue = ((String )this.newValue).length() * Integer.BYTES;
                break;
            default:
                this.bytesValue = 0;
                break;
        }
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The bean's id determined by the server at creation
     */
    public int getBeanId() {
        return this.beanId;
    }

    /**
     * @return The property's name
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @return The the property's new value
     */
    public T getNewValue() {
        return newValue;
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
        super.setRequestLength(Integer.BYTES,                                 // Bean ID
                               this.propertyName.length() * Character.BYTES,  // Property name
                               Integer.BYTES,                                 // Property type
                               this.bytesValue);                              // Value
    }
}
