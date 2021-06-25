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
 * Name: DenseLayerComponent.java
 *
 * Description: Class defining the component used to display the dense layers
 *
 * Author: Charles MERINO
 *
 * Date: 21/06/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.beans.test.TestBean;
import fr.pops.cst.EnumCst;
import fr.pops.customnodes.plot.LinePlot;

public class DenseLayerComponent extends Component<TestBean> {

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
    public DenseLayerComponent(){
        super(EnumCst.ComponentTypes.LAYER_DENSE, new TestBean());



    }
}
