package fr.pops.clients.mnistclient;

public class Main {

    public static void main(String[] args){
        // Create client
        Client client = Client.getInstance();

        // Start client
        client.start();
    }

}
