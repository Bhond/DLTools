package fr.pops.controllers.viewcontrollers;

import fr.pops.viewmodels.MNISTModel;
import fr.pops.views.mnist.MNISTView;

public class MNISTController extends BaseController<MNISTView, MNISTModel> {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * MainController is a singleton
     */
    private MNISTController(){
        // Nothing to be done
    }
}
