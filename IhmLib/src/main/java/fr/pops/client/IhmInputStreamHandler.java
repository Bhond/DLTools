package fr.pops.client;

import fr.pops.sockets.streamhandler.InputStreamHandler;

import java.net.Socket;

public class IhmInputStreamHandler extends InputStreamHandler {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private IhmInputStreamHandler(){
        // Nothing to be done
        // Parent
        super();
    }

    /**
     * Ctor
     * @param socket The socket
     */
    public IhmInputStreamHandler(Socket socket){
        // Parent
        super(socket);
    }

    /*****************************************
     *
     * Parent methods
     *
     *****************************************/
    /**
     * Setup the communication as soon as the communication starts
     */
    @Override
    protected void onConnectionOpened() {

    }

    /**
     * Setup the handler
     */
    @Override
    protected void setup() {

    }
}
