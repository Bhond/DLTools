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
 * Name: Client.java
 *
 * Description: Class defining the client used by HOLOPOPS.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.sockets.client.VertxBaseClient;
import fr.pops.sockets.cst.EnumCst;

public class Client extends VertxBaseClient {

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public Client(){
        super(EnumCst.ClientTypes.HOLOPOPS, new HoloPopsRequestHandler());
    }

}
