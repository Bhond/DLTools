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
 * Name: BaseView.java
 *
 * Description: Abstract class defining the basic structure for the views
 *
 * Author: Charles MERINO
 *
 * Date: 10/02/2021
 *
 ******************************************************************************/
package fr.pops.views;

import fr.pops.controllers.viewcontrollers.BaseController;
import fr.pops.cst.DblCst;
import fr.pops.cst.StrCst;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class BaseView<controllerT extends BaseController<?,?>> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // General
    private String name;
    protected controllerT controller;

    // Basic components
    protected Stage stage;
    protected AnchorPane root;
    protected VBox rootLayout;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    protected BaseView() {
        // Nothing to be done
    }

    /**
     * Ctor
     * @param stage Stage of the view
     * @param name Name of the view, used in misc occasions
     */
    protected BaseView(Stage stage, String name){
        // Initialization
        this.stage = stage;
        this.name = name;
        this.configureRoot();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Configure the rootPane
     */
    private void configureRoot(){
        // Root pane
        this.root = new AnchorPane();
        HBox.setHgrow(this.root, Priority.ALWAYS);
        VBox.setVgrow(this.root, Priority.ALWAYS);
        this.root.getStyleClass().add(StrCst.STYLE_CLASS_ROOT);

        // Root layout
        this.rootLayout = new VBox();
        this.rootLayout.setAlignment(Pos.CENTER);
        this.rootLayout.getStyleClass().add(StrCst.STYLE_CLASS_ROOT_LAYOUT);

        // Resize the root layout
        AnchorPane.setTopAnchor(this.rootLayout, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setBottomAnchor(this.rootLayout, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setLeftAnchor(this.rootLayout, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setRightAnchor(this.rootLayout, DblCst.SIZE_ANCHOR_ZERO);

        // Build hierarchy
        this.root.getChildren().add(this.rootLayout);
    }

    /**
     * Initialize the view
     */
    protected abstract void onInit();

    /**
     * Configure the rootPane
     */
    protected abstract void configureContentPane();

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * Root pane
     * @return The root node of the view
     */
    public AnchorPane getRoot(){
        return this.root;
    }

    /**
     * @return Name of the view
     */
    public String getName() {
        return this.name;
    }
}
