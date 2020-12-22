/**
 * this class represents a request and contains name,url and its order.
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class Request {
    //its name
    private String name;
    //its url
    private String url;
    //its order
    private String order;

    /**
     * create req. with 3 string
     * @param name its name
     * @param url its url
     * @param order its order
     */
    public Request(String name, String url, String order) {
        this.name = name;
        this.url = url;
        this.order = order;
    }

    /**
     * this method is for displaying saved req.
     * @return string that includes its info
     */
    public String getInfo(){
     return order+" : "+name;
    }

}
