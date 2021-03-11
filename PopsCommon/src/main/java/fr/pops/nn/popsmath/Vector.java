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
package fr.pops.nn.popsmath;

import java.util.List;

public class Vector {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private boolean randomize = false;
    private int m_size;
    private double m_magnitude = 0;
    private double[] m_value;

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
            this.m_size = size;
            this.m_value = null;
            this.m_magnitude = 0;
            System.out.println("A vector has no size.");
        } else {
            this.m_size = size;
            this.m_value = new double[size];
            for (int i = 0; i < size; i++){
                if (randomize){
                    this.m_value[i] = PopsMath.rand();
                } else {
                    this.m_value[i] = 0;
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
        this.m_magnitude = PopsMath.dot(this, this);
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
        return PopsMath.apply(x -> x + PopsMath.rand(), new Vector(size));
    }

    /**
     *  Fill a vector of specified size with ones
     * @param size
     * @return
     */
    public static Vector ones(int size){
        return PopsMath.apply(x -> x + 1, new Vector(size));
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    private void setValue(double[] fltVec){
        this.m_value = fltVec;
    }

    public void setValue(int i, double val){
        this.m_value[i] = val;
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    public int getSize(){return this.m_size;}

    public double getMagnitude(){return this.m_magnitude;}

    public double getValue(int i){return this.m_value[i];}

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    public static Vector toVector(double[] dblVec){
        if (dblVec != null){
            Vector X = new Vector(dblVec.length);
            X.setValue(dblVec);

            return X;
        } else {
            return null;
        }
    }

    public static Vector toVector(List<Float> fltVec){
        if (fltVec != null){
            Vector X = new Vector(fltVec.size());
            for(int i = 0; i < X.getSize(); i++){
                X.setValue(i, fltVec.get(i));
            }

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
