package view;

import model.HttpRequest;
import model.HttpResponse;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class BackGroundConnection extends SwingWorker<HttpResponse,HttpRequest> {
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public BackGroundConnection(HttpRequest httpRequest/*, logic.HttpResponse httpResponse*/) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    @Override
    protected HttpResponse doInBackground() throws Exception {
        return httpRequest.method();
    }
    protected void done(){
        try {
            Response.setHttpResponse(get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
