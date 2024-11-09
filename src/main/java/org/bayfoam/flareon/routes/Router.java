package org.bayfoam.flareon.routes;

import org.bayfoam.flareon.http.HttpRequest;
import org.bayfoam.flareon.http.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {
    private static final Map<String,RouteHandler> routes = new HashMap<>();

    public static void addRoute(String path, RouteHandler handler) {
        routes.put(path,handler);
    }

    public static void handleRequest(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getRequestPath();
        List<String> pathSegments = new ArrayList<>();
        for (String segment : path.split("/")) {
            if (!segment.isEmpty()) {
                pathSegments.add(segment);
            }
        }

        RouteHandler handler = routes.get(path);

        if (handler != null) {
            handler.handle(request, response);
        }
    }

}
