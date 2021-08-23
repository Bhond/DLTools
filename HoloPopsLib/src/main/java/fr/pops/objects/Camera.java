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
package fr.pops.objects;

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

    private float mouseSensitivity = 0.01f;
    private float distance = 2.0f;
    private float horizontalAngle = 0f;
    private float verticalAngle = 0f;
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
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();

        float x = (float) Math.sin(Math.toRadians(rotation.y())) * moveSpeed;
        float z = (float) Math.cos(Math.toRadians(rotation.y())) * moveSpeed;

        this.moveSpeed = Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL) ? 0.5f : 0.1f;
        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(-z, 0, x));
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f(z, 0, -x));
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(-x, 0, -z));
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f(x, 0, z));
        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));

        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT))
            rotation = Vector3f.add(rotation, new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public void update(GameObject object) {
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();

        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            verticalAngle -= dy * mouseSensitivity;
            horizontalAngle += dx * mouseSensitivity;
        }
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            if (distance > 0) {
                distance += dy * mouseSensitivity / 4;
            } else {
                distance = 0.1f;
            }
        }

        float horizontalDistance = (float) (distance * Math.cos(Math.toRadians(verticalAngle)));
        float verticalDistance = (float) (distance * Math.sin(Math.toRadians(verticalAngle)));

        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));

        position = new Vector3f(object.getPosition().x() + xOffset, object.getPosition().y() - verticalDistance, object.getPosition().z() + zOffset);

        rotation = new Vector3f(verticalAngle, -horizontalAngle, 0);

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
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
