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
 * Date: 18/05/2019
 *
 ******************************************************************************/
package fr.pops.nn.popscst.cst;

public abstract class IntCst {

    /*****************************************
     *
     * Window sizes
     *
     *****************************************/
    // Main window default sizes
    public static final int DEFAULT_MAIN_WINDOW_WIDTH = 800; //3500;
    public static final int DEFAULT_MAIN_WINDOW_HEIGHT = 800; //1950;

    // Plots sizes
    public final static int DEFAULT_PLOT_WINDOW_HEIGHT = 800;
    public final static int DEFAULT_PLOT_WINDOW_WIDTH = 800;
    public final static int DEFAULT_PLOT_BORDER_HEIGHT = 50;
    public final static int DEFAULT_PLOT_BORDER_WIDTH = 50;

    // Graphics values for plots
    public final static int DEFAULT_LINE_PLOT_CIRCLE_RADIUS = 2;

    // Tool windows
    public final static int DEFAULT_TOOL_WINDOW_WIDTH = 800;
    public final static int DEFAULT_TOOL_WINDOW_HEIGHT = 750;

}
