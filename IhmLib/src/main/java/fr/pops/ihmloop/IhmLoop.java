package fr.pops.ihmloop;

import fr.pops.controllers.controllermanager.ControllerManager;
import fr.pops.controllers.viewcontrollers.BaseController;
import fr.pops.cst.IntCst;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class IhmLoop {

    private boolean isRunning = false;
    private final double UPDATE_CAP = 1.0d / IntCst.MODEL_LOOP_MAX_FPS;

    public IhmLoop(){

    }

    public void run() {

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                this.updateModels();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void updateModels(){
        this.isRunning = true;
        while(this.isRunning){
            for (BaseController<?, ?> controller : ControllerManager.getInstance().getControllers()) {
                controller.getModel().update();
            }
        }
    }
}
