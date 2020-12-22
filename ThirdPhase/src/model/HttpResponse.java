package model;

import view.Response;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private int statusCode;
    private String statusMessage;
    private Map<String,String> headers;
    private String messageBody;
    private long time;
    private double size;
    private boolean image;

    public HttpResponse(Map<String,String> headers, String messageBody, String statusMessage, int statusCode, long time, double size, boolean image) {
        this.headers = headers;
        this.messageBody = messageBody;
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
        this.time = time;
        this.size = size;
        this.image=image;
        Response.setHttpResponse(this);
    }

    public HttpResponse() {

    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }
}
