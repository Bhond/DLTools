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
import fr.pops.sockets.cst.EnumCst;
import fr.pops.utils.Utils;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class ServerInfoView extends BaseView<ServerInfoController> {

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
     * Standar ctor
     * Nothing to be done
     */
    private ServerInfoView(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param stage Stage of the view
     */
    public ServerInfoView(Stage stage){
        // Parent
        super(stage, StrCst.NAME_SERVER_VIEW);

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
    public void displayPingValue(Double value){
        Updater.update(this.pingValue, String.format("%3.0f ms", value));
    }

    /**
     * Set the frequency value received from the server
     * @param value The update rate of the server
     */
    public void displayFrequency(Double value){
        Updater.update(this.frequencyValue, String.format("%3.0f Hz", value));
    }

    /**
     * Set the clients connected to the server
     * @param clients The clients connected to the server
     */
    public void displayConnectedClients(List<EnumCst.ClientTypes> clients){

    }
}
