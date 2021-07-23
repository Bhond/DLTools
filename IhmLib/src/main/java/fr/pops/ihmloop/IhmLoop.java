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
 * Name: IhmLoop.java
 *
 * Description: Class defining the loop updating the models' views
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.ihmloop;

import fr.pops.controllers.controllermanager.ControllerManager;
import fr.pops.controllers.viewcontrollers.BaseController;
import fr.pops.cst.EnumCst;
import fr.pops.math.PopsMath;
import fr.pops.viewmodels.BaseModel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class IhmLoop {

    /*
     *
     * TODO: Correct this class
     *       By updating the fps rate
     *       Deal with the closing method
     *
     */

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private ScheduledFuture<?> service;
    private final long initialDelay = 0;
    private final double frequency = 100;
    private final long timeDelay = PopsMath.convertDoubleToLong(1 / this.frequency, 1E-2);
    private double runningTime = 0.0d;
    private double t0 = 0.0d;
    private long stepCount = 0;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public IhmLoop(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Run ihm loop
     * TODO: include the frequency in the computation of dt
     *       Clean the time management overall
     *       Works for now but not clean...
     */
    public void run() {
        AtomicReference<Double> dt = new AtomicReference<>(0.0d);
        this.t0 = System.currentTimeMillis();
        this.service = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                this.updateModels(dt.get());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            dt.set(dt.accumulateAndGet(this.frequency, Double::sum));
            this.runningTime = System.currentTimeMillis() - this.t0;
            this.stepCount++;
        }, this.initialDelay, this.timeDelay, TimeUnit.MILLISECONDS);
    }

    /**
     * Update the models
     * @param dt Elapsed time during
     */
    private void updateModels(double dt){
        for (BaseController<?, ?> controller : ControllerManager.getInstance().getControllers()) {
            /**
             * Update model if needed depending
             * on the model stepping family
             */
            BaseModel<?> model = controller.getModel();
            if (model != null && needUpdate(model.getModelSteppingFamily())){
                controller.getModel().update(dt);
            }
        }
    }

    /**
     * Check whether the model needs an update
     * depending on its stepping family or not
     * @param modelSteppingFamily The model stepping family
     * @return True if the model needs an update
     */
    private boolean needUpdate(EnumCst.ModelSteppingFamily modelSteppingFamily) {
        boolean needsUpdate = true;
        switch (modelSteppingFamily){
            case FAMILY_1_ON_1:
                break;
            case FAMILY_1_ON_10:
                needsUpdate = this.stepCount % 10 == 0;
                break;
            case FAMILY_1_ON_20:
                needsUpdate = this.stepCount % 20 == 0;
                break;
            case FAMILY_1_ON_100:
                needsUpdate = this.stepCount % 100 == 0;
                break;
            case FAMILY_1_ON_500:
                needsUpdate = this.stepCount % 500 == 0;
                break;
            case FAMILY_1_ON_1000:
                needsUpdate = this.stepCount % 1000 == 0;
                break;
            case FAMILY_1_ON_5000:
                needsUpdate = this.stepCount % 5000 == 0;
                break;
        }
        return needsUpdate;
    }

    /*****************************************
     *
     * Connection handling
     *
     *****************************************/
    /**
     * Cancel loop when the connection is closed
     */
    public void onConnectionClosed(){
        // Service
        this.service.cancel(true);
    }
}
