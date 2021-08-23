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
 * Name: GameObject.java
 *
 * Description: Class defining the game object rendered onto the window.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.objects;

import fr.pops.graphics.meshes.Mesh;
import fr.pops.math.Vector3f;

public class GameObject {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private int beanId;
    private Mesh mesh;

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    private float moveSpeed = .01f;
    private float temp = .0f;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    /*****************************************
     *
     * Update
     *
     *****************************************/
    public void update(){
//        temp += 0.02f;
//        position.setValue(2, -temp);
//        this.rotation.setValue(0, (float) Math.sin(0.01 * temp) * 360);
//        this.rotation.setValue(1, (float) Math.sin(0.01 * temp) * 360);
//        this.rotation.setValue(2, (float) Math.sin(0.01 * temp) * 360);
//        this.scale.setValue(0, (float) Math.sin(temp));
//        this.scale.setValue(1, (float) Math.sin(temp));
//        this.scale.setValue(2, (float) Math.sin(temp));
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Translate the game object
     * @param v The amount of translation
     *          along each axis
     */
    public void translate(Vector3f v){
        this.position = Vector3f.add(this.position, v);
    }

    /**
     * Rotate the game object
     * @param v The amount of rotation
     *          along each axis
     */
    public void rotate(Vector3f v){
        this.rotation = v;
    }

    /**
     * Scale the game object
     * @param v The amount of scaling
     *          along each axis
     */
    public void scale(Vector3f v){
        this.scale = v;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The mesh rendered for this game object
     */
    public Mesh getMesh() {
        return this.mesh;
    }

    /**
     * @return The position of the game object
     *         in the scene
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * @return The rotation of the game object
     *         in the scene
     */
    public Vector3f getRotation() {
        return this.rotation;
    }

    /**
     * @return The scale of the game object
     *         in the scene
     */
    public Vector3f getScale() {
        return this.scale;
    }
}
