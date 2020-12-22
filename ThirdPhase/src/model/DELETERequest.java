package model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
/**
 * this class represents a request whose method is DELETE.
 * @author Seyyed Mahdi Mirfendereski
 * @version 0.0
 */
public class DELETERequest extends HttpRequest {
    /**
     *
     * @param url its url(link)
     * @param headers does it have headers to send?
     * @param displayingHeader must we print received headers or not
     * @param follow must we follow redirect or not
     * @param saveFilePath where must response saved
     * @param groupSave to which groups must save it
     */
    public DELETERequest(URL url, HashMap<String, String> headers,boolean displayingHeader,boolean follow,String saveFilePath,String groupSave) throws IOException {
        super(url, headers,displayingHeader,follow,saveFilePath,groupSave);
    }
    /**
     * opening a connection to receive response and get response with getResponse method
     * @return response object to show in gui part.
     */
    @Override
    public HttpResponse method() {
        try {
            super.setConnection((HttpURLConnection)((super.getUrl()).openConnection()));
            super.getConnection().setRequestMethod("DELETE");
            HashMap<String,String> headers=super.getHeaders();
            if(headers!=null&&!headers.isEmpty())
                for (String s : headers.keySet())
                    super.getConnection().setRequestProperty(s, headers.get(s));
        } catch (IOException e) {
            System.out.println("failed to open a connection or failed to set delete method");
            e.printStackTrace();
        }
        return super.getResponse();
    }

}
