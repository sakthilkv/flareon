package org.bayfoam.flareon;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer {
    private final ServerSocket _Server;
    public HttpServer(InetSocketAddress address) throws IOException {
        _Server = new ServerSocket(address.getPort());
        System.out.println("Listening for connection on port " + address.getPort());
    }

    public static HttpServer create(InetSocketAddress address, int backlog) throws IOException {
        return new HttpServer(address);
    }

    public void start() throws IOException {
        //noinspection InfiniteLoopStatement
        while(true) {
            try(Socket client = _Server.accept()) {
                InputStreamReader request = new InputStreamReader(client.getInputStream());
                HttpRequest req = new HttpRequest(request);
                System.out.println(req.getRequestBody());
                OutputStreamWriter response = new OutputStreamWriter(client.getOutputStream());
            }
        }
    }

    public void stop() throws IOException {
        _Server.close();
    }
}