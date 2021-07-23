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
 * Name: ServerInfoModel.java
 *
 * Description: Class defining the model associated with the server info view
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.viewmodels;

import fr.pops.client.Client;
import fr.pops.controllers.viewcontrollers.NetworkInfoController;
import fr.pops.cst.EnumCst;
import fr.pops.sockets.resquest.PingRequest;

public class NetworkInfoModel extends BaseModel<NetworkInfoController>{

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     */
    public NetworkInfoModel(){
        // Parent
        super();

        // Slow down the model
        this.modelSteppingFamily = EnumCst.ModelSteppingFamily.FAMILY_1_ON_1;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Update model
     */
    @Override
    public void update(double dt)  {

        // Send ping
        PingRequest ping = new PingRequest();
        Client.getInstance().send(ping);

        // Send a get server info request
//        GetNetworkInfoRequest serverInfoRequest = new GetNetworkInfoRequest();
//        Client.getInstance().send(serverInfoRequest);

    }
}
