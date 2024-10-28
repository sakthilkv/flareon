package org.bayfoam.flareon;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpServer {
    private final ServerSocket _Server;
    private final ExecutorService executor;

    public HttpServer(InetSocketAddress address, int threadPoolSize) throws IOException {
        _Server = new ServerSocket(address.getPort());
        executor = Executors.newFixedThreadPool(threadPoolSize);
        System.out.println("Listening for connection on port " + address.getPort());
    }

    public static HttpServer create(InetSocketAddress address, int threadPoolSize, int backlog) throws IOException {
        return new HttpServer(address, threadPoolSize);
    }

    public void start() throws IOException {
        //noinspection InfiniteLoopStatement
        while(true) {
            try(Socket client = _Server.accept()){
                executor.submit(() -> {
                    try {
                        handleClient(client);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

        }
    }

    public void handleClient(Socket client) throws IOException {
        InputStreamReader request = new InputStreamReader(client.getInputStream());
        HttpRequest req = new HttpRequest(request);
        System.out.println(req.getRequestBody());
        Date today = new Date(); String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
        client.getOutputStream() .write(httpResponse.getBytes(StandardCharsets.UTF_8));
    }
    public void stop() throws IOException {
        _Server.close();
    }
}