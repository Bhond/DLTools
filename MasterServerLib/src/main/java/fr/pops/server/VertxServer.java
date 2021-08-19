package fr.pops.server;

import fr.pops.client.VertxClientSession;
import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquest.VertxRequestFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class VertxServer extends AbstractVerticle {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8163;
    private static final int NB_CORES_SERVER = 4;

    private NetServerOptions options;
    private NetServer server;
    private List<NetServer> multiCoreServer = new ArrayList<>();

    private HashSet<NetSocket> clientSockets = new HashSet<>();
    private VertxServerRequestHandler requestHandler;

    private HashMap<NetSocket, VertxClientSession> clientMap = new HashMap<>();
    private HashMap<Long, NetSocket> connectedClientsId = new HashMap<>();

    /**
     * Standard ctor
     */
    public VertxServer(){
        // Initialize the server
        this.onInit();
    }

    private void onInit(){

        // Vertx
        this.vertx = Vertx.vertx();

        // Options
        this.options = new NetServerOptions()
                .setHost(SERVER_IP)
                .setPort(SERVER_PORT);

        // Server
        for (int i = 0; i < NB_CORES_SERVER; i++){
            this.multiCoreServer.add(vertx.createNetServer(this.options));
        }

        // Request handler
        this.requestHandler = new VertxServerRequestHandler(this);
    }

    /**
     * Start the server
     */
    @Override
    public void start(){
        // Listen for incoming connections
        for (int i = 0; i < NB_CORES_SERVER; i++){
            this.multiCoreServer.get(i).connectHandler(this::onClientConnection);

            // Listen on incoming requests
            this.multiCoreServer.get(i).listen();
        }
    }

    /**
     * Listen for incoming client connection
     * @param netSocket The created socket when a new client is connecting
     */
    private void onClientConnection(NetSocket netSocket) {

        System.out.println("Client connected: " + netSocket.toString());

        RecordParser parser = RecordParser.newDelimited("\\n", netSocket);

        // Store connecting socket
        this.clientMap.put(netSocket, new VertxClientSession());

        // Setup request handling
        parser.handler(buffer -> {

            // Get request from header
            try {
                JsonObject o = new JsonObject(buffer);
                Request request = VertxRequestFactory.getRequest(o);
                // Process request
                if (request != null){
                    // Handle request
                    this.requestHandler.handle(netSocket, request);

                    // Post process request
                    this.postProcessRequest(request, netSocket);
                }
            } catch (DecodeException ignored) {
                //System.out.println("Decode exception raised when retrieving the request received: " + buffer);
            }
        });

        // Register the client
        this.clientSockets.add(netSocket);
    }

    /**
     * Post process the request
     * Check what to do with it:
     *      - Nothing
     *      - Write it back
     *      - Transfer
     * @param request The request to post process
     * @param senderSocket The sender socket to select the proper receiver
     */
    private void postProcessRequest(Request request, NetSocket senderSocket){
        // Select next operation
        EnumCst.RequestOperations operation = this.requestHandler.selectNextOperation(request);

        // Adapt to next the next operation
        switch (operation){
            case WRITE_BACK:
                senderSocket.write(request.toPlainJson().toBuffer().appendString("\\n"));
                break;
            case TRANSFER:
                this.transfer(request, senderSocket);
                break;
        }
    }

    /**
     * Transfer request
     * Select the receiver depending on the sender and the request's type
     * @param request The request to transfer
     * @param senderSocket The sender socket to select the proper receiver
     */
    private void transfer(Request request, NetSocket senderSocket) {
        long id = this.clientMap.get(senderSocket).getType().getId();
        long receiverId = this.requestHandler.selectReceiver(request, id);
        if (this.isClientConnected(receiverId)) {
            NetSocket receiverSocket = this.connectedClientsId.get(receiverId);
            receiverSocket.write(request.toPlainJson().toBuffer().appendString("\\n"));
        }
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
     * Add client id to a dictionary
     * Set the type of the client in the client session
     * @param clientId The client id to add
     * @param socket The socket of the client to add
     */
    public void addClient(long clientId, NetSocket socket) {
        this.connectedClientsId.put(clientId, socket);
        this.clientMap.get(socket).setType(fr.pops.sockets.cst.EnumCst.ClientTypes.getType(clientId));
    }

    /**
     * Remove client id from dictionaries
     * Close session
     * @param clientId The client id to remove
     * @param socket The socket to close
     */
    public void removeClient(long clientId, NetSocket socket) {
        this.connectedClientsId.remove(clientId);
        System.out.println("VertxServer::removeClient not fully implemented.");
    }
}
