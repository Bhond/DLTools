module PopsCommon {
    requires java.desktop;

    // Beans
    exports fr.pops.beans.bean;

    // Test
    exports fr.pops.beans.test;
    exports fr.pops.sockets.streamhandler;
    exports fr.pops.sockets.resquesthandler;
    exports fr.pops.sockets.cst;
}