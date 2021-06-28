package fr.pops.clients.beanclient;

import fr.pops.beans.bean.Bean;
import fr.pops.beans.bean.BeanCreator;
import fr.pops.beans.bean.BeanManager;
import fr.pops.beans.beanloop.BeanLoop;
import fr.pops.jsonparser.IRecordable;
import fr.pops.sockets.client.BaseClient;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.beanrequests.UpdateBeanPropertyRequest;
import org.json.JSONObject;

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
        super(EnumCst.ClientTypes.BEAN,
                new InetSocketAddress("127.0.0.1", 8163),
                new BeanRequestHandler(),
                new BeanCommunicationPipeline());

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

        // Initialize the bean manager
        BeanManager.getInstance().setOnPropertyUpdated((p) -> this.send(new UpdateBeanPropertyRequest<>(p)));
        BeanLoop.getInstance().start();
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
    /**
     * Create bean
     * @param beanTypeId The bean type id use to create
     *                   the desired type of bean
     * @return The created bean
     */
    public Bean onBeanCreationEvent(String beanTypeId){
        return BeanCreator.getInstance().createBeanByReflection(beanTypeId);
    }

    /**
     * Create bean
     * @param beanId The bean's id to removed
     * @return True if the given been has been successfully removed
     */
    public boolean onBeanDeletionEvent(int beanId){
        return BeanManager.getInstance().removeBean(beanId);
    }

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
