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
 * Description: Class defining the 3D vectors with floating precision.
 *
 * Author: Charles MERINO
 *
 * Date: 15/08/2021
 *
 ******************************************************************************/
package fr.pops.math;

public class Vector3f extends Vectorf {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
     /**
      * Standard ctor
     */
    public Vector3f() {
        super(3);
    }

    /**
     * Ctor
     * @param x The x axis value of the vector
     * @param y The y axis value of the vector
     * @param z The z axis value of the vector
     */
    public Vector3f(float x, float y, float z) {
        super(x, y, z);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return X axis
     */
    public float x(){
        return value[0];
    }

    /**
     * @return Y axis
     */
    public float y(){
        return value[1];
    }

    /**
     * @return Y axis
     */
    public float z(){
        return value[2];
    }
}
