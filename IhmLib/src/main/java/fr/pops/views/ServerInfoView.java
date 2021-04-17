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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ServerInfoView extends BaseView<ServerInfoView, ServerInfoModel> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Ping
    HBox pingBox;
    private Label pingLbl;
    private Label pingValue;

    // Frequency
    HBox frequencyBox;
    private Label frequencyLbl;
    private Label frequencyValue;

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
        // Ping
        this.pingBox = new HBox();
        this.pingLbl = new Label("Ping: ");
        this.pingValue = new Label();
        this.pingBox.getChildren().addAll(this.pingLbl, this.pingValue);
        this.rootLayout.getChildren().add(this.pingBox);

        // Frequency
        this.frequencyBox = new HBox();
        this.frequencyLbl = new Label("Frequency: ");
        this.frequencyValue = new Label();
        this.frequencyBox.getChildren().addAll(this.frequencyLbl, this.frequencyValue);
        this.rootLayout.getChildren().add(this.frequencyBox);
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

    /**
     * Set the frequency value received from the server
     * @param value The update rate of the server
     */
    public void setFrequency(Double value){
        Updater.update(this.frequencyValue, String.format("%3f ms", value));
    }
}
