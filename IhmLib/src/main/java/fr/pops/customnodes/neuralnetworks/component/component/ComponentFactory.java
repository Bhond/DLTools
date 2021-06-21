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
 * Name: ComponentFactory.java
 *
 * Description: Abstract class defining the factory for components
 *
 * Author: Charles MERINO
 *
 * Date: 21/06/2021
 *
 ******************************************************************************/
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

            case LAYER_DENSE:
                return new DenseLayerComponent();

            case LAYER_CONVOLUTION:
                return new ConvolutionLayerComponent();

            case LAYER_POOLING:
                return new PoolingLayerComponent();

            case LAYER_LSTM:
                return new LSTMLayerComponent();

            default:
                return null;
        }
    }
}
