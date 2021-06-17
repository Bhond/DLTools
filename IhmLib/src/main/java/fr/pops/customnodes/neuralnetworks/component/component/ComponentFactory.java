package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.cst.EnumCst;

public abstract class ComponentFactory {

    /**
     * Build and retrieve a specific type of component
     * @param type The type of the component to create
     * @return The component created
     */
    public static Component get(EnumCst.ComponentTypes type){

        switch(type){
            case INPUT_LOCAL:
                return new LocalInputComponent();

            case LAYER_INPUT:
                return null;

            case LAYER_DENSE:
                return null;

            case LAYER_CONVOLUTION:
                return null;

            case LAYER_POOLING:
                return null;

            case LAYER_LSTM:
                return null;

            default:
                return null;
        }
    }
}
