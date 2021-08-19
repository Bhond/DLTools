open module PopsCommon {
    // Requires
    requires java.desktop;
    requires org.json;
    requires io.vertx.core;
    requires io.vertx.web.client;

    // Sockets
    exports fr.pops.sockets.communicationpipeline;
    exports fr.pops.sockets.cst;
    exports fr.pops.sockets.client;
    exports fr.pops.sockets.resquest;
    exports fr.pops.sockets.resquesthandler;

    // Common cst
    exports fr.pops.commoncst;

    // Math
    exports fr.pops.math;
    exports fr.pops.math.ndarray;

    // Json
    exports fr.pops.jsonparser;

    // Beans
    exports fr.pops.beans.bean;
    exports fr.pops.beans.beanloop;
    exports fr.pops.beans.beanobservable;
    exports fr.pops.beans.properties;
    exports fr.pops.beans.test;
    exports fr.pops.sockets.resquest.beanrequests;
    exports fr.pops.utils;

}