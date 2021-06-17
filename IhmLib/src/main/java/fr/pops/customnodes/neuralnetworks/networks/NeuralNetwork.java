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
 * Name: NeuralNetwork.java
 *
 * Description: Abstract class describing the general definition
 *              of the display of a neural network
 *
 * Author: Charles MERINO
 *
 * Date: 16/05/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.networks;

import fr.pops.client.Client;
import fr.pops.commoncst.EnumCst;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.labelvaluepair.LabelValuePair;
import fr.pops.customnodes.neuralnetworks.component.component.Component;
import fr.pops.customnodes.neuralnetworks.component.component.ComponentFactory;
import fr.pops.customnodes.neuralnetworks.component.link.Link;
import fr.pops.customnodes.neuralnetworks.component.component.DragContainer;
import fr.pops.sockets.resquest.GetMNISTConfiguration;
import fr.pops.utils.Utils;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class NeuralNetwork extends AnchorPane {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Gui
    protected int heightNeuralNetwork;
    protected int widthNeuralNetwork;

    // General
    private EnumCst.NeuralNetworkTypes type;
    protected HashMap<Integer, Integer> layersConf;

    // The group to plot
    protected HBox neuralNetworkBox;
    protected Group neuralNetworkGroup;

    // Configuration box
    private VBox configurationBox;
    private Label configurationTitleLabel;
    private LabelValuePair neuralNetworkTypePair;
    private LabelValuePair nbLayersPair;
    private LabelValuePair learningRatePair;
    // Box reg on
    private LabelValuePair l1Pair;
    private LabelValuePair l2Pair;
    // Layers
    private Label layersTitleLabel;
    private int idxLayersLabel;
    private List<Node> layersConfiguration;
    // Controls
    // Mode selection
    private Button loadConfigurationButton;
    private Button runButton;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param type the type of the neural network
     */
    protected NeuralNetwork(EnumCst.NeuralNetworkTypes type){
        // Fields
        this.type = type;
        this.layersConf = new HashMap<>();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the scene
     */
    protected void onInit(){
        // Style sheet
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_NEURAL_NETWORK_CSS));

        // Styleclass
        this.getStyleClass().add(StrCst.STYLE_CLASS_ROOT);

        // This
        this.heightNeuralNetwork = this.heightProperty().intValue();
        this.widthNeuralNetwork = this.widthProperty().intValue();
        VBox.setVgrow(this, Priority.ALWAYS);

        // Neural network group
        this.neuralNetworkBox = new HBox();
        this.neuralNetworkBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(this.neuralNetworkBox, Priority.ALWAYS);
        this.neuralNetworkGroup = new Group();
        this.neuralNetworkBox.widthProperty().addListener((obs, oldValue, newValue) -> {
            this.widthNeuralNetwork = newValue.intValue();
            this.clear();
            this.build();
        });
        this.neuralNetworkBox.heightProperty().addListener((obs, oldValue, newValue) -> {
            this.heightNeuralNetwork = newValue.intValue();
            this.clear();
            this.build();
        });

        // Configuration men
        this.configureConfigurationMenu();

        // Add controls
        this.buildMouseControls();

        // Build hierarchy
        this.buildHierarchy();
    }

    /**
     * Build the neural network control menu
     */
    private void configureConfigurationMenu(){
        // Create pane
        this.configurationBox = new VBox();
        this.configurationBox.setSpacing(10);

        // Configuration
        this.configurationTitleLabel = new Label("Configuration");
        this.neuralNetworkTypePair = new LabelValuePair("Type:", this.type.name());
        this.nbLayersPair = new LabelValuePair("Nb layers:", 0);
        this.learningRatePair = new LabelValuePair("Learning rate:", 0d);
        this.l1Pair = new LabelValuePair("l1:", 0d);
        this.l2Pair = new LabelValuePair("l2:", 0d);
        this.configureLayersConfiguration();

        // Controls
        this.loadConfigurationButton = new Button("Load");
        this.loadConfigurationButton.setOnAction((actionEvent) -> Client.getInstance().send(new GetMNISTConfiguration()));
        this.runButton = new Button("Run");
        this.runButton.setOnAction((actionEvent) -> System.out.println("Running...") );
    }

    /**
     * Configure the layers configuration content
     */
    private void configureLayersConfiguration(){
        this.layersTitleLabel = new Label("Layers");
        this.layersConfiguration = new LinkedList<>();
        for (int i = 0; i < this.layersConf.size(); i++){
            Label l = new Label("layer " + i);
            LabelValuePair lvp = new LabelValuePair("nOut: ", this.layersConf.get(i));
            lvp.setOrientation(LabelValuePair.ORIENTATION.VERTICAL);
            this.layersConfiguration.add(l);
            this.layersConfiguration.add(lvp);
        }
    }

    /**
     * Build hierarchy
     */
    private void buildHierarchy(){
        // Neural network
        this.neuralNetworkBox.getChildren().add(this.neuralNetworkGroup);

        // Configuration box
        this.configurationBox.getChildren().addAll(this.configurationTitleLabel,
                this.neuralNetworkTypePair,
                this.nbLayersPair,
                this.learningRatePair,
                this.l1Pair,
                this.l2Pair,
                this.layersTitleLabel,
                this.loadConfigurationButton,
                this.runButton);
        this.addLayersConfigurationToHierarchy();

        // Root
        //this.getChildren().addAll(this.neuralNetworkBox, this.configurationBox);
    }

    /**
     * Add the layers' configuration below the proper label
     */
    private void addLayersConfigurationToHierarchy(){
        int idx = this.idxLayersLabel;
        if (this.idxLayersLabel <= 0){ // The node is copied. The address doesn't match
            idx = this.configurationBox.getChildren().indexOf(this.layersTitleLabel);
            this.idxLayersLabel = idx;
        }
        this.configurationBox.getChildren().addAll(idx + 1, this.layersConfiguration);
    }

    /*****************************************
     *
     * Gui
     *
     *****************************************/
    /**
     * Build the neural network
     */
    public abstract void build();

    /**
     * Clear the neural network
     */
    public abstract void clear();

    /*****************************************
     *
     * Controls
     *
     *****************************************/
    /**
     * Define the mouse controls
     */
    private void buildMouseControls(){

        this.setOnDragDone(dragEvent -> {

            DragContainer container = (DragContainer) dragEvent.getDragboard().getContent(DragContainer.AddLink);
            if (container != null) {
                if (container.getValue("scene_coords") != null) {

                    Component node = ComponentFactory.get(fr.pops.cst.EnumCst.ComponentTypes.INPUT_LOCAL);

                    //node.setType(DragIconType.valueOf(container.getValue("type")));
                    this.getChildren().add(node);

                    Point2D cursorPoint = container.getValue("scene_coords");

                    node.relocateToPoint(
                            new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
                    );
                }
            }

            //AddLink drag operation
            container = (DragContainer) dragEvent.getDragboard().getContent(DragContainer.AddLink);
            if (container != null) {
                String sourceId = container.getValue("source");
                String targetId = container.getValue("target");

                if (sourceId != null && targetId != null) {

                    //System.out.println(container.getData());
                    Link link = new Link();

                    //add our link at the top of the rendering order so it's rendered first
                    this.getChildren().add(0, link);

                    Component source = null;
                    Component target = null;

                    for (Node n : this.getChildren()) {

                        if (n.getId() == null)
                            continue;

                        if (n.getId().equals(sourceId))
                            source = (Component) n;

                        if (n.getId().equals(targetId))
                            target = (Component) n;

                    }

                    if (source != null && target != null)
                        link.bindEnds(source, target);
                }

                dragEvent.consume();
            }
        });
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/

    /*****************************************
     *
     * Setter
     *
     ****************************************/

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Update the configuration
     */
    public void updateConfiguration(int nbLayers, HashMap<Integer, Integer> layersConf, double learningRate, boolean regularisationOn, double l1, double l2){
        // Fill in pairs
        this.nbLayersPair.setValue(nbLayers);
        this.layersConf = layersConf;
        this.learningRatePair.setValue(learningRate);
        this.l1Pair.setValue(l1);
        this.l2Pair.setValue(l2);

        // Update the layers configuration
        this.configurationBox.getChildren().removeAll(this.layersConfiguration);
        this.configureLayersConfiguration();
        this.addLayersConfigurationToHierarchy();

        // Draw the layers
        this.clear();
        this.build();
    }
}
