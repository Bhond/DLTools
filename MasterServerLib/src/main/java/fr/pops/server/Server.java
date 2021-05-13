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
 *              This class uses the singleton pattern
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
import fr.pops.math.PopsMath;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.Request;

import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    /*
     * TODO: Try moving the communicate loop somewhere else
     */

    /*****************************************
     *
     * Static attributes
     *
     *****************************************/
    private static Server instance = new Server();

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
    private final long initialDelay = 0;
    private final double frequency = DoubleCst.SERVER_FREQUENCY_HZ;
    private final long timeDelay = PopsMath.convertDoubleToLong(1 / this.frequency, 1E-2);

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

    /*****************************************
     *
     * Initialisation
     *
     *****************************************/
    /**
     * Initialize the server with the given socket address
     * @param socketAddress The socket address to connect to
     */
    public void init(InetSocketAddress socketAddress){
        this.onInit(socketAddress);
    }

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
            } catch (CancelledKeyException ignored){
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }, this.initialDelay, this.timeDelay, TimeUnit.MILLISECONDS);
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
               List<Request> requests = this.clientMap.get(key).read();
               for (Request request : requests){
                   // Handle the request
                   this.requestHandler.handle(key, request);

                   // Post processing checks
                   this.postProcessRequest(request, key);
               }
            }

            // Write output
            if (key.isWritable()){
                this.clientMap.get(key).write();
            }
        }
    }

    /**
     * Post process the request
     * Check what to do with it:
     *      - Nothing
     *      - Write it back
     *      - Transfer
     * @param request The request to post process
     */
    private void postProcessRequest(Request request, SelectionKey senderKey){
        // Select next operation
        EnumCst.RequestOperations operation = this.requestHandler.selectNextOperation(request);

        // Adapt to next the next operation
        switch (operation){
            case WRITE_BACK:
                this.clientMap.get(senderKey).send(request);
                break;
            case TRANSFER:
                this.transfer(request, senderKey);
                break;
        }
    }

    /**
     * Transfer request
     * Select the receiver depending on the sender and the request's type
     * @param request The request to transfer
     * @param senderKey The sender key to select the proper receiver
     */
    private void transfer(Request request, SelectionKey senderKey){
        long id = this.clientMap.get(senderKey).getType().getId();
        long receiverId = this.requestHandler.selectReceiver(request, id);
        if (isClientConnected(receiverId)){
            SelectionKey receiverKey = this.connectedClientsId.get(receiverId);
            this.clientMap.get(receiverKey).send(request);
        }
    }

    /**
     * Add client id to a dictionary
     * Set the type of the client in the client session
     * @param clientId The client id to add
     * @param key The corresponding selection key to find
     *            which client session to point in case of a transfer
     */
    public void addClient(long clientId, SelectionKey key) {
        this.connectedClientsId.put(clientId, key);
        this.clientMap.get(key).setType(fr.pops.sockets.cst.EnumCst.ClientTypes.getType(clientId));
    }

    /**
     * Remove client id from dictionaries
     * Close session
     * @param clientId The client id to remove
     * @param key The client's corresponding selection key
     */
    public void removeClient(long clientId, SelectionKey key){
        // Log
        try {
            System.out.println("Disconnecting:" + this.clientMap.get(key).getChannel().getRemoteAddress() + ", total clients: " + this.clientMap.size());
        } catch (Exception ignored){}

        // Disconnect client's session
        this.clientMap.get(key).disconnect();

        // Remove selection key from the client map
        this.clientMap.remove(key);
    }

    /**
     * Check if the client is connected
     * @param id The client's id
     * @return True if the client is connected
     */
    private boolean isClientConnected(long id){
        return this.connectedClientsId.containsKey(id);
    }

    /**
     * Check if the client is connected
     * @param key The client's key
     * @return True if the client is connected
     */
    private boolean isClientConnected(SelectionKey key){
        return this.clientMap.containsKey(key);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * Singleton pattern
     * @return The instance of the server
     */
    public static Server getInstance(){
        return instance;
    }

    /**
     * @return The frequency of the server
     */
    public double getFrequency() {
        return this.frequency;
    }

    /**
     * @return The map between the connected clients' ids
     *          and their selection key
     */
    public Long[] getConnectedClientsId() {
        return this.connectedClientsId.keySet().toArray(new Long[0]);
    }
}
