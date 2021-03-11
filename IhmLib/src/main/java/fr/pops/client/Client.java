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
import fr.pops.views.MainView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Instance
    private final static Client instance = new Client();

    // Ihm
    private boolean isStandalone = true;
    private Stage stage;
    private MainView mainView;
    private Parent root;
    private Scene scene;

    // Socket
    private Socket socket;

    // Communication pipeline
    private Scanner in;
    private PrintWriter out;

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

    /**
     * Build the communication between the server and the client
     */
    private void buildCommunicationPipeline(){
        try {
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                this.buildCommunicationPipeline();
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
        // TODO: Correct this part to make a dedicated thread that handles requests
//        this.out.println(msg);
//        if (this.in.hasNextLine()){
//            System.out.println(in.nextLine());
//        }
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
