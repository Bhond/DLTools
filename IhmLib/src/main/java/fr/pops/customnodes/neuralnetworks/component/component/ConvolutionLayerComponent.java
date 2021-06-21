package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.cst.EnumCst;
import fr.pops.customnodes.plot.BasePlot;

public class ConvolutionLayerComponent extends Component {

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
    public ConvolutionLayerComponent(){
        super(EnumCst.ComponentTypes.LAYER_CONVOLUTION);
    }
}
