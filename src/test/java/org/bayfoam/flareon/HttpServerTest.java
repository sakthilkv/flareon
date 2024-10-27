package org.bayfoam.flareon;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerTest {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.start();
    }
}
