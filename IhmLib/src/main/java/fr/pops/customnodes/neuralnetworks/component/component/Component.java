package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.cst.DblCst;
import fr.pops.cst.EnumCst;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.neuralnetworks.component.link.Link;
import fr.pops.customnodes.neuralnetworks.component.link.LinkHandler;
import fr.pops.utils.Utils;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Component extends AnchorPane {

    /**
     *
     * TODO: Comment control methods
     *
     */


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
    private Label title;

    // Drag parameters
    private Point2D dragOffset = new Point2D (0.0, 0.0);

    // Header
    private HBox header;
    private LinkHandler leftLinkHandle;
    private HBox centralBox;
    private LinkHandler rightLinkHandle;
    private Link link = new Link();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    protected Component(EnumCst.ComponentTypes type){
        // Fields
        this.type = type;

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
        this.setPrefWidth(600);

        // Title
        this.title = new Label(this.type.toString());
        this.title.setAlignment(Pos.CENTER);
        this.rootBox.widthProperty().addListener((observable -> {
            this.title.setPrefWidth(this.rootBox.getWidth());
        }));

        // Header
        this.buildHeader();

        // Build controls
        this.buildControls();

        // Build hierarchy
        this.buildHierarchy();
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
        this.leftLinkHandle = new LinkHandler();

        // Output button
        this.rightLinkHandle = new LinkHandler();
    }

    /**
     * Build hierarchy
     */
    private void buildHierarchy(){
        // Header
        this.header.getChildren().addAll(this.leftLinkHandle, this.centralBox, this.rightLinkHandle);

        // Root box
        this.rootBox.getChildren().addAll(this.title, this.header);

        // Root
        this.getChildren().add(this.rootBox);
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

    /**
     * Unregister a link
     * @param id The id of the link to unregister
     */
    public void unregisterLink(String id) {
        this.linkIds.remove(id);
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
        // Root
        //DraggableNode.makeDraggable(this, this.title);
        this.title.setOnDragDetected((event) -> {
            this.getParent().setOnDragOver(this::onContextDragOver);
            this.getParent().setOnDragDropped(this::onContextDragDropped);
            dragOffset = new Point2D(event.getX(), event.getY());
            relocateToPoint(
                    new Point2D(event.getSceneX() - dragOffset.getX() - this.getParent().getLayoutX(), event.getSceneY())
            );

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("type", "TYPE_TOTO");
            content.put(DragContainer.AddNode, container);

            startDragAndDrop (TransferMode.ANY).setContent(content);

            event.consume();
        });

        // Link handles
        this.leftLinkHandle.setOnDragDetected(this::onLinkHandleDragDetected);
        this.rightLinkHandle.setOnDragDetected(this::onLinkHandleDragDetected);
        this.leftLinkHandle.setOnDragDropped(this::onLinkHandleDragDropped);
        this.rightLinkHandle.setOnDragDropped(this::onLinkHandleDragDropped);
    }

    /**
     *
     * @param event
     */
    private void onContextDragOver(DragEvent event){
        event.acceptTransferModes(TransferMode.ANY);
        this.relocateToPoint(new Point2D( event.getSceneX(), event.getSceneY()));
        event.consume();
    }

    /**
     *
     * @param event
     */
    private void onContextDragDropped(DragEvent event){
        this.getParent().setOnDragOver(null);
        this.getParent().setOnDragDropped(null);
        event.setDropCompleted(true);
        event.consume();
    }

    /**
     *
     * @param event
     */
    private void onLinkHandleDragDetected(MouseEvent event){
        this.getParent().setOnDragOver(null);
        this.getParent().setOnDragDropped(null);
        this.getParent().setOnDragOver(this::onContextLinkDragOver);
        this.getParent().setOnDragDropped(this::onContextLinkDragDropped);

        //Set up user-draggable link
        ((AnchorPane)this.getParent()).getChildren().add(0,link);

        link.setVisible(false);
        LinkHandler linkHandle = (LinkHandler) event.getSource();
        Point2D p = new Point2D(
                this.getLayoutX() + linkHandle.getLayoutX() + (linkHandle.getWidth() / 2.0),
                this.getLayoutY() + this.header.getLayoutY() + (linkHandle.getHeight() / 2.0)
        );

        link.setStart(p);

        //Drag content code
        ClipboardContent content = new ClipboardContent();
        DragContainer container = new DragContainer();
        container.addData("source", linkHandle.getId());
        content.put(DragContainer.AddLink, container);

        linkHandle.startDragAndDrop (TransferMode.ANY).setContent(content);
        event.consume();
    }

    /**
     *
     * @param event
     */
    private void onLinkHandleDragDropped(DragEvent event){
        this.getParent().setOnDragOver(null);
        this.getParent().setOnDragDropped(null);
        //get the drag data.  If it's null, abort.
        //This isn't the drag event we're looking for.
        DragContainer container =
                (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

        if (container == null)
            return;

        AnchorPane linkHandle = (AnchorPane) event.getSource();
        ClipboardContent content = new ClipboardContent();
        container.addData("target", linkHandle.getId());
        content.put(DragContainer.AddLink, container);

        Point2D p = new Point2D(
                this.getLayoutX() + linkHandle.getLayoutX() + (linkHandle.getWidth() / 2.0),
                this.getLayoutY() + this.header.getLayoutY() + (linkHandle.getHeight() / 2.0)
        );
        link.setEnd(p);

        event.getDragboard().setContent(content);
        event.setDropCompleted(true);
        event.consume();
    }

    /**
     *
     * @param event
     */
    private void onContextLinkDragOver(DragEvent event){
        event.acceptTransferModes(TransferMode.ANY);

        //Relocate user-draggable link
        if (!link.isVisible())
            link.setVisible(true);

        link.setEnd(new Point2D(event.getX(), event.getY()));

        event.consume();
    }

    /**
     *
     * @param event
     */
    private void onContextLinkDragDropped(DragEvent event){
        this.getParent().setOnDragOver(null);
        this.getParent().setOnDragDropped(null);
        link.setVisible(false);
        ((AnchorPane)this.getParent()).getChildren().remove(0);
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
     * @return The left handle where a link can be attached
     */
    public LinkHandler getLeftLinkHandle() {
        return this.leftLinkHandle;
    }

    /**
     * @return The left handle where a link can be attached
     */
    public LinkHandler getRightLinkHandle() {
        return this.rightLinkHandle;
    }
}
