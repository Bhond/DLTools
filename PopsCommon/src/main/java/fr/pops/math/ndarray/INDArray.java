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
 * Name: INDArray.java
 *
 * Description: Interface class defining the N-Dimensional arrays.
 *              Aims at being the same architecture as N-Dimensional arrays
 *              From Numpy
 *
 * Author: Charles MERINO
 *
 * Date: 15/10/2020
 *
 ******************************************************************************/
package fr.pops.math.ndarray;

import java.io.Serializable;

public interface INDArray extends Serializable {

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Reshape the INDArray
     * @param shape The new shape to give to the data
     */
    public void reshape(int... shape);

    /**
     * Reshape the INDArray
     * @param shape The new shape to give to the data
     */
    public void reshape(Shape shape);

    /**
     * Stack the given INDArray in input behind
     * @param arr The array to stack
     */
    public INDArray stackUnsafe(INDArray arr);

    /**
     * Merge the given INDArray in input
     * @param arr The array to stack
     */
    public void merge(INDArray arr, int offset);

    /**
     * Extract a specific channel of a BaseNDArray
     * @param channel The index of the channel to extract
     */
    public INDArray extractChannel(int channel);

    /**
     * Turn literally the INDArray upside down
     * @return The calling INDArray turned upside down
     */
    public INDArray rot180();
    /*****************************************
     *
     * Assertions
     *
     *****************************************/
    /**
     * @param ordering Row-major (C-style, 'C') or column-major (Fortran-style, 'F') order
     * @return True if the given ordering is valid
     */
    boolean assertOrdering(char ordering);


    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public double[] getData();

    public Shape getShape();

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    @Override
    public String toString();

    public INDArray toNDArrayUnsafe(double[] arr);

    public INDArray toNDArrayUnsafe(double[][] arr);

}
