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
 * Name: BeanManager.java
 *
 * Description: Class defining the bean manager that stores and gold info
 *              about all the created beans
 *
 * Author: Charles MERINO
 *
 * Date: 18/02/2021
 *
 ******************************************************************************/
package fr.pops.beans.bean;

import fr.pops.beanobservable.BeanObservable;
import fr.pops.models.BeanModel;

import java.io.Serializable;
import java.util.HashSet;

public final class BeanManager extends BeanObservable implements Serializable {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    private static final BeanManager instance = new BeanManager();
    private static int currentBeanId = 0;

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Beans
    private HashSet<Bean> beans = new HashSet<>();

    // Models
    private HashSet<BeanModel<? extends Bean>> models = new HashSet<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Singleton
     */
    private BeanManager(){
        // Initialisation
        this.onInit();
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the BeanManager
     */
    private void onInit(){
        // Nothing to do now
    }

    /*****************************************
     *
     * Add
     *
     *****************************************/
    /**
     * Add a bean to the list holding all the beans
     * Fires a property change on the the Hashset to
     * listen to a creation of a bean --> rewrite this part....
     * @param bean The bean to add
     */
    public void addBean(Bean bean){
        this.beans.add(bean);
    }

    /**
     * Add a model to the list holding all the models
     * @param model The model to add. It has been instantiated
     */
    public <T extends Bean> void addModel(BeanModel<T> model){
        this.models.add(model);
    }

    /*****************************************
     *
     * Update beans
     *
     *****************************************/
    /**
     * Method called when a bean has changed
     * and a request should be sent to the server
     * to be displayed on the ihm if required
     */
    public void onBeanUpdate(){

    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * Get an id to give to a new created bean
     * @return An int representing to id the new bean created
     */
    public int getId(){
        currentBeanId++;
        return currentBeanId;
    }

    /**
     * @return The models
     */
    public HashSet<BeanModel<? extends Bean>> getModels() {
        return models;
    }

    /**
     * @return The instance of the bean manager
     */
    public static BeanManager getInstance() {
        return instance;
    }
}
