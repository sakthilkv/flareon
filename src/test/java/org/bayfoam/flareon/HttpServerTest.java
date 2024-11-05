package org.bayfoam.flareon;

import org.bayfoam.flareon.http.HttpServer;
import org.bayfoam.flareon.http.HttpRequest;
import org.bayfoam.flareon.http.HttpResponse;
import org.bayfoam.flareon.routes.RouteHandler;
import org.bayfoam.flareon.routes.Router;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;



public class HttpServerTest {
    public static class ImageHandler implements RouteHandler {
        @Override
        public void handle(HttpRequest request, HttpResponse response) throws IOException {
            String imageName = "pfp.jpg"; // Adjust to your image file name
            InputStream imageStream = HttpResponse.class.getResourceAsStream("/" + imageName);

            if (imageStream == null) {
                String notFoundHtml = "<html><h1>404 Not Found</h1></html>";
                response.send(404, "Not Found", "text/html",
                        new ByteArrayInputStream(notFoundHtml.getBytes(StandardCharsets.UTF_8)));
            } else {
                response.send(200, "OK", "image/jpeg", imageStream);
            }
        }
    }

    public static class DateHandler implements RouteHandler {
        @Override
        public void handle(HttpRequest request, HttpResponse response) throws IOException {
            Date today = new Date();
            String responseBody = "<html><h1>" + today + "</h1></html>";
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8))) {
                response.send(200, "OK", "text/html", inputStream);
            }
        }
    }

    public static class EventHandler implements RouteHandler {
        @Override
        public void handle(HttpRequest request, HttpResponse response) throws IOException {
            Date today = new Date();
            String responseBody = "<html><h1>" + today + "</h1></html>";
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8))) {
                response.send(200, "OK", "text/html", inputStream);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(new InetSocketAddress(8080));
        server.create();
        Router.addRoute("/date", new DateHandler());
        Router.addRoute("/event/123", new EventHandler());
        Router.addRoute("/pfp.png", new ImageHandler());
        server.start();
    }
}
