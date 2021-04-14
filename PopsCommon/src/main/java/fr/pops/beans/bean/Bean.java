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

import fr.pops.commoncst.GeneratorCst;
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
     */
    protected Bean(){
        // Set id
        this.id = BeanManager.getInstance().getId();

        // Store bean
        BeanManager.getInstance().addBean(this);

        // Get model if the class exists
        // instantiate it and store it
        BeanModel<Bean> model = FindModelAndInstantiateItIfExists(this);
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
     * Suppress warning: "unchecked" because the ClassCastExceptions exception raised
     * if the cast cannot be done should not appear unless the implementation of the
     * model does not satisfy the rules described above.
     * @param bean The bean used by the model
     * @param <T> The Bean type
     * @return If found, the model ; else, null
     */
    @SuppressWarnings("unchecked")
    private <T extends Bean> BeanModel<T> FindModelAndInstantiateItIfExists(T bean){
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
        } catch (ClassCastException e){
            System.out.println("Error raised when trying to cast the model: " + fullName + ". Check that the model's class implementation respects " +
                               "the rules specified in the description of the method Bean::FindModelAndInstantiateItIfExists.");
        } catch (ClassNotFoundException ignored) {
            // Error ignored since a bean does not necessarily have a model
        }
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
