package org.bayfoam.flareon.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HttpResponse {
    OutputStreamWriter _ResponseStream;
    private int _statusCode;
    private String _statusMessage;
    private StringBuffer _responseBody;
    private String _contentType;
    //private Hashtable<String,String> _responseHeaders;
    private String _Response;

    public HttpResponse(OutputStreamWriter responseStream) {
        this._ResponseStream = responseStream;
    }

    private String createResponse() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String PROTOCOL = "HTTP/1.1";
        String CRLF = "\r\n";
        String SPACE = " ";
        String SERVER = "Flareon/1.0";
        String _Header = PROTOCOL + SPACE + _statusCode + SPACE + _statusMessage + CRLF
                + "Content-Type: " + _contentType + CRLF
                + "Content-Length: " + _responseBody.toString().getBytes(StandardCharsets.UTF_8).length + CRLF
                + "Connection: keep-alive" + CRLF
                + "Date: " + formatter.format(new Date()) + CRLF
                + "Server: " + SERVER + CRLF;
        return _Header + CRLF + _responseBody.toString();
    }

    public void send(int statusCode, String statusMessage, String contentType, StringBuffer content) throws IOException {
        _statusCode = statusCode;
        _statusMessage = statusMessage;
        _contentType = contentType;
        _responseBody = content;
        _ResponseStream.write(createResponse());
        _ResponseStream.flush();
    }

    public void send(String contentType, StringBuffer content) throws IOException {
        _statusCode = 200;
        _statusMessage = "OK";
        _contentType = contentType;
        _responseBody = content;
        _ResponseStream.write(createResponse());
        _ResponseStream.flush();
    }

}