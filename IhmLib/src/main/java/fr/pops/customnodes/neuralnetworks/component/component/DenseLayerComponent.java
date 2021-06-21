package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.cst.EnumCst;
import fr.pops.customnodes.plot.BasePlot;

public class DenseLayerComponent extends Component {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String path;
    private BasePlot activationPlot;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public DenseLayerComponent(){
        super(EnumCst.ComponentTypes.LAYER_DENSE);
    }
}
