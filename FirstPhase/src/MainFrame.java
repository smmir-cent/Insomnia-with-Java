import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * this class represents a main frame and contains 3 sub-panel.
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class MainFrame {
    //main frame
    private JFrame mainFrame;
    //for CreateRequestPanel panel
    private CreateRequestPanel createRequestPanel;
    //for Response panel
    private Response response;
    //for PreviousRequestsPanel panel
    private PreviousRequestsPanel previousRequestsPanel;
    // a flag for hide in system tray
    private boolean exitS;
    // a flag for toggle slide bar
    private boolean previousRequestsPanelVisibility;
    //to match PreviousRequestsPanel and CreateRequestPanel panel
    private JSplitPane sp;
    //to match sp and Response
    private JSplitPane sp2;
    //icon image
    private Image image ;
    /**
     * first initial of main frame
     */
    public MainFrame() {
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
    public JMenuBar menuBar() {
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
        JMenuItem optionsMenuItem = new JMenuItem("Options");
        //set Accelerator for option item
        KeyStroke ctrlOKeyStroke = KeyStroke.getKeyStroke("control O");
        optionsMenuItem.setAccelerator(ctrlOKeyStroke);
        optionsMenuItem.addActionListener(
                new ActionListener() {
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
                                mainFrame.getContentPane().setBackground(Color.black);
                                previousRequestsPanel.getPanel().setBackground(Color.LIGHT_GRAY);
                                createRequestPanel.getCreateRequest().setBackground(Color.black);
                                response.getPanel().setBackground(Color.black);
                            }
                        });
                        //light theme item
                        JRadioButton lightTheme = new JRadioButton("Light theme");
                        lightTheme.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                //to change theme color
                                mainFrame.getContentPane().setBackground(Color.white);
                                previousRequestsPanel.getPanel().setBackground(Color.white);
                                createRequestPanel.getCreateRequest().setBackground(Color.white);
                                response.getPanel().setBackground(Color.white);
                            }
                        });
                        //if exit :exits will change to true
                        JRadioButton exit = new JRadioButton("Exit");
                        exit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {

                                exitS = true;
                            }
                        });
                        //if Hide in System tray :exits will change to true
                        JRadioButton systemTray = new JRadioButton("Hide in System tray");
                        systemTray.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {

                                exitS = false;
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
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        //set Accelerator for exit item
        KeyStroke ctrlEKeyStroke = KeyStroke.getKeyStroke("control E");
        exitMenuItem.setAccelerator(ctrlEKeyStroke);
        exitMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        mainFrame.dispose();
                    }
                }
        );
        JMenuItem tfsMenuItem = new JMenuItem("Toggle Full Screen");
        //set Accelerator for Toggle Full Screen item
        KeyStroke ctrlFKeyStroke = KeyStroke.getKeyStroke("control F");
        tfsMenuItem.setAccelerator(ctrlFKeyStroke);
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
        JMenuItem tsMenuItem = new JMenuItem("Toggle Sidebar");
        //set Accelerator for Toggle Sidebar item
        KeyStroke ctrlTKeyStroke = KeyStroke.getKeyStroke("control T");
        tsMenuItem.setAccelerator(ctrlTKeyStroke);
        tsMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        //hide previous Requests Panel
                        if(previousRequestsPanelVisibility){
                            previousRequestsPanel.getPanel().setVisible(false);
                            previousRequestsPanelVisibility=false;
                        }
                        //show previous Requests Panel
                        else {
                            previousRequestsPanel.getPanel().setVisible(true);
                            previousRequestsPanelVisibility=true;
                            sp.setDividerLocation(200);
                            previousRequestsPanel.getPanel().revalidate();
                            previousRequestsPanel.getPanel().repaint();
                            mainFrame.revalidate();
                            mainFrame.repaint();
                        }
                    }
                }
        );
        JMenuItem helpsMenuItem = new JMenuItem("Help");
        //set Accelerator for help item
        KeyStroke ctrlHKeyStroke = KeyStroke.getKeyStroke("control H");
        helpsMenuItem.setAccelerator(ctrlHKeyStroke);
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
        JMenuItem aboutMenuItem = new JMenuItem("About");
        //set Accelerator for about item
        KeyStroke ctrlAKeyStroke = KeyStroke.getKeyStroke("control A");
        aboutMenuItem.setAccelerator(ctrlAKeyStroke);
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
    public void prepareGui() {
        //create 3 panels
        previousRequestsPanel = new PreviousRequestsPanel();
        createRequestPanel = new CreateRequestPanel(previousRequestsPanel);
        response = new Response(createRequestPanel);
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
        mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("index.png"));
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * this method is for hide in system handling.
     */
    private void handleClosing() {
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
    public PopupMenu popupMenu() {
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
}