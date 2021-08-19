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
 * Name: Vector.java
 *
 * Description: Class defining the vectors with floating precision.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.math;

import java.util.function.Function;

public class Vectorf {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected int size;
    protected float[] value;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param size The size of the vector
     */
    public Vectorf(int size){
        if (size == 0){
            this.value = null;
            System.out.println("A vector has no size.");
        } else {
            this.size = size;
            this.value = new float[size];
            for (int i = 0; i < size; i++){
                this.value[i] = 0;
            }
        }
    }

    /**
     * Ctor
     * @param value An float array of containing the values of the vector
     */
    public Vectorf(float... value){
        this.size = value.length;
        this.value = value;
    }

    /*****************************************
     *
     * Algebraic ops
     *
     *****************************************/
    /**
     * Compute the magnitude of the vector
     */
    private float length(){
        return Vectorf.dot(this, this);
    }

    /*****************************************
     *
     * Math
     *
     *****************************************/
    /* *
     * Return a new Vector corresponding to X + Y
     * */
    public static Vectorf add(Vectorf X, Vectorf Y){
        float[] res = new float[X.getSize()];
        if (X.getSize() == Y.getSize()){
            for (int i = 0; i < X.getSize(); i++){
                res[i] = X.getValue(i) + Y.getValue(i);
            }
        } else {
            for (int i = 0; i < X.getSize(); i++){
                res[i] = 0;
            }
        }
        return Vectorf.toVectorf(res);
    }

    /* *
     * Return a new Vector corresponding to -1 * X
     * */
    public static Vectorf negate(Vectorf X){
        return Vectorf.apply(x -> (-1) * x, X);
    }

    /* *
     * Apply a function to every element of the vector X
     * */
    public static Vectorf apply(Function<Float, Float> f, Vectorf X){
        float[] buffer = new float[X.getSize()];
        for (int i = 0; i < X.getSize(); i++){
            buffer[i] = f.apply(X.getValue(i));
        }
        return Vectorf.toVectorf(buffer);
    }

    /* *
     * Return a new Vector corresponding to Ax != xA
     * */
    public static Vectorf times(Matrix A, Vectorf X){
        float[] res = new float[A.getNbRows()];
        for (int i = 0; i < A.getNbRows(); i++){
            float buffer = 0;
            for (int k = 0; k < X.getSize(); k++){
                buffer += A.getValue(i,k) * X.getValue(k);
            }
            res[i] = buffer;
        }
        return Vectorf.toVectorf(res);
    }

    /* *
     * Compute Hadamard product of  Vector X and Vector Y (multiplication component wise)
     * */
    public static Vectorf Hadamard(Vectorf X, Vectorf Y){
        // TODO: add an error when sizes don't match
        float[] res = new float[X.getSize()];
        if( X.getSize() == Y.getSize()){
            for (int i = 0; i < X.getSize(); i++){
                res[i] = X.getValue(i) * Y.getValue(i);
            }
        }
        return Vectorf.toVectorf(res);
    }

    /* *
     * Sum every element of vector X to each other
     * */
    public static float reduceSum(Vectorf X){
        float res = 0;
        for(int i = 0; i < X.getSize(); i++){
            res += X.getValue(i);
        }
        return res;
    }

    /* *
     * Return the usual scalar product of vectors X and Y
     * */
    public static float dot(Vectorf X, Vectorf Y){
        float res = 0;
        if (X.getSize() == Y.getSize()){
            for (int i = 0; i < X.getSize(); i++){
                res += X.getValue(i) * Y.getValue(i);
            }
        }else{
            System.out.println("Size of vector X and vector Y don't match.");
        }
        return res;
    }

    /* *
     * Return the maximal value in the vector X
     * */
    public static float max(Vectorf X){
        float res = X.getValue(0);
        for(int i = 1; i < X.getSize(); i++){
            res = Math.max(res, X.getValue(i));
        }
        return res;
    }

    /* *
     * Return the minimal value in the vector X
     * */
    public static float min(Vectorf X){
        float res = X.getValue(0);
        for(int i = 1; i < X.getSize(); i++){
            res = Math.min(res, X.getValue(i));
        }
        return res;
    }

    /* *
     * Return the index of either the first min or the first max value of the vector X
     * */
    public static int indexOfMinMaxValue(Vectorf X, String minOrMax){
        int res = 0;
        float buff = X.getValue(0);
        if (minOrMax.toLowerCase().equals("min")){
            for(int i = 1; i < X.getSize(); i++){
                if (buff > X.getValue(i)){
                    buff = X.getValue(i);
                    res = i;
                }
            }
        } else if (minOrMax.toLowerCase().equals("max")){
            for(int i = 1; i < X.getSize(); i++){
                if (buff < X.getValue(i)){
                    buff = X.getValue(i);
                    res = i;
                }
            }
        } else {
            System.out.println("Unknown string input : specify either min or max.");
        }

        return res;
    }

    /*****************************************
     *
     * Specific vectors
     *
     *****************************************/
    /**
     * Create a random vector
     * @param size
     * @return
     */
    public static Vectorf random(int size){
        return Vectorf.apply(x -> x + PopsMath.randf(), new Vectorf(size));
    }

    /**
     *  Fill a vector of specified size with ones
     * @param size
     * @return
     */
    public static Vectorf ones(int size){
        return Vectorf.apply(x -> x + 1, new Vectorf(size));
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Set the value of the vector
     * @param fltVec The float array to store
     */
    private void setValue(float[] fltVec){
        this.value = fltVec;
    }

    /**
     * Set the value at the given index
     * @param i The index of the value to set
     * @param val The actual value
     */
    public void setValue(int i, float val){
        this.value[i] = val;
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * @return The number of values
     */
    public int getSize(){return this.size;}

    /**
     * The value at index i
     * @param i The index to retrieve the value from
     * @return The value at the given index
     */
    public float getValue(int i){return this.value[i];}

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * Cast float array to a vector
     * @param fltVec The float array to cast
     * @return The vector created from the float array
     */
    public static Vectorf toVectorf(float[] fltVec){
        if (fltVec != null){
            Vectorf X = new Vectorf(fltVec.length);
            for(int i = 0; i < X.getSize(); i++){
                X.setValue(i, fltVec[i]);
            }
            return X;
        } else {
            return null;
        }
    }

    /**
     * Cast the vector to a string version
     * @return The string version of a vector
     */
    @Override
    public String toString(){
        String msg = " ";
        for (int i = 0; i < this.getSize(); i++){
            msg += this.getValue(i) + " \n ";
        }
        return msg;
    }
}
