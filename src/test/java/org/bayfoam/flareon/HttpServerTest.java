package org.bayfoam.flareon;

import org.bayfoam.flareon.http.HttpServer;
import org.bayfoam.flareon.http.HttpRequest;
import org.bayfoam.flareon.http.HttpResponse;
import org.bayfoam.flareon.routes.Route;
import org.bayfoam.flareon.routes.RouteHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;



public class HttpServerTest {

    public static class DateHandler implements RouteHandler {
        @Override
        public void handle(HttpRequest request, HttpResponse response) throws IOException {
            Date today = new Date();
            response.send(200,"OK", "text/html", new StringBuffer("<html><h1>" + today + "</h1></html>"));
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(new InetSocketAddress(8080));
        server.create();
        Route.addRoute("/date", new DateHandler());
        server.start();
    }
}
