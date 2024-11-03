package org.bayfoam.flareon.concurrent;

import org.bayfoam.flareon.http.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConcurrentThread extends Thread{

    private final ServerSocket _Server;
    private final ExecutorService executor;
    private final HttpServer httpServer;

    public ServerConcurrentThread(InetSocketAddress address, int backlog, int poolSize, HttpServer server) throws IOException {
        _Server = new ServerSocket(address.getPort(), backlog);
        executor = Executors.newFixedThreadPool(poolSize);
        this.httpServer = server;
        System.out.println("Listening for connection on port " + address.getPort());
    }

    @Override
    public void run() {

        while(_Server.isBound() && !_Server.isClosed()) {
            try {
                Socket client = _Server.accept();
                executor.submit(new HttpWorkerThread(client, httpServer));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
