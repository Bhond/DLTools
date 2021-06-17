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
package fr.pops.views.networkinfo;

import fr.pops.controllers.viewcontrollers.NetworkInfoController;
import fr.pops.cst.DblCst;
import fr.pops.cst.StrCst;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.utils.Utils;
import fr.pops.views.base.BaseView;
import fr.pops.views.updater.Updater;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NetworkInfoView extends BaseView<NetworkInfoController> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
     // Layouts
     private VBox rootLayout;
    private HBox contentBox;
    private VBox leftBox;
    private VBox rightBox;

    // Ping
    private HBox pingBox;
    private Label pingLbl;
    private Label pingValue;

    // Frequency
    private HBox frequencyBox;
    private Label frequencyLbl;
    private Label frequencyValue;

    // Connected clients
    private ListView<ClientInfo> clientInfoListView;

    // Earth animation
    private Sphere earth;
    private AnimationTimer earthAnimationTimer;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standar ctor
     * Nothing to be done
     */
    private NetworkInfoView(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param stage Stage of the view
     */
    public NetworkInfoView(Stage stage){
        // Parent
        super(stage, StrCst.NAME_SERVER_VIEW, fr.pops.cst.EnumCst.Views.NETWORK_INFO);

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
        this.controller = new NetworkInfoController(this);

        // Content
        this.configureContentPane();

        // Hierarchy
        this.buildHierarchy();
    }

    /**
     * Configure the rootPane
     */
    @Override
    protected void configureRoot() {
        // Root
        this.root = new AnchorPane();
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_SERVER_INFO_VIEW_CSS));

        // Root layout
        this.rootLayout = new VBox();
        AnchorPane.setTopAnchor(this.rootLayout, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setBottomAnchor(this.rootLayout, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setLeftAnchor(this.rootLayout, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setRightAnchor(this.rootLayout, DblCst.SIZE_ANCHOR_ZERO);
    }

    /**
     * Build the content pane
     */
    @Override
    protected void configureContentPane(){
        // Layouts
        this.contentBox = new HBox();
        VBox.setVgrow(this.contentBox, Priority.ALWAYS);
        this.leftBox = new VBox();
        this.leftBox.setAlignment(Pos.CENTER);
        this.leftBox.setSpacing(10);
        this.rightBox = new VBox();
        this.rightBox.setAlignment(Pos.CENTER);
        this.rightBox.setSpacing(10);
        HBox.setHgrow(this.rightBox, Priority.ALWAYS);

        // Ping
        this.pingBox = new HBox();
        this.pingBox.getStyleClass().add(StrCst.STYLE_CLASS_LABEL_VALUE_PAIR_BOX);
        this.pingLbl = new Label(StrCst.LABEL_PING);
        this.pingValue = new Label();

        // Frequency
        this.frequencyBox = new HBox();
        this.frequencyBox.getStyleClass().add(StrCst.STYLE_CLASS_LABEL_VALUE_PAIR_BOX);
        HBox.setHgrow(this.frequencyBox, Priority.NEVER);
        this.frequencyLbl = new Label(StrCst.LABEL_FREQUENCY);
        this.frequencyValue = new Label();

        // Connected clients;
        this.clientInfoListView = new ListView<>();
        ClientInfo header = ClientInfo.HEADER;
        header.getStyleClass().add("listViewHeader");
        this.clientInfoListView.getItems().add(header);
        this.clientInfoListView.getStyleClass().add(StrCst.STYLE_CLASS_LISTVIEW);
        this.clientInfoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.leftBox.getChildren().add(this.clientInfoListView);

        // Earth animation
        this.configureEarthAnimation();
        this.earthAnimationTimer.start();
    }

    /**
     * Build the hierarchy of the view
     */
    @Override
    protected void buildHierarchy() {
        // Ping
        this.pingBox.getChildren().addAll(this.pingLbl, this.pingValue);
        this.leftBox.getChildren().add(this.pingBox);

        // Frequency
        this.frequencyBox.getChildren().addAll(this.frequencyLbl, this.frequencyValue);
        this.leftBox.getChildren().add(this.frequencyBox);

        // Earth
        this.rightBox.getChildren().add(this.earth);

        // Content
        this.contentBox.getChildren().addAll(this.leftBox, this.rightBox);

        // Root
        this.rootLayout.getChildren().add(this.contentBox);
        ((AnchorPane) this.root).getChildren().add(this.rootLayout);
    }

    /**
     * Configure the earth animation
     */
    private void configureEarthAnimation(){
        // Earth
        this.earth = new Sphere(DblCst.EARTH_SPHERE_RADIUS);
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(Color.TRANSPARENT);
        Image image = new Image(Utils.getResource(StrCst.PATH_EARTH_TEXTURE));
        mat.setSelfIlluminationMap(image);
        this.earth.setMaterial(mat);
        this.earth.setDrawMode(DrawMode.FILL);
        this.earth.setBlendMode(BlendMode.ADD);
        this.earth.setRotationAxis(Rotate.Y_AXIS);

        // Animation
        this.earthAnimationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                earth.rotateProperty().set(earth.getRotate() + 0.5);
            }
        };
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The connected clients
     */
    public List<EnumCst.ClientTypes> getConnectedClients() {
        List<EnumCst.ClientTypes> result = new LinkedList<>();
        for (ClientInfo info : this.clientInfoListView.getItems()){
            result.add(info.getType());
        }
        return result;
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
        Updater.update(this.pingValue, String.format(StrCst.FORMAT_VALUE_PING, value));
    }

    /**
     * Set the frequency value received from the server
     * @param value The update rate of the server
     */
    public void displayFrequency(Double value){
        Updater.update(this.frequencyValue, String.format(StrCst.FORMAT_VALUE_FREQUENCY, value));
    }

    /**
     * Set the clients connected to the server
     * @param connectedClients The clients connected to the server
     */
    public void displayConnectedClients(List<EnumCst.ClientTypes> connectedClients){
        // Remove disconnected clients
        List<EnumCst.ClientTypes> displayedClientsId = new LinkedList<>();
        List<ClientInfo> clientsInfoToRemove = new LinkedList<>();
        for (ClientInfo info : this.clientInfoListView.getItems()){
            if (info.isHeader()) continue;
            if (!connectedClients.contains(info.getType())) {
                clientsInfoToRemove.add(info);
            } else {
                displayedClientsId.add(info.getType());
            }
        }
        Updater.update(this.clientInfoListView, fr.pops.cst.EnumCst.ListViewOps.REMOVE, clientsInfoToRemove);

        // Add new clients
        for (EnumCst.ClientTypes type : connectedClients){
            if (!displayedClientsId.contains(type)){
                Updater.update(this.clientInfoListView, fr.pops.cst.EnumCst.ListViewOps.ADD, new ClientInfo(type));
            }
        }
    }

    /*****************************************
     *
     * Load / Save
     *
     *****************************************/
    /**
     * Map the view to json format
     *
     * @return The mapping between object fields and json fields
     */
    @Override
    public Map<String, Object> viewToJsonMap() {
        // Initialization
        Map<String, Object> brace = new HashMap<>();

        return brace;
    }

    /**
     * Cast the json object stored in the fields
     * to a view
     * @return The view built from the JSONObject stored in the fields
     */
    @Override
    public void jsonToView(Map<String, Object> map) {
    }

    /**
     * Read the fields stored in the json
     */
    @Override
    protected void readFields(Map<String, Object> fields) {

    }
}
