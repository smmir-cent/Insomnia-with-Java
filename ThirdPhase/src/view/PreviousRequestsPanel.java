package view;

import model.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class represents saved requests.
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class PreviousRequestsPanel {
    //list of requests
    private static HashMap<String,ArrayList<HttpRequest>> groups;
    //main Previous Requests Panel
    private static JPanel savedPanel;
    private static DefaultMutableTreeNode gr ;
    private static JTree tree;
    /**
     * first init of gui.PreviousRequestsPanel
     */
    public PreviousRequestsPanel(){
        savedPanel=new JPanel();
        savedPanel.setPreferredSize(new Dimension(400,400));
        savedPanel.setLayout(new BorderLayout());
        //savedPanel.add(new JLabel("     Saved Requests     "),BorderLayout.NORTH);
        groups =new HashMap<>();
        gr=new DefaultMutableTreeNode("Groups");
        Path path = Paths.get("D:/Fourth semester/Advanced Programming/HW/HW5/3/MidTermProjectThirdPhase/Groups");
        try {
            if(!Files.exists(path))
                Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file =new File("Groups");
        String[] groupsFile=file.list();
        for (String s :groupsFile) {
            int temp=s.lastIndexOf(".txt");
            String groupName=s.substring(0,temp);
            RequestSaving.loadRequests(groupName);
            ArrayList<HttpRequest> requests= RequestSaving.getTempGroup();
            groups.put(groupName,requests);
        }
        tree=new JTree(gr);
        savedPanel.add(tree);
        prepareGui();

    }
    /**
     * get the main Previous Requests Panel
     * @return main Previous Requests Panel
     */
    public JPanel getPanel(){
        return savedPanel;
    }
    public static void updateTree(String group, HttpRequest httpRequest){
        ArrayList<HttpRequest> temp=groups.containsKey(group)?groups.get(group):new ArrayList<>();
        temp.add(httpRequest);
        groups.put(group,temp);
        savedPanel.removeAll();
        gr.removeAllChildren();
        prepareGui();
    }

    /**
     * adding groups to j tree.
     */
    public static void prepareGui(){

        for(String s:groups.keySet()){
            DefaultMutableTreeNode groupTree=new DefaultMutableTreeNode(s);
            for(HttpRequest httpRequest:groups.get(s)){
                String method="GET";
                if(httpRequest instanceof POSTRequest)
                    method="POST";
                else if(httpRequest instanceof PUTRequest)
                    method="PUT";
                else if(httpRequest instanceof DELETERequest)
                    method="DELETE";
                DefaultMutableTreeNode treeNode=new DefaultMutableTreeNode("url: "+httpRequest.getUrl().toString()+" | Method : "+method);
                groupTree.add(treeNode);
            }
            gr.add(groupTree);
        }
        JTree tree=new JTree(gr);
        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                if(treeSelectionEvent.getPath().getPathCount()==3){
                    DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                    int selected=selectedNode.getParent().getIndex(selectedNode);
                    CreateRequestPanel.showRequest(groups.get(selectedNode.getParent().toString()).get(selected));
                }
            }
        });
        savedPanel.add(tree);
        savedPanel.revalidate();
        savedPanel.repaint();
    }

    public static JTree getTree() {
        return tree;
    }

    public static HashMap<String, ArrayList<HttpRequest>> getGroups() {
        return groups;
    }
}
