import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
/**
 * this class represents Create Request Panel.
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class CreateRequestPanel {
    //main Create Request Panel
    private JPanel createRequest;
    //to keep headers
    private final ArrayList<JPanel> headers;
    //to give saved req.
    private PreviousRequestsPanel previousRequestsPanel;
    /**
     * first init of CreateRequestPanel
     * @param previousRequestsPanel previousRequestsPanel is needed to give saved req.
     */
    public CreateRequestPanel(PreviousRequestsPanel previousRequestsPanel){
        headers=new ArrayList<>();
        createRequest=new JPanel();
        createRequest.setLayout(new BorderLayout());
        createRequest.setPreferredSize(new Dimension(200,400));
        //preparing panel
        prepareGui();
        this.previousRequestsPanel=previousRequestsPanel;
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
        createRequest.add(URL(),BorderLayout.NORTH);
        createRequest.add(tab(),BorderLayout.CENTER);
    }
    /**
     * get creating url panel
     * @return creating url panel
     */
    public JPanel URL(){
        JPanel url=new JPanel();
        url.setLayout(new FlowLayout());
        String[] orders={"GET", "DELETE", "POST", "PUT", "PATCH"};
        final JComboBox order = new JComboBox(orders);
        order.setSelectedIndex(0);
        JScrollPane orderScrollPane = new JScrollPane(order);
        JTextField address=new JTextField(8);
        JButton save=new JButton("Save");
        //move saved req. to previousRequestsPanel
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(address.getText().length()!=0){
                    if (order.getSelectedIndex() != -1) {
                        String name = JOptionPane.showInputDialog("Name :");
                        previousRequestsPanel.addRequest(new Request(name,address.getText(), (String) order.getItemAt(order.getSelectedIndex())));
                        address.setText("");
                    }
                }
            }
        });
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
        JTabbedPane tabbedPane = new JTabbedPane();
        //adding header to tabbedPane
        tabbedPane.setPreferredSize(new Dimension(200,300));
        JPanel header=header();
        tabbedPane.add("Header",header);
        //body panel add to body tab
        JPanel temp=new JPanel();
        temp.setLayout(new BorderLayout());
        temp.setPreferredSize(new Dimension(200,300));
        tabbedPane.add("Body",temp);
        // to show sub part of body
        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    //to create JPopupMenu
                    JPopupMenu pm = new JPopupMenu();
                    JMenuItem form = new JMenuItem("Form Data");
                    JMenuItem JSON = new JMenuItem("JSON");
                    JMenuItem binary = new JMenuItem("Binary File");
                    pm.add(form);
                    pm.add(JSON);
                    pm.add(binary);
                    //if form clicked
                    form.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //add header to it
                            temp.removeAll();
                            JPanel form=header();
                            temp.add(new JLabel("Form Data",JLabel.CENTER),BorderLayout.NORTH);
                            temp.add(form);
                            temp.revalidate();
                            temp.repaint();
                        }
                    });
                    //if JSON clicked
                    JSON.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //add text field to it
                            JTextArea text=new JTextArea();
                            JScrollPane jp = new JScrollPane(text);
                            text.setSize(100,100);
                            temp.removeAll();
                            temp.add(new JLabel("JSON",JLabel.CENTER),BorderLayout.NORTH);
                            temp.add(jp);
                            //temp.add(text);
                            temp.revalidate();
                            temp.repaint();

                        }
                    });
                    //if binary file clicked
                    binary.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //add a button and label
                            temp.removeAll();
                            JPanel choose=new JPanel();
                            choose.setLayout(new FlowLayout());
                            choose.add(new JLabel("Binary Files",JLabel.CENTER));
                            JButton openFile=new JButton("Open File...");
                            openFile.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //to show JFileChooser
                                    JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                                    int returnValue = chooser.showOpenDialog(null);
                                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                                        File selectedFile = chooser.getSelectedFile();
                                        //printing the selected File path
                                        System.out.println(selectedFile.getAbsolutePath());
                                    }
                                }
                            });
                            choose.add(openFile);
                            temp.add(choose,BorderLayout.NORTH);
                            temp.revalidate();
                            temp.repaint();

                        }
                    });
                    pm.show(temp, e.getX(), e.getY());
                }
            }

        });
        return tabbedPane;
    }

    /**
     * get header tab
     * @return header tab
     */
    public JPanel header(){
        //init of header panel
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel panel =new JPanel();
        panel.setSize(createRequest.getSize());
        panel.setLayout(new GridLayout(15,1));
        JButton adder=new JButton("+");
        mainPanel.add(adder,BorderLayout.NORTH);
        mainPanel.add(panel);
        //add a header when "+" clicked
        adder.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        //add to headers
                        headers.add(rowOfHeader(panel));
                        panel.add(headers.get(headers.size()-1));
                        panel.revalidate();
                        panel.repaint();
                    }
                }
        );
        return mainPanel;
    }
    /**
     * get a row (a panel to keep texts and...)
     * @param panel panel to keep rows
     * @return panel row (a panel to keep texts and...)
     */
    public JPanel rowOfHeader(JPanel panel){
        //row contains 2 text , checkbox,button
        JPanel row =new JPanel();
        row.setLayout(new FlowLayout());
        row.setSize(createRequest.getSize());
        JTextField first=new JTextField(8);
        row.add(first);
        //add by clicking on text
        first.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                headers.add(rowOfHeader(panel));
                panel.add(headers.get(headers.size()-1));
                panel.revalidate();
                panel.repaint();
            }
        });

        JTextField second=new JTextField(8);
        row.add(second);
        //add by clicking on text
        second.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                headers.add(rowOfHeader(panel));
                panel.add(headers.get(headers.size()-1));
                panel.revalidate();
                panel.repaint();
            }
        });
        row.add(new JCheckBox());
        JButton delete=new JButton("X");
        row.add(delete);
        //when "X" clicked delete from header
        delete.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        headers.remove(row);
                        panel.remove(row);
                        panel.revalidate();
                        panel.repaint();
                    }
                }
        );
        return row;
    }
    /**
     * get texts from rows
     * @return texts from rows
     */
    public  ArrayList<JTextField> getTexts(){
        ArrayList<JTextField> textFields=new ArrayList<>();
        for(JPanel panel:headers){
            textFields.add((JTextField)panel.getComponent(0));
            textFields.add((JTextField)panel.getComponent(1));
        }
        return textFields;
    }
}
