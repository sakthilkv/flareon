package org.bayfoam.flareon.concurrent;

import org.bayfoam.flareon.http.HttpRequest;
import org.bayfoam.flareon.http.HttpResponse;
import org.bayfoam.flareon.http.HttpServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class HttpWorkerThread extends Thread{

    private final Socket client;
    private final HttpServer httpServer;
    public HttpWorkerThread(Socket socket, HttpServer httpServer) {
        this.client = socket;
        this.httpServer = httpServer;
    }


    @Override
    public void run() {
        try {
            InputStreamReader requestReader = new InputStreamReader(client.getInputStream());
            HttpRequest req = new HttpRequest(requestReader);
            HttpResponse res = new HttpResponse(client.getOutputStream());

            httpServer.handleRequest(req, res);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
