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
 * Name: MainView.java
 *
 * Description: Class defining the main view of the IHM
 *              Singleton
 *
 * Author: Charles MERINO
 *
 * Date: 10/02/2021
 *
 ******************************************************************************/
package fr.pops.views;

import fr.pops.controllers.viewcontrollers.MainViewController;
import fr.pops.cst.EnumCst;
import fr.pops.cst.IntCst;
import fr.pops.cst.StrCst;
import fr.pops.systeminfo.DisplayInfo;
import fr.pops.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends BaseView {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // System info
    private final DisplayInfo displayInfo = DisplayInfo.getInstance();

    // Controller
    private MainViewController controller = MainViewController.getInstance();

    // Sizes
    private Insets contentLayoutInsets;
    private double contentWidth;
    private double contentHeight;

    /*****************************************
     *
     * JavaFX objects
     *
     *****************************************/
    // Top bar
    private HBox topBar;
    private Button minimizeWindowButton;
    private Button closeWindowButton;

    // Menu bar
    private HBox menuBarLayout;
    private MenuBar menuBar;
    private Menu viewsMenu;

    private MenuItem serverViewMenuItem;
    private MenuItem neuralNetworkViewMenuItem;
    private MenuItem stockViewMenuItem;

    // Main layout, contains all of the objects
    private TabPane viewsTabPane;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Singleton
     * @param stage Stage of the view
     */
    public MainView(Stage stage){
        // Parent
        super(stage, StrCst.ZE_NAME);

        // Initialize the view
        this.onInit();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the view
     */
    @Override
    protected void onInit(){
        // Root
        this.configureRoot();

        // Content pane
        this.configureContentPane();

        // Add content views
        this.configureContentViews();
    }

    /**
     * Configure content pane
     * TODO: Configure content depending on user's preferences
     */
    @Override
    protected void configureContentPane() {
        // Top bar
        this.configureTopBar();

        // Main layout
        this.configureMainLayout();

        // Build hierarchy
        this.rootLayout.getChildren().addAll(this.topBar,
                                             this.viewsTabPane);
    }

    /**
     * Build the root pane
     */
    protected void configureRoot(){
        // Root pane
        this.root.setPrefHeight(IntCst.DEFAULT_MAIN_WINDOW_HEIGHT_TEST);
        this.root.setPrefWidth(IntCst.DEFAULT_MAIN_WINDOW_WIDTH_TEST);
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_MAIN_VIEW_CSS));

        // Controller
        this.controller.setMainView(this);
        this.controller.onWindowDragged(this.root, this.stage);
        this.controller.onWindowResized(this.root, this.stage);
    }

    /**
     *  Build top bar
     */
    private void configureTopBar(){
        // Parent HBox
        this.topBar = new HBox();
        this.topBar.setAlignment(Pos.CENTER_RIGHT);
        this.topBar.setPrefHeight(IntCst.DEFAULT_MENU_BAR_HEIGHT);

        // MenuBar
        this.menuBarLayout = new HBox();
        this.menuBarLayout.setAlignment(Pos.CENTER_LEFT);
        this.menuBarLayout.setPrefHeight(IntCst.DEFAULT_MENU_BAR_HEIGHT);
        this.menuBarLayout.setMinHeight(IntCst.DEFAULT_MENU_BAR_HEIGHT);
        this.menuBarLayout.setMaxHeight(IntCst.DEFAULT_MENU_BAR_HEIGHT);
        HBox.setHgrow(this.menuBarLayout, Priority.ALWAYS);
        this.configureMenuBar();

        // Buttons
        // Reduce window
        this.minimizeWindowButton = new Button(StrCst.LABEL_MINIMIZE_WINDOW_BUTTON);
        this.minimizeWindowButton.getStyleClass().add(StrCst.STYLE_CLASS_CONTROL_WINDOW_BUTTON);
        this.minimizeWindowButton.rotateProperty().set(180);
        this.minimizeWindowButton.setOnAction(a -> this.controller.onMinimizeWindow(a));

        // Close window
        this.closeWindowButton = new Button(StrCst.LABEL_CLOSE_WINDOW_BUTTON);
        this.closeWindowButton.getStyleClass().add(StrCst.STYLE_CLASS_CONTROL_WINDOW_BUTTON);
        this.closeWindowButton.setOnAction(a -> this.controller.onCloseWindow(a));

        // Add buttons to the menu bar
        this.topBar.getChildren().addAll(this.menuBarLayout,
                                          this.minimizeWindowButton,
                                          this.closeWindowButton);
    }

    /**
     * Configure the menu bar
     */
    private void configureMenuBar(){
        // Menu bar
        this.menuBar = new MenuBar();
        this.menuBar.getStyleClass().add(StrCst.STYLE_CLASS_MENUBAR);

        // Menus
        this.viewsMenu = new Menu(StrCst.MENUBAR_LABEL_VIEWS);
        this.viewsMenu.getStyleClass().add(StrCst.STYLE_CLASS_MENUBAR_MENU);

        // Menu items
        // Tool menu
        this.serverViewMenuItem = new MenuItem(StrCst.NAME_SERVER_VIEW);
        this.serverViewMenuItem.getStyleClass().add(StrCst.STYLE_CLASS_MENUBAR_MENU_ITEM);
        this.serverViewMenuItem.setOnAction(a -> this.controller.onServerViewMenuItemClicked(a));
        this.neuralNetworkViewMenuItem = new MenuItem(StrCst.NAME_NEURAL_NETWORK_VIEW);
        this.neuralNetworkViewMenuItem.getStyleClass().add(StrCst.STYLE_CLASS_MENUBAR_MENU_ITEM);
        this.neuralNetworkViewMenuItem.setOnAction(a -> this.controller.onNeuralNetworkViewMenuItemClicked(a));
        this.stockViewMenuItem = new MenuItem(StrCst.NAME_STOCK_VIEW);
        this.stockViewMenuItem.getStyleClass().add(StrCst.STYLE_CLASS_MENUBAR_MENU_ITEM);
        this.stockViewMenuItem.setOnAction(a -> this.controller.onStockViewMenuItemClicked(a));

        // Build hierarchy
        this.viewsMenu.getItems().addAll(this.serverViewMenuItem,
                                         this.neuralNetworkViewMenuItem,
                                         this.stockViewMenuItem);
        this.menuBar.getMenus().add(viewsMenu);
        this.menuBarLayout.getChildren().add(this.menuBar);
    }

    /**
     * Build main pane
     */
    private void configureMainLayout(){
        // Content Grid pane
        this.viewsTabPane = new TabPane();
        VBox.setVgrow(this.viewsTabPane, Priority.ALWAYS);
        HBox.setHgrow(this.viewsTabPane, Priority.ALWAYS);
        //this.viewsTabPane.setSide(Side.LEFT);
        this.viewsTabPane.getStyleClass().add(StrCst.STYLE_CLASS_VIEWS_TAB_PANE);
    }

    /**
     * Add content views
     * Nothing to be done
     * TODO: Configure content depending on user's preferences
     */
    private void configureContentViews(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Add views
     *
     *****************************************/
    /**
     * Add a view
     * @param viewType The type of the view to display
     */
    public void addView(EnumCst.Views viewType){
        // Update active views sizes
        this.updateActiveViewsSizes();

        // Switch on the type of the view to add
        BaseView view = null;
        switch (viewType){
            case SERVER:
                view = new ServerInfoView(this.stage);
                break;
            case NEURAL_NETWORK:
                System.out.println("Not implemented yet...");
                break;
            case STOCK:
                view = new StockView(this.stage);
                break;
            default:
                System.out.println("Unknown view");
                break;
        }

        // Add view to active views
        if (view != null){
            Tab tab = new Tab();
            tab.setText(view.getName());
            tab.setContent(view.getRoot());
            tab.getStyleClass().add("viewTab");
            this.viewsTabPane.getTabs().add(tab);
        }
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Update active views sizes
     */
    private void updateActiveViewsSizes(){
        System.out.println("Not implemented yet...");
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * @return The current height of the root
     */
    public double getHeight() {
        return this.contentHeight;
    }

    /**
     * @return The current width of the root
     */
    public double getWidth() {
        return this.contentWidth;
    }

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * Cast the root pane to Parent
     * @return The root pane of the IHM
     */
    public Parent asParent(){
        return this.root;
    }

}
