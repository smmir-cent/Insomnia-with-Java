package model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
/**
 * this class represents a request whose method is PUT.
 * @author Seyyed Mahdi Mirfendereski
 * @version 0.0
 */
public class PUTRequest extends HttpRequest {
    //binary path
    private String uploadBinary;
    //key-value of body
    private HashMap<String, String>  multiPartBody;
    public PUTRequest(URL url, HashMap<String, String> headers, boolean displayingHeader, boolean follow, String saveFilePath, String groupSave, String uploadBinary, HashMap<String, String>  multiPartBody) {
        super(url, headers,displayingHeader,follow,saveFilePath,groupSave);
        this.uploadBinary = uploadBinary;
        this.multiPartBody=multiPartBody;
    }
    @Override
    public HttpResponse method() {
        try {
            super.setConnection((HttpURLConnection)((super.getUrl()).openConnection()));
            super.getConnection().setRequestMethod("PUT");
            HashMap<String,String> headers=super.getHeaders();
            if(headers!=null&&!headers.isEmpty())
                for (String s : headers.keySet())
                    super.getConnection().setRequestProperty(s, headers.get(s));
        }catch (IOException e){
            e.printStackTrace();

        }

        //upload binary
        //if(multiPartBody==null){
        //    return uploadBinary();
        //}
        ////multipart body
        //else
            return formData();
    }
    /**
     * this method is for upload Binary
     * @return response object
     */
    public HttpResponse uploadBinary() {
        try {
            File file = new File(uploadBinary);
            HttpURLConnection connection = super.getConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedOutputStream.write(fileInputStream.readAllBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            return super.getResponse();
        } catch (IOException  e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * for creating request body to sent.
     * @param body key-value body
     * @param boundary to separate different parts
     * @param bufferedOutputStream to write it
     */
    public void bufferOutFormData(HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(body.get(key))));
                    byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                    bufferedOutputStream.write(filesBytes);
                    bufferedOutputStream.write("\r\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
            }
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
    /**
     * to send multipart data
     * @return response object
     */
    public HttpResponse formData() {
        try {
            //File file = new File(filePath);
            HttpURLConnection connection = super.getConnection();
            String boundary = System.currentTimeMillis() + "";
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
            bufferOutFormData(multiPartBody, boundary, request);
            return super.getResponse();
        } catch (Exception e) {
            return null;
        }
    }
    public String getUploadBinary() {
        return uploadBinary;
    }

    public void setUploadBinary(String uploadBinary) {
        this.uploadBinary = uploadBinary;
    }

    public HashMap<String, String> getMultiPartBody() {
        return multiPartBody;
    }

    public void setMultiPartBody(HashMap<String, String> multiPartBody) {
        this.multiPartBody = multiPartBody;
    }
}
