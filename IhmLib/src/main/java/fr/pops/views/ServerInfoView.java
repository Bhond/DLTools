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
 *
 * Author: Charles MERINO
 *
 * Date: 15/02/2021
 *
 ******************************************************************************/
package fr.pops.views;

import fr.pops.controllers.viewcontrollers.ServerInfoController;
import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import fr.pops.viewmodels.ServerInfoModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ServerInfoView extends BaseView<ServerInfoView, ServerInfoModel> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    HBox pingBow = new HBox();
    private Label pingLbl = new Label("Ping:");
    private Label pingValue = new Label("0.0 ms");

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public ServerInfoView(Stage stage, double height, double width){
        // Parent
        super(stage, height, width);

        // Initialization
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
        // Set controller
        this.controller = new ServerInfoController(this);

        // Set root stylesheet
        // Root pane
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_SERVER_INFO_VIEW_CSS));

        // Content
        this.configureContentPane();
    }

    /**
     * Build the content pane
     */
    @Override
    protected void configureContentPane(){
        /** TEMP **/
        Button test = new Button("This is a test");
        test.setOnAction(actionEvent -> System.out.println("Test button clicked"));
        this.rootLayout.getChildren().add(test);
        /** TEMP **/

        // Ping
        this.pingBow = new HBox();
        this.pingLbl = new Label("Ping:");
        this.pingValue = new Label("0.0 ms");
        this.pingBow.getChildren().addAll(this.pingLbl, this.pingValue);
        this.rootLayout.getChildren().add(this.pingBow);
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Set the ping value received from the request
     * @param value The response delay between the client and the server
     */
    public void setPingValue(Double value){
        Updater.update(this.pingValue, String.format("%3f ms", value));
    }

    public void setServerFrequency(Double value){

    }
}
