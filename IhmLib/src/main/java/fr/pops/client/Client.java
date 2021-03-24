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
 * Description: Class defining Pops IHM client
 *              Used to define the interface between the client and the server
 *              Singleton
 *
 * Author: Charles MERINO
 *
 * Date: 15/02/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.ihmlibcst.StrCst;
import fr.pops.sockets.cst.IdCst;
import fr.pops.views.MainView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.Socket;

public class Client {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Instance
    private final static Client instance = new Client();

    // General parameters
    private final static long id = IdCst.ID_IHM;
    private boolean isStandalone = false;

    // Ihm
    private Stage stage;
    private MainView mainView;
    private Parent root;
    private Scene scene;

    // Socket
    private Socket socket;

    // Communication pipeline
    private IhmRequestHandler requestHandler;
    private IhmInputStreamHandler inputStreamHandler;
    private IhmOutputStreamHandler outputStreamHandler;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public Client(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the client
     */
    public void init(Stage stage){
        // Set the stage
        this.stage = stage;

        // Set main view
        this.mainView = new MainView(stage);

        // Set the root
        this.root = this.mainView.asParent();

        // Set the scene
        this.scene = new Scene(this.root);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream(StrCst.PATH_MAIN_ICON)));

        // Configure the stage
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.setTitle(StrCst.ZE_NAME);
        this.stage.setScene(this.scene);
    }

    /*****************************************
     *
     * Start the client
     *
     *****************************************/
    /**
     * Start the client
     */
    public void start() {
        try {
            // Connect to the server
            if (!this.isStandalone){
                // Connect to the server
                this.socket = new Socket("127.0.0.1", 8163);

                // Build communication pipeline
                this.outputStreamHandler = new IhmOutputStreamHandler(this.socket);
                this.outputStreamHandler.start();
                this.requestHandler = new IhmRequestHandler(this.outputStreamHandler);
                this.inputStreamHandler = new IhmInputStreamHandler(this.socket, this.requestHandler);
                this.inputStreamHandler.start();
            }

            // Display the main view
            this.stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    public void sendMessageToServer(String msg){
        System.out.println(msg);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * This class is a singleton
     * @return The only instance of the object
     */
    public static Client getInstance(){
        return instance;
    }

}
