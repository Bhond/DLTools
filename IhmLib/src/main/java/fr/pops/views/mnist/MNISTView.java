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
 * Name: MNISTView.java
 *
 * Description: Class defining the MNIST view used to display
 *              the neural network training and guessing of MNIST dataset
 *
 * Author: Charles MERINO
 *
 * Date: 16/05/2021
 *
 ******************************************************************************/
package fr.pops.views.mnist;

import fr.pops.client.Client;
import fr.pops.controllers.viewcontrollers.MNISTController;
import fr.pops.cst.EnumCst;
import fr.pops.cst.StrCst;
import fr.pops.customnodes.heximage.HexImage;
import fr.pops.customnodes.labelvaluepair.LabelValuePair;
import fr.pops.customnodes.neuralnetworks.networks.NeuralNetwork;
import fr.pops.customnodes.neuralnetworks.networks.NeuralNetworkFactory;
import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.sockets.resquest.GetMNISTImageRequest;
import fr.pops.utils.Utils;
import fr.pops.views.base.BaseView;
import fr.pops.views.updater.Updater;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MNISTView extends BaseView<MNISTController> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
     // Content box
     private VBox inputOutputBox;
     private HBox contentBox;

     // Image
    private HexImage image;
    private LabelValuePair labelPair;
    private LabelValuePair guessPair;

    //Neural network
    private NeuralNetwork neuralNetwork;

    private Button nextImageButton;
    private int counter = 0;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private MNISTView(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param stage Stage of the view
     */
    public MNISTView(Stage stage){
        // Parent
        super(stage, StrCst.NAME_MNIST_VIEW, EnumCst.Views.MNIST);

        // Initialisation
        this.onInit();
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the view
     */
    @Override
    protected void onInit() {
        // Controller
        this.controller = new MNISTController(this);

        // Root
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_MNIST_VIEW_CSS));

        // Content pane
        this.configureContentPane();
    }

    /**
     * Configure the rootPane
     */
    @Override
    protected void configureContentPane() {
        // Input
        this.inputOutputBox = new VBox();
        this.inputOutputBox.setSpacing(10);

        // Content box
        this.contentBox = new HBox();
        this.contentBox.setSpacing(50);
        HBox.setHgrow(this.contentBox, Priority.ALWAYS);
        VBox.setVgrow(this.contentBox, Priority.ALWAYS);

        // Image
        this.configureImage();

        // Input / output
        this.labelPair = new LabelValuePair("Label:");
        HBox.setHgrow(this.labelPair, Priority.ALWAYS);
        this.guessPair = new LabelValuePair("Guess:");
        HBox.setHgrow(this.guessPair, Priority.ALWAYS);
        this.nextImageButton = new Button("Next image");
        nextImageButton.setOnAction(actionEvent -> {
            Client.getInstance().send(new GetMNISTImageRequest(counter));
            counter++;
        });


        // Neural network
        this.configureNeuralNetwork();

        // Build hierarchy
        this.buildHierarchy();
    }

    /**
     * Build the hierarchy of the view
     */
    @Override
    protected void buildHierarchy() {
        // Input box
        this.inputOutputBox.getChildren().addAll(this.image, this.labelPair, this.guessPair, this.nextImageButton);

        // Content box
        this.contentBox.getChildren().addAll(this.inputOutputBox, this.neuralNetwork);

        // Root
        this.rootLayout.getChildren().add(this.contentBox);
    }

    /**
     * Configure the image
     */
    private void configureImage(){
        this.image = new HexImage(new BaseNDArray.BaseNDArrayBuilder().zeros(28,28,1).build());
    }

    /**
     * Configure the neural network
     */
    private void configureNeuralNetwork(){
        // Build network
        this.neuralNetwork = NeuralNetworkFactory.get(fr.pops.commoncst.EnumCst.NeuralNetworkTypes.CLASSIFIER);
        if (this.neuralNetwork != null){
            this.neuralNetwork.build();
            HBox.setHgrow(this.neuralNetwork, Priority.ALWAYS);
        }
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Update the image
     * @param label The image to display
     */
    public void updateLabel(int label){
        Updater.update(this.labelPair, String.valueOf(label));
    }

    /**
     * Update the image
     * @param image The image to display
     */
    public void updateImage(BaseNDArray image){
        Updater.update(this.image, image);
    }

    /**
     * Update the configuration
     */
    public void updateConfiguration(int nbLayers, HashMap<Integer, Integer> layers, double learningRate, boolean regularisationOn, double l1, double l2){
        Updater.update(this.neuralNetwork, layers, nbLayers, learningRate, regularisationOn, l1, l2);
    }

    /*****************************************
     *
     * Load / Save
     *
     *****************************************/
    /**
     * Read the fields stored in the json
     *
     * @param fields The fields to read
     */
    @Override
    protected void readFields(Map fields) {

    }
}
