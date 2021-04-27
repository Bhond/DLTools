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

import fr.pops.cst.StrCst;
import fr.pops.ihmloop.IhmLoop;
import fr.pops.sockets.client.BaseClient;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.views.MainView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.InetSocketAddress;

public class Client extends BaseClient {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Instance
    private final static Client instance = new Client();

    // Ihm
    private IhmLoop ihmLoop;
    private Stage stage;
    private MainView mainView;
    private Parent root;
    private Scene scene;

    // Settings
    private boolean isStandAlone = false;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public Client(){
        // Parent
        super(EnumCst.ClientTypes.IHM, new InetSocketAddress("localhost", 8163), new IhmRequestHandler());
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the client
     */
    public void init(Stage stage, boolean isStandAlone){
        // Set the stage
        this.stage = stage;

        // Set stand alone
        this.isStandAlone = isStandAlone;

        // Set the model loop
        this.ihmLoop = new IhmLoop();

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
        // Parent
        super.start(this.isStandAlone);

        // Display the main view
        this.stage.show();

        // Start models
        if (!this.isStandAlone){
            this.ihmLoop.run();
        }
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
