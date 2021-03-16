package fr.pops.client;

import java.util.Hashtable;

public class ClientManager {

    private static ClientManager instance = new ClientManager();

    private int nextClientId = 0;
    private Hashtable<Integer, ServerInputStreamHandler> clients = new Hashtable<>();

    private ClientManager(){

    }

    public void addClient(ServerInputStreamHandler client){
        client.setId(this.nextClientId);
        this.clients.put(this.nextClientId, client);
        this.nextClientId++;
    }

    public static ClientManager getInstance() {
        return instance;
    }
}
