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
 * Name: IntCst.java
 *
 * Description: Enumeration storing Constant Integer values like states or sizes.
 *
 * Author: Charles MERINO
 *
 * Date: 12/02/2021
 *
 ******************************************************************************/
package fr.pops.cst;

public abstract class IntCst {

    /*****************************************
     *
     * Window sizes
     *
     *****************************************/
    // Main window default sizes
    public static final int DEFAULT_MAIN_WINDOW_WIDTH = 3500;
    public static final int DEFAULT_MAIN_WINDOW_HEIGHT = 1950;
    public static final int DEFAULT_MAIN_WINDOW_WIDTH_TEST = 1600;
    public static final int DEFAULT_MAIN_WINDOW_HEIGHT_TEST = 1600;

    public static final int DEFAULT_MENU_BAR_HEIGHT = 50;

    /*****************************************
     *
     * Window parameters
     *
     *****************************************/
    public static final int DEFAULT_MAX_NB_COLUMNS = 3;
    public static final int DEFAULT_MAIN_VIEW_CONTENT_MARGIN = 20;
    public static final int DEFAULT_MAIN_VIEW_CONTENT_SPACING = 10;

    /*****************************************
     *
     * Model loop
     *
     *****************************************/
    public final static int MODEL_LOOP_MAX_FPS = 120;
}
