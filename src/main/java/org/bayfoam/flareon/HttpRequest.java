package org.bayfoam.flareon;

import org.bayfoam.flareon.exceptions.HttpFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class HttpRequest {
    private String[] _requestLine;
    private final Hashtable<String, String> _requestHeaders;
    private final StringBuffer _messageBody;


    public HttpRequest(InputStreamReader requestStream) throws IOException {
        _requestHeaders = new Hashtable<String, String>();
        _messageBody = new StringBuffer();
        parseRequest(requestStream);
    }

    private void parseRequest(InputStreamReader requestStream) throws IOException, HttpFormatException {
        BufferedReader reader = new BufferedReader(requestStream);

        setRequestLine(reader.readLine());

        String header = reader.readLine();
        long postion = header.length() + System.lineSeparator().length();
        while (!header.isEmpty()) {
            appendHeaderParameter(header);
            header = reader.readLine();
            postion += header.length() + System.lineSeparator().length();
        }

        while (reader.ready()) {
            appendMessageBody((char) reader.read());
        }

    }

    public void setRequestLine(String requestLine) throws HttpFormatException {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new HttpFormatException("Invalid Request-Line: "+  requestLine);
        }
        _requestLine = requestLine.split(" ");
    }

    private void appendHeaderParameter(String header) throws HttpFormatException {
        int idx = header.indexOf(":");
        if (idx == -1){
            throw new HttpFormatException("Invalid Header Parameter: "+header);
        }
        _requestHeaders.put(header.substring(0,idx),header.substring(idx+2));
    }

    private void appendMessageBody(char body) {
        _messageBody.append(body);
    }

    public String getRequestMethod() {
        return _requestLine[0];
    }

    public String getRequestURI() {
        return _requestLine[1];
    }

    public String getProtocol() {
        return _requestLine[2];
    }

    public Hashtable<String, String> getRequestHeaders() {
        return _requestHeaders;
    }

    public StringBuffer getRequestBody() {
        return _messageBody;
    }

}
