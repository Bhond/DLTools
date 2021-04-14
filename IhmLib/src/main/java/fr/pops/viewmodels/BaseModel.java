package fr.pops.viewmodels;

import fr.pops.controllers.viewcontrollers.BaseController;

public abstract class BaseModel<controllerT extends BaseController<?,?>> {

    protected controllerT controller;

    protected BaseModel(controllerT controller){
        this.controller = controller;
    }

    public abstract void update();


}
