import model.*;
import view.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * this class represents control mvc pattern
 * @author Seyyed Mahdi Mirfendereski
 * @version 0.0
 */
public class Controller {
    //main frame
    private View view;

    /**
     * to init of controller with view
     * @param view view of app
     */
    public Controller(View view){
        this.view=view;
    }

    /**
     * to add action listener to all components
     */
    public void initController(){
        mainFrameControlling();
        requestControlling();
    }

    /**
     * to handle menu item action listener
     */
    public void mainFrameControlling(){
        JMenuItem optionsMenuItem=View.getOptionsMenuItem();
        JMenuItem exitMenuItem=View.getExitMenuItem();
        JMenuItem tfsMenuItem=View.getTfsMenuItem();
        JMenuItem tsMenuItem=View.getTsMenuItem();
        JMenuItem helpsMenuItem=View.getHelpsMenuItem();
        JMenuItem aboutMenuItem=View.getAboutMenuItem();
        JFrame mainFrame=View.getMainFrame();
        CreateRequestPanel createRequestPanel=View.getCreateRequestPanel();
        Response response=View.getResponse();
        PreviousRequestsPanel previousRequestsPanel=View.getPreviousRequestsPanel();
        boolean exitS=View.isExitS();
        final boolean[] previousRequestsPanelVisibility = {View.isPreviousRequestsPanelVisibility()};
        JSplitPane sp=View.getSp();
        JSplitPane sp2=View.getSp2();
        Image image=View.getImage() ;
        Option appOption=View.getAppOption();
        optionsMenuItem.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        //displaying new JFrame to get options
                        JFrame option = new JFrame("Option");
                        option.setSize(300, 300);
                        //set icon
                        option.setIconImage(image);
                        //can't resize
                        option.setResizable(false);
                        //dark theme item
                        JRadioButton darkTheme = new JRadioButton("Dark theme");
                        darkTheme.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                //to change theme color
                                UIManager.put( "control", new Color( 128, 128, 128) );
                                UIManager.put( "info", new Color(128,128,128) );
                                UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
                                UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
                                UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
                                UIManager.put( "nimbusFocus", new Color(115,164,209) );
                                UIManager.put( "nimbusGreen", new Color(176,179,50) );
                                UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
                                UIManager.put( "nimbusLightBackground", new Color( 18, 30, 49) );
                                UIManager.put( "nimbusOrange", new Color(191,98,4) );
                                UIManager.put( "nimbusRed", new Color(169,46,34) );
                                UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
                                UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
                                UIManager.put( "text", new Color( 230, 230, 230) );
                                appOption.setLightTheme(false);
                                View.saveOption(appOption);
                            }
                        });
                        //light theme item
                        JRadioButton lightTheme = new JRadioButton("Light theme");
                        lightTheme.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                //to change theme color
                                UIManager.put( "control", new Color( 214, 217, 223) );
                                UIManager.put( "info", new Color(241,242,189) );
                                UIManager.put( "nimbusBase", new Color( 50, 99, 139) );
                                UIManager.put( "nimbusAlertYellow", new Color( 254, 221, 30) );
                                UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
                                UIManager.put( "nimbusFocus", new Color(115,164,209) );
                                UIManager.put( "nimbusGreen", new Color(175,180,50) );
                                UIManager.put( "nimbusInfoBlue", new Color( 50, 92, 180) );
                                UIManager.put( "nimbusLightBackground", new Color( 250, 250, 250) );
                                UIManager.put( "nimbusOrange", new Color(191,98,4) );
                                UIManager.put( "nimbusRed", new Color(169,46,34) );
                                UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
                                UIManager.put( "nimbusSelectionBackground", new Color( 55, 104, 138) );
                                UIManager.put( "text", new Color( 0, 0, 0) );
                                appOption.setLightTheme(true);
                                View.saveOption(appOption);
                            }
                        });
                        //if exit :exits will change to true
                        JRadioButton exit = new JRadioButton("Exit");
                        exit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                View.setExitS(true);
                                appOption.setTray(false);
                                View.saveOption(appOption);
                            }
                        });
                        //if Hide in System tray :exits will change to true
                        JRadioButton systemTray = new JRadioButton("Hide in System tray");
                        systemTray.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                View.setExitS(false);
                                appOption.setTray(true);
                                View.saveOption(appOption);
                            }
                        });
                        //to choose one between exit and hide ...
                        JRadioButton follow = new JRadioButton("follow redirect");
                        //to choose one between exit and hide ...
                        ButtonGroup exit1_2 = new ButtonGroup();
                        exit1_2.add(exit);
                        exit1_2.add(systemTray);
                        //to choose one between light and dark
                        ButtonGroup theme = new ButtonGroup();
                        theme.add(darkTheme);
                        theme.add(lightTheme);
                        JPanel panel = new JPanel();
                        panel.setLayout(new GridLayout(3, 1));
                        panel.setSize(100, 30);
                        //adding items to option
                        panel.add(darkTheme);
                        panel.add(lightTheme);
                        panel.add(exit);
                        panel.add(systemTray);
                        panel.add(follow);
                        option.add(panel);
                        option.setVisible(true);
                    }
                }
        );
        exitMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        mainFrame.dispose();
                    }
                }
        );
        tfsMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        //getting large
                        if (mainFrame.getExtendedState() == JFrame.NORMAL)
                            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                            //getting small
                        else
                            mainFrame.setExtendedState(JFrame.NORMAL);
                    }
                }
        );
        tsMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        //hide previous Requests Panel
                        if(previousRequestsPanelVisibility[0]){
                            previousRequestsPanelVisibility[0] =false;
                            previousRequestsPanel.getPanel().setVisible(false);
                            View.setPreviousRequestsPanelVisibility(false);
                        }
                        //show previous Requests Panel
                        else {
                            previousRequestsPanel.getPanel().setVisible(true);
                            previousRequestsPanelVisibility[0] =true;
                            View.setPreviousRequestsPanelVisibility(true);
                            sp.setDividerLocation(200);
                            View.getPreviousRequestsPanel().getPanel().revalidate();
                            View.getPreviousRequestsPanel().getPanel().repaint();
                            View.getMainFrame().revalidate();
                            View.getMainFrame().repaint();
                        }
                    }
                }
        );
        helpsMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        //must fill with correct data
                        JOptionPane.showMessageDialog(mainFrame,
                                "mirmahdiseyyed@gmail.com\nMahdi Mirfendereski\n9723093",
                                "About", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
        );
        aboutMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        //show developer information
                        JOptionPane.showMessageDialog(mainFrame,
                                "mirmahdiseyyed@gmail.com\nMahdi Mirfendereski\n9723093",
                                "About", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
        );

    }

    /**
     * to create action listener for create request panel.
     */
    public void requestControlling(){
        JComboBox order = (JComboBox)view.getCreateRequestPanel().getURL().getComponent(0);
        JTextField address=(JTextField)view.getCreateRequestPanel().getURL().getComponent(1);
        JButton save=(JButton)(view.getCreateRequestPanel().getURL().getComponent(2));
        JButton send=(JButton)(view.getCreateRequestPanel().getURL().getComponent(3));
        ArrayList<JPanel> property = CreateRequestPanel.getProperty();
        JButton addHeader=(JButton)CreateRequestPanel.getHeadersPanel().getComponent(0);
        JPanel headers=CreateRequestPanel.getHeaders();
        JPanel body=CreateRequestPanel.getBody();
        JPanel formDataMain =CreateRequestPanel.getFormDataMain();
        JPanel formData=CreateRequestPanel.getFormData();
        ArrayList<JPanel> parts = CreateRequestPanel.getParts();
        JPanel binaryFiles=CreateRequestPanel.getBinaryFiles();
        JButton openFile=CreateRequestPanel.getOpenFile();
        File upload=CreateRequestPanel.getUpload();
        JButton addPart=CreateRequestPanel.getAddPart();
        //send button
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((String)(order.getItemAt(order.getSelectedIndex()))).equals("GET")){
                    try {
                        BackGroundConnection task=new BackGroundConnection(new GETRequest(new URL(address.getText()), CreateRequestPanel.getPropertyAsHashMap(),false,false,"default.html",null));
                        task.execute();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(((String) order.getItemAt(order.getSelectedIndex())).equals("DELETE")){
                    try {
                        BackGroundConnection task=new BackGroundConnection(new DELETERequest(new URL(address.getText()),CreateRequestPanel.getPropertyAsHashMap(),false,false,"default.html",null));
                        task.execute();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(((String)(order.getItemAt(order.getSelectedIndex()))).equals("POST")){
                    try {
                        BackGroundConnection task=new BackGroundConnection(new POSTRequest(new URL(address.getText()),CreateRequestPanel.getPropertyAsHashMap(),false,false,"default.html",null,CreateRequestPanel.getUpload()==null?null:CreateRequestPanel.getUpload().getAbsolutePath(),CreateRequestPanel.getMultiPart()));
                        task.execute();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(((String) order.getItemAt(order.getSelectedIndex())).equals("PUT")){
                    try {
                        BackGroundConnection task=new BackGroundConnection(new PUTRequest(new URL(address.getText()),CreateRequestPanel.getPropertyAsHashMap(),false,false,"default.html",null,CreateRequestPanel.getUpload()==null?null:CreateRequestPanel.getUpload().getAbsolutePath(),CreateRequestPanel.getMultiPart()));
                        task.execute();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        //save button
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(address.getText().length()!=0){
                    if (order.getSelectedIndex() != -1) {
                        String name = JOptionPane.showInputDialog("Group :");
                        if(name.isEmpty())
                            name="default";
                        if(((String)(order.getItemAt(order.getSelectedIndex()))).equals("GET")){
                            try {
                                HttpRequest request=new GETRequest(new URL(address.getText()),CreateRequestPanel.getPropertyAsHashMap(),false,false,"default.html",name);
                                RequestSaving.saveRequest(name,request);
                                PreviousRequestsPanel.updateTree(name,request);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        else if(((String)(order.getItemAt(order.getSelectedIndex()))).equals("DELETE")){
                            try {
                                HttpRequest request=new DELETERequest(new URL(address.getText()),CreateRequestPanel.getPropertyAsHashMap(),false,false,"default.html",name);
                                RequestSaving.saveRequest(name,request);
                                PreviousRequestsPanel.updateTree(name,request);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        else if(((String)(order.getItemAt(order.getSelectedIndex()))).equals("PUT")){
                            try {
                                HttpRequest request=new PUTRequest(new URL(address.getText()),CreateRequestPanel.getPropertyAsHashMap(),false,false,"default.html",name,CreateRequestPanel.getUpload()==null?null:CreateRequestPanel.getUpload().getAbsolutePath(),CreateRequestPanel.getMultiPart());
                                RequestSaving.saveRequest(name,request);
                                PreviousRequestsPanel.updateTree(name,request);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        else if(((String)(order.getItemAt(order.getSelectedIndex()))).equals("POST")){
                            try {
                                HttpRequest request=new POSTRequest(new URL(address.getText()),CreateRequestPanel.getPropertyAsHashMap(),false,false,"default.html",name,CreateRequestPanel.getUpload()==null?null:CreateRequestPanel.getUpload().getAbsolutePath(),CreateRequestPanel.getMultiPart());
                                RequestSaving.saveRequest(name,request);
                                PreviousRequestsPanel.updateTree(name,request);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        address.setText("");
                        headers.removeAll();
                        headers.repaint();
                        headers.revalidate();
                        formData.removeAll();
                        formData.repaint();
                        formData.revalidate();
                    }
                }
            }
        });
        addHeader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //add to headers
                property.add(CreateRequestPanel.rowOfHeader());
                headers.add(property.get(property.size()-1));
                headers.revalidate();
                headers.repaint();
            }
        });
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //to show JFileChooser
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION)
                    CreateRequestPanel.setUpload(chooser.getSelectedFile());

            }
        });
        addPart.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        //add to headers
                        parts.add(CreateRequestPanel.rowOfParts());
                        formData.add(parts.get(parts.size()-1));
                        formData.revalidate();
                        formData.repaint();
                    }
                }
        );
    }
}
