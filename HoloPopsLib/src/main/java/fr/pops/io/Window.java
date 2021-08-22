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
 * Name: Window.java
 *
 * Description: Class defining the main window of the app.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.io;

import fr.pops.math.Matrix4f;
import fr.pops.math.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private long window;
    private int width;
    private int height;
    private String name;
    private boolean isResized;
    private boolean isFullScreen;
    private int[] windowPosX = new int[1];
    private int[] windowPosY = new int[1];
    private Matrix4f projection;

    private GLFWWindowSizeCallback sizeCallback;

    private int frames;
    private static long time;

    private Vector3f background = new Vector3f(0, 0, 0);

    private Input input;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public Window(int width, int height, String name){
        this.width = width;
        this.height = height;
        this.name = name;
        this.projection = Matrix4f.projection((float) Math.toRadians(70), (float) width / (float) height, 0.1f, 1000.0f);
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
    }

    /**
     * Create the display
     */
    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR: GLFW wasn't initializied");
            return;
        }

        input = new Input();
        window = GLFW.glfwCreateWindow(width, height, name, isFullScreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);

        if (window == 0) {
            System.err.println("ERROR: Window wasn't created");
            return;
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        windowPosX[0] = (videoMode.width() - width) / 2;
        windowPosY[0] = (videoMode.height() - height) / 2;
        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        createCallbacks();

        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1);

        time = System.currentTimeMillis();
    }


    private void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }

    public void update() {
        if (isResized) {
            GL11.glViewport(0, 0, width, height);
            isResized = false;
        }
        GL11.glClearColor(background.x(), background.y(), background.z(), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(window, name + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void setFullscreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        isResized = true;
        if (isFullScreen) {
            GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        } else {
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy() {
        input.destroy();
        sizeCallback.free();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    /**
     * @param r
     * @param g
     * @param b
     */
    public void setBackgroundColor(float r, float g, float b) {
        background = new Vector3f(r, g, b);
    }

    /**
     * @return
     */
    public boolean isFullscreen() {
        return isFullScreen;
    }

    public void mouseState(boolean lock){
        GLFW.glfwSetInputMode(this.window, GLFW.GLFW_CURSOR, lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
    }

    /**
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public long getWindow() {
        return window;
    }

    /**
     * @return
     */
    public Matrix4f getProjection() {
        return this.projection;
    }
}
