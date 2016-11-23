/**
 * 
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;


/**
 * @author freeman
 *
 */
public class MapPanel extends JPanel implements ComponentListener{
    private static final long serialVersionUID = 1L;
    private Map map;
    
    //Maps that are used for drawing
    private HashMap<String, Integer> widthMap; 
    private HashMap<String, Color> colorMap;
    
    //Drag start point
    private Point startPoint;

    private double scaleFactorA;
    private double scaleFactorB;
    
    private double centLat;
    private double centLon;
    
    private double centX;
    private double centY;

    private double zoomFactor;
    
    private boolean drawWayPoints;
    private boolean settingStartWayPoint;
    private boolean settingEndWayPoint;
    // Map Coordinate of start point
    private Point2D.Double startWayPoint;
    // Map Coordinate of end point
    private Point2D.Double endWayPoint;
    
    public MapPanel(Map map) {
	super();
	this.map = map;
	
	addComponentListener(this);
	centLat = (map.getMaxLat() + map.getMinLat()) / 2.0;
	centLon = (map.getMaxLon() + map.getMinLon()) / 2.0;
	
	centX = getWidth() / 2.0;
	centY = getHeight() / 2.0;
	
	scaleFactorA = 6500;

	zoomFactor = 1;
	
	drawWayPoints = true;
	settingStartWayPoint = true;
	settingEndWayPoint = false;
	startWayPoint = null;
	endWayPoint = null;
	
	this.setOpaque(true);
	this.setBackground(Color.WHITE);
	
	widthMap = new HashMap<String, Integer>();
	colorMap = new HashMap<String, Color>();

	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent event) {
		if(event != null) {
		    startPoint = event.getPoint();
		    // Convert point to coordinates
		    if(settingEndWayPoint) {
			Point p = event.getPoint();
			endWayPoint =  convertToCoordinate(p.getX(), p.getY());
			settingEndWayPoint = false;
    		    	repaint();
		    }
		    
		    if(settingStartWayPoint) {
			System.out.println("Received screen coords");

			Point p = event.getPoint();
			System.out.println(p.getX() + " " + p.getY());
			
			startWayPoint =  convertToCoordinate(p.getX(), p.getY());
			settingStartWayPoint = false;
			settingEndWayPoint = true;
			repaint();
		    }
		}
	    }
	    
	    @Override
	    public void mouseReleased(MouseEvent event) {
		startPoint = null;
	    }
	    
	});
	
	this.addMouseMotionListener(new MouseMotionAdapter() {
	    @Override
	    public void mouseDragged(MouseEvent event) {
		Point dragPoint = event.getPoint();
		double startX = startPoint.getX();
		double startY = startPoint.getY();
		double dragX = dragPoint.getX();
		double dragY = dragPoint.getY();
		
		double dx = dragX - startX;
		double dLon = dx / scaleFactorA;
		centLon += dLon;
		double dy = dragY - startY;
		double dLat = dy/scaleFactorB;
		centLat -= dLat;
		
		startPoint = dragPoint;
		
		repaint();
	    }
	    
	});
	
	this.addMouseWheelListener(new MouseWheelListener() {
	    @Override
	    public void mouseWheelMoved(MouseWheelEvent e) {
		double rotations = e.getPreciseWheelRotation();
		if(rotations < 0) {	// zoom in
		    if(zoomFactor < 14.0)
			zoomFactor += 0.1;
		} else {	// zoom out
		    if(zoomFactor > .5)
			zoomFactor -= 0.1;
		}
		repaint();
	    }
	});

    }

    /* 
     * Draws map
     */
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	
	HashMap<String, Way> ways = map.getWays();
	for(Way w : ways.values()) {
	    /**
	     * First check to see if we should draw this way.
	     * We should only be drawing roads and some buildings
	     * 
	     * Then get the color and width of the way
	     */
	    if(w.canDrive()) {
		String type = w.getRoadType();
	    } else if(w.isBuilding()) {
		
	    } else if(w.isBoundary()) {	//Remove boundary
		continue;
	    }
	    
	    ArrayList<String> refs = w.getRefs();
	    if(refs.size() > 0) {
		//Should prob check for null values
		GPSNode prev = (GPSNode) map.getNode(refs.get(0));
		GPSNode curr = null;
		for(int i = 1; i < refs.size(); i++) {
		    curr = (GPSNode) map.getNode(refs.get(i));
		    if(curr != null) {
			Point2D.Double prevCoord = convertToPoint(
				prev.getLatitude(), 
				prev.getLongitude());

			Point2D.Double currCoord = convertToPoint(
				curr.getLatitude(), 
				curr.getLongitude());

			g2.drawLine((int) prevCoord.getX(), 
					(int) prevCoord.getY(),
					(int) currCoord.getX(), 
					(int) currCoord.getY());
			prev = curr;
		    } else {
			continue;
		    }
		}				    
	    }
	}
	
	if(drawWayPoints) {
	    if(startWayPoint != null) {
		Point2D.Double p = convertToPoint(startWayPoint.getY(), startWayPoint.getX());
		//g2.drawOval((int)p.getX(), (int)p.getY(), 10, 10);
		g2.fillOval((int)p.getX(), (int)p.getY(), 10, 10);
	    }

	    if(endWayPoint != null) {
		Point2D.Double p = convertToPoint(endWayPoint.getY(), endWayPoint.getX());
		//g2.drawOval((int)p.getX(), (int)p.getY(), 10, 10);
		g2.fillOval((int)p.getX(), (int)p.getY(), 10, 10);

	    }
	}
    }
    
    /**
     * Converts lat and lon to a Point2D object containing on screen x,y pair
     * 
     * @param lat
     * @param lon
     * @return Point2D.Double
     */
    public Point2D.Double convertToPoint(double lat, double lon) {
	double x = 0;
	double y = 0;
	
	y = zoomFactor * scaleFactorA * (centLat - lat) + centY;

	scaleFactorB = scaleFactorA * Math.cos(Math.toRadians(lat));
	x = zoomFactor * scaleFactorB * (lon - centLon) + centX;

	return new Point2D.Double(x, y);
    }
    
    /**
     * Converts x, y to Point2D object with longitude and latitude
     * 
     * @param x
     * @param y
     * @return Point2D.Double
     */
    public Point2D.Double convertToCoordinate(double x, double y) {
	double latitude = centLat - ((y - centY)/(zoomFactor * scaleFactorA));
	System.out.println("Lat: " + latitude);
	scaleFactorB = scaleFactorA * Math.cos(Math.toRadians(latitude));
	double longitude = ((x - centX)/(zoomFactor * scaleFactorB)) + centLon;
	System.out.println(" Lon: " + longitude);
	
	return new Point2D.Double(longitude, latitude);
    }
    
    public void componentMoved(ComponentEvent e) {
	return;
    }
    
    public void componentResized(ComponentEvent e) {
	centX = getWidth() / 2.0;
	centY = getHeight() / 2.0;
	repaint();
    }
    
    public void componentShown(ComponentEvent e) {
	return;
    }

    public void componentHidden(ComponentEvent e) {
	return;
    }
}
