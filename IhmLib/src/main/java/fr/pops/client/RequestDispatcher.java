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
 * Name: RequestDispatcher.java
 *
 * Description: Abstract class used to dispatch incoming requests
 *              across the controllers
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.controllers.controllermanager.ControllerManager;
import fr.pops.controllers.viewcontrollers.ServerInfoController;
import fr.pops.sockets.resquest.PingRequest;
import fr.pops.sockets.resquest.Request;

public abstract class RequestDispatcher {

    /**
     * Dispatch the request across the controllers
     * @param request The request to dispatch
     */
    public static void dispatch(Request request){

        // Dispatch the request in the controllers
        switch (request.getType()){
            case PING:
                ServerInfoController controller = (ServerInfoController) ControllerManager.getInstance().getFirst(ServerInfoController.class);
                if (controller != null){
                    controller.setPingValue(((PingRequest) request).getResponseDelay());
                }
                break;
        }

    }

}
