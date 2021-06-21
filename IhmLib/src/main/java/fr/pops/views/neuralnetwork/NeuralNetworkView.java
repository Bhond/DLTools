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

import fr.pops.controllers.viewcontrollers.NeuralNetworkController;
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
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NeuralNetworkView extends BaseView<NeuralNetworkController> {

    /**
     *
     * TODO: Wrap the controls in the controller
     *
     */

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
    private ListView<Component> componentContainerListView;
    private FilteredList<Node> componentContainer;
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
        super(stage, StrCst.NAME_NEURAL_NETWORK_VIEW, EnumCst.Views.NEURAL_NETWORK);

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
        this.controller = new NeuralNetworkController(this);

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
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_NEURAL_NETWORK_VIEW_CSS));

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
        this.inputsTab = new Tab(StrCst.LIBRARY_INPUTS);
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
        this.layersTab = new Tab(StrCst.LIBRARY_LAYERS);
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
        this.componentContainer = new FilteredList<>(this.centerPane.getChildren(), (child) -> child instanceof Component);
        this.componentContainer.addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()){
                //If items are removed
                for (Node n : change.getRemoved()) {
                    this.componentContainerListView.getItems().remove(n);
                }
                //If items are added
                for (Node n : change.getAddedSubList()) {
                    this.componentContainerListView.getItems().add((Component) n);
                }
            }
        });
        // Component container list view
        this.componentContainerListView = new ListView<>();
        this.componentContainerListView.getStyleClass().add(StrCst.STYLE_CLASS_LISTVIEW);
        this.componentContainerListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.componentContainerListView.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!observableValue.getValue()){
                this.componentContainerListView.getSelectionModel().clearSelection();
            }
        });
        this.componentContainerListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Component> call(ListView<Component> nodeListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Component node, boolean isEmpty) {
                        super.updateItem(node, isEmpty);
                        if (node != null) {
                            setText(node.getType().toString());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });

        // Settings
        this.componentProperties = new BeanProperties("");
        this.componentProperties.setVisible(false);

        // Setup layout
        this.rightBox.heightProperty().addListener(observable -> {
            this.componentContainerListView.setPrefHeight(this.rightBox.getPrefHeight() / 2);
            this.componentProperties.setPrefHeight(this.rightBox.getPrefHeight() / 2);
        });
        this.rightBox.widthProperty().addListener(observable -> this.componentContainerListView.setPrefWidth(this.rightBox.getPrefWidth()));
        VBox.setVgrow(this.componentContainerListView, Priority.ALWAYS);
    }

    /**
     * Create icons and add evdrag event handler
     * @param category The category of icons to create
     * @return The list of created icons
     */
    private List<ComponentIcon> createIcons(EnumCst.ComponentCategories category) {
        return Arrays.stream(EnumCst.ComponentTypes.values()).filter((c) -> c.getCategory() == category)
                .map(ComponentIcon::new)
                .peek((icn) -> icn.setOnDragDetected(mouseEvent -> {
                    this.root.setOnDragOver(NeuralNetworkView.this::onDragOverRoot);
                    this.centerPane.setOnDragOver(NeuralNetworkView.this::onDragOverCenter);
                    this.centerPane.setOnDragDropped(NeuralNetworkView.this::onIconDragDropped);

                    // get a reference to the clicked DragIcon object
                    this.dragOverIcon.setType(icn.getType());

                    if (dragOverIcon != null) {
                        // Begin drag ops
                        dragOverIcon.relocateToPoint(new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
                        ClipboardContent content = new ClipboardContent();
                        DragContainer container = new DragContainer();
                        container.addData (StrCst.DRAG_CONTAINER_TYPE, this.dragOverIcon.getType().toString());
                        content.put(DragContainer.AddNode, container);
                        this.dragOverIcon.startDragAndDrop(TransferMode.ANY).setContent(content);
                        this.dragOverIcon.setVisible(true);
                        this.dragOverIcon.setMouseTransparent(true);
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
        this.rightBox.getChildren().addAll(this.componentContainerListView, this.componentProperties);

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

            DragContainer container = (DragContainer) dragEvent.getDragboard().getContent(DragContainer.AddNode);
            if (container != null) {
                if (container.getValue(StrCst.DRAG_CONTAINER_SCENE_COORDS) != null) {
                    Component component = ComponentFactory.get(this.dragOverIcon.getType());
                    if (component != null) {
                        component.focusedProperty().addListener((observableValue, aFocused, isFocused) -> {
                            if (isFocused){
                                this.displayBeanProperties(component.getBeanProperties());
                                if (!this.componentProperties.isVisible()) this.componentProperties.setVisible(true);
                            } else {
                                this.componentProperties.setVisible(false);
                            }
                        });
                        this.centerPane.getChildren().add(component);
                        Point2D cursorPoint = container.getValue(StrCst.DRAG_CONTAINER_SCENE_COORDS);
                        component.relocateToPoint(new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32));
                        component.requestFocus();
                    }
                }
            }

            // AddLink drag operation
            container = (DragContainer) dragEvent.getDragboard().getContent(DragContainer.AddLink);
            if (container != null) {
                String sourceId = container.getValue(StrCst.DRAG_CONTAINER_SOURCE);
                String targetId = container.getValue(StrCst.DRAG_CONTAINER_TARGET);

                if (sourceId != null && targetId != null) {

                    Link link = new Link();

                    //add our link at the top of the rendering order so it's rendered first
                    this.centerPane.getChildren().add(0, link);

                    // Retrieve source and target
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
                    // Bind ends for the link to follow its handles
                    if (source != null && target != null){
                        link.bindEnds(source, target);
                    }
            }
            // Consume event
            dragEvent.consume();
        }
        });
    }

    /**
     * Handle icon dropped onto the center pane
     * @param dragEvent The drag event dropped
     */
    private void onIconDragDropped(DragEvent dragEvent) {
        // Retrieve drag container
        DragContainer container = (DragContainer) dragEvent.getDragboard().getContent(DragContainer.AddNode);

        // Add new coordinates in the container
        container.addData(StrCst.DRAG_CONTAINER_SCENE_COORDS, new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY()));

        // Add node to the container
        ClipboardContent content = new ClipboardContent();
        content.put(DragContainer.AddNode, container);

        // Consume event
        dragEvent.getDragboard().setContent(content);
        dragEvent.setDropCompleted(true);
    }

    /**
     * Handle icon dragged over onto the center pane
     * @param dragEvent
     */
    private void onDragOverCenter(DragEvent dragEvent) {
        // Allow any transfer mode
        dragEvent.acceptTransferModes(TransferMode.ANY);

        // Relocate drag icon
        this.dragOverIcon.relocateToPoint(new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY()));

        // Consume event
        dragEvent.consume();
    }

    /**
     * Drag over root
     * @param dragEvent The associated drag event
     */
    private void onDragOverRoot(javafx.scene.input.DragEvent dragEvent) {
        // Relocate drag icon when exiting center pane
        Point2D p = centerPane.sceneToLocal(dragEvent.getSceneX(), dragEvent.getSceneY());
        if (!this.centerPane.boundsInLocalProperty().get().contains(p)) {
            this.dragOverIcon.relocateToPoint(new Point2D(dragEvent.getSceneX(), dragEvent.getSceneY()));
            return;
        }

        // Consume event
        dragEvent.consume();
    }

    /**
     * Reset the bean properties
     */
    private void displayBeanProperties(BeanProperties beanProperties){
        this.componentProperties.reset(beanProperties);
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
