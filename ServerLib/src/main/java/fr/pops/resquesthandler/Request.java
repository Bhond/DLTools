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
 * Name: Request.java
 *
 * Description: Class defining request exchange between the server and a client
 *
 * Author: Charles MERINO
 *
 * Date: 17/02/2021
 *
 ******************************************************************************/
package fr.pops.resquesthandler;

import java.net.Socket;

@SuppressWarnings("unused")
public class Request {

    /*****************************************
     *
     * Static Attributes
     *
     *****************************************/
    private enum DataTypes {CMD, DOUBLE, INT, STRING, INDARRAY}

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private Socket socket;
    private String message;

    private DataTypes dataType;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private Request(){
        // Nothing to be done
    }

    /**
     * Create a request that will be handle by the requestHandler
     * @param socket The socket calling the request
     * @param message The message to be parsed into a request
     */
    public Request(Socket socket, String message){
        // Initialize the request
        this.socket = socket;
        this.message = message;

        // Parse the message
        this.parseMessage();

        // Store request
        RequestDispatcher.getInstance().enqueueRequest(this);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Parse the message
     */
    private void parseMessage(){
        // Read the data type
        this.readDataType();

        // Read the data length
        this.readDataLength();

        // Read the data
        this.readData();
    }

    /**
     * Read the actual data
     */
    private void readDataType(){
//        System.out.println("Type = " + this.message);
    }

    /**
     * Read the actual data
     */
    private void readDataLength(){
//        System.out.println("Length = " + this.message);
    }

    /**
     * Read the actual data
     */
    private void readData(){
//        System.out.println("Data = " + this.message);
    }
}
