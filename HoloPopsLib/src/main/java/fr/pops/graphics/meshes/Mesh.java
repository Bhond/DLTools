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
 * Name: Mesh.java
 *
 * Description: Class defining meshes to be drawn on the window.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.graphics.meshes;

import fr.pops.graphics.Material;
import fr.pops.graphics.Vertex;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Data
    protected Vertex[] vertices;
    protected int[] indices;
    protected Material material;

    // Vertex objects
    private int vao;
    private int vbo;
    private int ibo;
    private int cbo;
    private int tbo;

    public enum RenderMode { TRIANGLES, LINES }
    protected RenderMode renderMode = RenderMode.TRIANGLES;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    protected Mesh(){
        // Nothing to be done
    }

    /**
     * Standard ctor
     * Nothing to be done
     */
    public Mesh(Vertex[] vertices, int[] indices, Material material){
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Create the mesh
     */
    public void create(){
        // Material
        this.material.create();

        // VAO
        this.vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(this.vao);
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[this.vertices.length * 3];
        for (int i = 0; i < vertices.length; i++){
            positionData[i * 3] = vertices[i].getPosition().x();
            positionData[i * 3 + 1] = vertices[i].getPosition().y();
            positionData[i * 3 + 2] = vertices[i].getPosition().z();
        }
        positionBuffer.put(positionData).flip();

        // VBO
        this.vbo = this.storeData(positionBuffer, 0, 3);

        // CBO
//        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
//        float[] colorData = new float[this.vertices.length * 3];
//        for (int i = 0; i < vertices.length; i++){
//            colorData[i * 3] = vertices[i].getColor().x();
//            colorData[i * 3 + 1] = vertices[i].getColor().y();
//            colorData[i * 3 + 2] = vertices[i].getColor().z();
//        }
//        colorBuffer.put(colorData).flip();
//        this.cbo = this.storeData(colorBuffer, 1, 3);

        // TBO
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[this.vertices.length * 2];
        for (int i = 0; i < vertices.length; i++){
            textureData[i * 2] = vertices[i].getTextureCoord().x();
            textureData[i * 2 + 1] = vertices[i].getTextureCoord().y();
        }
        textureBuffer.put(textureData).flip();
        this.tbo = this.storeData(textureBuffer, 2, 2);

        // IBO
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(this.indices).flip();
        this.ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Store data into a buffer
     * @param buffer The buffer to store data in
     * @param index The position of the first data in the buffer
     * @param size The size of 1 data in the buffer
     * @return The buffer id provided by GL15.glGenBuffers
     */
    private int storeData(FloatBuffer buffer, int index, int size){
        int bufferId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0); // TODO: check the parameters of the method
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferId;
    }

    /**
     * Merge vertices and indices
     * TODO: deal with the material
     * @param meshes The meshes to merge
     */
    public static Mesh merge(Mesh... meshes){
        Mesh merge = new Mesh();

        // Retrieve the size of the meshes
        int nbVertices = 0;
        int nbIndices = 0;
        for (int i = 0; i < meshes.length; i++){
            nbVertices += meshes[i].getVertices().length;
            nbIndices += 3 * meshes[i].getVertices().length;
        }

        // Retrieve vertices and indices to put them in their arrays
        // Vertices
        Vertex[] vertices = new Vertex[nbVertices];
        int indexOffset = 0;
        for (int i = 0; i < meshes.length; i++){
            for (int j = 0; j < meshes[i].vertices.length; j++){
                vertices[i * indexOffset + j] = meshes[i].getVertices()[j];
            }
            indexOffset+=meshes[i].vertices.length;
        }

        // Indices
        int[] indices = new int[nbIndices];
        indexOffset = 0;
        int vertexOffset = 0;
        for (int i = 0; i < meshes.length; i++){
            for (int j = 0; j < meshes[i].indices.length; j++){
                indices[i * indexOffset + j] = meshes[i].getIndices()[j] + vertexOffset;
            }
            indexOffset  += meshes[i].indices.length;
            vertexOffset += meshes[i].vertices.length;
        }

        // Fill in fields
        merge.vertices = vertices;
        merge.indices = indices;
        merge.material = meshes[0].getMaterial();
        return merge;
    }

    /**
     * Destroy the mesh:
     *      -   Destroy the buffers
     *      -   Destroy the arrays
     */
    public void destroy(){
        // Material
        //this.material.destroy();

        // Buffers
        GL15.glDeleteBuffers(this.vbo);
        GL15.glDeleteBuffers(this.ibo);
        GL15.glDeleteBuffers(this.cbo);
        GL15.glDeleteBuffers(this.tbo);

        // Arrays
        GL30.glDeleteVertexArrays(this.vao);
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * @return The vertices of the mesh
     */
    public Vertex[] getVertices() {
        return this.vertices;
    }

    /**
     * @return The indices of the vertices in the mesh
     */
    public int[] getIndices() {
        return this.indices;
    }

    /**
     * @return The Vertex Array Object
     */
    public int getVAO() {
        return this.vao;
    }

    /**
     * @return The Vertex Buffer Object containing
     *         position data
     */
    public int getVBO() {
        return this.vbo;
    }

    /**
     * @return The Indices Buffer Object containing
     *         indices data
     */
    public int getIBO() {
        return this.ibo;
    }

    /**
     * @return The Color Buffer Object containing
     *          color data
     */
//    public int getCBO() {
//        return this.cbo;
//    }

    /**
     * @return The Texture Buffer Object containing
     *          color data
     */
    public int getTBO() {
        return this.tbo;
    }

    /**
     * @return The material of the mesh
     */
    public Material getMaterial() {
        return this.material;
    }

    public RenderMode getRenderMode() {
        return this.renderMode;
    }
}
