# Flareon Documentation

## Overview
**Flareon** is a lightweight web application library built directly on sockets, designed to deliver fast and efficient HTTP request handling. Its simplicity and performance focus make it suitable for applications needing to handle high request volumes.

## Specifications
- **Performance:** Handles up to **20,000 requests per second** on a Windows PC. Optimized performance is achievable on Linux with better CPU and multiple cores.
- **Processing Time:** Approximately **20ms processing time per request**.

## Key Features
- **Static File Serving:** Efficiently serves static files, such as images, directly over HTTP.
- **Routing Capabilities:** Flareon supports defining custom routes with specific route handlers for flexible request handling.

## Sample Usage
This example demonstrates a basic HTTP server setup with Flareon, capable of:
- Handling an image request at `/pfp.png`
- Serving the current date at `/date`

### Code Example

First, set up the HTTP server and define the route handlers:

```java

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
            response.send(200, "OK", "text/html", new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8)));
        }
    }
    // more handlers...

}

```
Then, configure the main method to start the server and define your routes:
```java
public static void main(String[] args) throws IOException {
    HttpServer server = new HttpServer(new InetSocketAddress(8080));
    server.create();
    Router.addRoute("/date", new DateHandler());
    Router.addRoute("/event/123", new EventHandler());
    Router.addRoute("/pfp.png", new ImageHandler());
    server.start();
}
```
## Endpoints

- **`/pfp.png`** - Serves a static image (e.g., profile picture).
- **`/date`** - Responds with the current date in HTML format.

## Performance Considerations

- **Server Configuration:** For optimal performance, run Flareon on a Linux server with multiple CPU cores and adjust the server socket configuration as needed for high concurrency.
- **Request Rate Tuning:** Flareon's performance may vary based on server resources and network conditions; adjust socket timeout and buffer sizes for better stability under high loads.

## What I Learned

- Sockets programming
- HTTP protocols and request handling
- Reading and understanding documented procedures
- Threads and concurrency in Java
- Multi-threading for performance optimization
- Handling HTTP request methods (GET, POST, etc.)
- Server-side routing and route handlers
- Static file serving and content types
- Exception handling in networking contexts
- Implementing HTTP response status codes
- Performance tuning for high-concurrency applications
- Efficient input/output stream management
- Java's networking libraries and APIs
- Working with input/output streams for file handling
- Error handling and debugging in socket communication
