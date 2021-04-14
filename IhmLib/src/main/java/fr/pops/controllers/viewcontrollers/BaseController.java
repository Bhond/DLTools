package fr.pops.controllers.viewcontrollers;

import fr.pops.controllers.controllermanager.ControllerManager;
import fr.pops.viewmodels.BaseModel;
import fr.pops.views.BaseView;

public abstract class BaseController<viewT extends BaseView<?,?>, modelT extends BaseModel<?>> {

    protected viewT view;
    protected modelT model;

    /**
     * Standard ctor, nothing to be done
     */
    protected BaseController(){
        ControllerManager.getInstance().addController(this);
    }

    protected BaseController(viewT view){
        ControllerManager.getInstance().addController(this);
        this.view = view;
    }

    protected void onInit(modelT model){
        this.model = model;
    }

    public viewT getView() {
        return this.view;
    }

    public modelT getModel() {
        return this.model;
    }
}
