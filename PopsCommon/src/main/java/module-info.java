module PopsCommon {
    requires java.desktop;

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
    exports fr.pops.nn.popsmath;
    exports fr.pops.math.functions;
}