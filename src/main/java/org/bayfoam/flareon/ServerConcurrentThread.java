package org.bayfoam.flareon;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConcurrentThread extends Thread{

    private final ServerSocket _Server;

    public ServerConcurrentThread(InetSocketAddress address) throws IOException {
        _Server = new ServerSocket(address.getPort());
        System.out.println("Listening for connection on port " + address.getPort());
    }

    @Override
    public void run() {

        while(_Server.isBound() && !_Server.isClosed()) {
            Socket client;
            try {
                client = _Server.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            HttpWorkerThread workerThread = new HttpWorkerThread(client);
            workerThread.start();

        }
    }
}
