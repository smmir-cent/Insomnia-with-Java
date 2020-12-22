import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 * this class represents a response panel.
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class Response {
    //main response panel
    private JPanel response;
    //to keep text fields data
    private ArrayList<JTextField> textFields;

    /**
     * first init of Response
     * @param createRequestPanel request panel data needed in future
     */
    public Response( CreateRequestPanel createRequestPanel ){
        textFields=new ArrayList<>();
        response=new JPanel();
        this.textFields=createRequestPanel.getTexts();
        //prepare panel
        prepareGui();
    }

    /**
     * get the panel
     * @return main response panel
     */
    public JPanel getPanel(){
        return response;
    }

    /**
     * this method creates main response panel.
     */
    public void prepareGui(){
        JPanel labels=new JPanel();
        labels.setLayout(new FlowLayout());
        //3 label to show info
        JLabel statusCode=new JLabel("200 OK");
        JLabel time=new JLabel("6.64 s");
        JLabel size=new JLabel("13.1 KB");
        labels.add(statusCode);
        labels.add(time);
        labels.add(size);
        //create tabs header and body
        JTabbedPane tabs=new JTabbedPane();
        tabs.add("Header",header());
        tabs.add("Body",body());
        JPanel main=new JPanel();
        main.setLayout(new BorderLayout());
        main.add(labels,BorderLayout.NORTH);
        main.add(tabs);
        response.add(main);
    }

    /**
     * create header panel
     * @return header panel
     */
    public JPanel header(){
        JPanel header=new JPanel();
        header.setLayout(new GridLayout(0,2));
        //just for example some texts added
        for (int i=1;i<=4;i++){
            JTextField text=new JTextField("JTextField"+i);
            text.setEditable(false);
            header.add(text);
        }
        JLabel label=new JLabel();
        header.add(label);
        JButton copy=new JButton("Copy to clipboard");
        header.add(copy);
        return header;
    }
    /**
     * create body tab
     * @return body tab
     */
    public JTabbedPane body(){
        JTabbedPane body=new JTabbedPane();
        //adding raw panel
        body.add("Raw",raw());
        //adding preview panel
        body.add("Preview",preview());
        return body;
    }

    /**
     * create raw panel
     * @return raw panel
     */
    public JPanel raw(){
        JPanel body=new JPanel();
        //just for example
        JTextArea temp=new JTextArea("just for displaying this part");
        temp.setEditable(false);
        body.add(temp);
        return body;
    }

    /**
     * create preview panel
     * @return preview panel
     */
    public JPanel preview(){
        JPanel preview=new JPanel();
        //adding an image to panel for example
        ImageIcon icon = new ImageIcon("download.png");
        JLabel label = new JLabel(icon);
        preview.add(label);
        return preview;
    }
}