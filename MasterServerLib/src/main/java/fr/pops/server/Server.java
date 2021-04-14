/*******************************************************************************
 *
 *                         PPPP     OOOO     PPPP    SSSS
 *                        PP  PP   OO  OO   PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP   SSSS
 *                        PPPP    OO    OO  PPPP        SS
 *                        PP       OO  OO   PP          SS
 *                        PP        OOOO    PP       SSSS
 *
 * Name: Server.java
 *
 * Description: Class defining the master server for Pops
 *              It is used as a junction between the clients and
 *              other servers hosting devices
 *
 * Author: Charles MERINO
 *
 * Date: 11/04/2021
 *
 ******************************************************************************/
package fr.pops.server;

import fr.pops.client.ClientSession;
import fr.pops.commoncst.IntCst;
import fr.pops.cst.DoubleCst;
import fr.pops.sockets.resquest.Request;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    /*
     * TODO: Try moving the communicate loop somewhere else
     */
    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Server connexion parameters
    private InetSocketAddress socketAddress;
    private final String host = "localhost";
    private final int port = IntCst.SERVER_PORT;

    // Server loop parameters
    private final long initialDelay = (long) DoubleCst.SERVER_INITIAL_DELAY;
    private final long frequency = (long) (1E3 / DoubleCst.SERVER_FREQUENCY_HZ);

    // Communication
    private ServerSocketChannel serverChannel;
    private Selector selector;

    // Clients
    private ServerRequestHandler requestHandler;
    private HashMap<Long,SelectionKey> connectedClientsId = new HashMap<>();
    private HashMap<SelectionKey, ClientSession> clientMap = new HashMap<>();

    /*****************************************
     *
     * Ctors
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private Server(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param socketAddress The socket address to connect to
     */
    public Server(InetSocketAddress socketAddress){
        // Initialize server
        this.onInit(socketAddress);
    }

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize communication
     * @param socketAddress The socket adress to connect to
     */
    private void onInit(InetSocketAddress socketAddress) {
        // Store fields
        this.socketAddress = socketAddress;
        this.requestHandler = new ServerRequestHandler(this);

        // Initialize server
        try {
            this.selector = Selector.open();
            this.serverChannel = ServerSocketChannel.open();
            this.serverChannel.configureBlocking(false);
            this.serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            this.serverChannel.bind(socketAddress);

        } catch (Throwable ignored){}

    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Run the server
     */
    public void run(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                this.communicate();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }, this.initialDelay, this.frequency, TimeUnit.MILLISECONDS);
    }

    /**
     * Communicate with clients
     * @throws Throwable Exception raised by the different throws
     *                  across the communication
     */
    private void communicate() throws Throwable {
        // Select the keys
        this.selector.select();
        Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();

        // Loop over the keys
        for (; keys.hasNext();) {
            SelectionKey key = keys.next();
            // If key is not valid skip to next one
            if (!key.isValid()) continue;

            // Accept new client
            if (key.isAcceptable()){
                SocketChannel acceptedChannel = this.serverChannel.accept();
                if (acceptedChannel == null) continue;
                acceptedChannel.configureBlocking(false);
                SelectionKey readKey = acceptedChannel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                this.clientMap.put(readKey, new ClientSession(readKey, acceptedChannel));
                System.out.println("Connected:" + acceptedChannel.getRemoteAddress() + ", total clients: " + this.clientMap.size());
            }

            // Read input and handle it
            if (key.isReadable()){
                Request request = this.clientMap.get(key).read();
                if (request != null){
                    // Handle the request
                    this.requestHandler.handle(key, request);

                    // Post processing checks
                    if (request.needResponse()){
                        this.clientMap.get(key).send(request);
                    }
                }
            }

            // Write output
            if (key.isWritable()){
                this.clientMap.get(key).write();
            }
        }
    }

    /**
     * Add client id to a dictionary
     * @param clientId The client id to add
     * @param key The corresponding selection key to find
     *            which client session to point in case of a transfer
     */
    public void addClient(long clientId, SelectionKey key) {
        this.connectedClientsId.put(clientId, key);
    }
}
