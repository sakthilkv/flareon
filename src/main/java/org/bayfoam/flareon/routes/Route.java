package org.bayfoam.flareon.routes;

import org.bayfoam.flareon.http.HttpRequest;
import org.bayfoam.flareon.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Route {
    private static final Map<String,RouteHandler> routes = new HashMap<>();

    public static void addRoute(String path, RouteHandler handler) {
        routes.put(path,handler);
    }

    public static void handleRequest(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getRequestURI();
        RouteHandler handler = routes.get(path);

        if (handler != null) {
            handler.handle(request, response);
        }
    }
}
