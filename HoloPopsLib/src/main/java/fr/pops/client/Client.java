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

import fr.pops.graphics.Material;
import fr.pops.graphics.Renderer;
import fr.pops.graphics.Shader;
import fr.pops.graphics.meshes.Mesh;
import fr.pops.io.Input;
import fr.pops.io.ModelLoader;
import fr.pops.io.Window;
import fr.pops.math.Vector3f;
import fr.pops.objects.Camera;
import fr.pops.objects.GameObject;
import fr.pops.sockets.client.VertxBaseClient;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.utils.Utils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL20;

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
    public Mesh mesh = ModelLoader.loadModel(Utils.getResourceWithoutFilePrefix("/models/bunny.stl"), "/textures/image.png");//new Grid(10, 10, 1, 1);
    private Camera camera = new Camera(new Vector3f(.0f, .0f, 1.0f),
                                       new Vector3f(0, .0f, .0f));
    public GameObject object = new GameObject(new Vector3f(0, 0, 0), new Vector3f( 0, 0, 0), new Vector3f(1, 1, 1), mesh);

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
            if (Input.isKeyDown(GLFW.GLFW_KEY_Z)) GL20.glPolygonMode(GL20.GL_FRONT_AND_BACK, GL20.GL_LINE);
            if (Input.isKeyDown(GLFW.GLFW_KEY_F)) GL20.glPolygonMode(GL20.GL_FRONT_AND_BACK, GL20.GL_FILL);
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
        camera.update();
        this.window.mouseState(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT));
    }

    /**
     * Render the meshes
     */
    private void render() {
        renderer.renderMesh(object, camera);
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
