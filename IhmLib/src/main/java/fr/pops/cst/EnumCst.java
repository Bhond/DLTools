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
 * Name: EnumCst.java
 *
 * Description: Abstract class storing Constant Enum values.
 *
 * Author: Charles MERINO
 *
 * Date: 03/03/2021
 *
 ******************************************************************************/
package fr.pops.cst;

public abstract class EnumCst {

    /*****************************************
     *
     * View
     *
     *****************************************/
    public enum Views { SERVER, NEURAL_NETWORK, PLOT, TEST }

    /*****************************************
     *
     * View
     *
     *****************************************/
    public enum ModelSteppingFamily { FAMILY_1_ON_1, FAMILY_1_ON_10, FAMILY_1_ON_20, FAMILY_1_ON_100 }
}