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

import fr.pops.requesthandler.ServerRequestHandler;
import fr.pops.sockets.streamhandler.InputStreamHandler;

import java.net.Socket;

public class ServerInputStreamHandler extends InputStreamHandler {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private static final ServerInputStreamHandler instance = new ServerInputStreamHandler();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done...
     */
    private ServerInputStreamHandler(){
        // Nothing to be done...
        super();
    }

    /**
     * Instantiate a new fr.pops.client thread
     * @param socket The socket used by the fr.pops.client
     */
    public ServerInputStreamHandler(Socket socket) {
        // Call parent
        super(socket, ServerRequestHandler.getInstance());
        System.out.println(socket);
    }

    /*****************************************
     *
     * Stream handler Parent methods
     *
     *****************************************/
    /**
     * Initialize the connection by the sending the id
     * set by the client manager to the client
     * so that the server will know which is sending the message
     */
    @Override
    protected void onConnectionOpened(){
        //this.outputStream.print(this.id);
    }

    /**
     * Setup the stream
     */
    @Override
    protected  void setup(){

    }

    @Override
    public void run(){
        while(inputStream.hasNextLine()){
            //outputStream.println((inputStream.nextLine().toUpperCase()));
        }


//        while(this.isRunning){
//            this.outputStream.print("hello");
//        }
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The instance of ServerStreamHandler
     */
    public static ServerInputStreamHandler getInstance(){
        return instance;
    }

    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * Set the id of the client
     * @param id The id configured by the ClientStreamHandler
     */
    public void setId(int id){
        this.id = id;
    }
}
