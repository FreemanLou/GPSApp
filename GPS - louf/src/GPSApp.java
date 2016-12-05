/*
 * Displays GPS in a user interface with a list of points
 */
import java.util.*;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;

import com.starkeffect.highway.*;

public class GPSApp{
    private OSMParser parser;
    private Map map;
    
    private JFrame mainFrame;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu mapMenu;
    private JMenuItem menuItem;
    private JCheckBoxMenuItem navigateMenuItem;
    
    private JButton setStart;
    private JButton setEnd;
    private JButton getDirections;
    private JButton startNav;
    
    private GPSDevice gpsDevice;
    private Tracker tracker;

    private File mapFile;
    
    private MapPanel mapPanel;
    private JPanel sidePanel;
    private JTextArea textPanel;
    
    private boolean validRoute;
    
    public GPSApp(File f) throws Exception {
	mapFile = f;
	gpsDevice = new GPSDevice(mapFile.getAbsolutePath());

	map = new Map();
	parser = new OSMParser(mapFile, map);
	parser.parse();

	validRoute = false;
	
	buildFrame();
	
	tracker = new Tracker(this, mapPanel, map, textPanel);
	gpsDevice.addGPSListener(tracker);
    }
    
    private void buildFrame() throws Exception {
	mainFrame = new JFrame("GPS App " + mapFile.getName());
	Container content = mainFrame.getContentPane();
	content.setLayout(new BorderLayout());
	
	//Build map panel
	mapPanel = new MapPanel(map);
	mapPanel.setPreferredSize(new Dimension(1000, 650));
	content.add(mapPanel, BorderLayout.CENTER);
	
	//Build side panel
	sidePanel = new JPanel();
	sidePanel.setPreferredSize(new Dimension(150, 650));
	sidePanel.setLayout(new GridLayout(4, 1));

	setStart = new JButton("Set Start Point");
	setStart.addActionListener(new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e) {
		mapPanel.clearStartPoint();
		mapPanel.setStart(true);
	    }
	});
	
	setEnd = new JButton("Set End Point");
	setEnd.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		mapPanel.clearEndPoint();
		mapPanel.setEnd(true);
	    }
	});

	startNav = new JButton("Start Navigation");
	startNav.setEnabled(false);
	startNav.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent event) {
		toggleStartNav();
	    }
	});

	getDirections = new JButton("Show Direction");
	getDirections.addActionListener(new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if(!mapPanel.bothPointsSet()) {
		    JOptionPane.showMessageDialog(mainFrame, "Both waypoints are not set");
		} else {
		    ArrayList<GraphEdge> route = mapPanel.getRoute(false);
		    if(route == null) {
			JOptionPane.showMessageDialog(mainFrame, "Could not get route");
			startNav.setEnabled(false);
		    } else {
			mapPanel.setShowRoute(true);
			tracker.setRoute(route, mapPanel.getEndPoint());
			startNav.setEnabled(true);
		    }	
		}
	    }
	    
	});

	sidePanel.add(setStart);
	sidePanel.add(setEnd);
	sidePanel.add(getDirections);
	sidePanel.add(startNav);

	content.add(sidePanel, BorderLayout.WEST);
	sidePanel.setVisible(false);;
	
	textPanel = new JTextArea();
	textPanel.setPreferredSize(new Dimension(1000, 100));
	
	content.add(textPanel, BorderLayout.SOUTH);
	textPanel.setVisible(false);

	//Build menu bar
	menuBar = new JMenuBar();
	mainFrame.setJMenuBar(menuBar);

	fileMenu = new JMenu("File");
	menuBar.add(fileMenu);

	menuItem = new JMenuItem("Open File...");
	menuItem.addActionListener(new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e) {
		final JFileChooser chooser = new JFileChooser();

		int returnVal = chooser.showOpenDialog(mainFrame);

		if(returnVal == JFileChooser.APPROVE_OPTION) {
		    File file = chooser.getSelectedFile();

		    mapFile = file;
		    
		    Frame[] frames = Frame.getFrames();
		    for (Frame frame : frames) {
			frame.dispose();
		    }
		    
		    try{
			GPSApp newApp = new GPSApp(mapFile);
		    } catch (Exception exception) {
			System.out.println(exception.getMessage());
		    } finally {
			
		    }
		}
	    }
	});	
	
	fileMenu.add(menuItem);
	mapMenu = new JMenu("Map");
	menuBar.add(mapMenu);

	navigateMenuItem = new JCheckBoxMenuItem("Navigate Mode");
	navigateMenuItem.addActionListener(new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if(navigateMenuItem.isSelected()) {
		    sidePanel.setVisible(true);
		    textPanel.setVisible(true);
		} else {
		    sidePanel.setVisible(false);
		    mapPanel.setShowRoute(false);
		    mapPanel.clearStartPoint();
		    mapPanel.clearEndPoint();
		    mapPanel.setStart(false);
		    mapPanel.setEnd(false);
		    mapPanel.setTracking(false);
		    
		    textPanel.setVisible(false);
		    tracker.setTracking(false);
		}
	    }	    
	});
	mapMenu.add(navigateMenuItem);

	mainFrame.pack();
	mainFrame.setVisible(true);
    }
    
    /**
     * Handles the startNav button
     */
    public void toggleStartNav() {
	if(!mapPanel.isTracking()) {	//Start navigation mode
	    startNav.setText("Stop Navigation");
	    tracker.setTracking(true);
	    mapPanel.setTracking(true);
	} else {			//Stop navigation mode
	    startNav.setText("Start Navigation");
	    tracker.setTracking(false);
	    mapPanel.setTracking(false);
	}
    }
    
    /**
     * Called to let the app respond to an off course event
     */
    public void notifyOffCourse() {
	mapPanel.getRoute(true);
    }
    
    public static void main(String[] args) throws Exception {
	File f = new File("usb.osm");
	GPSApp demo = new GPSApp(f);
    }
}
