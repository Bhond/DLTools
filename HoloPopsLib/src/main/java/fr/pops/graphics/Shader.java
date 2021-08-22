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
 * Name: Shader.java
 *
 * Description: Class defining shaders used by meshes.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.graphics;

import fr.pops.math.Matrix4f;
import fr.pops.math.Vector2f;
import fr.pops.math.Vector3f;
import fr.pops.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Shader {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private String vertexFile;
    private String fragmentFile;

    private int vertexId;
    private int fragmentId;
    private int programId;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param vertexPath Path to vertex.glsl file
     * @param fragmentPath Path to fragment.glsl file
     */
    public Shader(String vertexPath, String fragmentPath){
        this.vertexFile = Utils.loadAsString(vertexPath);
        this.fragmentFile = Utils.loadAsString(fragmentPath);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Create shader
     */
    public void create(){
        // Program
        this.programId = GL20.glCreateProgram();

        // Vertex shader
        this.vertexId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(this.vertexId, this.vertexFile);
        GL20.glCompileShader(this.vertexId);

        // Error handling
        if (GL20.glGetShaderi(this.vertexId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println("Vertex shader: " + GL20.glGetShaderInfoLog(this.vertexId));
            return;
        }

        // Fragment
        this.fragmentId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(this.fragmentId, this.fragmentFile);
        GL20.glCompileShader(this.fragmentId);

        // Error handling
        if (GL20.glGetShaderi(this.fragmentId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println("Fragment shader: " + GL20.glGetShaderInfoLog(this.fragmentId));
        }

        // Program
        GL20.glAttachShader(this.programId, this.vertexId);
        GL20.glAttachShader(this.programId, this.fragmentId);

        // Link
        GL20.glLinkProgram(this.programId);
        if (GL20.glGetProgrami(this.programId, GL20.GL_LINK_STATUS) == GL11.GL_FALSE){
            System.err.println("Program linking: " + GL20.glGetProgramInfoLog(this.programId));
        }

        // Validation
        GL20.glValidateProgram(this.programId);
        if (GL20.glGetProgrami(this.programId, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE){
            System.err.println("Program validation: " + GL20.glGetProgramInfoLog(this.programId));
        }

        // Deletion
        GL20.glDeleteShader(this.vertexId);
        GL20.glDeleteShader(this.fragmentId);
    }

    /**
     * Bind the program
     */
    public void bind(){
        GL20.glUseProgram(this.programId);
    }

    /**
     * Unbind the program
     */
    public void unbind(){
        GL20.glUseProgram(0);
    }

    /**
     * Destroy the program
     */
    public void destroy(){
        // Shaders
        GL20.glDetachShader(this.programId, this.vertexId);
        GL20.glDetachShader(this.programId, this.fragmentId);
        GL20.glDeleteShader(this.vertexId);
        GL20.glDeleteShader(this.fragmentId);

        // Program
        GL20.glDeleteProgram(this.programId);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * Retrieve the uniform location
     * @param name Uniform's name
     * @return Uniform's id
     */
    public int getUniformLocation(String name){
        return GL20.glGetUniformLocation(this.programId, name);
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/
    /**
     * Set the uniform
     * @param name Uniform's name
     * @param value Uniform's value
     */
    public void setUniform(String name, float value){
        GL20.glUniform1f(this.getUniformLocation(name), value);
    }

    /**
     * Set the uniform
     * @param name Uniform's name
     * @param value Uniform's value
     */
    public void setUniform(String name, int value){
        GL20.glUniform1i(this.getUniformLocation(name), value);
    }

    /**
     * Set the uniform
     * @param name Uniform's name
     * @param value Uniform's value
     */
    public void setUniform(String name, boolean value){
        GL20.glUniform1i(this.getUniformLocation(name), value ? 1 : 0);
    }

    /**
     * Set the uniform
     * @param name Uniform's name
     * @param value Uniform's value
     */
    public void setUniform(String name, Vector2f value){
        GL20.glUniform2f(this.getUniformLocation(name), value.x(), value.y());
    }

    /**
     * Set the uniform
     * @param name Uniform's name
     * @param value Uniform's value
     */
    public void setUniform(String name, Vector3f value){
        GL20.glUniform3f(this.getUniformLocation(name), value.x(), value.y(), value.z());
    }

    /**
     * Set the uniform
     * @param name Uniform's name
     * @param value Uniform's value
     */
    public void setUniform(String name, Matrix4f value){
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
        matrix.put(value.getValue()).flip();
        GL20.glUniformMatrix4fv(this.getUniformLocation(name), true, matrix);
    }
}
