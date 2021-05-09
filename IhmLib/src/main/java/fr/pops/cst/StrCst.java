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
    public final static String NAME_SERVER_VIEW = "Network info";
    public final static String NAME_NEURAL_NETWORK_VIEW = "Neural Network";
    public final static String NAME_STOCK_VIEW = "Stock";

    // String format
    public final static String FORMAT_SIMPLE_DATE = "HH:mm";

    // Network view
    public final static String LABEL_PING = "Ping: ";
    public final static String FORMAT_VALUE_PING = "%3.0f ms";
    public final static String LABEL_FREQUENCY = "Frequency: ";
    public final static String FORMAT_VALUE_FREQUENCY = "%3.0f Hz";

    // Stock info
    public static final String ADD_QUOTE_BUTTON_LABEL = "Add";
    public static final String REMOVE_QUOTE_BUTTON_LABEL = "Remove";
    public static final String ADD_QUOTE_TEXT_FIELD_DEFAULT = "New symbol";

    /*****************************************
     *
     * Paths
     *
     *****************************************/
    // Directory
    public final static String PATH_CSS_DIRECTORY = "/resources/css/";
    public final static String PATH_MEDIA_DIRECTORY = "/resources/media/";
    public final static String PATH_IMAGES_DIRECTORY = fr.pops.cst.StrCst.PATH_MEDIA_DIRECTORY + "images/";
    public final static String PATH_CONF__DIRECTORY = "/resources/conf/";
    // Custom nodes
    public final static String PATH_BASE_PLOT_CSS = fr.pops.cst.StrCst.PATH_CSS_DIRECTORY + "BasePlot.css";
    public final static String PATH_CANDLESTICK_PLOT_CSS = fr.pops.cst.StrCst.PATH_CSS_DIRECTORY + "CandleStickPlot.css";
    // Views
    public final static String PATH_MAIN_VIEW_CSS = fr.pops.cst.StrCst.PATH_CSS_DIRECTORY + "MainView.css";
    public final static String PATH_SERVER_INFO_VIEW_CSS = fr.pops.cst.StrCst.PATH_CSS_DIRECTORY + "NetworkInfoView.css";
    public final static String PATH_STOCK_VIEW_CSS = fr.pops.cst.StrCst.PATH_CSS_DIRECTORY + "StockView.css";
    // Earth texture
    public final static String PATH_EARTH_TEXTURE =  fr.pops.cst.StrCst.PATH_IMAGES_DIRECTORY + "earth.png";

    // Jsons
    public final static String JSON_CONF_MAIN_VIEW = "mainView.json";

    /*****************************************
     *
     * Style classes
     *
     *****************************************/
    public final static String STYLE_CLASS_ROOT = "root";
    public final static String STYLE_CLASS_ROOT_LAYOUT = "rootLayout";
    public final static String STYLE_CLASS_CONTROL_WINDOW_BUTTON = "controlWindowButton";

    // Menubar
    public final static String STYLE_CLASS_MENUBAR = "menuBar";
    public final static String STYLE_CLASS_MENUBAR_MENU = "menuBarMenu";
    public final static String STYLE_CLASS_MENUBAR_MENU_ITEM = "menuBarMenuItem";

    // Controls
    public final static String STYLE_CLASS_VIEWS_TAB_PANE = "viewsTabPane";
    public static final String STYLE_CLASS_LISTVIEW = "listView";
    public final static String STYLE_CLASS_STANDARD_BUTTON = "button";
    public final static String STYLE_CLASS_LABEL_VALUE_PAIR_BOX = "labelValuePairBox";

    // Candlestic
    public final static String STYLE_CLASS_CANDLESTICK_BODY = "candlestick-body";
    public final static String STYLE_CLASS_CANDLESTICK_LINE = "candlestick-line";
    public final static String STYLE_CLASS_OPEN_ABOVE_CLOSE = "open-above-close";
    public final static String STYLE_CLASS_CLOSE_ABOVE_OPEN = "close-above-open";

    // Quote data
    public final static String STYLE_CLASS_QUOTE_DATA = "quote-data";
    public final static String STYLE_CLASS_QUOTE_DATA_SYMBOL = "quote-data-symbol";
    public final static String STYLE_CLASS_QUOTE_DATA_ARROW= "quote-data-arrow";
    public final static String STYLE_CLASS_QUOTE_DATA_PRICE = "quote-data-price";
    public static final String STYLE_CLASS_TEXT_FIELD = "textField";

    /*****************************************
     *
     * JSON key
     *
     *****************************************/
}
