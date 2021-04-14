package fr.pops.views;

import fr.pops.controllers.viewcontrollers.BaseController;
import fr.pops.cst.DblCst;
import fr.pops.cst.StrCst;
import fr.pops.viewmodels.BaseModel;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class BaseView<viewT extends BaseView<?,?>, modelT extends BaseModel<?>> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected BaseController<viewT, modelT> controller;

    protected Stage stage;
    protected AnchorPane root;
    protected VBox rootLayout;

    protected double height;
    protected double width;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    protected BaseView(Stage stage){
        // Initialization
        this.stage = stage;
        this.configureRoot();
    }

    /**
     * Standard ctor
     */
    protected BaseView(Stage stage, double height, double width){
        // Initialization
        this.stage = stage;
//        this.height = height;
//        this.width = width;
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
        AnchorPane.setTopAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);
        AnchorPane.setBottomAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);
        AnchorPane.setLeftAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);
        AnchorPane.setRightAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);

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
     * Update
     *
     *****************************************/

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
}
