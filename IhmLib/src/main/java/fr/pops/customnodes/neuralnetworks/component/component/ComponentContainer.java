package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.beans.bean.Bean;
import fr.pops.beans.test.TestBean;
import fr.pops.client.Client;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.beanproperties.PropertyNode;
import fr.pops.sockets.resquest.beanrequests.CreateBeanRequest;
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
import java.util.List;
import java.util.stream.Collectors;

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
        this.displayedComponent = new FilteredList<>(componentParent.getChildren(), (child) -> child instanceof Component<?>);

        this.displayedComponent.addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()){
                //If items are removed
                for (Node n : change.getRemoved()) {
                    this.getItems().remove(n);
                }
                //If items are added
                for (Node n : change.getAddedSubList()) {
                    this.getItems().add((Component<?>) n);
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
                    protected void updateItem(Component<?> node, boolean isEmpty) {
                        super.updateItem(node, isEmpty);
                        if (node != null) {
                            setText(node.getTitle());
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
        // Wait for creation of the bean from the server
        this.componentWaitingForBeanCreation.put(component, position2D);
        Client.getInstance().send(new CreateBeanRequest(component.getId(), TestBean.beanTypeId));
    }

    /**
     * Display the component on its parent
     * @param componentId The component's id to display
     */
    public void displayComponent(String componentId, int beanId){
        // Find the component among the ones waiting for the server creation
        Component<? extends Bean> component = findWaitingComponent(componentId);

        // Component might be null if an error occurred
        if (component != null){
            // Retrieve its coordinates
            Point2D position2D = this.componentWaitingForBeanCreation.get(component);

            // Set the bean id
            component.getBean().setId(beanId);

            // Change title
            component.setTitle(component.getType() + "_" + beanId);

            // Remove it from the hashmap
            this.componentWaitingForBeanCreation.remove(component);

            // Add it to parent
            this.componentParent.getChildren().add(component);

            // Relocate it
            component.relocateToPoint(position2D);

            // Request focus
            component.requestFocus();
        }
    }

    /**
     * Remove component
     * @param componentId The component to remove
     */
    public void removeComponent(String componentId){
        List<Node> componentsToRemove = this.displayedComponent.stream().filter((n) -> n.getId().equals(componentId)).collect(Collectors.toList());
        this.componentParent.getChildren().removeAll(componentsToRemove);
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

    /**
     * Update the property of the given bean
     * @param beanId The id of the bean to update
     * @param propertyName The property's name
     * @param newValue The property's new value
     * @param <T> The type of the value to update
     */
    @SuppressWarnings("unchecked")
    public <T> void updateBeanProperty(int beanId, String propertyName, T newValue){
        // Find the corresponding component holding the bean to update
        List<Node> componentToUpdate = this.displayedComponent.stream().filter((n) -> ((Component<?>)n).getBean().getId() == beanId).collect(Collectors.toList());
        for (Node n : componentToUpdate){
            Component<?> component = (Component<?>)n;
//            for (Property<?> property : component.getBean().getProperties()){
//                if (property.getName().equals(propertyName)){
//                    ((Property<T>)property).setValue(newValue);
//                }
//            }
            for (PropertyNode<?> property : component.getBeanProperties().getPropertyNodes()){
                if (property.getName().equals(propertyName)){
                    ((PropertyNode<T>)property).setValue(newValue);
                    System.out.println("New value: " + newValue + " for " + propertyName);
                }
            }
        }
    }
}
