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
 * Name: Component.java
 *
 * Description: Abstract class defining the components
 *
 * Author: Charles MERINO
 *
 * Date: 21/06/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.beans.bean.Bean;
import fr.pops.client.Client;
import fr.pops.cst.DblCst;
import fr.pops.cst.EnumCst;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.beanproperties.BeanProperties;
import fr.pops.customnodes.neuralnetworks.component.link.Link;
import fr.pops.customnodes.neuralnetworks.component.link.LinkHandle;
import fr.pops.sockets.resquest.beanrequests.DeleteBeanRequest;
import fr.pops.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public abstract class Component<T extends Bean> extends AnchorPane {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
     // General
     private EnumCst.ComponentTypes type;
     private final List<String> linkIds = new ArrayList<>();

    // Root
    private VBox rootBox;

    // Title
    private StringProperty titleProperty;
    private HBox titleBox;
    private Label titleLbl;
    private Button closeComponentButton;

    // Drag parameters
    private Point2D dragOffset = new Point2D (0.0, 0.0);

    // Header
    private HBox header;
    private LinkHandle leftLinkHandle;
    private HBox centralBox;
    private LinkHandle rightLinkHandle;

    // Link
    private Link link;
    private AnchorPane paneParent;

    // Properties
    protected BeanProperties beanProperties;

    // Bean
    private T bean;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    protected Component(EnumCst.ComponentTypes type, T bean){
        // Fields
        this.type = type;
        this.bean = bean;

        // Initialisation
        this.onInit();
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialisation
     */
    private void onInit(){
        // General
        this.setId(UUID.randomUUID().toString());

        // Root
        this.buildRoot();

        // Link
        this.buildLink();

        // Title
        this.buildTitle();

        // Header
        this.buildHeader();

        // Build controls
        this.buildControls();

        // Build hierarchy
        this.buildHierarchy();

        // Build bean properties
        this.buildProperties();
    }

    /**
     * Build the root
     */
    private void buildRoot() {

        // Root box
        this.rootBox = new VBox();
        AnchorPane.setTopAnchor(this.rootBox, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setBottomAnchor(this.rootBox, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setLeftAnchor(this.rootBox, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setRightAnchor(this.rootBox, DblCst.SIZE_ANCHOR_ZERO);
        this.rootBox.setAlignment(Pos.TOP_CENTER);
        this.rootBox.getStyleClass().add("rootBox");

        // Style root
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_CSS_DIRECTORY + "Component.css"));
        this.getStyleClass().add("rootPane");
        this.setPrefWidth(300);

        // TMP
        this.beanProperties = new BeanProperties(this.type.toString());
    }

    /**
     * Build the reference link
     */
    private void buildLink() {
        this.link = new Link();
        this.link.setVisible(false);
        this.parentProperty().addListener(observable -> {
            this.paneParent = (AnchorPane) this.getParent();
        });
    }

    /**
     * Build the title
     */
    private void buildTitle() {
        // HBox
        this.titleBox = new HBox();
        this.titleBox.hoverProperty().addListener((observableValue, aIsHovered, t1) -> {
            this.closeComponentButton.setVisible(!aIsHovered);
        });

        // Label
        this.titleLbl = new Label(this.type.toString());
        this.titleLbl.setAlignment(Pos.CENTER);
        this.titleBox.widthProperty().addListener(observable -> {
            this.titleLbl.setPrefWidth(this.titleBox.getWidth() - this.closeComponentButton.getWidth());
        });
        this.titleProperty = new SimpleStringProperty();
        this.titleProperty.addListener((observableValue, s, newTitle) -> {
            this.titleLbl.setText(newTitle);
        });

        // Close button
        this.closeComponentButton = new Button("x");
        this.closeComponentButton.textOverrunProperty().set(OverrunStyle.CLIP);
    }

    /**
     * Build the header
     */
    private void buildHeader() {
        // Box
        this.header = new HBox();
        this.header.setAlignment(Pos.CENTER);

        // Central box
        this.centralBox = new HBox();
        HBox.setHgrow(this.centralBox, Priority.ALWAYS);

        // Input button
        this.leftLinkHandle = new LinkHandle();

        // Output button
        this.rightLinkHandle = new LinkHandle();
    }

    /**
     * Build hierarchy
     */
    private void buildHierarchy(){
        // Ttile
        this.titleBox.getChildren().addAll(this.titleLbl, this.closeComponentButton);

        // Header
        this.header.getChildren().addAll(this.leftLinkHandle, this.centralBox, this.rightLinkHandle);

        // Root box
        this.rootBox.getChildren().addAll(this.titleBox, this.header);

        // Root
        this.getChildren().add(this.rootBox);
    }

    /**
     * Create the bean properties
     */
    private void buildProperties(){
        this.beanProperties.build(this.bean);
    }

    /*****************************************
     *
     * Register links
     *
     *****************************************/
    /**
     * Register a link
     * @param id The id of the link to register
     */
    public void registerLink(String id) {
        this.linkIds.add(id);
    }

    /*****************************************
     *
     * Register links
     *
     *****************************************/
    /**
     * Relocates the object to a point that has been converted to
     * scene coordinates
     * @param p The scene coordinates of the point to relocate
     */
    public void relocateToPoint (Point2D p) {
        Point2D localCoords = this.getParent().sceneToLocal(p);
        this.relocate (
                (int) (localCoords.getX() - this.dragOffset.getX()),
                (int) (localCoords.getY() - this.dragOffset.getY())
        );
    }

    /*****************************************
     *
     * Controls
     *
     *****************************************/
    /**
     * Build the controls
     */
    private void buildControls(){
        // Focus
        this.setOnMouseClicked(mouseEvent -> {
            this.requestFocus();
        });

        // Title
        this.titleBox.setOnDragDetected((event) -> {
            this.getParent().setOnDragOver(this::onContextDragOver);
            this.getParent().setOnDragDropped(this::onContextDragDropped);
            dragOffset = new Point2D(event.getX(), event.getY());
            relocateToPoint(
                    new Point2D(event.getSceneX() - dragOffset.getX() - this.getParent().getLayoutX(), event.getSceneY())
            );

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData(StrCst.DRAG_CONTAINER_TYPE, this.getType().toString());
            content.put(DragContainer.AddNode, container);

            startDragAndDrop (TransferMode.ANY).setContent(content);

            event.consume();
        });

        // Close button
        this.closeComponentButton.setOnMouseClicked(mouseEvent -> {
            Client.getInstance().send(new DeleteBeanRequest(this.getId(), this.bean.getId()));
            //this.onCloseComponent();
        });

        // Link handles
        this.leftLinkHandle.setOnDragDetected(this::onLeftLinkHandleDragDetected);
        this.rightLinkHandle.setOnDragDetected(this::onRightLinkHandleDragDetected);
        this.leftLinkHandle.setOnDragDropped(this::onLinkHandleDragDropped);
        this.rightLinkHandle.setOnDragDropped(this::onLinkHandleDragDropped);
    }

    /**
     * Deal with stuff when closing the component
     */
    private void onCloseComponent() {
        // Remove link and node
        this.removeLinkAndNode();
    }

    /**
     * Remove link and node when hitting the close button
     */
    private void removeLinkAndNode() {
        // Need to notify all the the node that are linked to this one that the link has been removed
        List<String> idsToRemove = new LinkedList<>();
        List<Node> nodesToRemove = new LinkedList<>();
        for (String linkId : this.linkIds){
            for (Node n : this.paneParent.getChildren()){
                // If the node doesn't have any id, skip
                if (n.getId() == null) continue;

                if (n.getId().equals(linkId)){
                    Link link = (Link) n;
                    link.getStartHandle().setState(LinkHandle.States.FREE);
                    link.getEndHandle().setState(LinkHandle.States.FREE);
                    nodesToRemove.add(n);
                }
            }
            idsToRemove.add(linkId);
        }
        this.linkIds.removeAll(idsToRemove);
        this.paneParent.getChildren().removeAll(nodesToRemove);
        this.paneParent.getChildren().remove(this);
    }

    /**
     * Handle drag over the component's parent
     * @param event The drag over event detected
     */
    private void onContextDragOver(DragEvent event){
        event.acceptTransferModes(TransferMode.ANY);
        this.relocateToPoint(new Point2D( event.getSceneX(), event.getSceneY()));
        event.consume();
    }

    /**
     * Handle drag dropped over the component's parent
     * @param event The drag event dropped detected
     */
    private void onContextDragDropped(DragEvent event){
        this.getParent().setOnDragOver(null);
        this.getParent().setOnDragDropped(null);
        event.setDropCompleted(true);
        event.consume();
    }


    /**
     * Handle drag detection on both link handles
     * @param event The mouse event detected
     */
    private void onLinkHandleDragDetected(MouseEvent event){
        // Reset drag handling
        this.getParent().setOnDragOver(null);
        this.getParent().setOnDragDropped(null);
        this.getParent().setOnDragOver(this::onContextLinkDragOver);
        this.getParent().setOnDragDropped(this::onContextLinkDragDropped);

        // Set up draggable link
        this.paneParent.getChildren().add(0, this.link);
        this.link.setVisible(false);

        // Locate link root
        LinkHandle linkHandle = (LinkHandle) event.getSource();
        Point2D p = new Point2D(
                this.getLayoutX() + linkHandle.getLayoutX() + (linkHandle.getWidth() / 2.0),
                this.getLayoutY() + this.header.getLayoutY() + (linkHandle.getHeight() / 2.0)
        );
        this.link.setStart(p);

        // Setup drag content
        ClipboardContent content = new ClipboardContent();
        DragContainer container = new DragContainer();
        Component linkHandleComponentParent = (Component<?>) linkHandle.getParent().getParent().getParent(); // header -> rootBox -> this
        container.addData(StrCst.DRAG_CONTAINER_SOURCE, linkHandle.getId());
        content.put(DragContainer.AddLink, container);
        linkHandleComponentParent.startDragAndDrop(TransferMode.ANY).setContent(content);

        // Consume event
        event.consume();
    }


    /**
     * Handle drag detection on the left link handle
     * @param event The mouse event detected
     */
    private void onLeftLinkHandleDragDetected(MouseEvent event){

        // Check the handle is already linked
        if (this.leftLinkHandle.getState() == LinkHandle.States.LINKED){
            this.rebuildLink(event, this.leftLinkHandle);
        } else {
            this.onLinkHandleDragDetected(event);
        }
    }

    private void rebuildLink(MouseEvent event, LinkHandle leftLinkHandle) {
        // Retrieve other end of the link
        List<String> idsToRemove = new LinkedList<>();
        List<Link> linksToRemoveFromParent = new LinkedList<>();
        LinkHandle startHandle = null;
        for (String id : this.linkIds){
            for (Node n : this.paneParent.getChildren()){
                // If the node doesn't have any id, skip
                if (n.getId() == null) continue;

                if (n.getId().equals(id)){
                    Link link = (Link) n;
                    startHandle = link.getStartHandle();
                    linksToRemoveFromParent.add(link);
                }
            }
        }

        // Remove link
        leftLinkHandle.setState(LinkHandle.States.FREE);
        startHandle.setState(LinkHandle.States.FREE);
        this.linkIds.removeAll(idsToRemove);
        this.paneParent.getChildren().removeAll(linksToRemoveFromParent);

        // Rebuild link
        this.getParent().setOnDragOver(null);
        this.getParent().setOnDragDropped(null);
        this.getParent().setOnDragOver(this::onContextLinkDragOver);
        this.getParent().setOnDragDropped(this::onContextLinkDragDropped);

        // Set up draggable link
        this.paneParent.getChildren().add(0, this.link);
        this.link.setVisible(false);
        Component<?> startHandleComponentParent = (Component<?>) startHandle.getParent().getParent().getParent(); // header -> rootBox -> this
        Point2D startP = new Point2D(
                startHandleComponentParent.getLayoutX() + startHandle.getLayoutX() + (startHandle.getWidth() / 2.0),
                startHandleComponentParent.getLayoutY() + startHandleComponentParent.header.getLayoutY() + (startHandle.getHeight() / 2.0)
        );
        this.link.setStart(startP);

        // Setup drag content
        ClipboardContent content = new ClipboardContent();
        DragContainer container = new DragContainer();
        container.addData(StrCst.DRAG_CONTAINER_SOURCE, startHandle.getId());
        content.put(DragContainer.AddLink, container);
        startHandleComponentParent.startDragAndDrop(TransferMode.ANY).setContent(content);

        // Consume event
        event.consume();
    }

    /**
     * Handle drag detection on the right link handle
     * @param event The mouse event detected
     */
    private void onRightLinkHandleDragDetected(MouseEvent event){
        this.onLinkHandleDragDetected(event);
    }

    /**
     * Handle drag dropped over Link handle
     * @param event The drag event detected
     */
    private void onLinkHandleDragDropped(DragEvent event){
        // Reset drag handling
        this.getParent().setOnDragOver(null);
        this.getParent().setOnDragDropped(null);

        // Retrieve the dragged data
        DragContainer container =
                (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

        // Abort event if there is no dragged data
        if (container == null) return;

        // Initialize the dragged data
        LinkHandle linkHandle = (LinkHandle) event.getSource();
        ClipboardContent content = new ClipboardContent();
        container.addData(StrCst.DRAG_CONTAINER_TARGET, linkHandle.getId());
        content.put(DragContainer.AddLink, container);

        // Locate the end point
        Point2D p = new Point2D(
                this.getLayoutX() + linkHandle.getLayoutX() + (linkHandle.getWidth() / 2.0),
                this.getLayoutY() + this.header.getLayoutY() + (linkHandle.getHeight() / 2.0)
        );
        link.setEnd(p);

        // Store content in dragboard
        event.getDragboard().setContent(content);
        event.setDropCompleted(true);

        // Hide the draggable link
        this.link.setVisible(false);

        // Remove it from the parent's pane
        this.paneParent.getChildren().remove(0);

        event.consume();
    }

    /**
     * Handle drag over the component's parent
     * @param event The drag event detected
     */
    private void onContextLinkDragOver(DragEvent event){
        // Start transfer
        event.acceptTransferModes(TransferMode.ANY);

        // Relocate draggable link
        if (!this.link.isVisible()) {
            this.link.setVisible(true);
        }
        this.link.setEnd(new Point2D(event.getX(), event.getY()));

        // Consume event
        event.consume();
    }

    /**
     * Handle drag dropped over the component's parent
     * @param event The drag event detected
     */
    private void onContextLinkDragDropped(DragEvent event){
        // Reset drag handling
        this.getParent().setOnDragOver(null);
        this.getParent().setOnDragDropped(null);

        // Hide the draggable link
        this.link.setVisible(false);

        // Remove it from the parent's pane
        this.paneParent.getChildren().remove(0);

        event.setDropCompleted(true);
        event.consume();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The type of the component
     */
    public EnumCst.ComponentTypes getType() {
        return this.type;
    }

    /**
     * @return The title of the component
     */
    public String getTitle() {
        return this.titleProperty.get();
    }

    /**
     * @return The left handle where a link can be attached
     */
    public LinkHandle getLeftLinkHandle() {
        return this.leftLinkHandle;
    }

    /**
     * @return The left handle where a link can be attached
     */
    public LinkHandle getRightLinkHandle() {
        return this.rightLinkHandle;
    }

    /**
     * @return The bean attached to this component
     */
    public T getBean() {
        return this.bean;
    }

    /**
     * @return The bean properties
     */
    public BeanProperties getBeanProperties() {
        return this.beanProperties;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the title of the component
     * @param title The new title of the component
     */
    public void setTitle(String title) {
        this.titleProperty.setValue(title);
    }
}
