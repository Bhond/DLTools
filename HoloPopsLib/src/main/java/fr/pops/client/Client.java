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
 * Name: Client.java
 *
 * Description: Class defining the client used by HOLOPOPS.
 *
 * Author: Charles MERINO
 *
 * Date: 19/08/2021
 *
 ******************************************************************************/
package fr.pops.client;

import org.lwjgl.glfw.GLFW;
import fr.pops.graphics.*;
import fr.pops.io.Input;
import fr.pops.io.Window;
import fr.pops.math.Vector2f;
import fr.pops.math.Vector3f;
import fr.pops.sockets.client.VertxBaseClient;
import fr.pops.sockets.cst.EnumCst;

public class Client extends VertxBaseClient implements Runnable {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 760;

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    public Thread game;
    private Window window;
    private Shader shader;
    private Material mat = new Material("/textures/image.png");
    private Renderer renderer;
    private Mesh mesh = new Mesh(new Vertex[]{
            //Back face
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

            //Front face
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

            //Right face
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

            //Left face
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

            //Top face
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

            //Bottom face
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
    }, new int[] {
            //Back face
            0, 1, 3,
            3, 1, 2,

            //Front face
            4, 5, 7,
            7, 5, 6,

            //Right face
            8, 9, 11,
            11, 9, 10,

            //Left face
            12, 13, 15,
            15, 13, 14,

            //Top face
            16, 17, 19,
            19, 17, 18,

            //Bottom face
            20, 21, 23,
            23, 21, 22
    }, mat);
    private GameObject gameObject = new GameObject(new Vector3f(.5f, 0.0f, 0.0f),
            new Vector3f(0.0f, .0f, .0f),
            new Vector3f(1.0f, 1.0f, 1.0f),
            this.mesh);
    private Camera camera = new Camera(new Vector3f(.0f, .0f, 1.0f),
                                       new Vector3f(.0f, (float)Math.PI / 4, .0f));

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public Client(){
        super(EnumCst.ClientTypes.HOLOPOPS, new HoloPopsRequestHandler());

        // Initialisation
        this.onInit();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Initialize the client
     */
    private void onInit() {
        this.window = new Window(WIDTH, HEIGHT, "Game");
        this.shader = new Shader("/shaders/vertex.glsl", "/shaders/fragment.glsl");
        this.mat = new Material("/textures/image.png");
        this.renderer = new Renderer(this.window, shader);
        this.window.setBackgroundColor(0.1f, 0.1f, 0.15f);
        this.window.create();
        this.mesh.create();
        this.shader.create();
    }

    /**
     * Run the client, should disappear
     */
    public void run() {
        onInit();
        while (!this.window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) this.window.setFullscreen(!this.window.isFullscreen());
        }
        close();
    }

    /**
     * Start the client
     * TODO: should call super to make it run as a Pops client
     */
    @Override
    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    /**
     * Update the client
     */
    private void update() {
        this.window.update();
        this.camera.update();
        this.window.mouseState(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT));
    }

    /**
     * Render the meshes
     */
    private void render() {
        this.renderer.renderMesh(gameObject, camera);
        this.window.swapBuffers();
    }

    /**
     * Close the client
     */
    public void close() {
        this.window.destroy();
        this.mesh.destroy();
        this.shader.destroy();
    }

}
