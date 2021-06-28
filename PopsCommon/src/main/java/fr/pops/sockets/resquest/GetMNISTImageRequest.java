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
 * Name: PingRequest.java
 *
 * Description: Class inheriting from PopsCommon.Request
 *              Describes the ping request
 *              It is simply used to ping the server or a client to know
 *              the response delay between both end points
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquest;

import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.sockets.cst.EnumCst;

public class GetMNISTImageRequest extends Request {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private int idx;
    private int label;
    private BaseNDArray image;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Create a request that will be handled by the requestHandler
     * @param idx The index of the image to retrieve
     */
    public GetMNISTImageRequest(int idx) {
        // Parent
        super(EnumCst.RequestTypes.GET_MNIST_IMAGE);
        this.idx = idx;
    }

    /**
     * Create a request that will be handled by the requestHandler
     * @param rawParams The raw parameters to decode
     * @param length The request's length
     */
    public GetMNISTImageRequest(byte[] rawParams, int length) {
        // Parent
        super(EnumCst.RequestTypes.GET_MNIST_IMAGE, rawParams, length);
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
    public void encode() {
        // Parent
        super.encode();

        // Index of the image
        this.encoderDecoderHelper.encodeInt32(this.idx);

        // Label of the image
        this.encoderDecoderHelper.encodeInt32(this.label);

        // Image
        if (this.image != null){
            this.encoderDecoderHelper.encodeBaseNDArray(this.image);
        }

        // Get encoded request
        this.rawRequest = this.encoderDecoderHelper.getRawParams();
    }

    /**
     *  Decode the ping request
     */
    @Override
    public void decode() {
        // Parent
        super.decode();

        // Idx
        this.idx = this.encoderDecoderHelper.decodeInt32();

        // Label
        this.label = this.encoderDecoderHelper.decodeInt32();

        // Image
        this.image = this.encoderDecoderHelper.decodeNDArray();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The index of the image
     */
    public int getIdx() {return this.idx;}

    /**
     * @return The label of the image
     */
    public int getLabel() {
        return this.label;
    }

    /**
     * @return The image
     */
    public BaseNDArray getImage() {
        return image;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the request's length
     */
    @Override
    protected void setRequestLength() {
        super.setRequestLength(Integer.BYTES, // Idx
                Integer.BYTES, // Label
                Integer.BYTES, Integer.BYTES, Integer.BYTES, // Shape
                (this.image != null ? this.image.getShape().getSize() : 0) * Double.BYTES); // Array
    }

    /**
     * Set the label
     * @param label The label of the image
     */
    public void setLabel(int label) {
        this.label = label;
    }

    /**
     * Set the image
     * @param image The INDArray containing all
     *             the pixel values of the image
     */
    public void setImage(BaseNDArray image) {
        this.image = image;
    }
}
