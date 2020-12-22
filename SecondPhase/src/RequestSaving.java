import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class is just for saving requests
 */
public class RequestSaving {
    //temp list of requests to keep one group
    private static ArrayList<HttpRequest> tempGroup=new ArrayList<>();
    /**
     * saving request in group
     * @param group which group
     * @param request we want save this!!!
     */
    public static void saveRequest(String group,HttpRequest request) {
        //load requests to temp list
        loadRequests(group);
        //add it to temp
        tempGroup.add(request);
        //write it to file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("D:/Fourth semester/Advanced Programming/HW/HW5/2/MidTermProjectSecondPhase/Groups/"+group+".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(tempGroup);
            oos.close();
        } catch (IOException e) {
            System.out.println("failed to write it to file");
            e.printStackTrace();
        }
    }
    /**
     * load request to temp list
     * @param group which group
     */
    public static void loadRequests(String group) {
        File file =new File("D:/Fourth semester/Advanced Programming/HW/HW5/2/MidTermProjectSecondPhase/Groups/"+group+".txt");
        //existing file or not-empty or not
        if(file.exists()&&file.length()!=0){
            //reade it
            FileInputStream fis = null;
            try {
                fis = new FileInputStream("D:/Fourth semester/Advanced Programming/HW/HW5/2/MidTermProjectSecondPhase/Groups/"+group+".txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(fis);
                tempGroup = (ArrayList<HttpRequest>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("failed to load it");
                e.printStackTrace();
            }
        }
        else{
            System.out.println("no request." );
        }
    }
    /**
     * printing groups in Groups folder
     */
    public static void printGroups() {
        File file =new File("Groups");
        String[] groups=file.list();
        boolean flag=true;
        for (String s: groups) {
            int temp=s.lastIndexOf(".txt");
            System.out.println(s.substring(0,temp));
            if(s.substring(0,temp).equals("default"))
                flag=false;
        }
        if(flag) {
            try {
                new File("D:/Fourth semester/Advanced Programming/HW/HW5/2/MidTermProjectSecondPhase/Groups/default.txt").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * printing request in group's name
     * @param group group's name
     */
    public static void printRequests(String group) {
        loadRequests(group);
        int i=1;
        for (HttpRequest request: tempGroup) {
            String method="GET";
            if(request instanceof POSTRequest)
                method="POST";
            else if(request instanceof PUTRequest)
                method="PUT";
            else if(request instanceof DELETERequest)
                method="DELETE";
            System.out.println(i+" . url: "+request.getUrl().toString()+" | Method : "+method+" | Headers: "+(getHeaders(request.getHeaders()).isEmpty()?"-":getHeaders(request.getHeaders())));
            i++;

        }
    }

    /**
     * creating group in folder
     * @param group its name
     */
    public static void createGroup(String group){
        try {
            new File("D:/Fourth semester/Advanced Programming/HW/HW5/2/MidTermProjectSecondPhase/Groups/"+group+".txt").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param headers
     * @return
     */
    private static String getHeaders(HashMap<String,String> headers){
        String temp="";
        if(headers!=null)
            for (String s: headers.keySet()) {
                temp+=s;
                temp+=": ";
                temp+=headers.get(s);
                temp+=" , ";
            }
        return temp;
    }

    /**
     * get temp list
     * @return list of requests
     */
    public  static ArrayList<HttpRequest> getTempGroup(){
        return tempGroup;
    }
}
