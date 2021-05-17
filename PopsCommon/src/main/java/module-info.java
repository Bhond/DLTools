module PopsCommon {
    requires java.desktop;
    requires org.json;

    // Beans
    exports fr.pops.beans.bean;

    // Test
    exports fr.pops.beans.test;
    exports fr.pops.sockets.communicationpipeline;
    exports fr.pops.sockets.cst;
    exports fr.pops.sockets.client;
    exports fr.pops.sockets.resquest;
    exports fr.pops.sockets.resquesthandler;
    exports fr.pops.commoncst;
    exports fr.pops.math;
    exports fr.pops.jsonparser;
    exports fr.pops.math.ndarray;

}