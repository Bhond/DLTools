package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.beans.bean.Bean;
import fr.pops.cst.StrCst;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.util.HashMap;

public class ComponentContainer extends ListView<Component<?>> {

    private Pane componentParent;
    private HashMap<Component<? extends Bean>, Point2D> componentWaitingForBeanCreation;
    private FilteredList<Node> displayedComponent;

    /**
     * Standard ctor
     * Nothing to be done
     */
    private ComponentContainer(){
        // Nothing to be done
    }

    /**
     *  Ctor
     */
    public ComponentContainer(Pane componentParent){
        // Fields
        this.componentParent = componentParent;
        this.componentWaitingForBeanCreation = new HashMap<>();
        this.displayedComponent = new FilteredList<>(componentParent.getChildren(), (child) -> child instanceof Component);

        this.displayedComponent.addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()){
                //If items are removed
                for (Node n : change.getRemoved()) {
                    this.getItems().remove(n);
                }
                //If items are added
                for (Node n : change.getAddedSubList()) {
                    this.getItems().add((Component) n);
                }
            }
        });
        // Component container list view
        this.getStyleClass().add(StrCst.STYLE_CLASS_LISTVIEW);
        this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!observableValue.getValue()){
                this.getSelectionModel().clearSelection();
            }
        });
        this.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Component<?>> call(ListView<Component<?>> nodeListView) {
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
    }

    /**
     * Add a component to the hashmap containing components
     * waiting for bean creation by the server
     * @param component The new component to manage
     * @param position2D The 2D position of the component to create
     * @param <T> The type of bean used by the component
     */
    public <T extends Bean> void manageComponent(Component<T> component, Point2D position2D){

        // TODO: add this below
//                        component.focusedProperty().addListener((observableValue, aFocused, isFocused) -> {
//                            if (isFocused){
//                                this.displayBeanProperties(component.getBeanProperties());
//                                if (!this.componentProperties.isVisible()) this.componentProperties.setVisible(true);
//                            }
//                        });
//                        this.centerPane.getChildren().add(component);
//                        component.relocateToPoint(new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32));
//                        component.requestFocus();

        // Wait for creation of the bean from the server
        this.componentWaitingForBeanCreation.put(component, position2D);
    }

    /**
     * Display the component on its parent
     * @param componentId The component's id to display
     */
    public void displayComponent(String componentId){
        // Find the component among the ones waiting for the server creation
        Component<? extends Bean> component = findWaitingComponent(componentId);

        // Component might be null if an error occurred
        if (component != null){
            // Retrieve its coordinates
            Point2D position2D = this.componentWaitingForBeanCreation.get(component);

            // Remove it from the hashmap
            this.componentWaitingForBeanCreation.remove(component);

            // Add it to the displayed components
            this.displayedComponent.add(component);

            // Add it to parent
            this.componentParent.getChildren().add(component);

            // Relocate it
            component.relocateToPoint(position2D);
        }
    }

    /**
     * Remove component
     * @param component The component to remove
     */
    public void removeComponent(Component<?> component){
        this.displayedComponent.remove(component);
        this.componentParent.getChildren().remove(component);
    }


    /**
     * Find the component waiting for bean creation by the server
     * @param componentId The component's id to retrieve
     * @return The component corresponding to the id if exists, else null
     */
    private Component<? extends Bean> findWaitingComponent(String componentId){
        Component<? extends Bean> result = null;
        for(Component<? extends Bean> component : this.componentWaitingForBeanCreation.keySet()){
            if (component.getId().equals(componentId)){
                result = component;
                break;
            }
        }
        return result;
    }
}
