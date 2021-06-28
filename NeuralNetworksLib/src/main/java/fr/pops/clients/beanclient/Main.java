package fr.pops.clients.beanclient;

public class Main {

    public static void main(String[] args){
        // Create client
        Client client = Client.getInstance();

        // Start client
        client.start();
    }

}
