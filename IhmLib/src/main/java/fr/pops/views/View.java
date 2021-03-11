package fr.pops.views;

import fr.pops.ihmlibcst.DblCst;
import fr.pops.ihmlibcst.StrCst;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class View {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
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
    protected View(Stage stage){
        // Initialization
        this.stage = stage;
        this.configureRoot();
    }

    /**
     * Standard ctor
     */
    protected View(Stage stage, double height, double width){
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
