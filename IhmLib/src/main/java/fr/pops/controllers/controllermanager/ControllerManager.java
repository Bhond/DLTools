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
 * Name: ControllerManager.java
 *
 * Description: Class used to managed all the controllers that have been created
 *              along with the views displayed by the GUI
 *              Singleton
 *
 * Author: Charles MERINO
 *
 * Date: 14/04/2021
 *
 ******************************************************************************/
package fr.pops.controllers.controllermanager;

import fr.pops.controllers.viewcontrollers.BaseController;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerManager {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    private static ControllerManager instance = new ControllerManager();

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private HashSet<BaseController<?,?>> controllers = new HashSet<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private ControllerManager(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Add a controller to the manager
     * @param controller The controller to add to the manager
     */
    public void addController(BaseController<?,?> controller){
        this.controllers.add(controller);
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * @return The controllers currently managed
     */
    public HashSet<BaseController<?, ?>> getControllers() {
        return this.controllers;
    }

    /**
     * Get all controllers matching the input class
     * @param controllerClass The class of the controllers to look for
     * @return The controllers matching the class given in the inputs
     */
    public List<BaseController<?,?>> getAll(Class<BaseController<?,?>> controllerClass){
        return controllers.stream().filter(c -> c.getClass().equals(controllerClass)).collect(Collectors.toList());
    }

    /**
     * Get the first controller matching the input class
     * @param controllerClass The class of the controller to look for
     * @return The first controller matching the class given in the inputs
     */
    public <controllerT extends BaseController<?,?>> BaseController<?,?> getFirst(Class<controllerT> controllerClass){
        List<BaseController<?,?>> result = controllers.stream().filter(c -> c.getClass().equals(controllerClass)).collect(Collectors.toList());
        return result.size() > 0 ? result.get(0) : null;
    }

    /**
     * Singleton
     * @return The instance of the controller manager
     */
    public static ControllerManager getInstance() {
        return instance;
    }
}
