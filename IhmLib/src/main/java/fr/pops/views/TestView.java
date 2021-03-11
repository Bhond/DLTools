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
 * Name: TestView.java
 *
 * Description: Class defining the test view to implement beans
 *              and communication pipeline
 *
 * Author: Charles MERINO
 *
 * Date: 04/03/2021
 *
 ******************************************************************************/
package fr.pops.views;

import fr.pops.customnodes.panes.CorneredPane;
import fr.pops.ihmlibcst.StrCst;
import fr.pops.utils.Utils;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestView extends View {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private AnchorPane mainPane;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public TestView(Stage stage, double height, double width){
        // Parent
        super(stage, height, width);

        // Initialisation
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
    protected void onInit() {
        // Root style sheet
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_TEST_VIEW_CSS));

        // Configure content
        this.configureContentPane();
    }

    /**
     * Build the content pane
     */
    @Override
    protected void configureContentPane() {
        this.mainPane = new CorneredPane(this.height, this.width, 10, 20);
        this.mainPane.getStyleClass().add("testPane");
        VBox.setVgrow(this.mainPane, Priority.ALWAYS);
        HBox.setHgrow(this.mainPane, Priority.ALWAYS);
        this.rootLayout.getChildren().add(this.mainPane);
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/

}
