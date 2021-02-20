package fr.pops.views;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public abstract class View {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected AnchorPane root;
    protected VBox rootLayout;

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the view
     */
    protected abstract void onInit();

    /**
     * Configure the rootPane
     */
    protected abstract void configureRoot();

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
