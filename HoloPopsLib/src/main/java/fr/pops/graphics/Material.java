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
 * Name: Material.java
 *
 * Description: Class defining materials for meshes.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.graphics;

import fr.pops.utils.Utils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.stb.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Material {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    String filePath;
    private int textureId;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param filePath Path to the texture file
     */
    public Material(String filePath){
        this.filePath = Utils.getResourceWithoutFilePrefix(filePath);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Create Material
     */
    public void create(){
        // Generate texture on GPU
        this.textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);

        // Set texture parameters
        // Repeat image in both direction
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        // When stretching image, pixelate
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        // When shrinking image, pixelate
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        // Buffers
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        // Image
        STBImage.stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = STBImage.stbi_load(filePath, width, height, channels, 0);
        if (image != null){
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width.get(0), height.get(0),
                       0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, image);
        } else {
            System.err.println("Loading texture: " + this.filePath);
        }

        // Free image
        STBImage.stbi_image_free(image);
    }

    /**
     * Bind texture
     */
    public void bind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
    }

    /**
     * Unbind texture
     */
    public void unbind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    /**
     * Destroy the material
     */
    public void destroy(){
        GL13.glDeleteTextures(this.textureId);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The texture's id
     */
    public int getTextureId() {
        return this.textureId;
    }
}
