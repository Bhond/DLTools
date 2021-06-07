package fr.pops.clients.mnistclient;

import fr.pops.datareader.MNISTDataset;
import fr.pops.jsonparser.IRecordable;
import fr.pops.math.ArrayUtil;
import fr.pops.math.ndarray.BaseNDArray;
import fr.pops.nn.networks.*;
import fr.pops.sockets.client.BaseClient;
import fr.pops.sockets.cst.EnumCst;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Client extends BaseClient implements IRecordable {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    private static Client instance = new Client();

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private MNISTDataset dataReader;
    private Classifier neuralNetwork;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    private Client(){
        // Nothing to be done
        super(EnumCst.ClientTypes.MNIST,
                new InetSocketAddress("127.0.0.1", 8163),
                new MNISTRequestHandler(),
                new MNISTCommunicationPipeline());

        // Initialize the client
        this.ontInit();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the client
     */
    private void ontInit(){
        // Initialize data set
        try {
            this.dataReader = new MNISTDataset(fr.pops.popscst.cst.EnumCst.RunningMode.TRAINING);
        } catch (IOException ignored){}

    }

    /*****************************************
     *
     * Dispose the client
     *
     *****************************************/
    /**
     * Close connection to the server
     */
    public void dispose(){

    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The instance of the client
     */
    public static Client getInstance() {
        return instance;
    }

    /**
     *
     * @param idx The index of the label in the dataset to retrieve
     * @return The label at the given index in the dataset
     */
    public int getLabel(int idx){
        return ArrayUtil.maskedMax(this.dataReader.getLabel(idx).getData()).getKey();
    }

    /**
     * @param idx The index of the image in the dataset to retrieve
     * @return The image at the given index in the dataset
     */
    public BaseNDArray getImage(int idx){
        return (BaseNDArray) this.dataReader.getSample(idx);
    }

    /**
     * @return The neural network
     */
    public Classifier getNeuralNetwork() {
        return this.neuralNetwork;
    }

    /*****************************************
     *
     * Load / Save
     *
     *****************************************/
    /**
     * Cast the instance of the object into a JSONObject
     */
    @Override
    public JSONObject record() {
        return null;
    }

    /**
     * Load JSONObject
     *
     * @param jsonObject
     */
    @Override
    public void load(JSONObject jsonObject) {

    }
}
