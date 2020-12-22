package model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * this class represents a request.
 * @author Seyyed Mahdi Mirfendereski
 * @version 0.0
 */
public abstract class  HttpRequest implements Serializable {
    //HttpRequest's url
    private URL url;
    //HttpRequest's headers(hash Map)
    private HashMap<String,String> headers;
    //show response's header or not
    private boolean displayingHeader;
    //follow redirect or not
    private boolean follow;
    //where must we save response
    private String saveFilePath;
    //where must save request
    private String groupSave;
    //connection to get response
    private transient HttpURLConnection connection;
    /**
     *
     * @param url request's url
     * @param headers request's header
     * @param displayingHeader show response's header or not
     * @param follow follow redirect or not
     * @param saveFilePath where must we save response
     * @param groupSave where must save request
     */
    public HttpRequest( URL url, HashMap<String, String> headers,boolean displayingHeader,boolean follow,String saveFilePath,String groupSave) {
        this.url = url;
        this.headers = headers;
        this.displayingHeader=displayingHeader;
        this.follow=follow;
        this.saveFilePath=saveFilePath;
        this.groupSave=groupSave;
    }
    /**
     * get connection to set its method in sub-classes
     * @return connection
     */
    public HttpURLConnection getConnection() {
        return connection;
    }

    /**
     * this method is for customize the request.
     * @return response for gui part
     */
    public abstract HttpResponse method();
    /**
     * this method is for saving text,html, ... response
     * @param saveFilePath where it must save
     * @param content its content as string
     */
    public int output(String saveFilePath, String content){
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(saveFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("initial of creating file failed");
            e.printStackTrace();
        }
        byte[] strToBytes = content.getBytes();
        int size=strToBytes.length;
        try {
            //write in file
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (IOException e) {
            System.out.println("writing in file failed");
            e.printStackTrace();
        }
        return size;
    }
    /**
     * this method is for saving image response
     * @param saveFilePath where it must save
     * @param inputStream what must be write in file
     */
    public int outputImage(String saveFilePath, InputStream inputStream,String response) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(saveFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("initial of creating file failed");
            e.printStackTrace();
        }
        int bytesRead = -1;
        byte[] buffer = new byte[16384];
        //for get size
        int size=0;
        //write in file
        try {
            while (true){ if ((bytesRead = inputStream.read(buffer)) == -1)
                break;
            size+=bytesRead;
            outputStream.write(buffer, 0, bytesRead);}
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * receive response and display
     * @return httpResponse
     */
    public HttpResponse getResponse() {
        BufferedReader reader;
        int status = 0;
        StringBuilder response = new StringBuilder();
        String message=null;
        InputStream inputStream = null;
        //saving request
        if (groupSave!=null)
         RequestSaving.saveRequest(groupSave,this);
        //set follow redirect
        if (follow){
            connection.setInstanceFollowRedirects(true);
        }
        long elapsedTime = 0;
        try {
            //get response
            long starTime = System.currentTimeMillis();
            status=connection.getResponseCode();
            message=connection.getResponseMessage();
            String responseSingle ;
            inputStream=connection.getInputStream();
            elapsedTime = System.currentTimeMillis() - starTime;
            String xx = null;
            System.out.println(status+" "+message);
            reader =new BufferedReader(new InputStreamReader(inputStream));
            if(connection.getHeaderFields().containsKey("Content-Type")&&connection.getHeaderField("Content-Type").contains("image")){
                while ((responseSingle = reader.readLine()) != null) {
                    response.append(responseSingle);
                    response.append("\n");
                }
                xx = response.toString();
                System.out.println(xx);
            }
            else{
                while ((responseSingle = reader.readLine()) != null) {
                    response.append(responseSingle);
                    response.append("\n");
                }
                xx = response.toString();
                System.out.println(xx);
            }
            //displaying header
            if(displayingHeader)
                displayingResponseHeader();
            System.out.println("================================");
            //saving file
            if(saveFilePath!=null)
                savingFile(inputStream,xx);
        }catch (IOException e){
            e.printStackTrace();
        }
        int size=0;
        if(connection.getHeaderFields().containsKey("Content-Type")&&connection.getHeaderField("Content-Type").contains("image")){
            try {
                size=savingFile(connection.getInputStream(),"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            size=response.toString().getBytes().length;
        }
        //for displaying in gui
        HttpResponse httpResponse;
        if(connection.getHeaderFields().containsKey("Content-Type")&&connection.getHeaderField("Content-Type").contains("image")){
            HashMap<String,String> temp=new HashMap<>();
            for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                if(entry.getKey()!=null)
                    temp.put(entry.getKey(),entry.getValue().toString());
            }

            httpResponse=new HttpResponse(temp,response.toString(),message,status,elapsedTime,size,true);
        }
        else{
            HashMap<String,String> temp=new HashMap<>();
            for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                if(entry.getKey()!=null)
                    temp.put(entry.getKey(),entry.getValue().toString());
            }
            httpResponse=new HttpResponse(temp,response.toString(),message,status, elapsedTime,size,false);
        }
        return httpResponse;
    }
    /**
     * get url
     * @return its url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * get request's headers
     * @return headers
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }
    /**
     * printing response's headers
     */
    public void displayingResponseHeader(){
        Map<String, List<String>> map = connection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if(entry.getKey()!=null)
                System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue());
        }
    }

    /**
     * saving response in system
     * @param inputStream for image
     * @param xx for text,html,...
     */
    public int savingFile(InputStream inputStream,String xx){
        if(connection.getHeaderFields().containsKey("Content-Type") && connection.getHeaderField("Content-Type").contains("image"))
            return outputImage(saveFilePath,inputStream,xx);
        else
             return output(saveFilePath,xx);
    }
    /**
     * swt request's connection
     * @param connection request's connection
     */
    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    /**
     * set request's url
     * @param url request's url
     */
    public void setUrl(URL url) {
        this.url = url;
    }
}
