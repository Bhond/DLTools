package fr.pops.customnodes.neuralnetworks.component.component;

import fr.pops.cst.EnumCst;
import fr.pops.customnodes.plot.BasePlot;

public class LocalInputComponent extends Component {

    private String path;
    private BasePlot activationPlot;

    public LocalInputComponent(){
        super(EnumCst.ComponentTypes.INPUT_LOCAL);
    }
}
