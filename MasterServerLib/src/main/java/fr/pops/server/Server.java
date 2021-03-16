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
 * Name: Server.java
 *
 * Description: Class defining the fr.pops.server running Pops
 *
 * Author: Charles MERINO
 *
 * Date: 10/02/2021
 *
 ******************************************************************************/
package fr.pops.server;

import fr.pops.client.ClientManager;
import fr.pops.client.ServerInputStreamHandler;
import fr.pops.serverlibcst.IntCst;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("unused")
public class Server {

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private boolean isServerReady;
    private boolean isServerRunning;
    private int port;

    private ExecutorService pool;
    private ServerSocket serverSocket;

    private ClientManager clientManager;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done...
     */
    private Server() {
     // Nothing to be done...
    }

    /**
     * Instantiate the fr.pops.server
     * @param port The port used by the fr.pops.server
     */
    public Server(int port) {
        System.out.println("Pops server is starting...");

        // Initialization
        this.onInit(port);

        // Run the fr.pops.server
        this.run();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Initialize the fr.pops.server
     */
    private void onInit(int port) {
        // Boolean needed to ensure that the fr.pops.server is properly initialized
        this.isServerReady = false;

        try {
            // Create fr.pops.server socket
            this.serverSocket = new ServerSocket(port);

            // Configure the pool
            this.pool = Executors.newFixedThreadPool(IntCst.SERVER_POOL_SIZE);

            // Configure the client manager
            this.clientManager = ClientManager.getInstance();

            // Fill in missing properties
            this.port = port;

            // If no error occurred the fr.pops.server is ready to be ran
            this.isServerReady = true;

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Run the server
     */
    private void run(){
        System.out.println("Server is running...");

        // Start fr.pops.server
        this.isServerRunning = this.isServerReady;

        // Server loop
        while (this.isServerRunning) {
            try {
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                ServerInputStreamHandler client = new ServerInputStreamHandler(clientSocket);
                this.clientManager.addClient(client);
                this.pool.execute(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}