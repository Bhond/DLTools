package fr.pops.client;

import fr.pops.requesthandler.ServerRequestHandler;

import java.net.Socket;

public class Client {

    private int id;
    private Socket socket;

    ServerOutputStreamHandler outputStreamHandler;
    ServerRequestHandler requestHandler;
    ServerInputStreamHandler inputStreamHandler;

    public Client(Socket socket){
        this.socket = socket;
        this.onInit();
    }

    private void onInit(){
        this.outputStreamHandler = new ServerOutputStreamHandler(this.socket);
        this.inputStreamHandler = new ServerInputStreamHandler(this.socket);
        this.requestHandler = new ServerRequestHandler(this.inputStreamHandler, this.outputStreamHandler);

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
