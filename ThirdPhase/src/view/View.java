package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class View {
    //main frame
    private static JFrame mainFrame;
    //for gui.CreateRequestPanel panel
    private static CreateRequestPanel createRequestPanel;
    //for gui.Response panel
    private static Response response;
    //for gui.PreviousRequestsPanel panel
    private static PreviousRequestsPanel previousRequestsPanel;
    // a flag for hide in system tray
    private static boolean exitS;
    // a flag for toggle slide bar
    private static boolean previousRequestsPanelVisibility;
    //to match gui.PreviousRequestsPanel and gui.CreateRequestPanel panel
    private static JSplitPane sp;
    //to match sp and gui.Response
    private static JSplitPane sp2;
    //icon image
    private static Image image ;
    private static Option appOption;
    private static JMenuItem optionsMenuItem;
    private static JMenuItem exitMenuItem;
    private static JMenuItem tfsMenuItem;
    private static JMenuItem tsMenuItem;
    private static JMenuItem helpsMenuItem;
    private static  JMenuItem aboutMenuItem;
    /**
     * first initial of main frame
     */
    public View() {
        mainFrame = new JFrame();
        //title of it
        mainFrame = new JFrame("Insomnia");
        //first mainframe size
        mainFrame.setSize(1000, 600);
        //setting the layout
        mainFrame.setLayout(new BorderLayout(10,10));
        //adding the j menu bar to frame
        mainFrame.setJMenuBar(menuBar());
        //get image
        image = Toolkit.getDefaultToolkit().getImage("index.png");
        //first state of exit
        exitS = true;
        prepareGui();
        //first state of previous Requests Panel Visibility
        previousRequestsPanelVisibility=true;
    }
    /**
     * this method is for creating the main frame menu
     * @return JMenuBar for main frame menu
     */
    public static JMenuBar menuBar() {
        JMenuBar menuBar = new JMenuBar();
        // Application item
        JMenu appMenu = new JMenu("Application");
        //set Mnemonic for Application item
        appMenu.setMnemonic(KeyEvent.VK_A);
        JMenu viewMenu = new JMenu("View");
        //set Mnemonic for view item
        viewMenu.setMnemonic(KeyEvent.VK_V);
        JMenu helpMenu = new JMenu("Help");
        //set Mnemonic for help item
        helpMenu.setMnemonic(KeyEvent.VK_H);
        optionsMenuItem = new JMenuItem("Options");
        //set Accelerator for option item
        KeyStroke ctrlOKeyStroke = KeyStroke.getKeyStroke("control O");
        optionsMenuItem.setAccelerator(ctrlOKeyStroke);
        exitMenuItem = new JMenuItem("Exit");
        //set Accelerator for exit item
        KeyStroke ctrlEKeyStroke = KeyStroke.getKeyStroke("control E");
        exitMenuItem.setAccelerator(ctrlEKeyStroke);
        tfsMenuItem = new JMenuItem("Toggle Full Screen");
        //set Accelerator for Toggle Full Screen item
        KeyStroke ctrlFKeyStroke = KeyStroke.getKeyStroke("control F");
        tfsMenuItem.setAccelerator(ctrlFKeyStroke);
        tsMenuItem = new JMenuItem("Toggle Sidebar");
        //set Accelerator for Toggle Sidebar item
        KeyStroke ctrlTKeyStroke = KeyStroke.getKeyStroke("control T");
        tsMenuItem.setAccelerator(ctrlTKeyStroke);
        helpsMenuItem = new JMenuItem("Help");
        //set Accelerator for help item
        KeyStroke ctrlHKeyStroke = KeyStroke.getKeyStroke("control H");
        helpsMenuItem.setAccelerator(ctrlHKeyStroke);
        aboutMenuItem = new JMenuItem("About");
        //set Accelerator for about item
        KeyStroke ctrlAKeyStroke = KeyStroke.getKeyStroke("control A");
        aboutMenuItem.setAccelerator(ctrlAKeyStroke);
        //adding items to menu
        appMenu.add(optionsMenuItem);
        appMenu.add(exitMenuItem);
        viewMenu.add(tfsMenuItem);
        viewMenu.add(tsMenuItem);
        helpMenu.add(helpsMenuItem);
        helpMenu.add(aboutMenuItem);
        menuBar.add(appMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }
    /**
     *  this method connect 3 panel to each other.
     */
    public static void prepareGui() {
        //create 3 panels
        previousRequestsPanel = new PreviousRequestsPanel();
        createRequestPanel = new CreateRequestPanel();
        response = new Response();
        //connect 3 panels together
        sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, previousRequestsPanel.getPanel(), createRequestPanel.getCreateRequest());
        sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, response.getPanel());
        sp2.setOneTouchExpandable(true);
        sp.setDividerLocation(200);
        sp2.setDividerLocation(550);
        mainFrame.add(sp2, BorderLayout.CENTER);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //if must hide
                if(!exitS){
                    mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    handleClosing();
                }
                else{
                    System.exit(0);
                }
            }
        });
        appOption =loadOption();
        if(appOption.isTray()){
            exitS=false;
        }
        if(!appOption.isTray()){
            exitS=true;
        }
        if(appOption.isLightTheme()){
            //mainFrame.getContentPane().setBackground(Color.white);
            //previousRequestsPanel.getPanel().setBackground(Color.white);
            //createRequestPanel.getCreateRequest().setBackground(Color.white);
            //response.getPanel().setBackground(Color.white);
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
        }
        if(!appOption.isLightTheme()){
            //mainFrame.getContentPane().setBackground(Color.black);
            //previousRequestsPanel.getPanel().setBackground(Color.LIGHT_GRAY);
            //createRequestPanel.getCreateRequest().setBackground(Color.black);
            //response.getPanel().setBackground(Color.black);
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
        }
        mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("index.png"));
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * this method is for hide in system handling.
     */
    private static void handleClosing() {
        PopupMenu popup=popupMenu();
        SystemTray tray = SystemTray.getSystemTray();
        //add image and tooltip and popup menu to TrayIcon
        TrayIcon trayIcon=new TrayIcon(image,"Insomnia", popup);
        trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
            mainFrame.setVisible(false);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
    /**
     * this method creates popup menu for system tray
     * @return PopMenu for system tray
     */
    public static PopupMenu popupMenu() {
        PopupMenu popup = new PopupMenu();
        //create open item
        MenuItem openItem = new MenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //back from system tray
                mainFrame.setVisible(true);
                mainFrame.setExtendedState(JFrame.NORMAL);
            }
        });
        //create exit item
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //adding item
        popup.add(openItem);
        popup.addSeparator();
        popup.add(exitItem);
        return popup;
    }

    /**
     * saving option
     * @param option with which field
     */
    public static void saveOption(Option option){
        try {
            FileOutputStream fos = new FileOutputStream("option.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(option);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * for loading option from file
     * @return option object
     */
    public static Option loadOption(){
        FileInputStream fis = null;
        Option result=new Option(true,false);
        try {
            fis = new FileInputStream("option.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (Option) ois.readObject();
            ois.close();
            //return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    //
    //
    //
    // getter and setter method that we need.
    //
    //
    //
    public static Response getResponse() {
        return response;
    }
    public static CreateRequestPanel getCreateRequestPanel() {
        return createRequestPanel;
    }
    public static PreviousRequestsPanel getPreviousRequestsPanel() {
        return previousRequestsPanel;
    }

    public static JMenuItem getOptionsMenuItem() {
        return optionsMenuItem;
    }

    public static JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public static JMenuItem getTfsMenuItem() {
        return tfsMenuItem;
    }

    public static JMenuItem getTsMenuItem() {
        return tsMenuItem;
    }

    public static JMenuItem getHelpsMenuItem() {
        return helpsMenuItem;
    }

    public static JMenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static boolean isExitS() {
        return exitS;
    }

    public static boolean isPreviousRequestsPanelVisibility() {
        return previousRequestsPanelVisibility;
    }

    public static JSplitPane getSp() {
        return sp;
    }

    public static JSplitPane getSp2() {
        return sp2;
    }

    public static Image getImage() {
        return image;
    }

    public static Option getAppOption() {
        return appOption;
    }

    public static void setExitS(boolean exitS) {
        View.exitS = exitS;
    }

    public static void setPreviousRequestsPanelVisibility(boolean previousRequestsPanelVisibility) {
        View.previousRequestsPanelVisibility = previousRequestsPanelVisibility;
    }

}