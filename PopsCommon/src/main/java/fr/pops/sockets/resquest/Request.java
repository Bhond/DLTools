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
 * Name: Request.java
 *
 * Description: Class defining request exchange between the server and a client
 *
 * Author: Charles MERINO
 *
 * Date: 17/02/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquest;

import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.encodedecoder.EncoderDecoderHelper;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public abstract class Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected final Set<String> filterFields = new HashSet<>(Arrays.asList("encoderDecoderHelper", "length", "type", "buffer", "rawParams", "rawRequest"));
    protected final EncoderDecoderHelper encoderDecoderHelper = new EncoderDecoderHelper();
    protected final Buffer buffer = new BufferImpl();

    protected EnumCst.RequestTypes type;
    protected int length;
    protected byte[] rawParams;

    protected byte[] rawRequest;
    protected JsonObject requestBody;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private Request(){
        // Nothing to be done
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param type The type of the request, defined in EnumCst
     */
    protected Request(EnumCst.RequestTypes type){
        // Initialize the request
        this.type = type;
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param type The type of the request, defined in EnumCst
     * @param requestBody The request body in Json format
     */
    protected Request(EnumCst.RequestTypes type, JsonObject requestBody){
        // Initialize the request
        this.type = type;
        this.requestBody = requestBody;
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param rawParams The raw Request in binary
     * @param type The type of the request, defined in EnumCst
     * @param length The length of the request
     */
    protected Request(EnumCst.RequestTypes type, byte[] rawParams, int length){
        // Initialize the request
        this.rawParams = rawParams;
        this.type = type;
        this.length = length;
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
        // Encode request type
        this.setRequestLength();
        this.encoderDecoderHelper.reset(this.length);
        // Request type
        this.encoderDecoderHelper.encodeInt32(this.type.ordinal());
        // Request length
        this.encoderDecoderHelper.encodeInt32(this.length);
    }

    /**
     * Decode request from a binary representation
     */
    public void decode(){
        // Reset helper
        this.encoderDecoderHelper.reset(this.rawParams);
        // Request type
        this.type = EnumCst.RequestTypes.values()[encoderDecoderHelper.decodeInt32()];
        // Request length
        this.encoderDecoderHelper.decodeInt32();
    }

    /**
     * Encode the header of the request
     * It is composed of the type of the request coming from
     * EnumCst.RequestTypes and the length of the request
     */
    public void encodeHeader(){
        // Request type
        this.buffer.appendInt(this.type.ordinal());
        // Request length
        this.buffer.appendInt(this.length);
    }

    /**
     * Decode the header of the request
     * It is composed of the type of the request coming from
     * EnumCst.RequestTypes and the length of the request
     */
    public void decodeHeader(){
        // Request type
        this.type = EnumCst.RequestTypes.values()[this.buffer.getInt(0)];
        // Request length
        this.length = this.buffer.getInt(4);
    }

    /**
     * Decode the body of the request received
     */
    public void decodeBody(){
        // Retrieve request's fields
        List<Field> filteredFields = retrieveRequestFilteredFields();

        // Loop over the fields and set the
        for (Field f : filteredFields){
            try {
                f.setAccessible(true);
                this.setFieldValue(f, this.requestBody);
                f.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fill in the value of the given field
     * depending on the field's type
     * @param f The field to fill in
     * @param body The body of the request received
     *             containing the value to set
     */
    private void setFieldValue(Field f, JsonObject body){
        try {
            if (f.getType() == double.class){
                f.set(this, body.getDouble(f.getName()));
            } else if (f.getType() == int.class){
                f.set(this, body.getInteger(f.getName()));
            } else if (f.getType() == long.class){
                f.set(this, body.getLong(f.getName()));
            } else if (f.getType() == String.class){
                f.set(this, body.getString(f.getName()));
            } else if (f.getType() == boolean.class){
                f.set(this, body.getBoolean(f.getName()));
            } else if (f.getType() == Object.class){
                f.set(this, body.getNumber(f.getName()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Filter this class's fields
     * to retrieve only the ones to send from one client
     * to another
     * @return The fields composing the request's body
     */
    private List<Field> retrieveRequestFilteredFields(){
        // Retrieve the declared fields
        Field[] fields = this.getClass().getDeclaredFields();

        // Filter them
        return Arrays.stream(fields).filter(f -> !this.filterFields.contains(f.getName())).collect(Collectors.toList());
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The type of the request, defined in EnumCst
     */
    public EnumCst.RequestTypes getType() {
        return this.type;
    }

    /**
     * @return The number of bytes encoded in the array
     */
    public int getLength() {
        return this.length;
    }

    /**
     * @return The raw request encoded to be sent to server or a client
     */
    public byte[] getRawRequest() {
        // Add delimiter
        //this.encoderDecoderHelper.encodeASCII("\n");
        this.rawRequest = this.encoderDecoderHelper.getRawParams();

        return this.rawRequest;
    }

    /**
     * Read the type of the request
     * stored in the header of a plain JsonObject
     * @param o The JsonObject containing the type
     * @return The type of the request
     */
    public static EnumCst.RequestTypes getType(JsonObject o){
        JsonObject header = o.getJsonObject("request").getJsonObject("header");
        return EnumCst.RequestTypes.valueOf(header.getString("type"));
    }

    /**
     * Retrieve the request's body
     * @param o The JsonObject containing the body
     * @return The request's body
     */
    public static JsonObject getBody(JsonObject o){
        return o.getJsonObject("request").getJsonObject("body");
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the request length
     */
    protected void setRequestLength(){
        //            Id              Length
        this.length = Integer.BYTES + Integer.BYTES;
    }

    /**
     * Set the request length
     * @param length The length of the request without the header
     *               composed of the id and the length of the whole request
     */
    protected void setRequestLength(int... length){
        //            Id              Length          // Delimiter
        this.length = Integer.BYTES + Integer.BYTES + Arrays.stream(length).sum();
    }

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * @return String version of the class
     */
    @Override
    public String toString(){
        return Arrays.toString(this.rawParams);
    }

    /**
     * Turn all the declared fields
     * into a json with plain format
     * omitting the general parameters
     * except type and length
     * @return The json formatted request
     */
    public JsonObject toPlainJson(){
        // Retrieve fields to write
        List<Field> filteredFields = retrieveRequestFilteredFields();

        // Turn the request into a json
        // Header
        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put("type", this.type);
        headerMap.put("length", this.length);

        // Body
        HashMap<String, Object> bodyMap = new HashMap<>();
        for (Field f : filteredFields){
            try {
                f.setAccessible(true);
                bodyMap.put(f.getName(), f.get(this));
                f.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Concatenate the header and the body
        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("header", headerMap);
        requestMap.put("body", bodyMap);

        // Return the json result
        HashMap<String, Object> fullRequest = new HashMap<>();
        fullRequest.put("request", requestMap);
        return new JsonObject(fullRequest);
    }
}
