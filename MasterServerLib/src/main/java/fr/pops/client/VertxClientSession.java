package fr.pops.client;

import fr.pops.sockets.cst.EnumCst;
import io.vertx.core.AbstractVerticle;

public class VertxClientSession extends AbstractVerticle{

    private EnumCst.ClientTypes type;

    /**
     * Standard ctor
     */
    public VertxClientSession(){

    }

    @Override
    public void start(){

    }

    public EnumCst.ClientTypes getType() {
        return type;
    }

    public void setType(EnumCst.ClientTypes type) {
        this.type = type;
    }
}
