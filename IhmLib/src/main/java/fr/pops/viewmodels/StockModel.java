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
 * Name: StockModel.java
 *
 * Description: Class defining the model associated with the stock view
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.viewmodels;

import fr.pops.client.Client;
import fr.pops.controllers.viewcontrollers.StockController;
import fr.pops.cst.EnumCst;
import fr.pops.sockets.resquest.GetCurrentStockInfoRequest;
import fr.pops.views.stock.QuoteInfo;

import java.util.HashSet;

public class StockModel extends BaseModel<StockController> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private HashSet<QuoteInfo> infos;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public StockModel() {
        // Parent
        super();
        // Change stepping family
        this.modelSteppingFamily = EnumCst.ModelSteppingFamily.FAMILY_1_ON_100;

        // Create new list
        this.infos = new HashSet<>();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    @Override
    public void update(double dt) {
        // Update all quote infos by sending convenient requests
        for (QuoteInfo info : infos){
            GetCurrentStockInfoRequest request = new GetCurrentStockInfoRequest(info.getSymbol());
            Client.getInstance().send(request);
        }
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Add info to update by sending requests
     * @param info The info to add
     */
    public void addQuoteInfo(QuoteInfo info){
        this.infos.add(info);
    }

    /**
     * Remove info from the update list
     * @param info The info to remove
     */
    public void removeQuoteInfo(QuoteInfo info){
        this.infos.remove(info);
    }
}
