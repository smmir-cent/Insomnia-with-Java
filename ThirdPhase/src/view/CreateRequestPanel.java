package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class represents Create Request Panel.
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class CreateRequestPanel {
    //main Create Request Panel
    private static JPanel createRequest ;
    //to keep headers
    private static ArrayList<JPanel> property ;
    private static ArrayList<JPanel> parts ;
    private static JPanel URL ;
    private static JTabbedPane tabs ;
    private static JPanel headersPanel ;
    private static JPanel headers ;
    private static JButton addHeader ;
    private static JPanel body ;
    private static JPanel formData ;
    private static JButton addPart ;
    private static JPanel formDataMain ;
    private static JPanel binaryFiles ;
    private static JButton openFile;
    private static File upload ;
    //to give saved req.
    //private gui.PreviousRequestsPanel previousRequestsPanel;
    /**
     * first init of gui.CreateRequestPanel
     */
    public CreateRequestPanel(/*PreviousRequestsPanel previousRequestsPanel*/){
        property =new ArrayList<>();
        parts =new ArrayList<>();
        createRequest=new JPanel();
        createRequest.setLayout(new BorderLayout());
        createRequest.setPreferredSize(new Dimension(200,400));
        upload=null;
        //preparing panel
        prepareGui();
        //this.previousRequestsPanel=previousRequestsPanel;
    }
    /**
     * get main Create Request Panel
     * @return main Create Request Panel
     */
    public JPanel getCreateRequest() {
        return createRequest;
    }

    /**
     * connect panels to Create Request Panel
     */
    public void prepareGui(){
        //adding sub-panel to main panel
        URL=URL();
        createRequest.add(URL,BorderLayout.NORTH);
        tabs=tab();
        createRequest.add(tabs,BorderLayout.CENTER);
    }
    /**
     * get creating url panel
     * @return creating url panel
     */
    public static JPanel URL(){
        JPanel url=new JPanel();
        url.setLayout(new FlowLayout());
        String[] orders={"GET", "DELETE", "POST", "PUT"};
        JComboBox order = new JComboBox(orders);
        order.setSelectedIndex(0);
        JScrollPane orderScrollPane = new JScrollPane(order);
        JTextField address=new JTextField(8);
        JButton save=new JButton("Save");
        //move saved req. to previousRequestsPanel

        //adding components to url panel
        JButton send=new JButton("Send");

        url.add(order);
        url.add(address);
        url.add(save);
        url.add(send);
        return url;
    }

    /**
     * get second part of main panel(tabs)
     * @return second part of main panel(tabs)
     */
    public JTabbedPane tab(){
        tabs = new JTabbedPane();
        //adding header to tabbedPane
        tabs.setPreferredSize(new Dimension(200,300));
        headersPanel = header();
        tabs.add("Header", headersPanel);
        //body panel add to body tab
        body=new JPanel();
        body.setLayout(new BorderLayout());
        body.setPreferredSize(new Dimension(200,300));
        tabs.add("Body",body);
        // to show sub part of body
        JTabbedPane bodyTab= new JTabbedPane();
        formDataMain=multiPart();
        binaryFiles=new JPanel();
        binaryFiles.setLayout(new FlowLayout());
        binaryFiles.add(new JLabel("Binary Files",JLabel.CENTER));
        openFile=new JButton("Open File...");

        binaryFiles.add(openFile);
        bodyTab.add("Form Data",formDataMain);
        bodyTab.add("Binary File",binaryFiles);
        body.add(bodyTab);
        return tabs;
    }
    /**
     * get header tab
     * @return header tab
     */
    public JPanel header(){
        //init of header panel
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        headers =new JPanel();
        headers.setSize(createRequest.getSize());
        headers.setLayout(new GridLayout(15,1));
        addHeader=new JButton("+");
        mainPanel.add(addHeader,BorderLayout.NORTH);
        mainPanel.add(headers);
        //add a header when "+" clicked
        return mainPanel;
    }
    /**
     * get a row (a panel to keep texts and...)
     * @return panel row (a panel to keep texts and...)
     */
    public static JPanel rowOfHeader(){
        //row contains 2 text , checkbox,button
        JPanel row =new JPanel();
        row.setLayout(new FlowLayout());
        row.setSize(createRequest.getSize());
        JTextField first=new JTextField(8);
        row.add(first);
        JTextField second=new JTextField(8);
        row.add(second);
        JCheckBox active=new JCheckBox();
        row.add(active);
        JButton delete=new JButton("X");
        row.add(delete);
        //when "X" clicked delete from header
        delete.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        property.remove(row);
                        headers.remove(row);
                        headers.revalidate();
                        headers.repaint();
                    }
                }
        );
        return row;
    }

    /**
     * get header as hash map
     * @return header as hash map
     */
    public static HashMap<String,String> getPropertyAsHashMap(){
        HashMap<String,String> temp=new HashMap<>();
        for (JPanel p:property) {
            if(((JCheckBox)p.getComponent(2)).isSelected())
                temp.put(((JTextField)p.getComponent(0)).getText(),((JTextField)p.getComponent(1)).getText());
        }
        if(temp.keySet().isEmpty())
            return null;
        return temp;
    }

    /**
     * to show saved request on panel
     * @param httpRequest request object
     */
    public static void showRequest(HttpRequest httpRequest){
        int select=0;
        if(httpRequest instanceof PUTRequest)
            select=3;
        else if(httpRequest instanceof POSTRequest)
            select=2;
        else if(httpRequest instanceof DELETERequest)
            select=1;

        ((JComboBox)URL.getComponent(0)).setSelectedIndex(select);
        ((JTextField)URL.getComponent(1)).setText(httpRequest.getUrl().toString());
        URL.repaint();
        URL.revalidate();
        //property=new ArrayList<>();
        HashMap<String,String> headersMap=httpRequest.getHeaders();
        HashMap<String,String> part=null;
        String path=null;
        if(httpRequest instanceof GETRequest || httpRequest instanceof DELETERequest){
            //part=new HashMap<>();
        }
        else if(httpRequest instanceof PUTRequest){
            part=((PUTRequest) httpRequest).getMultiPartBody();
            path=((PUTRequest) httpRequest).getUploadBinary();
        }
        else if(httpRequest instanceof POSTRequest){
            part=((POSTRequest) httpRequest).getMultiPartBody();
            path=((POSTRequest) httpRequest).getUploadBinary();
        }
        int i=0;
        //if(headersMap!=null)
        //for(;i<headersMap.keySet().size();i++)
        //    property.add(rowOfHeader());
        i=0;
        if(headersMap!=null)
            for(String key:headersMap.keySet()){
                headers.removeAll();
                property.clear();
                property.add(rowOfHeader());
                ((JTextField)property.get(i).getComponent(0)).setText(key);
                ((JTextField)property.get(i).getComponent(1)).setText(headersMap.get(key));
                i++;
            }
        i=0;
        if(part!=null)
            for(String key:part.keySet()){
                formData.removeAll();
                parts.clear();
                parts.add(rowOfParts());
                ((JTextField)parts.get(i).getComponent(0)).setText(key);
                ((JTextField)parts.get(i).getComponent(1)).setText(part.get(key));
                i++;
            }
        if(headersMap!=null)
            for(i=0;i<headersMap.keySet().size();i++)
                headers.add(property.get(i));
        if(part!=null)
            for(i=0;i<part.keySet().size();i++)
                formData.add(parts.get(i));
        if(formData!=null){
            formData.revalidate();
            formData.repaint();
        }
        if(headers!=null){
            headers.revalidate();
            headers.repaint();
        }
        createRequest.repaint();
        createRequest.revalidate();
    }

    /**
     * get row of jpanel for form data
     * @return jpanel for form data
     */
    public static JPanel rowOfParts(){
        //row contains 2 text , checkbox,button
        JPanel row =new JPanel();
        row.setLayout(new FlowLayout());
        row.setSize(createRequest.getSize());
        JTextField first=new JTextField(8);
        row.add(first);
        JTextField second=new JTextField(8);
        row.add(second);
        JCheckBox active=new JCheckBox();
        row.add(active);
        JButton delete=new JButton("X");
        row.add(delete);
        //when "X" clicked delete from header
        delete.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        parts.remove(row);
                        formData.remove(row);
                        formData.revalidate();
                        formData.repaint();
                    }
                }
        );
        return row;
    }
    public static JPanel multiPart(){
        //init of header panel
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        formData =new JPanel();
        formData.setSize(createRequest.getSize());
        formData.setLayout(new GridLayout(15,1));
        addPart=new JButton("+");
        mainPanel.add(addPart,BorderLayout.NORTH);
        mainPanel.add(formData);
        //add a header when "+" clicked

        return mainPanel;
    }

    /**
     * get form data as Hash map
     * @return Hash map
     */
    public static HashMap<String,String> getMultiPart(){
        HashMap<String,String> temp=new HashMap<>();
        for (JPanel p:parts) {
            if(((JCheckBox)p.getComponent(2)).isSelected())
                temp.put(((JTextField)p.getComponent(0)).getText(),((JTextField)p.getComponent(1)).getText());
        }
        if(temp.isEmpty())
            return null;
        return temp;
    }

    //
    //
    //
    // getter and setter methods
    //
    //
    //

    public JPanel getURL() {
        return URL;
    }

    public static ArrayList<JPanel> getParts() {
        return parts;
    }

    public static JTabbedPane getTabs() {
        return tabs;
    }

    public static JPanel getHeadersPanel() {
        return headersPanel;
    }

    public static JPanel getHeaders() {
        return headers;
    }

    public static JButton getAddHeader() {
        return addHeader;
    }

    public static JPanel getBody() {
        return body;
    }

    public static JPanel getFormData() {
        return formData;
    }

    public static JPanel getFormDataMain() {
        return formDataMain;
    }

    public static JPanel getBinaryFiles() {
        return binaryFiles;
    }

    public static File getUpload() {
        return upload;
    }

    public static ArrayList<JPanel> getProperty() {
        return property;
    }

    public static JButton getOpenFile() {
        return openFile;
    }

    public static void setUpload(File upload) {
        CreateRequestPanel.upload = upload;
    }

    public static JButton getAddPart() {
        return addPart;
    }

    public static void setAddPart(JButton addPart) {
        CreateRequestPanel.addPart = addPart;
    }
}
