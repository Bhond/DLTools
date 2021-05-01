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
 * Description: Class defining Pops
 *
 * Author: Charles MERINO
 *
 * Date: 14/03/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.sockets.client.BaseClient;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.stockinfo.StockInfo;

import java.net.InetSocketAddress;

public class Client extends BaseClient {

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
    public Client(){
        // Nothing to be done
        super(EnumCst.ClientTypes.STOCK,
                new InetSocketAddress("127.0.0.1", 8163),
                new StockRequestHandler(),
                new StockCommunicationPipeline());

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
    private void ontInit(){ }

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
}
