package org.bayfoam.flareon.concurrent;

import org.bayfoam.flareon.http.HttpRequest;
import org.bayfoam.flareon.http.HttpResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class HttpWorkerThread extends Thread{

    private final Socket client;

    public HttpWorkerThread(Socket socket) {
        this.client = socket;
    }


    @Override
    public void run() {
        try {
            InputStreamReader request = new InputStreamReader(client.getInputStream());
            HttpRequest req = new HttpRequest(request);
            sleep(20);
            Date today = new Date();
            HttpResponse response = new HttpResponse(new OutputStreamWriter(client.getOutputStream()));
            response.send(400,"OK", "text/html", new StringBuffer("<html><h1>" + today + "</h1></html>"));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                client.close(); // Ensure socket is closed after processing
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
