package org.bayfoam.flareon.concurrent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConcurrentThread extends Thread{

    private final ServerSocket _Server;
    private final ExecutorService executor;

    public ServerConcurrentThread(InetSocketAddress address, int backlog, int poolSize) throws IOException {
        _Server = new ServerSocket(address.getPort(), backlog);
        executor = Executors.newFixedThreadPool(poolSize);
        System.out.println("Listening for connection on port " + address.getPort());
    }

    @Override
    public void run() {

        while(_Server.isBound() && !_Server.isClosed()) {

            try {
                Socket client = _Server.accept();
                executor.submit(new HttpWorkerThread(client));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
