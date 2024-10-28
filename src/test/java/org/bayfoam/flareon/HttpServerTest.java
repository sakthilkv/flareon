package org.bayfoam.flareon;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerTest {
    public static void main(String[] args) throws IOException {
        ServerConcurrentThread server = new ServerConcurrentThread(new InetSocketAddress(8080));
        server.start();
    }
}
