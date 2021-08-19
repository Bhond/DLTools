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
 * Description: Class defining the 2D vectors with floating precision.
 *
 * Author: Charles MERINO
 *
 * Date: 15/08/2021
 *
 ******************************************************************************/
package fr.pops.math;

public final class Vector2f extends Vectorf {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
     /**
      * Standard ctor
     */
    public Vector2f() {
        super(2);
    }

    /**
     * Ctor
     * @param x The x axis value of the vector
     * @param y The y axis value of the vector
     */
    public Vector2f(float x, float y) {
        super(x, y);
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
}
