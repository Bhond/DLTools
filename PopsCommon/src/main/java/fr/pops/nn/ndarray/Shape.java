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
 * Name: Shape.java
 *
 * Description: Class defining the shape of N-Dimensional arrays.
 *
 * Author: Charles MERINO
 *
 * Date: 31/10/2020
 *
 ******************************************************************************/
package fr.pops.nn.ndarray;

public class Shape {

    /*****************************************
     *
     * Static Attributes
     *
     *****************************************/
    private final static int DEFAULT_AXIS_LENGTH = 1;

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    /**
     * Representation of A = Matrix(m,n)
     *
     *      Y axis
     *    _ |------------>
     *  X |  a_1,1 a_1,2 ... a_1,n
     *    |  a_2,1 a_2,2 ... a_2,n
     *  A |  .
     *  x |  .
     *  i \/ .
     *  s    a_m,1 a_m,2 ... a_m,n
     *
     *  Z axis is the depth for tensors.
     *
     *     - A vector: xAxisLength = m
     *                 yAxisLength = 0
     *                 zAxisLength = 0
     *
     *     - A matrix: xAxisLength = m
     *                 yAxisLength = n
     *                 zAxisLength = 0
     *
     *     - A tensor: xAxisLength = m
     *                 yAxisLength = n
     *                 zAxisLength = p
     *
     */
    private int size;
    private int xAxisLength;
    private int yAxisLength;
    private int zAxisLength;

    /*****************************************
     *
     * Ctors
     *
     *****************************************/
    /**
     * This constructor cannot be called.
     * One must define a specific shape.
     */
    private Shape(){
        // Nothing to be done right now
    }

    /**
     * @param m X axis for vectors
     */
    public Shape(int m){
        size = m;
        xAxisLength = m;
        yAxisLength = DEFAULT_AXIS_LENGTH;
        zAxisLength = DEFAULT_AXIS_LENGTH;
    }

    /**
     * @param m X axis for matrices
     * @param n Y axis for matrices
     */
    public Shape(int m, int n){
        size = m * n;
        xAxisLength = m;
        yAxisLength = n;
        zAxisLength = DEFAULT_AXIS_LENGTH;
    }

    /**
     * @param m X axis for tensors
     * @param n Y axis for tensors
     * @param p Z axis for tensors
     */
    public Shape(int m, int n, int p){
        size = m * n * p;
        xAxisLength = m;
        yAxisLength = n;
        zAxisLength = p;
    }

    /**
     * @param m X axis for tensors
     * @param n Y axis for tensors
     * @param p Z axis for tensors
     */
    private Shape(int m, int n, int p, int size){
        this.size = size;
        xAxisLength = m;
        yAxisLength = n;
        zAxisLength = p;
    }

    /*****************************************
     *
     * Copy ctor
     *
     *****************************************/
    /**
     * Create a copy of the given shape
     * @param shape The shape to copy
     */
    public Shape(Shape shape){
        this.xAxisLength = shape.xAxisLength;
        this.yAxisLength = shape.yAxisLength;
        this.zAxisLength = shape.yAxisLength;
        this.size = shape.size;
    }


    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public int getSize() { return size; }

    public int getXAxisLength() { return xAxisLength; }

    public int getYAxisLength() { return yAxisLength; }

    public int getZAxisLength() { return zAxisLength; }

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * Print info about the shape
     * @return A string version of the shape to describe it
     */
    @Override
    public String toString(){
        return "(" + this.xAxisLength + ", " + this.yAxisLength + ", " + this.zAxisLength + ")";
    }

    /*****************************************
     *
     * Builder
     *
     *****************************************/
    public static class ShapeBuilder {

        /*****************************************
         *
         * Attributes
         *
         *****************************************/
        private int xAxisLength;
        private int yAxisLength;
        private int zAxisLength;
        private int size;

        /*****************************************
         *
         * With
         *
         *****************************************/
        /**
         *
         * @param shape
         */
        public ShapeBuilder withShape(int... shape){

            if (shape.length == 1){
                xAxisLength = shape[0];
                yAxisLength = DEFAULT_AXIS_LENGTH;
                zAxisLength = DEFAULT_AXIS_LENGTH;
            }
            else if (shape.length == 2){
                xAxisLength = shape[0];
                yAxisLength = shape[1];
                zAxisLength = DEFAULT_AXIS_LENGTH;
            }
            else if (shape.length == 3){
                xAxisLength = shape[0];
                yAxisLength = shape[1];
                zAxisLength = shape[2];
            } else {
                System.out.println("Unknown given shape. You gave: " + shape.length + " elements." );
            }

            size = xAxisLength * yAxisLength * zAxisLength;

            return this;
        }

        /*****************************************
         *
         * Build method
         *
         *****************************************/
        public Shape build(){
            return new Shape(xAxisLength, yAxisLength, zAxisLength, size);
        }
    }
}
