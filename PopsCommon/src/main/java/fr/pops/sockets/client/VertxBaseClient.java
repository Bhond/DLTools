package fr.pops.sockets.client;

import fr.pops.sockets.cst.EnumCst;
import fr.pops.sockets.resquest.AuthenticateRequest;
import fr.pops.sockets.resquest.DisconnectionRequest;
import fr.pops.sockets.resquest.Request;
import fr.pops.sockets.resquest.VertxRequestFactory;
import fr.pops.sockets.resquesthandler.RequestHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;

public class VertxBaseClient extends AbstractVerticle {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Static
    private final static String CLIENT_IP = "localhost";
    private final static int CLIENT_PORT = 8163;
    private final static int CLIENT_TIMEOUT = 10000;
    private final static int CLIENT_RECONNECT_ATTEMPTS = 10;
    private final static int CLIENT_RECONNECT_INTERVAL = 500;

    // General
    protected EnumCst.ClientTypes type;

    // Vertx
    private NetClientOptions options;
    private NetClient client;
    private NetSocket clientSocket;

    // Handler
    protected RequestHandler requestHandler;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    public VertxBaseClient(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param type The client's type
     */
    protected VertxBaseClient(EnumCst.ClientTypes type, RequestHandler requestHandler){
        // Initialisation
        this.type = type;
        this.requestHandler = requestHandler;
    }

    /*****************************************
     *
     * Vertx
     *
     *****************************************/
    /**
     * Start the verticle
     */
    @Override
    public void start(){

        // Vertx
        this.vertx = Vertx.vertx();

        // Options
        this.options = new NetClientOptions()
                .setConnectTimeout(CLIENT_TIMEOUT)
                .setReconnectAttempts(CLIENT_RECONNECT_ATTEMPTS)
                .setReconnectInterval(CLIENT_RECONNECT_INTERVAL);

        // Client
        this.client = this.vertx.createNetClient(this.options);
        this.client.connect(CLIENT_PORT, CLIENT_IP, res -> {
            if (res.succeeded()){

                /* Setup request handling */
                // Retrieve client's socket
                this.clientSocket = res.result();

                RecordParser parser = RecordParser.newDelimited("\\n", this.clientSocket);

                // Send authentication request
                this.send(new AuthenticateRequest(this.type.getId()));

                // Setup request's handler
                parser.handler(buffer -> {
                    // Get request from header
                    Request request = VertxRequestFactory.getRequest(new JsonObject(buffer));

                    // Process request
                    if (request != null){
                        // Handle request
                        this.requestHandler.handle(request);

                        // Post process request
                        this.requestHandler.postProcess(request);
                    }
                });

            } else {
                System.out.println("Failed to connect: " + res.cause().getMessage());
            }
        });

    }

    /**
     * Close the client
     */
    public void close(){
        this.send(new DisconnectionRequest(this.getType().getId()));
        this.clientSocket.close();
        System.out.println("Implementation not finished yet...");
    }

    /*****************************************
     *
     * Handling requests
     *
     *****************************************/
    /**
     * Send the input request
     * @param request The request to send
     */
    public void send(Request request){
        this.clientSocket.write(request.toPlainJson().toBuffer().appendString("\\n"));
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The client's id
     */
    public final EnumCst.ClientTypes getType(){
        return this.type;
    }

    /**
     * @return The request handler
     */
    public RequestHandler getRequestHandler() {
        return this.requestHandler;
    }

}
