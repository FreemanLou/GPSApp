/*
 * Main GPS application
 * 
 * Contains the frame for the application and the GPS device. Initiallizes the frame with 
 * the mapPanel and sets a listener for the GPS device.
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

public class Application{
    private OSMParser parser;
    private Map map;
    
    private JFrame mainFrame;
    private MapPanel mapPanel;
    private JList<String> wayList;
    
    private GPSDevice gps;

    /*
     * Initializes the GPS Application
     */
    public Application(File f) throws Exception {	
    }
}
