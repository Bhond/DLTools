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
 * Name: StrCst.java
 *
 * Description: Abstract class storing String Constants like path or
 *              GUI displayed messages.
 *
 * Author: Charles MERINO
 *
 * Date: 12/02/2021
 *
 ******************************************************************************/
package fr.pops.cst;

public abstract class StrCst {

    /*****************************************
     *
     * Name of the whole project
     *
     *****************************************/
    public final static String ZE_NAME = "Pops";

    /*****************************************
     *
     * String constants
     *
     *****************************************/
    // Buttons
    public final static String LABEL_CLOSE_WINDOW_BUTTON = "C";
    public final static String LABEL_MINIMIZE_WINDOW_BUTTON = "A";

    // Menu bar
    public final static String MENUBAR_LABEL_VIEWS = "Views";

    // View names
    public final static String NAME_SERVER_VIEW = "Server";
    public final static String NAME_NEURAL_NETWORK_VIEW = "Neural Network";
    public final static String NAME_PLOT_VIEW = "Plot";
    public final static String NAME_STOCK_VIEW = "Plot";

    /*****************************************
     *
     * Paths
     *
     *****************************************/
    public final static String PATH_CSS_DIRECTORY = "/resources/css/";
    public final static String PATH_MAIN_VIEW_CSS = fr.pops.cst.StrCst.PATH_CSS_DIRECTORY + "MainView.css";
    public final static String PATH_SERVER_INFO_VIEW_CSS = fr.pops.cst.StrCst.PATH_CSS_DIRECTORY + "ServerInfoView.css";
    public final static String PATH_PLOT_VIEW_CSS = fr.pops.cst.StrCst.PATH_CSS_DIRECTORY + "PlotView.css";

    /*****************************************
     *
     * Style classes
     *
     *****************************************/
    public final static String STYLE_CLASS_ROOT = "root";
    public final static String STYLE_CLASS_ROOT_LAYOUT = "rootLayout";
    public final static String STYLE_CLASS_CONTROL_WINDOW_BUTTON = "controlWindowButton";
    public final static String STYLE_CLASS_MENUBAR = "menuBar";
    public final static String STYLE_CLASS_MENUBAR_MENU = "menuBarMenu";
    public final static String STYLE_CLASS_MENUBAR_MENU_ITEM = "menuBarMenuItem";
    public final static String STYLE_CLASS_VIEWS_TAB_PANE = "viewsTabPane";
}
