package fr.pops.controllers.controllermanager;

import fr.pops.controllers.viewcontrollers.BaseController;
import fr.pops.viewmodels.BaseModel;
import fr.pops.views.BaseView;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerManager {

    private static ControllerManager instance = new ControllerManager();

    private HashSet<BaseController<?,?>> controllers = new HashSet<>();

    private ControllerManager(){

    }

    public HashSet<BaseController<?, ?>> getControllers() {
        return this.controllers;
    }

    public <viewT extends BaseView<?,?>, modelT extends BaseModel<?>> void addController(BaseController<viewT, modelT> controller){
        this.controllers.add(controller);
    }

    public List<BaseController<?,?>> getAll(Class<BaseController<?,?>> controllerClass){
        return controllers.stream().filter(c -> c.getClass().equals(controllerClass)).collect(Collectors.toList());
    }

    public <controllerT extends BaseController<?,?>> BaseController<?,?> getFirst(Class<controllerT> controllerClass){
        List<BaseController<?,?>> result = controllers.stream().filter(c -> c.getClass().equals(controllerClass)).collect(Collectors.toList());
        return result.size() > 0 ? result.get(0) : null;
    }

    public static ControllerManager getInstance() {
        return instance;
    }
}
