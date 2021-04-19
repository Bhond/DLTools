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

import fr.pops.controllers.viewcontrollers.StockController;

public class StockModel extends BaseModel<StockController> {

    /**
     * Standard ctor
     */
    public StockModel() {
        super();
    }

    @Override
    public void update(double dt) {

    }
}
