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
 * Name: BeanCreator.java
 *
 * Description: Class defining the bean creator that creates bean by reflection
 *
 * Author: Charles MERINO
 *
 * Date: 25/06/2021
 *
 ******************************************************************************/
package fr.pops.beans.bean;

import fr.pops.commoncst.GeneratorCst;

import java.lang.reflect.InvocationTargetException;

public class BeanCreator {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private static final BeanCreator instance = new BeanCreator();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private BeanCreator(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Create a bean by reflection
     * @param beanTypeId The bean type id written automatically by the generator in the bean's
     *                   class when generated
     * @param <T> The Bean type
     * @return If found, the model ; else, null
     */
    @SuppressWarnings("unchecked")
    public final <T extends Bean> T createBeanByReflection(String beanTypeId){
        // Initialisation
        String name = beanTypeId.toLowerCase();
        String fullName = GeneratorCst.BEAN_ROOT_PACKAGE + name + GeneratorCst.BEAN_DOT + beanTypeId + GeneratorCst.BEAN_BEAN;

        // Find the bean by reflection && instantiate it
        T bean = null;
        try {
            // Use reflection to retrieve the bean
            bean = (T) Class.forName(fullName).getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            System.out.println("Error raised when trying to cast the bean: " + fullName + ". Check that the bean's class implementation respects " +
                    "the rules specified in the description of the method Bean::createBeanByReflection.");
        } catch (ClassNotFoundException ignored) {
           System.out.println("Bean type: " + fullName + " has not been found. Check generated files.");
        }
        return bean;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The instance of the bean creator class
     */
    public static BeanCreator getInstance() {
        return instance;
    }
}
