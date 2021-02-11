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
 * Description: Class defining the thread running the client
 *
 * Author: Charles MERINO
 *
 * Date: 10/02/2021
 *
 ******************************************************************************/
package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private Socket socket;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done...
     */
    private Client(){
        // Nothing to be done...
    }

    /**
     * Instantiate a new client thread
     * @param socket The socket used by the client
     */
    public Client(Socket socket) {
        onInit(socket);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Initialize the client thread
     * @param socket The socket used by the client
     */
    private void onInit(Socket socket){
        // Store the socket
        this.socket = socket;
    }

    /*****************************************
     *
     * Parent method
     *
     *****************************************/
    @Override
    public void run() {
        try {
            // For now the client is just sending messages to the server
            System.out.println("Connected: " + this.socket);
            Scanner in = new Scanner(this.socket.getInputStream());
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            while(in.hasNextLine()){
                out.println("Server responded: " + in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error: " + this.socket);
            e.printStackTrace();
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Closed: " + this.socket);
        }
    }
}
