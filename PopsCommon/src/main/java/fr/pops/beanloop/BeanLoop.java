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
 * Name: BeanLoop.java
 *
 * Description: Loop used to update the models
 *
 * Author: Charles MERINO
 *
 * Date: 24/02/2021
 *
 ******************************************************************************/
package fr.pops.beanloop;

import fr.pops.beans.bean.Bean;
import fr.pops.beans.bean.BeanManager;
import fr.pops.cst.IntCst;
import fr.pops.models.BeanModel;

public class BeanLoop extends Thread implements Runnable {

    /*****************************************
     *
     * Static Attributes
     *
     *****************************************/
    private static BeanLoop instance = new BeanLoop();

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Thread
    private Thread thread;
    protected boolean running = false;
    private final double UPDATE_CAP = 1.0d / IntCst.BEAN_LOOP_MAX_FPS;

    // Bean manager
    private BeanManager beanManager = BeanManager.getInstance();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Singleton
     */
    private BeanLoop(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Start the loop
     */
    public void start(){
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Run the thread
     */
    @Override
    public final void run(){
        // Initialize the loop
        running = true;
        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1e9;
        double elapsedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        // Call the setup method
        for (BeanModel<? extends Bean> beanModel : beanManager.getModels()){
            beanModel.setup();
        }

        // Run the loop
        while (running){
            render = false;
            firstTime = System.nanoTime() / 1e9;
            elapsedTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += elapsedTime;
            frameTime += elapsedTime;

            // TODO: Describe this part
            while (unprocessedTime >= UPDATE_CAP){
                unprocessedTime -= UPDATE_CAP;
                render = true;
                if (frameTime >= 1.0d){
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            if (render){
                frames++;
                // Call the update method
                for (BeanModel<? extends Bean> beanModel : beanManager.getModels()){
                    beanModel.update(elapsedTime);
                }
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        for (BeanModel<? extends Bean> beanModel : beanManager.getModels()){
            beanModel.dispose();
        }
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * This class is a singleton
     * @return The only instance of the object
     */
    public static BeanLoop getInstance() {
        return instance;
    }
}
