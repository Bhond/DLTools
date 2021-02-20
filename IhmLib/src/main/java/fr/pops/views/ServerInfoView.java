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
 * Name: ServerInfoView.java
 *
 * Description: Class defining the server view used to display
 *              info from the server
 *              Singleton
 *
 * Author: Charles MERINO
 *
 * Date: 15/002/2021
 *
 ******************************************************************************/
package fr.pops.views;

import fr.pops.client.Client;
import fr.pops.ihmlibcst.DblCst;
import fr.pops.ihmlibcst.StrCst;
import fr.pops.utils.Utils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ServerInfoView extends View {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Instance
    private final static ServerInfoView instance = new ServerInfoView();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    private ServerInfoView(){
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
    protected void onInit(){
        // Root
        configureRoot();
    }

    /**
     * Build the root pane
     */
    @Override
    protected void configureRoot(){
        // Root pane
        this.root = new AnchorPane();
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_SERVER_INFO_VIEW_CSS));
        this.root.getStyleClass().add(StrCst.STYLE_CLASS_ROOT);
        HBox.setHgrow(this.root, Priority.ALWAYS);
        VBox.setVgrow(this.root, Priority.ALWAYS);

        // Root layout
        this.rootLayout = new VBox();
        this.rootLayout.setAlignment(Pos.CENTER);
        this.rootLayout.getStyleClass().add(StrCst.STYLE_CLASS_ROOT_LAYOUT);

        // Resize the root layout
        AnchorPane.setTopAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);
        AnchorPane.setBottomAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);
        AnchorPane.setLeftAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);
        AnchorPane.setRightAnchor(this.rootLayout, DblCst.ROOT_LAYOUT_ANCHOR_SIZE);

        /** TEMP **/
        Button test = new Button("This is a test");
        test.setOnAction(actionEvent -> Client.getInstance().sendMessageToServer("Clicked"));
        this.rootLayout.getChildren().add(test);
        /** TEMP **/

        // Build hierarchy
        this.root.getChildren().add(this.rootLayout);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * ServerInfoView is a singleton
     * @return The instance
     */
    public static ServerInfoView getInstance(){
        return instance;
    }
}
