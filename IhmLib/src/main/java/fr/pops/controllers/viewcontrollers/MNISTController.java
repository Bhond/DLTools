package fr.pops.controllers.viewcontrollers;

import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.viewmodels.MNISTModel;
import fr.pops.views.mnist.MNISTView;

import java.util.HashMap;

public class MNISTController extends BaseController<MNISTView, MNISTModel> {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private MNISTController(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param view The view to control
     */
    public MNISTController(MNISTView view){
        // Parent
        super(view, new MNISTModel());
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Display the label of the image on the view
     * @param label The label to display
     */
    public void setLabel(int label){
        this.view.updateLabel(label);
    }

    /**
     * Display the image on the view
     * @param image The image to display
     */
    public void setImage(BaseNDArray image){
        this.view.updateImage(image);
    }

    /**
     * Display the neural network configuration
     * @param nbLayers The nb of layers
     * @param learningRate The learning rate
     * @param regularisationOn True is the regularisation is activated
     * @param l1 The l1 learning rate
     * @param l2 The l2 learning rate
     */
    public void setConfiguration(int nbLayers, HashMap<Integer, Integer> layers, double learningRate, boolean regularisationOn, double l1, double l2){
        this.view.updateConfiguration(nbLayers, layers, learningRate, regularisationOn, l1, l2);
    }
}