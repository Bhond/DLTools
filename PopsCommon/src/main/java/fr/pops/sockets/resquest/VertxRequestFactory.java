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
 * Name: RequestFactory.java
 *
 * Description: Class using the Factory pattern to create on the fly request
 *              depending on its type encode directly in its raw representation
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.sockets.resquest;

import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.beanrequests.CreateBeanRequest;
import fr.pops.sockets.resquest.beanrequests.DeleteBeanRequest;
import fr.pops.sockets.resquest.beanrequests.UpdateBeanPropertyRequest;
import io.vertx.core.json.JsonObject;

public abstract class VertxRequestFactory {

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * Build request from the given Json representation
     * @param request The representation of the request
     *                   in Json format
     * @return A request filled with the right information and casted to the proper class
     */
    public static Request getRequest(JsonObject request){

        // Retrieve the request id
        // Retrieve type
        EnumCst.RequestTypes requestType = Request.getType(request);
        // Retrieve the request's body
        JsonObject requestBody = Request.getBody(request);

        // Create the correct type of request
        switch (requestType){
            case AUTHENTICATE:
                return new AuthenticateRequest(requestBody);
            case DISCONNECTION:
                return new DisconnectionRequest(requestBody);
            case PING:
                return new PingRequest(requestBody);
            case GET_NETWORK_INFO:
                return new GetNetworkInfoRequest(requestBody);
            case GET_CURRENT_STOCK_INFO:
                return new GetCurrentStockInfoRequest(requestBody);
            case GET_MNIST_IMAGE:
                return new GetMNISTImageRequest(requestBody);
            case GET_MNIST_CONFIGURATION:
                return new GetMNISTConfiguration(requestBody);
            case CREATE_BEAN:
                return new CreateBeanRequest(requestBody);
            case DELETE_BEAN:
                return new DeleteBeanRequest(requestBody);
            case UPDATE_BEAN_PROPERTY:
                return new UpdateBeanPropertyRequest<>(requestBody);
            default:
                return null;
        }

    }







}
