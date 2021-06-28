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
 * Name: BeanContainer.java
 *
 * Description: Class defining a bean container that fires events when
 *              a bean of the given type has been added or removed
 *
 * Author: Charles MERINO
 *
 * Date: 25/06/2021
 *
 ******************************************************************************/
package fr.pops.beans.bean;

import fr.pops.beans.beanobservable.BeanObservable;

import java.beans.PropertyChangeEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class BeanContainer<T extends Bean> extends BeanObservable {

    /*****************************************
     *
     * Static Attributes
     *
     *****************************************/
    private static final String BEAN_ADDED_LISTENER_TAG = "onBeanAddedListenerTag";
    private static final String BEAN_REMOVED_LISTENER_TAG = "onBeanRemovedListenerTag";

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private List<T> beans;
    private Class<T> beanClass;
    private Consumer<T> onBeanAdded;
    private Consumer<T> onBeanRemoved;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param onBeanAdded The custom consumer to call when a bean has bean added
     * @param onBeanRemoved The custom consumer to call when a bean has bean removed
     */
    public BeanContainer(Class<T> beanClass, Consumer<T> onBeanAdded, Consumer<T> onBeanRemoved){
        // Fields
        this.beans = new LinkedList<>();
        this.beanClass = beanClass;
        this.onBeanAdded = onBeanAdded;
        this.onBeanRemoved = onBeanRemoved;

        // Listener handling
        this.support.addPropertyChangeListener(this::onListenerChanged);

        // Add the container to the manager
        BeanManager.getInstance().addContainer(this);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Add bean to the container's list
     * Triggers an event to inform that a bean has been added
     * @param bean The bean to add
     */
    public void addBean(T bean){
        this.beans.add(bean);
        // Old value is not interesting, hence null
        this.support.firePropertyChange(BEAN_ADDED_LISTENER_TAG, null, bean);
    }

    /**
     * Add bean to the container's list
     * Triggers an event to inform that a bean has been added
     * @param bean The bean to add
     */
    public void addAllBean(List<T> bean){
        this.beans.addAll(bean);
        // Old value is not interesting, hence null
        this.support.firePropertyChange(BEAN_ADDED_LISTENER_TAG, null, bean);
    }

    /**
     * Add bean to the container's list
     * Triggers an event to inform that a bean has been removed
     * @param bean The bean to remove
     */
    public void removeBean(T bean){
        this.beans.remove(bean);
        // Old value is not interesting, hence null
        this.support.firePropertyChange(BEAN_REMOVED_LISTENER_TAG, null, bean);
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    /**
     * Update the container when an change event occurred
     * @param evt The event occurred
     */
    private void onListenerChanged(PropertyChangeEvent evt){
        // Check which listener has bean triggered
        if (evt.getPropertyName().equals(BEAN_ADDED_LISTENER_TAG)){
            this.beanAdded((T) evt.getNewValue());
        } else if (evt.getPropertyName().equals(BEAN_REMOVED_LISTENER_TAG)){
            this.beanRemoved((T) evt.getNewValue());
        }
    }

    /**
     * Calls a the custom property change event when a bean has bean added
     * @param bean The added bean
     */
    private void beanAdded(T bean){
        if (this.onBeanAdded != null){
            // Call custom event from the class creating this bean container
            this.onBeanAdded.accept(bean);
        }
    }

    /**
     * Calls a the custom property change event when a bean has bean removed
     * @param bean The removed bean
     */
    private void beanRemoved(T bean){
        if (this.onBeanRemoved != null){
            // Call custom event from the class creating this bean container
            this.onBeanRemoved.accept(bean);
        }
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The type of the bean managed by this container
     */
    public Class<T> getBeanClass() {
        return this.beanClass;
    }
}
