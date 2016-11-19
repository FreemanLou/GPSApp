/*
 * Displays GPS in a user interface with a list of points
 */
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.*;

public class GPSApp{
    private OSMParser parser;
    private Map map;
    
    private JFrame mainFrame;
    private MapPanel mapPanel;
    private JList<String> wayList;

    public GPSApp(File f) throws Exception {	
	map = new Map();
	
	parser = new OSMParser(f, map);
	parser.parse();

	mainFrame = new JFrame("GPS App");
	Container content = mainFrame.getContentPane();
	content.setLayout(new BorderLayout());
	
	mapPanel = new MapPanel(map);
	mapPanel.setPreferredSize(new Dimension(650, 650));
	content.add(mapPanel, BorderLayout.CENTER);
	
	//Initialize and add list to left side
//	String[] listData = new String[map.getNameList().size()];	//Should be alphabetized
//	listData = map.getNameList().toArray(listData);
//	
//	wayList = new JList<String>(listData);
//	wayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//	wayList.setLayoutOrientation(JList.VERTICAL);
//	
//	JScrollPane wayListScroller = new JScrollPane(wayList);
//	wayListScroller.setPreferredSize(new Dimension(250, 600));
//	content.add(wayListScroller, BorderLayout.WEST);
	
	mainFrame.pack();
	mainFrame.setVisible(true);
    }
    
    public static void main(String[] args) throws Exception {
	File f = new File("usb.osm");
	GPSApp demo = new GPSApp(f);
    }
}
