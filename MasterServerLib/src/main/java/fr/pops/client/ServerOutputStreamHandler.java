package fr.pops.client;

import fr.pops.requesthandler.ServerRequestHandler;
import fr.pops.sockets.streamhandler.OutputStreamHandler;

import java.net.Socket;

public class ServerOutputStreamHandler extends OutputStreamHandler {

     private ServerOutputStreamHandler(){
        super();
     }

     public ServerOutputStreamHandler(Socket socket){
         super(socket, ServerRequestHandler.getInstance());
     }

    @Override
    protected void onConnectionOpened() {

    }

    @Override
    protected void setup() {

    }
}
