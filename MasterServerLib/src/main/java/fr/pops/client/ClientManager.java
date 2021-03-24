package fr.pops.client;

import java.util.HashSet;

public class ClientManager {

    private static ClientManager instance = new ClientManager();

    private HashSet<Client> clients = new HashSet<>();

    private ClientManager(){

    }

    public void addClient(Client client){
        this.clients.add(client);
    }

    public static ClientManager getInstance() {
        return instance;
    }
}
