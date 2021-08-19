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
 * Name: Vertex.java
 *
 * Description: Class defining the vertices defining the meshes on the window.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.graphics;

import fr.pops.math.Vector2f;
import fr.pops.math.Vector3f;

public class Vertex {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private Vector3f position;
    private Vector3f color;
    private Vector2f textureCoord;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param position A Vector3f representing the position of the vertex
     */
    public Vertex(Vector3f position, Vector3f color, Vector2f textureCoord){
        this.position = position;
        this.color = color;
        this.textureCoord = textureCoord;
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return A Vector3f representing the position of the vertex
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * @return The Vector3f representing the color of the vertex
     */
    public Vector3f getColor() {
        return this.color;
    }

    /**
     * @return The texture coordinates
     */
    public Vector2f getTextureCoord() {
        return this.textureCoord;
    }
}
