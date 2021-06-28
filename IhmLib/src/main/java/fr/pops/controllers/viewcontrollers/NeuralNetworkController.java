package fr.pops.controllers.viewcontrollers;

import fr.pops.viewmodels.NeuralNetworkModel;
import fr.pops.views.neuralnetwork.NeuralNetworkView;

public class NeuralNetworkController extends BaseController<NeuralNetworkView, NeuralNetworkModel> {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private NeuralNetworkController(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param view The view to control
     */
    public NeuralNetworkController(NeuralNetworkView view){
        // Parent
        super(view, new NeuralNetworkModel());
    }

    /*****************************************
     *
     * Controls
     *
     *****************************************/


    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Display the component corresponding to the bean id received from the server
     * @param componentId The id of the component to display
     * @param beanId The bean id created by the server
     */
    public void displayNeuralNetworkComponents(String componentId, int beanId){
        this.view.displayNeuralNetworkComponents(componentId, beanId);
    }

    /**
     * Close component after deletion by the server
     * @param componentId The component's id to delete
     */
    public void closeNeuralNetworkComponent(String componentId){
        this.view.closeNeuralNetworkComponent(componentId);
    }

    /**
     * Update the property of the given bean
     * @param beanId The id of the bean to update
     * @param propertyName The property's name
     * @param newValue The property's new value
     * @param <T> The type of the value to update
     */
    public <T> void updateBeanProperty(int beanId, String propertyName, T newValue){
        this.view.updateBeanProperty(beanId, propertyName, newValue);
    }
}
