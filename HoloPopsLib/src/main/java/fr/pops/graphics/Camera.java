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
 * Name: Camera.java
 *
 * Description: Class defining cameras to use onto the app to render objects.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.graphics;

import fr.pops.io.Input;
import fr.pops.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private Vector3f position;
    private Vector3f rotation;
    private float moveSpeed = 0.1f;

    private float mouseSensitivity = 0.05f;
    private double oldMouseX;
    private double oldMouseY;
    private double newMouseX;
    private double newMouseY;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param position The position of the camera
     * @param rotation The rotation of the camera
     */
    public Camera(Vector3f position, Vector3f rotation){
        this.position = position;
        this.rotation = rotation;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Update camera position
     */
    public void update(){

        this.newMouseX = Input.getMouseX();
        this.newMouseY = Input.getMouseY();

        float x = (float) Math.sin(rotation.y()) * moveSpeed;
        float z = (float) Math.cos(rotation.y()) * moveSpeed;

        // Translation
        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(moveSpeed, 0, 0));
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f(-moveSpeed, 0, 0));
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(0, 0, -moveSpeed));
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f(0, 0, moveSpeed));
        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));

        // Rotation
        float dx = (float) (this.newMouseX - this.oldMouseX);
        float dy = (float) (this.newMouseY - this.oldMouseY);
        this.rotation = Vector3f.add(this.rotation, new Vector3f(- mouseSensitivity * dy, - mouseSensitivity * dx, 0));
        this.oldMouseX = this.newMouseX;
        this.oldMouseY = this.newMouseY;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The position of the camera
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * @return The rotation of the camera
     */
    public Vector3f getRotation() {
        return rotation;
    }
}
