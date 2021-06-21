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
 * Name: PoolingLayerComponent.java
 *
 * Description: Class defining the component used to display the pooling layers
 *
 * Author: Charles MERINO
 *
 * Date: 21/06/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.cst.EnumCst;
import fr.pops.customnodes.plot.LinePlot;

public class PoolingLayerComponent extends Component {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String path;
    private LinePlot activationPlot;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public PoolingLayerComponent(){
        super(EnumCst.ComponentTypes.LAYER_POOLING);
    }
}
