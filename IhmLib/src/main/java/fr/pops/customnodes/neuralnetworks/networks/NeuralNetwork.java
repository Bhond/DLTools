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

import fr.pops.commoncst.EnumCst;
import fr.pops.cst.StrCst;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;

public abstract class NeuralNetwork extends HBox {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // General
    private EnumCst.NeuralNetworkTypes type;

    // The group to plot
    protected HBox neuralNetworkBox;
    protected Group neuralNetworkGroup;

    // Menu
    private Accordion accordion;

    // Display control menu
    private TitledPane displayControlMenu;
    private VBox displayControlMenuContent;
    private Button backToFrontButton;

    // Neural network  control menu
    private TitledPane neuralNetworkControlMenu;
    private VBox neuralNetworkControlMenuContent;

    // Rotation control
    private double anchorX = 0;
    private double anchorY = 0;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

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
        // This
        this.setSpacing(10);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setAlignment(Pos.CENTER);

        // Neural network group
        this.neuralNetworkBox = new HBox();
        this.neuralNetworkBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(this.neuralNetworkBox, Priority.ALWAYS);
        this.neuralNetworkGroup = new Group();

        // Accordion
        this.configureAccordionMenu();

        // Add controls
        this.buildMouseControls();

        // Build hierarchy
        this.buildHierarchy();
    }

    /**
     * Configure the accordion menu on the left side
     */
    protected void configureAccordionMenu(){
        // Initialisation
        this.accordion = new Accordion();
        this.accordion.setPrefWidth(100);

        // Display control menu
        this.displayControlMenu = new TitledPane();
        this.displayControlMenu.setText(StrCst.LABEL_DISPLAY);
        this.displayControlMenu.getStyleClass().add(StrCst.STYLE_CLASS_ACCORDION);
        this.configureDisplayControlMenuContent();

        // Neural network control menu
        this.neuralNetworkControlMenu = new TitledPane();
        this.neuralNetworkControlMenu.setText(StrCst.LABEL_NEURAL_NETWORK);
        this.neuralNetworkControlMenu.getStyleClass().add(StrCst.STYLE_CLASS_ACCORDION);
        this.configureNeuralNetworkControlMenuContent();
    }

    /**
     * Build the display control menu
     */
    private void configureDisplayControlMenuContent(){
        // Create pane
        this.displayControlMenuContent = new VBox();
        this.displayControlMenuContent.setAlignment(Pos.TOP_CENTER);

        // Control buttons
        this.backToFrontButton = new Button(StrCst.LABEL_FRONT);
        this.backToFrontButton.setOnAction(action -> this.backToFront());
    }

    /**
     * Build the neural network control menu
     */
    private void configureNeuralNetworkControlMenuContent(){
        // Create pane
        this.neuralNetworkControlMenuContent = new VBox();
        this.neuralNetworkControlMenuContent.setAlignment(Pos.CENTER);
    }

    /**
     * Build hierarchy
     */
    private void buildHierarchy(){
        // Neural network
        this.neuralNetworkBox.getChildren().add(this.neuralNetworkGroup);
        // Display control menu
        this.displayControlMenuContent.getChildren().add(this.backToFrontButton);
        this.displayControlMenu.setContent(this.displayControlMenuContent);
        // Neural network control menu
        this.neuralNetworkControlMenu.setContent(this.neuralNetworkControlMenuContent);
        // Accordion
        this.accordion.getPanes().addAll(this.displayControlMenu, this.neuralNetworkControlMenu);
        // Root
        this.getChildren().addAll(this.neuralNetworkBox, this.accordion);
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

    /*****************************************
     *
     * Controls
     *
     *****************************************/
    /**
     * Define the mouse controls
     */
    private void buildMouseControls(){
        // Rotations
        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        this.neuralNetworkGroup.getTransforms().addAll(xRotate, yRotate);
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);
        this.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.SECONDARY){
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                anchorAngleX = angleX.get();
                anchorAngleY = angleY.get();
            }
        });
        this.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.SECONDARY){
                angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
                angleY.set(anchorAngleY + (anchorX - event.getSceneX()));
            }
        });

        // Scroll -> Zoom +-
        this.addEventHandler(ScrollEvent.SCROLL, event -> {
            double dz = event.getDeltaY();
            this.neuralNetworkGroup.translateZProperty().set(this.neuralNetworkGroup.getTranslateZ() + dz);
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
     *****************************************/
    /**
     * Display the front view of the neural network
     */
    public void backToFront(){
        this.angleX.set(0);
        this.angleY.set(0);
    }
}
