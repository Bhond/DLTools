package fr.pops.viewmodels;

import fr.pops.client.Client;
import fr.pops.controllers.viewcontrollers.ServerInfoController;
import fr.pops.sockets.resquest.PingRequest;

public class ServerInfoModel extends BaseModel<ServerInfoController>{

    private ServerInfoController controller;

    public ServerInfoModel(ServerInfoController controller){
        super(controller);

    }

    @Override
    public void update() {
        // Ping server to get its repsonse delay
        PingRequest ping = new PingRequest();
        Client.getInstance().send(ping);
    }

    public ServerInfoController getController() {
        return this.controller;
    }
}
