import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 * this class represents saved requests.
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class PreviousRequestsPanel {
    //list of requests
    private ArrayList<Request> requests;
    //main Previous Requests Panel
    private JPanel panel;
    /**
     * first init of PreviousRequestsPanel
     */
    public PreviousRequestsPanel(){
        panel=new JPanel();
        panel.setPreferredSize(new Dimension(400,400));
        panel.setLayout(new GridLayout(20,1));
        panel.add(new JLabel("     Saved Requests     "),JLabel.CENTER);
        requests=new ArrayList<>();
    }

    /**
     * adding a request to saved requests and display it
     * @param request which req.
     */
    public void addRequest(Request request){
        requests.add(request);
        JLabel info=new JLabel(request.getInfo());
        panel.add(info);
        //repaint panel to display new req.
        panel.revalidate();
        panel.repaint();
    }

    /**
     * get the main Previous Requests Panel
     * @return main Previous Requests Panel
     */
    public JPanel getPanel(){
        return panel;
    }

}
