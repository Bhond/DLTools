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
 * Name: MNISTView.java
 *
 * Description: Class defining the MNIST view used to display
 *              the neural network training and guessing of MNIST dataset
 *
 * Author: Charles MERINO
 *
 * Date: 16/05/2021
 *
 ******************************************************************************/
package fr.pops.views.neuralnetwork;

import fr.pops.controllers.viewcontrollers.MNISTController;
import fr.pops.cst.EnumCst;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.beanproperties.BeanProperties;
import fr.pops.customnodes.neuralnetworks.component.component.Component;
import fr.pops.customnodes.neuralnetworks.component.component.ComponentFactory;
import fr.pops.customnodes.neuralnetworks.component.component.ComponentIcon;
import fr.pops.customnodes.neuralnetworks.component.component.DragContainer;
import fr.pops.customnodes.neuralnetworks.component.link.Link;
import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.utils.Utils;
import fr.pops.views.base.BaseView;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NeuralNetworkView extends BaseView<MNISTController> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
     // Center
    private AnchorPane centerPane;

    // Bottom
    private ComponentIcon dragOverIcon;
    private TabPane componentLibrary;
    private Tab inputsTab;
    private FlowPane inputsPane;
    private Tab layersTab;
    private FlowPane layersPane;

    // Right
    private VBox rightBox;
    private ListView<Label> componentContainer;
    private BeanProperties componentProperties;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private NeuralNetworkView(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param stage Stage of the view
     */
    public NeuralNetworkView(Stage stage){
        // Parent
        super(stage, StrCst.NAME_MNIST_VIEW, EnumCst.Views.MNIST);

        // Initialisation
        this.onInit();
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the view
     */
    @Override
    protected void onInit() {
        // Controller
        this.controller = new MNISTController(this);

        // Content pane
        this.configureContentPane();

        // Bottom
        this.configureBottom();

        // Right
        this.configureRight();

        // Hierarchy
        this.buildHierarchy();

        // Build controls
        this.buildControls();
    }

    /**
     * Configure the rootPane
     */
    @Override
    protected void configureRoot() {
        // Root
        this.root = new BorderPane();
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_MNIST_VIEW_CSS));

        // Drag icon
        this.dragOverIcon = new ComponentIcon();
        this.dragOverIcon.setVisible(false);
    }

    /**
     * Configure the bottom pane
     */
    private void configureBottom() {
        // Root pane
        this.componentLibrary = new TabPane();
        this.componentLibrary.setPrefHeight(200);
        this.componentLibrary.setMinHeight(Control.USE_PREF_SIZE);
        this.componentLibrary.setMaxHeight(Control.USE_PREF_SIZE);
        this.componentLibrary.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            this.componentLibrary.setTabMinWidth(this.componentLibrary.getWidth() / this.componentLibrary.getTabs().size());
            this.componentLibrary.setTabMaxWidth(this.componentLibrary.getWidth() / this.componentLibrary.getTabs().size());
        });
        this.centerPane.heightProperty().addListener((observable, oldValue, newValue) ->
        {
            this.centerPane.setMinHeight(this.root.getHeight() - this.componentLibrary.getPrefHeight());
            this.centerPane.setMaxHeight(this.root.getWidth() - this.componentLibrary.getPrefHeight());
        });

        // Inputs
        this.inputsTab = new Tab("Inputs");
        this.inputsTab.setClosable(false);
        this.inputsPane = new FlowPane();
        this.inputsPane.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            this.inputsPane.setMinWidth(this.componentLibrary.getWidth());
            this.inputsPane.setMaxWidth(this.componentLibrary.getWidth());
        });
        this.inputsPane.setPadding(new Insets(10));
        this.inputsPane.setHgap(50);
        this.inputsPane.setVgap(50);

        // Layers
        this.layersTab = new Tab("Layers");
        this.layersTab.setClosable(false);
        this.layersPane = new FlowPane();
        this.layersPane.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            this.layersPane.setMinWidth(this.componentLibrary.getWidth());
            this.layersPane.setMaxWidth(this.componentLibrary.getWidth());
        });
        this.layersPane.setPadding(new Insets(10));
        this.layersPane.setHgap(50);
        this.layersPane.setVgap(50);
    }

    /**
     * Configure right
     */
    private void configureRight() {
        // Box
        this.rightBox = new VBox();
        this.rightBox.setPrefWidth(300);

        // Component container
        this.componentContainer = new ListView<>();
        this.componentContainer.getStyleClass().add(StrCst.STYLE_CLASS_LISTVIEW);
        this.componentContainer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.componentContainer.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!observableValue.getValue()){
                this.componentContainer.getSelectionModel().clearSelection();
            }
        });
        this.rightBox.heightProperty().addListener(observable -> {
            this.componentContainer.setPrefHeight(this.rightBox.getPrefHeight() / 2);
        });
        this.rightBox.widthProperty().addListener(observable -> this.componentContainer.setPrefWidth(this.rightBox.getPrefWidth()));
        VBox.setVgrow(this.componentContainer, Priority.ALWAYS);

        // Settings
        this.componentProperties = new BeanProperties();
    }

    /**
     * Create icons
     * @param category The category of icons to create
     * @return The list of created icons
     */
    private List<ComponentIcon> createIcons(EnumCst.ComponentCategories category) {
        return Arrays.stream(EnumCst.ComponentTypes.values()).filter((c) -> c.getCategory() == category)
                .map(ComponentIcon::new)
                .peek((icn) -> icn.setOnDragDetected(mouseEvent -> {
                    root.setOnDragOver(NeuralNetworkView.this::onDragOverRoot);
                    centerPane.setOnDragOver(NeuralNetworkView.this::onDragOverCenter);
                    centerPane.setOnDragDropped(NeuralNetworkView.this::onIconDragDropped);

                    // get a reference to the clicked DragIcon object
                    String icnStr = icn.getText();
                    dragOverIcon.setType(icn.getType());

                    if (dragOverIcon != null) {
                        //begin drag ops
                        dragOverIcon.relocateToPoint(new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
                        ClipboardContent content = new ClipboardContent();
                        DragContainer container = new DragContainer();
                        container.addData ("type", dragOverIcon.getType().toString());
                        content.put(DragContainer.AddNode, container);
                        dragOverIcon.startDragAndDrop(TransferMode.ANY).setContent(content);
                        dragOverIcon.setVisible(true);
                        dragOverIcon.setMouseTransparent(true);
                    }
                    mouseEvent.consume();
                }))
                .collect(Collectors.toList());
    }

    /**
     * Configure the rootPane
     */
    @Override
    protected void configureContentPane() {

        // Center
        this.centerPane = new AnchorPane();

    }

    /**
     * Build the hierarchy of the view
     */
    @Override
    protected void buildHierarchy() {
        // Center
        this.centerPane.getChildren().add(this.dragOverIcon);

        // Bottom
        this.inputsPane.getChildren().addAll(this.createIcons(EnumCst.ComponentCategories.INPUTS));
        this.inputsTab.setContent(this.inputsPane);
        this.layersPane.getChildren().addAll(this.createIcons(EnumCst.ComponentCategories.LAYERS));
        this.layersTab.setContent(this.layersPane);
        this.componentLibrary.getTabs().addAll(this.inputsTab, this.layersTab);

        // Right
        this.rightBox.getChildren().addAll(this.componentContainer, this.componentProperties);

        // Root
        ((BorderPane) this.root).setCenter(this.centerPane);
        ((BorderPane) this.root).setBottom(this.componentLibrary);
        ((BorderPane) this.root).setRight(this.rightBox);
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Build the drag controls
     */
    private void buildControls() {
        // Drag over center
        this.centerPane.setOnDragOver(this::onDragOverCenter);

        // Drag dropped center
        this.centerPane.setOnDragDropped(this::onIconDragDropped);

        // Drag done center
        this.centerPane.setOnDragDone(dragEvent -> {

            // Remove handlers
            this.centerPane.removeEventHandler(DragEvent.DRAG_OVER, this::onDragOverCenter);
            this.centerPane.removeEventHandler(DragEvent.DRAG_DROPPED, this::onIconDragDropped);
            this.root.removeEventHandler(DragEvent.DRAG_OVER, this::onDragOverRoot);
            this.dragOverIcon.setVisible(false);

            dragEvent.consume();

            DragContainer container = (DragContainer) dragEvent.getDragboard().getContent(DragContainer.AddNode);
            if (container != null) {
                if (container.getValue("scene_coords") != null) {

                    Component node = ComponentFactory.get(fr.pops.cst.EnumCst.ComponentTypes.INPUT_LOCAL);

                    if (node != null) {

                        this.centerPane.getChildren().add(node);
                        Label componentLabel = new Label(node.getType().toString());
                        this.componentContainer.getItems().add(componentLabel);
                        Point2D cursorPoint = container.getValue("scene_coords");
                        node.relocateToPoint(
                                new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
                        );
                    }
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
                    this.centerPane.getChildren().add(0, link);

                    Component source = null;
                    Component target = null;

                    for (Node n : this.centerPane.getChildren()) {
                        if (n instanceof Component){
                            Component component = ((Component) n);

                            if (component.getLeftLinkHandle().getId() == null && component.getRightLinkHandle().getId() == null) continue;

                            if (component.getLeftLinkHandle().getId().equals(sourceId)){
                                source = component;
                            } else if (component.getLeftLinkHandle().getId().equals(targetId)){
                                target = component;
                            } else if (component.getRightLinkHandle().getId().equals(sourceId)){
                                source = component;
                            } else if (component.getRightLinkHandle().getId().equals(targetId)){
                                target = component;
                            }
                        }
                    }

                    if (source != null && target != null){
                        link.bindEnds(source, target);
                    }
                }

                dragEvent.consume();
            }
        });

    }

    /**
     * @param dragEvent
     */
    private void onIconDragDropped(DragEvent dragEvent) {
        DragContainer container =
                (DragContainer) dragEvent.getDragboard().getContent(DragContainer.AddNode);

        container.addData("scene_coords",
                new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY()));

        ClipboardContent content = new ClipboardContent();
        content.put(DragContainer.AddNode, container);

        dragEvent.getDragboard().setContent(content);
        dragEvent.setDropCompleted(true);
    }

    /**
     * @param dragEvent
     */
    private void onDragOverCenter(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.ANY);
        dragOverIcon.relocateToPoint(
                new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY()));
        dragEvent.consume();
    }

    /**
     * Drag over components
     *
     * @param dragEvent The associated drag event
     */
    private void onDragOverRoot(javafx.scene.input.DragEvent dragEvent) {
        Point2D p = centerPane.sceneToLocal(dragEvent.getSceneX(), dragEvent.getSceneY());
        if (!centerPane.boundsInLocalProperty().get().contains(p)) {
            dragOverIcon.relocateToPoint(new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY()));
            return;
        }
        dragEvent.consume();
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Update the image
     *
     * @param label The image to display
     */
    public void updateLabel(int label) {
        System.out.println("Nothing is done");
    }

    /**
     * Update the image
     *
     * @param image The image to display
     */
    public void updateImage(BaseNDArray image) {
        System.out.println("Nothing is done");
    }

    /**
     * Update the configuration
     */
    public void updateConfiguration(int nbLayers, HashMap<Integer, Integer> layers, double learningRate, boolean regularisationOn, double l1, double l2) {
        System.out.println("Nothing is done");
    }

    /*****************************************
     *
     * Load / Save
     *
     *****************************************/
    /**
     * Read the fields stored in the json
     *
     * @param fields The fields to read
     */
    @Override
    protected void readFields(Map fields) {

    }
}
