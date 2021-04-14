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

import fr.pops.controllers.main.MainController;
import fr.pops.cst.EnumCst;
import fr.pops.cst.IntCst;
import fr.pops.cst.StrCst;
import fr.pops.systeminfo.DisplayInfo;
import fr.pops.utils.Utils;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
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
    private MainController controller = MainController.getInstance();

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
    private MenuItem plotViewMenuItem;
    // Temp
    private MenuItem testViewMenuItem;

    // Main layout, contains all of the objects
    private GridPane mainLayout;

    // Active Views
    private int nbRows;
    private int currentRowIdx;
    private int nbColumns;
    private int  currentColumnIdx;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Singleton
     */
    public MainView(Stage stage){
        // Parent
        super(stage);

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
                this.mainLayout);

        // Update sizes
        this.updateContentSizes();
    }

    /**
     * Build the root pane
     */
    protected void configureRoot(){
        // Root pane
        this.height = IntCst.DEFAULT_MAIN_WINDOW_HEIGHT_TEST;
        this.width = IntCst.DEFAULT_MAIN_WINDOW_WIDTH_TEST;
        this.root.setPrefHeight(IntCst.DEFAULT_MAIN_WINDOW_HEIGHT_TEST); //this.displayInfo.getHeight());
        this.root.setPrefWidth(IntCst.DEFAULT_MAIN_WINDOW_WIDTH_TEST); //this.displayInfo.getWidth());
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
        this.serverViewMenuItem = new MenuItem(StrCst.MENUBAR_LABEL_SERVER_VIEW_ITEM);
        this.serverViewMenuItem.getStyleClass().add(StrCst.STYLE_CLASS_MENUBAR_MENU_ITEM);
        this.serverViewMenuItem.setOnAction(a -> this.controller.onServerViewMenuItemClicked(a));
        this.neuralNetworkViewMenuItem = new MenuItem(StrCst.MENUBAR_LABEL_NEURAL_NETWORK_VIEW_ITEM);
        this.neuralNetworkViewMenuItem.getStyleClass().add(StrCst.STYLE_CLASS_MENUBAR_MENU_ITEM);
        this.neuralNetworkViewMenuItem.setOnAction(a -> this.controller.onNeuralNetworkViewMenuItemClicked(a));
        this.plotViewMenuItem = new MenuItem(StrCst.MENUBAR_LABEL_PLOT_VIEW_ITEM);
        this.plotViewMenuItem.getStyleClass().add(StrCst.STYLE_CLASS_MENUBAR_MENU_ITEM);
        this.plotViewMenuItem.setOnAction(a -> this.controller.onPlotViewMenuItemClicked(a));

        /**
         *
         * TEMP
         *
         */
        this.testViewMenuItem = new MenuItem("Test");
        this.testViewMenuItem.getStyleClass().add(StrCst.STYLE_CLASS_MENUBAR_MENU_ITEM);
        this.testViewMenuItem.setOnAction(a -> this.controller.onTestViewMenuItemClicked(a));
        /**
         *
         * TEMP
         *
         */


        // Build hierarchy
        this.viewsMenu.getItems().addAll(this.serverViewMenuItem, this.neuralNetworkViewMenuItem, this.plotViewMenuItem, this.testViewMenuItem);
        this.menuBar.getMenus().add(viewsMenu);
        this.menuBarLayout.getChildren().add(this.menuBar);
    }

    /**
     * Build main pane
     */
    private void configureMainLayout(){
        // Content Grid pane
        this.mainLayout = new GridPane();
        VBox.setVgrow(this.mainLayout, Priority.ALWAYS);
        HBox.setHgrow(this.mainLayout, Priority.ALWAYS);
        this.mainLayout.setHgap(IntCst.DEFAULT_MAIN_VIEW_CONTENT_SPACING);
        this.mainLayout.setVgap(IntCst.DEFAULT_MAIN_VIEW_CONTENT_SPACING);
        this.mainLayout.setAlignment(Pos.CENTER);
        this.contentLayoutInsets = new Insets(IntCst.DEFAULT_MAIN_VIEW_CONTENT_MARGIN,
                IntCst.DEFAULT_MAIN_VIEW_CONTENT_MARGIN,
                IntCst.DEFAULT_MAIN_VIEW_CONTENT_MARGIN,
                IntCst.DEFAULT_MAIN_VIEW_CONTENT_MARGIN);
        this.mainLayout.setPadding(this.contentLayoutInsets);

        // Constraints
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setHalignment(HPos.CENTER);
        this.mainLayout.getColumnConstraints().add(columnConstraints);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setValignment(VPos.CENTER);
        this.mainLayout.getRowConstraints().add(rowConstraints);

        // Store layout current state
        this.nbRows = 1;
        this.nbColumns = 0;
        this.currentRowIdx = 0;
        this.currentColumnIdx = -1;
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
    public void addView(EnumCst.VIEWS viewType){
        // Update grid
        this.updateGrid();

        // Update content sizes
        this.updateContentSizes();

        // Update active views sizes
        this.updateActiveViewsSizes();

        // Switch on the type of the view to add
        BaseView<?,?> view = null;
        switch (viewType){
            case SERVER:
                view = new ServerInfoView(this.stage, this.contentHeight, this.contentWidth);
                break;
            case NEURAL_NETWORK:
                System.out.println("Not implemented yet...");
                break;
            case PLOT:
                view = new PlotView(this.stage, this.contentHeight, this.contentWidth);
                break;
            default:
                System.out.println("Unknown view");
                break;
        }

        // Add view to active views
        if (view != null){
            this.mainLayout.add(view.getRoot(), this.currentColumnIdx, this.currentRowIdx);
        }
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Update the grid
     * TODO: Implement the case where a view is deleted
     */
    private void updateGrid(){
        // Update the indices and the number of columns and rows
        if (this.currentColumnIdx == IntCst.DEFAULT_MAX_NB_COLUMNS - 1){
            this.nbRows++;
            this.currentRowIdx++;
            this.currentColumnIdx = 0;
        } else {
            if (!(this.nbColumns == IntCst.DEFAULT_MAX_NB_COLUMNS)){
                this.nbColumns++;
            }
            this.currentColumnIdx++;
        }
    }

    /**
     * Update the content sizes
     */
    public void updateContentSizes(){
        this.contentHeight = (this.mainLayout.getHeight()
                - this.contentLayoutInsets.getTop()
                - this.contentLayoutInsets.getBottom()
                - (this.nbRows - 1) * this.mainLayout.getVgap()) /
                (this.nbRows == 0 ? 1 : this.nbRows);

        this.contentWidth = (this.mainLayout.getHeight()
                - this.contentLayoutInsets.getLeft()
                - this.contentLayoutInsets.getRight()
                - (this.nbColumns - 1) * this.mainLayout.getHgap()) /
                (this.nbColumns == 0 ? 1 : this.nbColumns);
    }

    /**
     * Update active views sizes
     */
    private void updateActiveViewsSizes(){
        // Loop over all the active view
//        for (View view : this.activeViews){
//            view.updateSize(this.contentHeight, this.contentWidth);
//        }
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
