package fr.pops.client;

import fr.pops.sockets.streamhandler.OutputStreamHandler;

import java.net.Socket;

public class ServerOutputStreamHandler extends OutputStreamHandler {

     private ServerOutputStreamHandler(){
        super();
     }

     public ServerOutputStreamHandler(Socket socket){
         super(socket);
     }

    @Override
    protected void onConnectionOpened() {

    }

    @Override
    protected void setup() {

    }
}
