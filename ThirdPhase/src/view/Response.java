package view;
import model.HttpResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * this class represents a response panel.
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class Response {
    //main response panel
    private static JPanel response;
    private static JLabel status;
    private static JLabel time;
    private static JLabel size;
    private static JPanel raw;
    private static JPanel preview;
    private static JPanel header;
    private static JTabbedPane body;
    private static JTabbedPane tabs;
    private static HashMap<String,String> headers;
    private static JButton copy;

    /**
     * first init of gui.Response
     */
    public Response( /*CreateRequestPanel createRequestPanel*/ ){
        status =new JLabel("200 OK");
        time=new JLabel("6.64 s");
        size=new JLabel("13.1 KB");
        tabs=new JTabbedPane();
        headers=new HashMap<>();
        header=header();
        raw=raw("");
        preview=preview(false);

        response=new JPanel();
        response.setLayout(new BorderLayout());
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
    public static void prepareGui(){
        JPanel labels=new JPanel();
        labels.setLayout(new FlowLayout());
        //3 label to show info
        labels.add(status);
        labels.add(time);
        labels.add(size);
        //create tabs header and body
        body=body(raw,preview);
        tabs.add("Header",header);
        tabs.add("Body",body);
        response.add(labels,BorderLayout.NORTH);
        response.add(tabs);
    }

    /**
     * create header panel
     * @return header panel
     */
    public static JPanel header(){
        JPanel header=new JPanel();
        header.setLayout(new GridLayout(headers.keySet().size()+1,2));
        //just for example some texts added
        for (String s : headers.keySet()) {
            if(s!=null){
                JTextField key=new JTextField(s);
                key.setEditable(false);
                header.add(key);
                JTextField value=new JTextField(headers.get(s));
                value.setEditable(false);
                header.add(value);
            }
        }
        JLabel label=new JLabel();
        header.add(label);
        copy=new JButton("Copy to clipboard");
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                StringSelection stringSelection = new StringSelection(Response.getFormattedHeaders());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
        header.add(copy);
        return header;
    }
    /**
     * create body tab
     * @return body tab
     */
    public static JTabbedPane body(JPanel raw, JPanel preview){
        JTabbedPane body=new JTabbedPane();
        //adding raw panel
        body.add("Raw",raw);
        //adding preview panel
        body.add("Preview",preview);
        return body;
    }

    /**
     * create raw panel
     * @return raw panel
     */
    public static JPanel raw(String messageBody){
        JPanel body=new JPanel();
        body.setLayout(new BorderLayout());
        //just for example
        JTextArea temp=new JTextArea();
        temp.setText(messageBody);
        temp.setPreferredSize(new Dimension(600,Integer.MAX_VALUE));
        temp.setLineWrap(true);
        temp.setEditable(false);
        temp.setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane (temp);
        scroll.setPreferredSize(new Dimension(650,Integer.MAX_VALUE));
        body.add(scroll,BorderLayout.CENTER);
        //body.add(temp);
        System.out.println(messageBody);
        return body;
    }

    /**
     * create preview panel
     * @return preview panel
     */
    public static JPanel preview(boolean isImage){
        JPanel preview=new JPanel();
        //adding an image to panel for example
        //if(isImage){
        //    ImageIcon icon = new ImageIcon("default.png");
        //    JLabel label = new JLabel(icon);
        //    preview.add(label);
        //}
        preview.setLayout(new FlowLayout());
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        jEditorPane.setContentType("text/html");
        File file=new File("D:/Fourth semester/Advanced Programming/HW/HW5/3/MidTermProjectThirdPhase/default.html");
        try {
            URL url= file.toURI().toURL();
            jEditorPane.setPage(url);
        } catch (IOException e) {
            jEditorPane.setText("<html>Page not found.</html>");
        }
        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(540,400));
        preview.add(jScrollPane);
        return preview;
    }

    public static void setHttpResponse(HttpResponse httpResponse) {
        //this.httpResponse = httpResponse;
        status.setText(httpResponse.getStatusCode()+" "+httpResponse.getStatusMessage());
        //System.out.println(httpResponse.getMessageBody());
        time.setText(httpResponse.getTime()+" ms");
        size.setText((double)httpResponse.getSize()/1000+"KB");
        headers= (HashMap<String,String>) httpResponse.getHeaders();
        header.removeAll();
        header=header();
        raw.removeAll();
        raw=raw(httpResponse.getMessageBody());
        //raw.setLayout(new BorderLayout());
        preview=preview(httpResponse.isImage());
        body=body(raw,preview);
        //header.repaint();
        //header.revalidate();
        //preview.revalidate();
        //preview.repaint();
        //raw.revalidate();
        //raw.repaint();
        tabs.removeAll();
        response.removeAll();
        prepareGui();
        tabs.add("Header",header);
        tabs.add("Body",body);
    }
    public static String getFormattedHeaders(){
        String temp="";
        for (Map.Entry<String, String> entry : headers.entrySet())
            if(entry.getKey()!=null)
                temp+=(entry.getKey()+": "+entry.getValue()+"\n");
        return temp;
    }

    public static JButton getCopy() {
        return copy;
    }
}