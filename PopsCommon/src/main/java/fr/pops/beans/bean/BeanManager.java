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

import fr.pops.beans.beanobservable.BeanObservable;
import fr.pops.beans.beanmodels.BeanModel;
import fr.pops.beans.properties.Property;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public final class BeanManager extends BeanObservable implements Serializable {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    private static final BeanManager instance = new BeanManager();
    private static int currentBeanId = -1;

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Beans
    private HashSet<Bean> beans = new HashSet<>();
    private List<BeanContainer<Bean>> containers = new LinkedList<>();
    private Consumer<Property<?>> onPropertyUpdated;

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
     * Fires a property change on the containers which
     * listen to the creation of a bean
     * @param bean The bean to add
     */
    public <T extends Bean> void addBean(T bean){
        this.beans.add(bean);

        // Add the bean to all of the containers used for this type of bean
        for (BeanContainer<Bean> container : this.containers){
            if (bean.getClass() == container.getBeanClass()){
                container.addBean(bean);
            }
        }
    }

    /**
     * Add a model to the list holding all the models
     * @param model The model to add. It has been instantiated
     */
    public <T extends Bean> void addModel(BeanModel<T> model){
        this.models.add(model);
    }

    /**
     * Add container which triggers events when modified
     * @param container The container to add
     * @param <T> The type of bean managed
     */
    public <T extends Bean> void addContainer(BeanContainer<T> container){
        // Fill in with the already existing pertinent beans to the container
        List<? extends Bean> pertinentBeans = this.beans.stream().filter((b) -> b.getClass() == container.getBeanClass()).collect(Collectors.toList());
        container.addAllBean((List<T>) pertinentBeans);

        // Add container to the managed ones
        this.containers.add((BeanContainer<Bean>) container);
    }

    /*****************************************
     *
     * Remove
     *
     *****************************************/
    /**
     * Remove a bean from the list holding all the beans
     * Fires a property change on the containers which
     * listen to the  deletion of a bean
     * @param bean The bean to remove
     */
    public <T extends Bean> void removeBean(T bean){
        // Remove bean from the managed ones
        this.beans.remove(bean);

        // Remove the bean to all of the containers used for this type of bean
        for (BeanContainer<Bean> container : this.containers){
            if (bean.getClass() == container.getBeanClass()){
                container.removeBean(bean);
            }
        }
    }

    /**
     * Remove a bean to the list holding all the beans
     * Fires a property change on the containers which
     * listen to the  deletion of a bean
     * @param beanId The beanId of the bean to remove
     */
    public boolean removeBean(int beanId){
        // Remove bean from the managed ones
        List<Bean> beansToRemove = this.beans.stream().filter(((b) -> b.getId() == beanId)).collect(Collectors.toList());
        for (Bean b : beansToRemove){
            this.removeBean(b);
        }
        return beansToRemove.size() > 0;
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
    public void onPropertyUpdate(Property<?> property){
        if (this.onPropertyUpdated != null){
            this.onPropertyUpdated.accept(property);
        }
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

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the action to perform when a property has been updated
     * @param onPropertyUpdated The action to perform
     */
    public void setOnPropertyUpdated(Consumer<Property<?>> onPropertyUpdated) {
        this.onPropertyUpdated = onPropertyUpdated;
    }
}
