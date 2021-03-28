package fr.pops.client;

import fr.pops.requesthandler.ServerRequestHandler;
import fr.pops.sockets.resquesthandler.request.RequestQueue;

import java.net.Socket;

public class Client {

    private int id;
    private Socket socket;

    private RequestQueue inputRequestQueue;
    private RequestQueue outputRequestQueue;

    private ServerOutputStreamHandler outputStreamHandler;
    private ServerRequestHandler requestHandler;
    private ServerInputStreamHandler inputStreamHandler;

    public Client(Socket socket){
        this.socket = socket;
        this.onInit();
    }

    private void onInit(){
        this.inputRequestQueue = new RequestQueue();
        this.outputRequestQueue = new RequestQueue();
        this.inputStreamHandler = new ServerInputStreamHandler(this.socket, this.inputRequestQueue);
        this.requestHandler = new ServerRequestHandler(this.inputRequestQueue, this.outputRequestQueue);
        this.outputStreamHandler = new ServerOutputStreamHandler(this.socket, this.outputRequestQueue);

        this.outputStreamHandler.start();
        this.inputStreamHandler.start();
    }

    public ServerRequestHandler getRequestHandler() {
        return this.requestHandler;
    }

    public void setId(int id) {
        this.id = id;
    }
}
