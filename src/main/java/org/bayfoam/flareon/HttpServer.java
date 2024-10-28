package org.bayfoam.flareon;

import org.bayfoam.flareon.concurrent.ServerConcurrentThread;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServer {
    private ServerConcurrentThread _ServerThread;
    private final InetSocketAddress address;
    private final int backlog;
    private final int poolSize;

    public HttpServer(InetSocketAddress address) throws IOException {
        this(address,2048,300);
    }
    public HttpServer(InetSocketAddress address, int backlog, int poolSize) throws IOException {
        this.address = address;
        this.backlog = backlog;
        this.poolSize = poolSize;
    }

    

    public void create() throws IOException {
        _ServerThread = new ServerConcurrentThread(address, backlog, poolSize);
    }

    public void start() {
        _ServerThread.start();
    }
}