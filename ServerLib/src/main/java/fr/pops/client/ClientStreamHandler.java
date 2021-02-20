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
 * Name: ClientStreamHandler.java
 *
 * Description: Class defining the thread running to listen to the client's
 *              input and output streams
 *
 * Author: Charles MERINO
 *
 * Date: 10/02/2021
 *
 ******************************************************************************/
package fr.pops.client;

import fr.pops.resquesthandler.Request;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientStreamHandler implements Runnable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Socket
    private Socket socket;

    // Input / output streams
    private Scanner inputStream;
    private PrintWriter outputStream;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done...
     */
    private ClientStreamHandler(){
        // Nothing to be done...
    }

    /**
     * Instantiate a new fr.pops.client thread
     * @param socket The socket used by the fr.pops.client
     */
    public ClientStreamHandler(Socket socket) {
        // Initialize the socket
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

        // Build input / output streams
        try {
            // Build the input stream
            this.inputStream = new Scanner(this.socket.getInputStream());

            // Build the output stream
            this.outputStream = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error: " + this.socket);
            e.printStackTrace();
        }
    }

    /*****************************************
     *
     * Parent method
     *
     *****************************************/
    @Override
    public void run() {
        try {
            // For now the fr.pops.client is just sending messages to the server
            System.out.println("Connected: " + this.socket);
            while (this.inputStream.hasNextLine()){
                outputStream.println("Handling request for: " + this.socket);
                new Request(this.socket, this.inputStream.nextLine());
            }

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
