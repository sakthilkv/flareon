package org.bayfoam.flareon.http;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HttpResponse {
    OutputStream _ResponseStream;
    private int _statusCode;
    private String _statusMessage;
    private StringBuffer _responseBody;
    private String _contentType;
    //private Hashtable<String,String> _responseHeaders;
    private String _Response;

    public HttpResponse(OutputStream responseStream) {
        this._ResponseStream = responseStream;
    }

    private String createResponseHeader(long contentLength) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String PROTOCOL = "HTTP/1.1";
        String CRLF = "\r\n";
        String SPACE = " ";
        String SERVER = "Flareon/1.0";

        return PROTOCOL + SPACE + _statusCode + SPACE + _statusMessage + CRLF
                + "Content-Type: " + _contentType + CRLF
                + "Content-Length: " + contentLength + CRLF
                + "Connection: keep-alive" + CRLF
                + "Date: " + formatter.format(new Date()) + CRLF
                + "Server: " + SERVER + CRLF + CRLF;
    }

    private long getContentLength(InputStream contentStream) throws IOException {
        if (contentStream instanceof java.io.ByteArrayInputStream) {
            return contentStream.available();
        } else {
            return -1;
        }
    }

    public void send(int statusCode, String statusMessage, String contentType, InputStream contentStream) throws IOException {
        _statusCode = statusCode;
        _statusMessage = statusMessage;
        _contentType = contentType;

        long contentLength = contentStream.available();
        if (contentLength == -1) {
            throw new IOException("Content length cannot be determined.");
        }


        String header = createResponseHeader(contentLength);
        _ResponseStream.write(header.getBytes(StandardCharsets.UTF_8));

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = contentStream.read(buffer)) != -1) {
            _ResponseStream.write(buffer, 0, bytesRead);
        }

        _ResponseStream.flush();
    }

    public void send(String contentType, StringBuffer content) throws IOException {
        send(200, "OK", contentType, new java.io.ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8)));
    }

    public void sendFile(File file) throws IOException {
        try (InputStream fileInputStream = new FileInputStream(file)) {
            send(200, "OK", "application/octet-stream", fileInputStream);
        }
    }

}