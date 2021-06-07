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
 * Name: ServerInfoController.java
 *
 * Description: Class describing the parameters of the server info controller
 *
 * Author: Charles MERINO
 *
 * Date: 20/04/2021
 *
 ******************************************************************************/
package fr.pops.controllers.viewcontrollers;

import fr.pops.sockets.cst.EnumCst;
import fr.pops.viewmodels.NetworkInfoModel;
import fr.pops.views.networkinfo.NetworkInfoView;

import java.util.List;

public class NetworkInfoController extends BaseController<NetworkInfoView, NetworkInfoModel>{

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private NetworkInfoController(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param view The view to control
     */
    public NetworkInfoController(NetworkInfoView view){
        // Parent
        super(view, new NetworkInfoModel());
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The connected clients
     */
    public List<EnumCst.ClientTypes> getConnectedClients(){
        return this.view.getConnectedClients();
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Display the new ping value on the ServerInfoView
     * @param value The new ping value to display
     */
    public void setPingValue(double value){
        this.view.displayPingValue(value);
    }

    /**
     * Display the new frequency value on the ServerInfoView
     * @param value The new frequency value to display
     */
    public void setFrequency(double value){
        this.view.displayFrequency(value);
    }

    /**
     * Display the connected clients on the ServerInfoView
     * @param clients The connected clients
     */
    public void setConnectedClients(List<EnumCst.ClientTypes> clients){
        this.view.displayConnectedClients(clients);
    }
}
