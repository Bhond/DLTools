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
 * Name: BaseNDArray.java
 *
 * Description: Class defining the basic N-Dimensional arrays.
 *
 * Author: Charles MERINO
 *
 * Date: 15/10/2020
 *
 ******************************************************************************/
package fr.pops.nn.ndarray;

import fr.pops.nn.nncst.cst.StrCst;
import fr.pops.nn.popsmath.ArrayUtil;
import fr.pops.nn.popsmath.PopsMath;

public class BaseNDArray implements INDArray {

    /*****************************************
     *
     * Static values
     *
     *****************************************/
    private static final char DEFAULT_ORDERING = 'C';

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // TODO: Turn this into an DataBuffer
    private double[] data;
    private Shape shape;

    /*****************************************
     *
     * Ctors
     *
     *****************************************/
    /**
     * Don't try to call this one
     */
    private BaseNDArray(){
        // Nothing to be done
    }

    /**
     * Creates an empty array with the given shape
     * @param shape Tuple of array dimension
     */
    public BaseNDArray(int... shape){
        this.shape = new Shape.ShapeBuilder().withShape(shape).build();
        this.data = new double[this.shape.getSize()];
    }

    /**
     * @param data The actual data
     * @param shape Tuple of array dimension
     */
    public BaseNDArray(double[] data, int... shape){
        this.shape = new Shape.ShapeBuilder().withShape(shape).build();
        this.data = data;
    }

    /**
     * @param data The actual data
     * @param shape Tuple of array dimension
     */
    public BaseNDArray(double[][] data, int... shape){
        this.shape = new Shape.ShapeBuilder().withShape(shape).build();
        this.data = ArrayUtil.flatten(data);
    }

    /*****************************************
     *
     * Copy ctors
     *
     *****************************************/
    /**
     * Copy the given BaseNDArray
     * @param arr The BaseNDArray to copy
     */
    public BaseNDArray(BaseNDArray arr){
        this.data = arr.data;
        this.shape = arr.shape;
    }

    /**
     * // TODO: Make sure it is a valid BaseNDArray to copy
     * Copy the given INDArray
     * @param arr The INDArray to copy
     */
    public BaseNDArray(INDArray arr){
        this.data = arr.getData();
        this.shape = arr.getShape();
    }

    /*****************************************
     *
     * Parent Methods
     *
     *****************************************/
    /**
     * Reshape the INDArray
     * @param shape The new shape to give to the data
     */
    @Override
    public void reshape(int... shape){
        if (ArrayUtil.prod(shape) != this.shape.getSize()){
            System.out.println("INDArray cannot be reshaped. Sizes don't match.");
        }
        else {
            this.shape = new Shape.ShapeBuilder().withShape(shape).build();
        }
    }

    /**
     * Reshape the INDArray
     * @param shape The new shape to give to the data
     */
    @Override
    public void reshape(Shape shape){
        if (shape.getSize() != this.shape.getSize()){
            System.out.println("INDArray cannot be reshaped. Sizes don't match.");
        } else {
            this.shape = shape;
        }
    }

    /**
     * Stack the given INDArray in input behind
     * @param arr The array to stack
     */
    @Override
    @Deprecated
    public INDArray stackUnsafe(INDArray arr) {
        if (this.shape.getXAxisLength() != arr.getShape().getXAxisLength() && this.shape.getYAxisLength() != arr.getShape().getYAxisLength()){
            System.out.println("INDArray cannot be stacked. Shapes don't match along X or Y axis.");
        } else {
            double[] value = new double[this.shape.getSize() + arr.getShape().getSize()];
            // TODO: To be continued ...
            //       This implementation implies dynamic change of the array dimensions, ain't good, keep it to avoid errors
            //       and you might find some usage to it anyway !!!!!!!
        }
        return null;
    }

    /**
     * Stack the given INDArray inside the one calling the method
     * @param arr The array to push
     * @param offset The z axis index to put the values of the input INDArray
     */
    @Override
    public void merge(INDArray arr, int offset) {
        if (this.shape.getXAxisLength() != arr.getShape().getXAxisLength() && this.shape.getYAxisLength() != arr.getShape().getYAxisLength()){
            System.out.println("INDArray cannot be push. Shapes don't match along X or Y axis.");
        } else if (offset >= this.shape.getZAxisLength()){
            System.out.println("INDArray cannot be push. Offset is greater than the depth of the root array.");
        } else if (arr.getShape().getSize() > this.shape.getSize()){
            System.out.println("INDArray cannot be push. The array to stack is larger than the root array.");
        } // else if ( cannot push because of (this.shape.zLength - offset) * this.shape.xLength * this.shape.yLength - (this.shape.zLength - offset) * arr.xLength * arr.yLength > 0){}
        int startIndex = offset * this.shape.getXAxisLength() * this.shape.getYAxisLength();
        System.arraycopy(arr.getData(), 0, this.data, startIndex, arr.getShape().getSize());
    }

    /**
     * Extract a specific channel of the calling BaseNDArray
     * @param channel The index of the channel to extract
     */
    @Override
    public INDArray extractChannel(int channel) {
        if (this.shape.getZAxisLength() < channel){
            System.out.println("Cannot extract the specified channel of the array.");
            return new BaseNDArray(this);
        }
        double[] res = new double[this.shape.getXAxisLength() * this.shape.getYAxisLength()];
        int startIndex = channel * this.shape.getXAxisLength() * this.shape.getYAxisLength();
        System.arraycopy(this.getData(), startIndex, res, 0, this.shape.getXAxisLength() * this.shape.getYAxisLength());
        return new BaseNDArray.BaseNDArrayBuilder().withData(res).withShape(this.shape.getXAxisLength(), this.shape.getYAxisLength()).build();
    }

    /**
     * Turn literally the INDArray upside down
     * @return The calling INDArray turned upside down
     *         It is a symmetry along both X and Y axis per depth
     */
    @Override
    public INDArray rot180(){
        int counter = 0;
        double[] res = new double[this.shape.getSize()];
        // Loop over the depth
        double[] buffer = new double[this.shape.getXAxisLength() * this.shape.getYAxisLength()];
        for (int c = 0; c < this.shape.getZAxisLength(); c++){
            int startIndex = c * this.shape.getXAxisLength() * this.shape.getYAxisLength();
            System.arraycopy(this.getData(), startIndex, buffer, 0, this.shape.getXAxisLength() * this.shape.getYAxisLength());
            // Loop over the values in the buffer
            for (int i = buffer.length - 1; i >= 0; i--){
                res[counter] = buffer[i];
                counter++;
            }
        }
        return new BaseNDArray.BaseNDArrayBuilder().withData(res).withShape(this.shape).build();
    }

    @Override
    public boolean assertOrdering(char ordering){
        boolean res = ordering == 'C' || ordering == 'F';
        if(!res) System.out.println("Unknown given ordering. Must be either 'C' or 'F'. You gave: " + ordering);
        return res;
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    @Override
    public double[] getData() { return this.data; }

    @Override
    public Shape getShape() { return this.shape; }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    // Nothing for now ...

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    @Override
    public String toString() {
        // Initialization
        String spaceBetweenValues = "   ";
        StringBuilder message = new StringBuilder("{\n");
        if (this.shape.getZAxisLength() != 0)
        {
            message.append("   {\n");
        }

        // Build message
        for (int i = 0; i < this.shape.getSize(); i++)
        {
            // New line
            message.append(String.format(StrCst.INDARRAY_VALUES_FORMAT, data[i])).append(spaceBetweenValues);

            if ((i + 1) % this.shape.getYAxisLength() == 0)
            {
                message.append("\n");
            }

            // New depth
            if ( this.shape.getZAxisLength() != 0
                    && i != 0
                    && ((i + 1) % (this.shape.getXAxisLength() * this.shape.getYAxisLength())) == 0)
            {
                message.append("   }\n");
                if (i+1 != this.getShape().getSize()){
                    message.append("   {\n");
                }
            }
        }
        message.append("}\n");
        return message.toString();
    }

    @Override
    @Deprecated
    public INDArray toNDArrayUnsafe(double[] arr){
        return new BaseNDArray();
    }

    @Override
    @Deprecated
    public INDArray toNDArrayUnsafe(double[][] arr){
        return new BaseNDArray();
    }

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class BaseNDArrayBuilder {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        private double[] data;
        private Shape shape;

        /*****************************************
         *
         * With methods
         *
         *****************************************/
        /**
         * @param data The data to store
         * @return The builder itself
         */
        public BaseNDArrayBuilder withData(double[] data){
            this.data = data;
            return this;
        }

        /**
         * @param shape The shape of the data to store
         * @return The builder itself
         */
        public BaseNDArrayBuilder withShape(Shape shape){
            this.shape = shape;
            return this;
        }

        /**
         * @param shape The shape of the data to store
         * @return The builder itself
         */
        public BaseNDArrayBuilder withShape(int... shape){
            this.shape = new Shape.ShapeBuilder().withShape(shape).build();
            return this;
        }

        /*****************************************
         *
         * Specific BaseNDArray
         *
         *****************************************/
        /**
         * Build a INDArray filled with zeros
         * @param shape the shape to give to the data holder
         * @return The builder itself
         */
        public BaseNDArrayBuilder zeros(Shape shape){
            this.shape = shape;
            this.data = new double[shape.getSize()];
            return this;
        }

        /**
         * Build a INDArray filled with zeros
         * @param shape the shape to give to the data holder
         * @return The builder itself
         */
        public BaseNDArrayBuilder zeros(int... shape){
            this.shape = new Shape.ShapeBuilder().withShape(shape).build();
            this.data = new double[this.shape.getSize()];
            return this;
        }

        /**
         * Build a INDArray filled with ones
         * @param shape the shape to give to the data holder
         * @return The builder itself
         */
        public BaseNDArrayBuilder ones(Shape shape){
            this.shape = shape;
            this.data = ArrayUtil.apply(x -> x + 1, new double[shape.getSize()]);
            return this;
        }

        /**
         * Build a INDArray filled with ones
         * @param shape the shape to give to the data holder
         * @return The builder itself
         */
        public BaseNDArrayBuilder ones(int... shape){
            this.shape = new Shape.ShapeBuilder().withShape(shape).build();
            this.data = ArrayUtil.apply(x -> x + 1, new double[this.shape.getSize()]);
            return this;
        }

        /**
         * Build a INDArray filled with ones
         * @param shape the shape to give to the data holder
         * @return The builder itself
         */
        public BaseNDArrayBuilder randomize(Shape shape){
            this.shape = shape;
            this.data = ArrayUtil.apply(x -> x + PopsMath.rand(), new double[shape.getSize()]);
            return this;
        }

        /**
         * Build a INDArray filled with ones
         * @param shape the shape to give to the data holder
         * @return The builder itself
         */
        public BaseNDArrayBuilder randomize(int... shape){
            this.shape = new Shape.ShapeBuilder().withShape(shape).build();
            this.data = ArrayUtil.apply(x -> x + PopsMath.rand(), new double[this.shape.getSize()]);
            return this;
        }

        /**
         * Build method
         */
        public BaseNDArray build(){
            if (this.shape == null){
                this.shape = new Shape(data.length);
                System.out.println("Unknown shape in the builder.");
            }

            return new BaseNDArray(this.data, shape.getXAxisLength(), shape.getYAxisLength(), shape.getZAxisLength());
        }

    }

}
