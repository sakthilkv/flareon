package org.bayfoam.flareon.concurrent;

import org.bayfoam.flareon.HttpRequest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
            OutputStreamWriter response = new OutputStreamWriter(client.getOutputStream());
            sleep(20);
            Date today = new Date();
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
            client.getOutputStream().write(httpResponse.getBytes(StandardCharsets.UTF_8));
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
