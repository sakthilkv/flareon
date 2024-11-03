package org.bayfoam.flareon.routes;

import org.bayfoam.flareon.http.HttpRequest;
import org.bayfoam.flareon.http.HttpResponse;

import java.io.IOException;

public interface RouteHandler {
    void handle (HttpRequest request, HttpResponse response) throws IOException;
}
