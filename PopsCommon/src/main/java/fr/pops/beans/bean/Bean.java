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
 * Name: Bean.java
 *
 * Description: Abstract class defining Beans which will be serialized and shared
 *              amongst clients
 *
 * Author: Charles MERINO
 *
 * Date: 18/02/2021
 *
 ******************************************************************************/
package fr.pops.beans.bean;

import fr.pops.cst.GeneratorCst;
import fr.pops.models.BeanModel;

import java.lang.reflect.InvocationTargetException;

public abstract class Bean {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected int id;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Initialize the id and add this bean to the bean manager
     * TODO: Find a model.class and if exists, instantiate it
     */
    protected Bean(){
        // Set id
        this.id = BeanManager.getInstance().getId();

        // Store bean
        BeanManager.getInstance().addBean(this);

        // Get model if the class exists
        // instantiate it and store it
        BeanModel<Bean> model = FindModelIfExists(this);
        if (model != null){
            BeanManager.getInstance().addModel(model);
        }
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Find a model, by reflection, if implemented related to the given bean &&
     * instantiate it.
     * Must extends BeanModel<T> with T the Bean type it is suppose to model.
     * Must be named xxxModel with xxx the bean's name without the word "Bean".
     * ej.: public class FooModel extends BeanModel<FooBean>{}.
     * Ignore ClassNotFoundException since not every bean has an implemented model
     * @param bean The bean used by the model
     * @param <T> The Bean type
     * @return If found, the model ; else, null
     */
    private <T extends Bean> BeanModel<T> FindModelIfExists(T bean){
        // Initialisation
        String classpath = bean.getClass().toString();
        String beanName = classpath.substring(classpath.lastIndexOf('.')+1);
        String name = beanName.substring(0, beanName.lastIndexOf(GeneratorCst.BEAN_BEAN));
        String fullName = GeneratorCst.BEAN_MODELS_PACKAGE_DOT + name + GeneratorCst.BEAN_MODEL;

        // Find the model by reflection &&
        // Instantiate it
        BeanModel<T> model = null;
        try {
            // Use reflection to retrieve the model
            // The warning raised about the cast is irrelevant if the template for the models is respected:
            //      - Must extends BeanModel<T> with T the Bean type it is suppose to model
            //      - Must be named xxxModel with xxx the bean's name without the word "Bean"
            model = (BeanModel<T>) Class.forName(fullName).getDeclaredConstructor().newInstance();
            model.setBean(bean);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {}
        return model;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The id of the bean
     */
    public final int getId() {
        return this.id;
    }

}
