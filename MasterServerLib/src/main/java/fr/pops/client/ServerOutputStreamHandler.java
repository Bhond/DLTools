package fr.pops.client;

import fr.pops.sockets.resquesthandler.request.RequestQueue;
import fr.pops.sockets.streamhandler.OutputStreamHandler;

import java.net.Socket;

public class ServerOutputStreamHandler extends OutputStreamHandler {

     private ServerOutputStreamHandler(){
        super();
     }

     public ServerOutputStreamHandler(Socket socket, RequestQueue requestQueue){
         super(socket, requestQueue);
     }

    @Override
    protected void onConnectionOpened() {

    }

    @Override
    protected void setup() {

    }
}
