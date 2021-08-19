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
 * Description: Class defining the vectors.
 *
 * Author: Charles MERINO
 *
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.math;

import java.util.function.Function;

public class Vector {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private boolean randomize = false;
    private int size;
    private double magnitude = 0;
    private double[] value;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     *
     * @param size
     */
    public Vector(int size){
        if (size == 0){
            this.size = size;
            this.value = null;
            this.magnitude = 0;
            System.out.println("A vector has no size.");
        } else {
            this.size = size;
            this.value = new double[size];
            for (int i = 0; i < size; i++){
                if (randomize){
                    this.value[i] = PopsMath.rand();
                } else {
                    this.value[i] = 0;
                }
            }
            this.computeMagnitude();
        }
    }

    /*****************************************
     *
     * Algebraic ops
     *
     *****************************************/
    /**
     * Compute the magnitude of the vector
     */
    private void computeMagnitude(){
        this.magnitude = Vector.dot(this, this);
    }

    /*****************************************
     *
     * Math
     *
     *****************************************/
    /* *
     * Return a new Vector corresponding to X + Y
     * */
    public static Vector add(Vector X, Vector Y){
        double[] res = new double[X.getSize()];
        if (X.getSize() == Y.getSize()){
            for (int i = 0; i < X.getSize(); i++){
                res[i] = X.getValue(i) + Y.getValue(i);
            }
        } else {
            for (int i = 0; i < X.getSize(); i++){
                res[i] = 0;
            }
        }
        return Vector.toVector(res);
    }

    /* *
     * Return a new Vector corresponding to -1 * X
     * */
    public static Vector negate(Vector X){
        return Vector.apply(x -> (-1) * x, X);
    }

    /* *
     * Apply a function to every element of the vector X
     * */
    public static Vector apply(Function<Double, Double> f, Vector X){
        double[] buffer = new double[X.getSize()];
        for (int i = 0; i < X.getSize(); i++){
            buffer[i] = f.apply(X.getValue(i));
        }
        return Vector.toVector(buffer);
    }

    /* *
     * Return a new Vector corresponding to Ax != xA
     * */
    // TODO : Check correspondence between sizes
    public static Vector times(Matrix A, Vector X){
        double[] res = new double[A.getNbRows()];
        for (int i = 0; i < A.getNbRows(); i++){
            double buffer = 0;
            for (int k = 0; k < X.getSize(); k++){
                buffer += A.getValue(i,k) * X.getValue(k);
            }
            res[i] = buffer;
        }
        return Vector.toVector(res);
    }

    /* *
     * Compute Hadamard product of  Vector X and Vector Y (multiplication component wise)
     * */
    public static Vector Hadamard(Vector X, Vector Y){
        // TODO: add an error when sizes don't match
        double[] res = new double[X.getSize()];
        if( X.getSize() == Y.getSize()){
            for (int i = 0; i < X.getSize(); i++){
                res[i] = X.getValue(i) * Y.getValue(i);
            }
        }
        return Vector.toVector(res);
    }


    /* *
     * Create diagonal Matrix A from a vector X
     * */
    public static Matrix diag(Vector X) {
        double[][] res = new double[X.getSize()][X.getSize()];
        for (int i = 0; i < X.getSize(); i++) {
            res[i][i] = X.getValue(i);
        }
        return Matrix.toMatrix(res);
    }

    /* *
     * Sum every element of vector X to each other
     * */
    public static double reduceSum(Vector X){
        double res = 0;
        for(int i = 0; i < X.getSize(); i++){
            res += X.getValue(i);
        }
        return res;
    }

    /* *
     * Return the usual scalar product of vectors X and Y
     * */
    public static double dot(Vector X, Vector Y){
        double res = 0;
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
    public static double max(Vector X){
        double res = X.getValue(0);
        for(int i = 1; i < X.getSize(); i++){
            res = Math.max(res, X.getValue(i));
        }
        return res;
    }

    /* *
     * Return the minimal value in the vector X
     * */
    public static double min(Vector X){
        double res = X.getValue(0);
        for(int i = 1; i < X.getSize(); i++){
            res = Math.min(res, X.getValue(i));
        }
        return res;
    }

    /* *
     * Return the index of either the first min or the first max value of the vector X
     * */
    public static int indexOfMinMaxValue(Vector X, String minOrMax){
        int res = 0;
        double buff = X.getValue(0);
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
    public static Vector randomize(int size){
        return Vector.apply(x -> x + PopsMath.rand(), new Vector(size));
    }

    /**
     *  Fill a vector of specified size with ones
     * @param size
     * @return
     */
    public static Vector ones(int size){
        return Vector.apply(x -> x + 1, new Vector(size));
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    private void setValue(double[] fltVec){
        this.value = fltVec;
    }

    public void setValue(int i, double val){
        this.value[i] = val;
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/

    public int getSize(){return this.size;}

    public double getMagnitude(){return this.magnitude;}

    public double getValue(int i){return this.value[i];}

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * Cast double array to a vector
     * @param dblVec The double array to cast
     * @return The vector created from the double array
     */
    public static Vector toVector(double[] dblVec){
        if (dblVec != null){
            Vector X = new Vector(dblVec.length);
            X.setValue(dblVec);

            return X;
        } else {
            return null;
        }
    }

    @Override
    public String toString(){
        String msg = " ";
        for (int i = 0; i < this.getSize(); i++){
            msg += this.getValue(i) + " \n ";
        }
        return msg;
    }
}
