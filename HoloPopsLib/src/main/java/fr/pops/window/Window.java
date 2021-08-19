package fr.pops.window;

import fr.pops.graphics.*;
import fr.pops.math.Vector2f;
import fr.pops.math.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Window {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private long window;

    /**
     * TODO: make this better
     */
    private Shader shader = new Shader("/shaders/vertex.glsl", "/shaders/fragment.glsl");
    private Material mat = new Material("/textures/image.png");
    private Renderer renderer = new Renderer(shader);
    private Mesh mesh = new Mesh(new Vertex[]{
        new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f),  new Vector3f(1.0f, 0.19f, 0.0f), new Vector2f(0.0f, 1.0f)),
        new Vertex(new Vector3f(0.5f, 0.5f, 0.0f),   new Vector3f(1.0f, 0.19f, 0.0f), new Vector2f(1.0f, 1.0f)),
        new Vertex(new Vector3f(0.5f, -0.5f, 0.0f),  new Vector3f(1.0f, 1.0f, 1.0f), new Vector2f(1.0f, 0.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector2f(0.0f, 0.0f)),
    }, new int[]{
        0, 1, 2,
        0, 3, 2
    }, mat);
    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public Window(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Run the app
     */
    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        // Initialize the window
        init();

        // Main loop
        loop();
    }

    /**
     * Create the display
     */
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        //glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        //glfwWindowHint(GLFW_DECORATED, GLFW_FALSE); // the window won't be decorated

        // Create the window
        this.window = glfwCreateWindow(800, 800, "HoloPops", org.lwjgl.system.MemoryUtil.NULL, org.lwjgl.system.MemoryUtil.NULL);
        if (this.window == org.lwjgl.system.MemoryUtil.NULL) throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(this.window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ){
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            } else if (key == GLFW_KEY_Z && action == GLFW_RELEASE){
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                glDisable(GL_TEXTURE_2D);
            } else if (key == GLFW_KEY_F && action == GLFW_RELEASE){
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                glEnable(GL_TEXTURE_2D);
            }
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(this.window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    this.window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(this.window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(this.window);
    }

    /**
     * Update the display
     */
    private void loop(){
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(.05f, .05f, .1f, 0.5f);

        // Mesh creation
        this.mesh.create();

        // SHader creation
        this.shader.create();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(this.window)) {
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

            // Deal with input keys
            inputs();

            // Render
            render();

            // Clear the framebuffer
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        }

        // At the end of the loop, close the window
        close();
    }

    /**
     * Manage input keys
     * glfwGetKey uses QWERTY layout
     */
    private void inputs(){
        // Switch over key pressed
        if (glfwGetKey(this.window, GLFW_KEY_Q) == GLFW_TRUE){
            System.out.println("A key is pressed");
        }
    }

    /**
     * Render window
     */
    private void render(){
        // Render mesh
        this.renderer.renderMesh(this.mesh);

        // Swap the color buffer
        glfwSwapBuffers(this.window);
    }

    /**
     * Close the display
     */
    private void close(){
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(this.window);
        glfwDestroyWindow(this.window);

        // Destroy mesh
        this.mesh.destroy();

        // Destroy shader
        this.shader.destroy();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
}
