package fr.pops.clients.mnistclient;

import fr.pops.math.PopsMath;
import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.GetMNISTConfiguration;
import fr.pops.sockets.resquest.GetMNISTImageRequest;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquesthandler.RequestHandler;

import java.util.HashMap;

public class MNISTRequestHandler extends RequestHandler {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public MNISTRequestHandler(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Process the request
     * Not every request has to be processed depending on the client
     *
     * @param request The request to process
     */
    @Override
    protected void process(Request request) {

        switch (request.getType()){
            case GET_MNIST_IMAGE:
                this.handleGetMNISTImageRequest((GetMNISTImageRequest) request);
                break;
            case GET_MNIST_CONFIGURATION:
                this.handleGetMNISTConfigurationRequest((GetMNISTConfiguration) request);
                break;
        }
    }

    /**
     * Select the next operation to perform with the request
     * @param request The request to handle
     * @return The operation to perform next:
     *         Either NONE, WRITE_BACK
     */
    public EnumCst.RequestOperations selectNextOperation(Request request){
        // Init
        EnumCst.RequestOperations operation;

        // Select next operation
        switch (request.getType()){
            case GET_MNIST_IMAGE:
                operation = EnumCst.RequestOperations.WRITE_BACK;
                break;
            case GET_MNIST_CONFIGURATION:
                operation = EnumCst.RequestOperations.WRITE_BACK;
                break;
            default:
                operation = EnumCst.RequestOperations.NONE;
                break;
        }
        return operation;
    }

    /*****************************************
     *
     * Requests handling
     *
     *****************************************/
    /**
     * Handle the GET_MNIST_IMAGE request
     * @param request The request to handle
     */
    private void handleGetMNISTImageRequest(GetMNISTImageRequest request) {
        int idx = request.getIdx();
        BaseNDArray image = Client.getInstance().getImage(idx);
        int label = Client.getInstance().getLabel(idx);
        request.setLabel(label);
        request.setImage(image);
    }

    /**
     * Handle the GET_MNIST_CONFIGURATION request
     * @param request The request to handle
     */
    private void handleGetMNISTConfigurationRequest(GetMNISTConfiguration request) {
        // Retrieve configuration
        //NeuralNetworkConfiguration conf = Client.getInstance().getNeuralNetwork().getNeuralNetworkConfiguration();
        // Fill in request
//        request.setNbLayers(conf.getNbLayers());
//        request.setLearningRate(conf.getLearningRate());
//        request.setRegularisationOn(conf.isRegularisationOn());
//        request.setL1(conf.getL1LearningRate());
//        request.setL2(conf.getL2LearningRate());
        HashMap<Integer, Integer> layers = new HashMap<>();
        for (int i = 0; i < 5; i++){
            layers.put(i, (int) PopsMath.rand(5, 20));
        }

        request.setNbLayers(5);
        request.setLayers(layers);
        request.setLearningRate(69);
        request.setRegularisationOn(true);
        request.setL1(8163);
        request.setL2(13507);
    }
}
