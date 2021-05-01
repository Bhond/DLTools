package fr.pops.stocklibmain;

import fr.pops.client.Client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // Create client
        Client client = new Client();

        // Start client
        client.start();
    }

}
