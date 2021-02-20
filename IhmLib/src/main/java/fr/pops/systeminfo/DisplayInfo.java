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
 * Name: DisplayInfo.java
 *
 * Description: Class retrieving some info of the display
 *              Singleton
 *
 * Author: Charles MERINO
 *
 * Date: 13/02/2021
 *
 ******************************************************************************/
package fr.pops.systeminfo;

import java.awt.*;
import java.util.stream.Stream;

public class DisplayInfo {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private GraphicsDevice[] devices;
    private int width;
    private int height;
    private final static DisplayInfo instance = new DisplayInfo();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    private DisplayInfo(){
        this.buildInfo();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Computes the info of the display
     * Maximum resolution available
     */
    private void buildInfo(){

        this.devices  = GraphicsEnvironment.
                getLocalGraphicsEnvironment().
                getScreenDevices();

        this.width = Stream.of(this.devices).
                map(GraphicsDevice::getDefaultConfiguration).
                map(GraphicsConfiguration::getBounds).
                mapToInt(bounds -> bounds.x + bounds.width).
                max().
                orElse(0);

        this.height = Stream.of(this.devices).
                map(GraphicsDevice::getDefaultConfiguration).
                map(GraphicsConfiguration::getBounds).
                mapToInt(bounds -> bounds.y + bounds.height).
                max().
                orElse(0);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * The DisplayInfo class is a singleton
     * Hence, it cannot be instantiated
     * @return The instance
     */
    public static DisplayInfo getInstance(){
        return instance;
    }

    /**
     * @return The maximum height of the available displays
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * @return The maximum width of the available displays
     */
    public int getWidth(){
        return this.width;
    }
}
