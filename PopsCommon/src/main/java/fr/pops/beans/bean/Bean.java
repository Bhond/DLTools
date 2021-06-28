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

import fr.pops.beans.properties.*;
import fr.pops.commoncst.EnumCst;
import fr.pops.commoncst.GeneratorCst;
import fr.pops.beans.beanmodels.BeanModel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Bean {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    protected int id;
    protected String beanTypeId;
    protected List<Property<?>> properties = new ArrayList<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Initialize the id and add this bean to the bean manager
     */
    protected Bean(String beanTypeId){
        // Sets the bean type id
        this.beanTypeId = beanTypeId;

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

    /**
     * Create a double property
     * @param name The name of the property
     * @param defaultValue The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    protected  DoubleProperty createProperty(String name, double defaultValue, boolean isComputed, boolean isInternal){
        DoubleProperty property = new DoubleProperty(this.id, name, EnumCst.PropertyTypes.DOUBLE, defaultValue, isComputed, isInternal);
        this.properties.add(property);
        return property;
    }

    /**
     * Create a int property
     * @param name The name of the property
     * @param defaultValue The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    protected  IntegerProperty createProperty(String name, int defaultValue, boolean isComputed, boolean isInternal){
        IntegerProperty property = new IntegerProperty(this.id, name, EnumCst.PropertyTypes.INT, defaultValue, isComputed, isInternal);
        this.properties.add(property);
        return property;
    }

    /**
     * Create a string property
     * @param name The name of the property
     * @param defaultValue The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    protected  StringProperty createProperty(String name, String defaultValue, boolean isComputed, boolean isInternal){
        StringProperty property = new StringProperty(this.id, name, EnumCst.PropertyTypes.STRING, defaultValue, isComputed, isInternal);
        this.properties.add(property);
        return property;
    }

    /**
     * Create a boolean property
     * @param name The name of the property
     * @param defaultValue The value of the property
     * @param isComputed Define is the property is computed by the model
     * @param isInternal Define is the property is modifiable by the user
     */
    protected BooleanProperty createProperty(String name, boolean defaultValue, boolean isComputed, boolean isInternal){
        BooleanProperty property = new BooleanProperty(this.id, name, EnumCst.PropertyTypes.BOOLEAN, defaultValue, isComputed, isInternal);
        this.properties.add(property);
        return property;
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

    /**
     * @return The bean type id
     */
    public String getBeanTypeId() {
        return this.beanTypeId;
    }

    /**
     * @return The properties of this bean
     */
    public List<Property<?>> getProperties() {
        return this.properties;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the bean id for clients receiving bean updates from the server
     * @param id The id given the server where the bean manager is hosted
     */
    public void setId(int id) {
        this.id = id;
    }
}
