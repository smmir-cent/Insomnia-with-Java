import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{
        //Scanner scanner =new Scanner(System.in);
        //String[] args=scanner.nextLine().trim().split("");
        //printing groups
        if (args[0].equals("list") && args.length == 1) {
            RequestSaving.printGroups();
        }
        //printing requests in groups
        else if (args[0].equals("list")) {
            RequestSaving.printRequests(args[1]);

        }
        //send requests in groups
        else if (args[0].equals("fire")) {
            String group = args[1];
            RequestSaving.loadRequests(group);
            ArrayList<HttpRequest> requests = RequestSaving.getTempGroup();
            for (int i = 2; i < args.length; i++)
                requests.get((args[i].charAt(0)-'0')-1).method();
        }
        //creating group
        else if(args[0].equals("create")){
            RequestSaving.createGroup(args[1]);
        }
        else if (args[0].equals("-h") || args[0].equals("--help")) {
            menu();}
        else {

            URL url = new URL(args[0]);
            String method = "GET";
            String headers = null;
            boolean displayingHeader = false;
            boolean follow = false;
            String saveFilePath = null;
            String groupSave = null;
            String multipart = null;
            String uploadBinary = null;
            //determine args
            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("-M") || args[i].equals("--method")) {
                    i++;
                    method = args[i];
                } else if (args[i].equals("-H") || args[i].equals("--headers")) {
                    i++;
                    headers = args[i];
                } else if (args[i].equals("-i")) {
                    displayingHeader = true;
                } else if (args[i].equals("-h") || args[i].equals("--help")) {
                    menu();
                } else if (args[i].equals("-f")) {
                    follow = true;
                } else if (args[i].equals("-O") || args[i].equals("--output")) {

                    if ((i + 1) == args.length || args[i + 1].charAt(0) == '-') {
                        Date d=new Date();
                        saveFilePath = "output_" +d.toString().substring(0,10);
                    } else {
                        i++;
                        saveFilePath = args[i];
                    }
                } else if (args[i].equals("-S") || args[i].equals("--save")) {
                    if (args.length==i+1||args[i + 1].charAt(0) == '-'){
                        groupSave = "default";
                    }
                    else {
                        i++;
                        groupSave = args[i];
                    }
                } else if (args[i].equals("-d") || args[i].equals("--data")) {
                    i++;
                    multipart = args[i];
                }  else if (args[i].equals("--uploadBinary")) {
                    i++;
                    String path="";
                    int j=i;
                    for(;i!=args.length && args[i].charAt(0)!='-';i++){
                        path+=args[i];
                        path+=" ";
                    }
                    uploadBinary = path.substring(0,path.length()-1);
                } else if (args[i].equals("list") || args[i].equals("create") || args[i].equals("fire")  ) {
                    System.out.println(args[i]+"can't handle with other args("+args[i]+"must be first)");
                }
                else{
                    System.out.println(args[i]+" wasn't defined.");
                    return;
                }
            }
            //C:\Users\mohsen\Desktop\New folder (2)\ricardo-rocha-TtsEBUkwPs8-unsplash.jpg
            //set the method and send it
            switch (method) {
                case "GET": {
                    HttpRequest request = new GETRequest(url, convertingHeaders(headers), displayingHeader, follow, saveFilePath, groupSave);
                    request.method();
                    break;
                }
                case "POST": {
                    System.out.println("POST");
                    HttpRequest request = new POSTRequest(url, convertingHeaders(headers), displayingHeader, follow, saveFilePath, groupSave, uploadBinary, convertingHeaders(multipart));
                    request.method();
                    break;
                }
                case "DELETE": {
                    HttpRequest request = new DELETERequest(url, convertingHeaders(headers), displayingHeader, follow, saveFilePath, groupSave);
                    request.method();
                    break;
                }
                case "PUT": {
                    HttpRequest request = new PUTRequest(url, convertingHeaders(headers), displayingHeader, follow, saveFilePath, groupSave, uploadBinary, convertingHeaders(multipart));
                    request.method();
                    break;
                }
                default:{
                    System.out.println("method not defined");
                    break;
                }
            }
        }
    }

    /**
     * printing help args
     */
    public static void menu(){
        System.out.println("url must be first argument!");
        System.out.println("--method or -M : set request method(GET,PUT,DELETE,POST");
        System.out.println("--headers or -H : set headers");
        System.out.println("-i : to display headers that we received");
        System.out.println("-f : set follow redirect");
        System.out.println("--output or -O : save the response body");
        System.out.println("--save or -S : saving request to file");
        System.out.println("--data or -d : set message body (form data) ");
        System.out.println("--upload : set message body (binary file) ");
        System.out.println("list : displaying the name of groups");
        System.out.println("list [list name] : displaying the requests in list name ");
        System.out.println("fire : send the requests with their number in list name ");
    }

    /**
     * converting header string to header HashMap
     * @param headers header string
     * @return header HashMap
     */
    public static HashMap<String,String> convertingHeaders(String headers){
        if(headers==null)
            return null;
        HashMap<String,String> temp =new HashMap<>();
        if(headers==null)
            return new HashMap<>();
        String[] arrOfStr= headers.split("[:;]");
        for(int i=0;i<arrOfStr.length;i++){
            temp.put(arrOfStr[i],arrOfStr[i+1]);
            i++;
        }
        return temp;
    }
}

