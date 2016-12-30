
/**
 * 
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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
import java.util.HashSet;

import javax.swing.JPanel;

/**
 * @author freeman
 *
 */
public class MapPanel extends JPanel implements ComponentListener {
    private static final long serialVersionUID = 1L;

    private Map map;

    // Maps that are used for drawing
    private HashMap<String, Double> widthMap;

    private HashMap<String, Color> colorMap;
    
    //Used to store computation result
    private ArrayList<GraphEdge> route;
    //private ArrayList<GraphNode> route;

    // Drag start point
    private Point startPoint;

    // Keeps track if dragging actually occured
    private boolean dragged;
    
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
    
    private boolean showRoute;
    
    private boolean driveMode;

    private boolean tracking;
    
    private int pointRadius = 5;
    
    // Panel Coordinate of start point
    private Point2D.Double startWayPoint;

    // Panel Coordinate of end point
    private Point2D.Double endWayPoint;
    
    // Closest point to cursor
    private Point2D.Double closestPoint;
    
    // User location
    private Point2D.Double userLocation;

    public MapPanel(Map map) {
	super();
	this.map = map;

	addComponentListener(this);
	centLat = (map.getMaxLat() + map.getMinLat()) / 2.0;
	centLon = (map.getMaxLon() + map.getMinLon()) / 2.0;

	centX = getWidth() / 2.0;
	centY = getHeight() / 2.0;

	dragged = false;
	
	scaleFactorA = 6500;

	zoomFactor = 1;

	drawWayPoints = true;
	settingStartWayPoint = false;
	settingEndWayPoint = false;
	startWayPoint = null;
	endWayPoint = null;
	
	closestPoint = null;
	
	route = null;
	showRoute = false;
	driveMode = false;
	
	this.setOpaque(true);
	this.setBackground(Color.WHITE);

	tracking = false;
	
	widthMap = new HashMap<String, Double>();
	colorMap = new HashMap<String, Color>();

	widthMap.put("residential", 2.0);
	widthMap.put("unclassified", 2.0);
	widthMap.put("primary", 7.0);
	widthMap.put("primary_link", 7.0);
	widthMap.put("secondary", 6.0);
	widthMap.put("secondary_link", 6.0);
	widthMap.put("tertiary", 5.0);
	widthMap.put("tertiary_link", 5.0);
	widthMap.put("turning_circle", 2.0);
	widthMap.put("living_street", 2.0);
	widthMap.put("trunk", 3.0);
	widthMap.put("motorway", 7.0);
	widthMap.put("motorway_link", 3.5);
	widthMap.put("trunk_link", 2.5);
	widthMap.put("service", 2.0);

	colorMap.put("residential", Color.lightGray);
	colorMap.put("unclassified", Color.lightGray);
	colorMap.put("service", Color.lightGray);
	colorMap.put("primary", Color.ORANGE);
	colorMap.put("primary_link", Color.ORANGE);
	colorMap.put("secondary", Color.lightGray);
	colorMap.put("secondary_link", Color.lightGray);
	colorMap.put("tertiary", Color.lightGray);
	colorMap.put("tertiary_link", Color.lightGray);
	colorMap.put("turning_circle", Color.lightGray);
	colorMap.put("living_street", Color.lightGray);
	colorMap.put("trunk", Color.ORANGE);
	colorMap.put("motorway", Color.ORANGE);
	colorMap.put("motorway_link", Color.ORANGE);
	colorMap.put("trunk_link", Color.ORANGE);

	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent event) {
		startPoint = event.getPoint();
		dragged = false;
	    }

	    @Override
	    public void mouseReleased(MouseEvent event) {
		if (dragged == false) {
		    // Convert point to coordinates
		    if (settingEndWayPoint) {
			//Point p = event.getPoint();
			Point p = startPoint;
			endWayPoint = convertToCoordinate(p.getX(), p.getY());
			
			GPSNode closest = map.getClosestNodeOnRoad(endWayPoint.getY(), endWayPoint.getX());
			
			endWayPoint.setLocation(closest.getLongitude(), closest.getLatitude());
			// Distance test
			// System.out.println(map.calcDistance(startWayPoint.getY(),
			// startWayPoint.getX(),
			// endWayPoint.getY(), endWayPoint.getX()));

			settingEndWayPoint = false;
			
			repaint();
		    }

		    if (settingStartWayPoint) {
			System.out.println("Received screen coords");

			Point p = startPoint;
			System.out.println(p.getX() + " " + p.getY());

			startWayPoint = convertToCoordinate(p.getX(), p.getY());
			
			GPSNode closest = map.getClosestNodeOnRoad(startWayPoint.getY(), startWayPoint.getX());
			startWayPoint.setLocation(closest.getLongitude(), closest.getLatitude());
			
			settingStartWayPoint = false;
			//settingEndWayPoint = true;
			repaint();
		    }
		}
		startPoint = null;
	    }
	});

	this.addMouseMotionListener(new MouseMotionAdapter() {
	    @Override
	    public void mouseDragged(MouseEvent event) {
		dragged = true;
		Point dragPoint = event.getPoint();
		double startX = startPoint.getX();
		double startY = startPoint.getY();
		double dragX = dragPoint.getX();
		double dragY = dragPoint.getY();

		double dx = dragX - startX;
		double dLon = dx / scaleFactorA;
		centLon -= dLon;
		double dy = dragY - startY;
		double dLat = dy / scaleFactorB;
		centLat += dLat;

		startPoint = dragPoint;

		repaint();
	    }

	    @Override
	    public void mouseMoved(MouseEvent event) {
		if(settingStartWayPoint || settingEndWayPoint) {
		    Point hoverPoint = event.getPoint();
		    closestPoint = convertToCoordinate(hoverPoint.getX(), hoverPoint.getY());

		    GPSNode closestNode = map.getClosestNodeOnRoad(closestPoint.getY(), closestPoint.getX());
		    closestPoint.setLocation(closestNode.getLongitude(), closestNode.getLatitude());
		    repaint();
		}
	    }
	});

	this.addMouseWheelListener(new MouseWheelListener() {
	    @Override
	    public void mouseWheelMoved(MouseWheelEvent e) {
		double rotations = e.getPreciseWheelRotation();
		if (rotations < 0) { // zoom in
		    if (zoomFactor < 14.0)
			zoomFactor += 0.5;
		} else { // zoom out
		    if (zoomFactor > .5)
			zoomFactor -= 0.5;
		}
		repaint();
	    }
	});
    }

    /**
     * If true, then the user click will set a start point
     * Exclusive with setEndPoint
     * @param option
     */
    public void setStart(boolean option) {
	settingStartWayPoint = option;
//	settingEndWayPoint = !option;
    }
    
    /**
     * Clears existing start point
     */
    public void clearStartPoint() {
	startWayPoint = null;
	route = null;
	repaint();
    }
    
    /**
     * Clears existing end point
     */
    public void clearEndPoint() {
	endWayPoint = null;
	route = null;
	repaint();
    }
    
    /**
     * Checks to see that both the start and end points are set
     * so that route computation can start
     * 
     * @return true if both are set, false other wise
     */
    public boolean bothPointsSet(){
	return startWayPoint != null && 
		endWayPoint != null;
    }
    
    /**
     * Choose whether to set the end point
     * 
     * @param option
     */
    public void setEnd(boolean option) {
	settingEndWayPoint = option;
//	settingStartWayPoint = !option;
    }
    
    /**
     * Choose whether to show route
     * 
     * @param show
     */
    public void setShowRoute(boolean show) {
	showRoute = show;
	repaint();
    }

    /**
     * Gets route from map
     * 
     * @return true if valid route was returned, false otherwise
     */
    public ArrayList<GraphEdge> getRoute(boolean useUserLoc) {
	if(bothPointsSet()) {
	    GPSNode start = null;
	    if(!useUserLoc) {
		start = map.getClosestNodeOnRoad(startWayPoint.getY(), startWayPoint.getX());
	    } else {
		startWayPoint = map.getUserLocation();
		start = map.getClosestNodeOnRoad(startWayPoint.getY(), startWayPoint.getX());
	    }
	    GPSNode end = map.getClosestNodeOnRoad(endWayPoint.getY(), endWayPoint.getX());

	    route = map.getRoute(start, end);
	    return route;
	}
	return null;
    }
    
    /**
     * Sets whether or not the user location is shown
     */
    public void setTracking(boolean tracking) {
	this.tracking = tracking;
	repaint();
    }
    
    /**
     * Gets whether or not mapPanel is currently in tracking mode
     * 
     * @return true if tracking
     */
    public boolean isTracking() {
	return tracking;
    }
    
    /**
     * Gets the end point of a route as a GPSNode
     * performs conversion from point to closest GPSNode
     * 
     * @return GPSNode
     */
    public GPSNode getEndPoint() {
	return map.getClosestNodeOnRoad(endWayPoint.getY(), endWayPoint.getX());
    }
    
    /**
     * Draws map and any associated objects
     */
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;

//	THIS BLOCK OF CODE DISPLAYS THE RAW DRIVABLE NODES AND EDGES
//	USED FOR TESTING
//
//	HashSet<GraphNode> drivableNodes = map.drivableNodes;
//	
//	for(GraphNode n : drivableNodes) {
//	    GPSNode node = (GPSNode) n;
//	    Point2D.Double p = convertToPoint(node.getLatitude(), node.getLongitude());
//	    g2.fillOval((int) p.getX(), (int) p.getY(), 10, 10);
//	    
//	    ArrayList<GraphEdge> edges = n.getEdges();
//
//	    for(GraphEdge e : edges) {
//		GPSNode prev = (GPSNode) e.getVertexA();
//		GPSNode curr = (GPSNode) e.getVertexB();
//
//		Point2D.Double prevCoord = convertToPoint(
//				prev.getLatitude(), prev.getLongitude());
//
//		Point2D.Double currCoord = convertToPoint(
//			curr.getLatitude(), curr.getLongitude());
//
//		g2.drawLine((int) prevCoord.getX(),
//			(int) prevCoord.getY(), (int) currCoord.getX(),
//			(int) currCoord.getY());
//	    }
//	}

	// Set anti-aliasing
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
	
	HashMap<String, Way> ways = map.getWays();
	for (Way w : ways.values()) {
	    /**
	     * First check to see if we should draw this way. We should only be
	     * drawing roads and some buildings
	     * 
	     * Then get the color and width of the way
	     */
	    if (w.canDrive()) {
		String type = w.getRoadType();

		double width = widthMap.get(type);
		g2.setStroke(new BasicStroke((float) width));
		g2.setColor(colorMap.get(type));
	    } else if (w.isBuilding()) {
		// Buildings get smallest stroke
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.pink);
	    } else if (w.isBoundary()) {
//		g2.setStroke(new BasicStroke(1));
//		g2.setColor(Color.BLACK);
		continue;
	    } else if (w.isWaterWay()) {
//		g2.setStroke(new BasicStroke(7));
//		g2.setColor(Color.blue);
		continue;
	    } else if(w.isNatural()) {
		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(222,184,135));
	    } else {
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.pink);
	    }

	    ArrayList<String> refs = w.getRefs();
	    if (refs.size() > 0) {
		GPSNode prev = (GPSNode) map.getNode(refs.get(0));
		if (prev == null)
		    continue;

		GPSNode curr = null;
		for (int i = 1; i < refs.size(); i++) {
		    curr = (GPSNode) map.getNode(refs.get(i));
		    if (curr != null) {
			Point prevPoint = convertToPoint(
				prev.getLatitude(), prev.getLongitude());

			Point currPoint = convertToPoint(
				curr.getLatitude(), curr.getLongitude());

			g2.drawLine((int) prevPoint.getX(),
				(int) prevPoint.getY(), (int) currPoint.getX(),
				(int) currPoint.getY());
			prev = curr;
		    } else {
			continue;
		    }
		}
	    }
	}

	if (settingStartWayPoint || settingEndWayPoint) {
	    g2.setColor(Color.green);
	    if(closestPoint != null) {
		convertToPointAndDraw(g2, closestPoint.getY(), closestPoint.getX());
	    }
	}
	
	if(showRoute && route != null) {
	    g2.setColor(Color.yellow);
	    for(GraphEdge e : route) {
		GPSNode a = (GPSNode) e.getVertexA();
		Point aPoint = convertToPoint(a.getLatitude(), a.getLongitude());
		
		GPSNode b = (GPSNode) e.getVertexB();
		Point bPoint = convertToPoint(b.getLatitude(), b.getLongitude());

		g2.drawLine((int)aPoint.getX(), (int)aPoint.getY(), 
			(int)bPoint.getX(), (int)bPoint.getY());
	    }
	}
	
	if (drawWayPoints) {
	    g2.setColor(Color.red);
	    if (startWayPoint != null) {
		convertToPointAndDraw(g2, startWayPoint.getY(), startWayPoint.getX());
	    }

	    if (endWayPoint != null) {
		convertToPointAndDraw(g2, endWayPoint.getY(), endWayPoint.getX());
	    }
	}
	
	if(tracking) {
	    g2.setColor(Color.blue);
	    Point2D.Double location = map.getUserLocation();

	    if(location != null) {
		convertToPointAndDraw(g2, location.getY(), location.getX());
	    }
	}
    }

    /**
     * Converts lat and lon to a Point2D object containing on screen x,y pair
     * 
     * @param lat
     * @param lon
     * @return Point
     */
    public Point convertToPoint(double lat, double lon) {
	double x = 0;
	double y = 0;

	y = zoomFactor * scaleFactorA * (centLat - lat) + centY;

	scaleFactorB = scaleFactorA * Math.cos(Math.toRadians(lat));
	x = zoomFactor * scaleFactorB * (lon - centLon) + centX;

	return new Point((int)x, (int)y);
    }
    
    /**
     * Converts given coordinate to a point and draws it with pre-set radii
     * 
     * @param g2
     * @param lat
     * @param lon
     */
    public void convertToPointAndDraw(Graphics2D g2, double lat, double lon) {
	Point p = convertToPoint(lat, lon);
	g2.fillOval((int) p.getX() - pointRadius, (int) p.getY() - pointRadius, pointRadius * 2, pointRadius * 2);
    }

    /**
     * Converts x, y to Point2D object with longitude and latitude
     * 
     * @param x
     * @param y
     * @return Point2D.Double
     */
    public Point2D.Double convertToCoordinate(double x, double y) {
	double latitude = centLat - ((y - centY) / (zoomFactor * scaleFactorA));
	System.out.println("Lat: " + latitude);
	scaleFactorB = scaleFactorA * Math.cos(Math.toRadians(latitude));
	double longitude = ((x - centX) / (zoomFactor * scaleFactorB))
		+ centLon;
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
