package fr.pops.client;

import fr.pops.sockets.streamhandler.OutputStreamHandler;

import java.net.Socket;

public class IhmOutputStreamHandler extends OutputStreamHandler {

    private IhmOutputStreamHandler(){
        super();
    }

    public IhmOutputStreamHandler(Socket socket){
        super(socket, IhmRequestHandler.getInstance());
    }

    @Override
    protected void onConnectionOpened() {

    }

    @Override
    protected void setup() {

    }
}
