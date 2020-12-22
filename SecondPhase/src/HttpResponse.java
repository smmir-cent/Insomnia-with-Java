import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class represent response object in gui part.
 */
public class HttpResponse {
    private Map<String,String> headers;
    private String messageBody;
    private String statusMessage;
    private int statusCode;
    private int time;
    private double size;
    private boolean image;
    public HttpResponse(Map<String, String> headers, String messageBody, String statusMessage, int statusCode, int time, double size, boolean image) {
        this.headers = headers;
        this.messageBody = messageBody;
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
        this.time = time;
        this.size = size;
        this.image=image;
        //Response.setHttpResponse(this);
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

    public void setHeaders(HashMap<String,String> headers) {
        this.headers = headers;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
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
