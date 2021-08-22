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
 * Name: Matrixf.java
 *
 * Description: Class defining the matrices with float precision.
 *              Data is flattened into a 1D array since this
 *              type of data object is only used by opengl and graphics cards
 *
 * Author: Charles MERINO
 *
 * Date: 18/08/2021
 *
 ******************************************************************************/
package fr.pops.math;

import java.util.function.Function;

public class Matrixf {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected int nbRows;
    protected int nbColumns;
    protected int nbElements;
    protected float[] value;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param sizeI Number of rows
     * @param sizeJ Number of columns
     */
    public Matrixf(int sizeI, int sizeJ){
        this.nbRows = sizeI;
        this.nbColumns = sizeJ;
        this.nbElements = sizeI * sizeJ;
        this.value = new float[sizeI * sizeJ];
        for (int i = 0; i < sizeI * sizeJ; i++){
            this.value[i] = 0;
        }
    }

    /*****************************************
     *
     * Math
     *
     *****************************************/
    /* *
     * Return a new Vector corresponding to -1 * M
     * */
    public static Matrixf negate(Matrixf M){ return Matrixf.apply(x -> (-1) * x, M); }

    /* *
     * Apply a function to every element of the matrix M
     * */
    public static Matrixf apply(Function<Float, Float> f, Matrixf M){
        float[] buffer = new float[M.getNbElements()];
        for (int i = 0; i < M.getNbElements(); i++){
                buffer[i] = f.apply(M.value[i]);
        }
        return Matrixf.toMatrixf(buffer, M.getNbRows(), M.nbColumns);
    }

    /**
     * Matrix multiplication
     * @param A First matrix
     * @param B Second matrix
     * @return A matrix representing A * B
     */
    public static Matrixf times(Matrixf A, Matrixf B){
        Matrixf res = Matrixf.identity(A.getNbRows(), A.getNbColumns());
        float buf = 0;
        for (int i = 0; i < A.getNbRows(); i++){
            for (int j = 0; j < B.getNbColumns(); j++){
                buf = 0;
                for (int k = 0; k < A.getNbColumns(); k++){
                    buf += A.get(i, k) * B.get(k, j);
                }
                res.set(i, j, buf);
            }
        }
        return res;
    }

    /*****************************************
     *
     * Standard matrices
     *
     *****************************************/
    /**
     * Identity matrix
     * @param nbRows The number of rows
     * @param nbColumns The number of columns
     * @return The identity matrix
     */
    public static Matrixf identity(int nbRows, int nbColumns){
        float[] res = new float[nbColumns * nbRows];
        for (int i = 0; i < nbRows; i++){
            for (int j = 0; j < nbRows; j++){
                 if (i == j) res[i * nbColumns + j] = 1;
            }
        }
        return Matrixf.toMatrixf(res, nbRows, nbColumns);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * Get the value at the given row i and the given column j
     * @param i The row index
     * @param j The column index
     * @return The value at indices i and j
     */
    protected float get(int i, int j){
        return this.value[i * this.nbColumns + j];
    }

    /**
     * Value
     * @return The value
     */
    public float[] getValue(){return this.value;}

    /**
     * @return The number of rows
     */
    public int getNbRows(){return this.nbRows;}

    /**
     * @return The number of columns
     */
    public int getNbColumns(){return this.nbColumns;}

    /**
     * @return The number of elements in the matrix
     *          nbColumns * nbRows
     */
    public int getNbElements() {
        return this.nbElements;
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Set the value of the matrix
     * @param fltArr The new value of the matrix
     */
    protected void setValue(float[] fltArr){
        this.value = fltArr;
    }

    /**
     * Set the value at the given location in the matrix
     * @param i The row index
     * @param j The column index
     * @param val The value to set
     */
    protected void set(int i, int j, float val){
        this.value[i * nbColumns + j] = val;
    }


    /*****************************************
     *
     * Specific matrices
     *
     *****************************************/
    /**
     * Build a matrix with every element is a random num number between 0 and 1
     * @param M The matrix to random
     * @return A matrix with every element is a random num number between 0 and 1
     */
    public static Matrixf randomize(Matrixf M){
        return Matrixf.apply(x -> (float) (x + PopsMath.rand()), M);
    }

    /**
     * Build a matrix with every element is 1
     * @param sizeI The number of rows
     * @param sizeJ The number of columns
     * @return A matrix with every element is 1
     */
    public static Matrixf one(int sizeI, int sizeJ){
        return Matrixf.apply(x -> x + 1, new Matrixf(sizeI, sizeJ));
    }

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * Cast the float array into a Matrixf
     * @param fltArr The float array to cast
     * @param nbRows The number of rows
     * @param nbColumns  The number of columns
     * @return The matrix version of the array
     */
    public static Matrixf toMatrixf(float[] fltArr, int nbRows, int nbColumns){
        Matrixf res = new Matrixf(nbRows, nbColumns);
        res.setValue(fltArr);
        return res;
    }

    /**
     * Cast the matrix to a string representation of itself
     * @return The representation of the matrix in a string format
     */
    @Override
    public String toString(){
        String msg = " ";
        for (int i = 0; i < this.nbRows * this.nbColumns; i++){
            msg += String.format("%6.3f", this.value[i]);
            msg += i != 0 && (i % this.nbColumns - 1) == 0 ? " \n " : "  ";
        }
        return msg;
    }
}
