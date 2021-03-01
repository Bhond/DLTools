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
 *
 * Author: Charles MERINO
 *
 * Date: 10/02/2021
 *
 ******************************************************************************/
package fr.pops.views;

import fr.pops.controllers.main.MainController;
import fr.pops.ihmlibcst.DblCst;
import fr.pops.ihmlibcst.IntCst;
import fr.pops.ihmlibcst.StrCst;
import fr.pops.systeminfo.DisplayInfo;
import fr.pops.utils.Utils;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainView extends View {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // System info
    private final DisplayInfo displayInfo = DisplayInfo.getInstance();

    // Controller
    private MainController controller = MainController.getInstance();

    /*****************************************
     *
     * JavaFX objects
     *
     *****************************************/
    // Menu bar
    private HBox menuBar;
    private Button minimizeWindowButton;
    private Button closeWindowButton;

    // Main layout, contains all of the objects
    private HBox mainLayout;

    // Misc Views
    private ServerInfoView serverInfoView = ServerInfoView.getInstance();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public MainView(){
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

        // Add content views
        this.configureContentViews();
    }

    /**
     * Build the root pane
     */
    @Override
    protected void configureRoot(){
        // Root pane
        this.root = new AnchorPane();
        this.root.setPrefHeight(800); //this.displayInfo.getHeight());
        this.root.setPrefWidth(800); //this.displayInfo.getWidth());
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_MAIN_VIEW_CSS));
        this.root.getStyleClass().add(StrCst.STYLE_CLASS_ROOT);

        // Root layout
        this.rootLayout = new VBox();
        this.rootLayout.setAlignment(Pos.TOP_CENTER);

        // Resize the root layout
        AnchorPane.setTopAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);
        AnchorPane.setBottomAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);
        AnchorPane.setLeftAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);
        AnchorPane.setRightAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);

        // Menu bar
        this.configureMenuBar();

        // Main layout
        this.configureMainLayout();

        // Build hierarchy
        this.rootLayout.getChildren().addAll(this.menuBar,
                                             this.mainLayout);
        this.root.getChildren().add(this.rootLayout);
    }

    /**
     *  Build menu bar
     */
    private void configureMenuBar(){
        // Parent HBox
        this.menuBar = new HBox();
        this.menuBar.setAlignment(Pos.CENTER_RIGHT);
        this.menuBar.setPrefHeight(IntCst.DEFAULT_MENU_BAR_HEIGHT);

        // Buttons
        // Reduce window
        this.minimizeWindowButton = new Button(StrCst.LABEL_MINIMIZE_WINDOW_BUTTON);
        this.minimizeWindowButton.getStyleClass().add(StrCst.STYLE_CLASS_CONTROL_WINDOW_BUTTON);
        this.minimizeWindowButton.rotateProperty().set(180);
        this.minimizeWindowButton.setOnAction(a -> this.controller.minimizeWindow(a));

        // Close window
        this.closeWindowButton = new Button(StrCst.LABEL_CLOSE_WINDOW_BUTTON);
        this.closeWindowButton.getStyleClass().add(StrCst.STYLE_CLASS_CONTROL_WINDOW_BUTTON);
        this.closeWindowButton.setOnAction(a -> this.controller.closeWindow(a));

        // Add buttons to the menu bar
        this.menuBar.getChildren().addAll(this.minimizeWindowButton,
                                          this.closeWindowButton);
    }

    /**
     * Build main pane
     */
    private void configureMainLayout(){
        // Parent HBox
        this.mainLayout = new HBox();
        VBox.setVgrow(this.mainLayout, Priority.ALWAYS);
    }

    /**
     * Add content views
     */
    private void configureContentViews(){
        // ServerInfoView
        this.mainLayout.getChildren().add(this.serverInfoView.getRoot());
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
