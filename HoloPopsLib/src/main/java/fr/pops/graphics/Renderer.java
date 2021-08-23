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
 * Name: Renderer.java
 *
 * Description: Class defining the renderer that renders meshes on the window.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/

package fr.pops.graphics;

import fr.pops.graphics.meshes.Mesh;
import fr.pops.io.Window;
import fr.pops.math.Matrix4f;
import fr.pops.objects.Camera;
import fr.pops.objects.GameObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    public static final String TRANSFORM = "transform";
    public static final String PROJECTION = "projection";
    public static final String VIEW = "view";

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private Shader shader;
    private Window window;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param shader The shader to use
     */
    public Renderer(Window window, Shader shader){
        this.window = window;
        this.shader = shader;
    }

    /*****************************************
     *
     * Method
     *
     *****************************************/
    /**
     * Render a mesh
     */
    public void renderMesh(GameObject object, Camera camera){
        // Enable vertex array
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);

        // Indices array
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureId());
        this.shader.bind();
        this.shader.setUniform(TRANSFORM, Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        this.shader.setUniform(PROJECTION, this.window.getProjection());
        this.shader.setUniform(VIEW, Matrix4f.view(camera.getPosition(), camera.getRotation()));
        if (object.getMesh().getRenderMode() == Mesh.RenderMode.TRIANGLES){
            GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        } else {
            GL11.glDrawElements(GL11.GL_LINES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        }
        this.shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // Disable vertex array
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
}
