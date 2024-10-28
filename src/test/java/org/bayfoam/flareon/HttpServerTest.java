package org.bayfoam.flareon;

import org.bayfoam.flareon.http.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerTest {
    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(new InetSocketAddress(8080));
        server.create();
        server.start();
    }
}
