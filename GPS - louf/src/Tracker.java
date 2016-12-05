import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JTextArea;

import com.starkeffect.highway.GPSEvent;
import com.starkeffect.highway.GPSListener;

/**
 * Responds to GPSEvents by updating subscribed 
 * GPSApp, Map and MapPanel objects
 * 
 * Also performs off course check
 * 
 * @author Freeman
 *
 */

public class Tracker implements GPSListener {
    private GPSApp app;
    private MapPanel mapPanel;
    private JTextArea textArea;
    private Map map;
    
    //Max stray distance from edge in meters
    private double maxDist = 7.5;
    
    private Point2D.Double userLoc;
    private double heading;
        
    private boolean tracking;
    
    private GPSNode finalPoint;
    
    // 60 degree deviation from heading allowed
    private double maxDeviation = 60;
    
    private ArrayList<GraphEdge> route;

    public Tracker(GPSApp app, MapPanel mapPanel, Map map, JTextArea textArea) {
	this.app = app;
	this.mapPanel = mapPanel;
	this.map = map;
	this.textArea = textArea;
	
	route = null;
	userLoc = null;
	tracking = false;
	finalPoint = null;
    }
    
    /**
     * Sets whether the Tracker is activated
     * 
     * @param tracking
     */
    public void setTracking(boolean tracking) {
	this.tracking = tracking;
//	textArea.append("\n" + route.toString());
    }
    
    /**
     * Receives and processed GPSEvents
     * Calls functions to check if oncourse or
     * if at destination
     */
    @Override
    public void processEvent(GPSEvent event) {
	if(!tracking)
	    return;

	double lat = event.getLatitude();
	double lon = event.getLongitude();
	
	heading = event.getHeading();
	
	userLoc = new Point2D.Double(lon, lat);
	
	map.setUserLocation(lat, lon);
	
	if(checkFinished()) {
	    textArea.setText("Destination Reached");
	    tracking = false;
	    mapPanel.setTracking(false);
	    app.toggleStartNav();
	} else {
	    boolean onCourse = checkOnCourse();
	    
	    if(!onCourse) {
		app.notifyOffCourse();
		textArea.append("\n***OFF COURSE. RECALCULATING***");
	    }
	}
	mapPanel.repaint();
    }
    
    /**
     * Checks to see if user is close enough to the final point
     * 
     * @return true if finished, false otherwise
     */
    private boolean checkFinished() {
	double dist = map.calcDistance(userLoc.getY(), userLoc.getX(), 
		finalPoint.getLatitude(), finalPoint.getLongitude());
	
	//System.out.println("Dist: " + dist + "m");
	return dist < maxDist;
    }
    
    /**
     * Prints current road, next road, and distance info to textArea
     * 
     * @param currentEdge
     * @param currentWay
     */
    private void printInfo(GraphEdge edge, Way way) {
	textArea.setText("Current Road: " + way.getName());
	
	Double distance = 0.0;

	//Get next way
	int index = route.indexOf(edge);
	for(int i = index; i < route.size(); i++) {
	    GraphEdge next = route.get(i);
	    Way nextWay = map.getWay(next.getWay());
	    
	    if(nextWay.equals(way)) {
		continue;
	    } else {
		GPSNode nextNode = (GPSNode) next.getVertexA();
		distance = map.calcDistance(userLoc.getY(), userLoc.getX(), nextNode.getLatitude(), nextNode.getLongitude());
		textArea.append("\nNext Road: " + nextWay.getName() + " in " + distance + " meters");
		break;
	    }
	}

	distance =  map.calcDistance(userLoc.getY(), userLoc.getX(), finalPoint.getLatitude(), finalPoint.getLongitude());;
//	for(int i = index; i < route.size(); i++) {
//	    distance += route.get(i).getWeight();
//	}
	textArea.append("\nTotal Distance left: " + distance + " meters");
    }
    
    /**
     * Checks to see if the user is on course 
     * 
     * @return true if on course, false otherwise
     */
    private boolean checkOnCourse() {
	GraphEdge edge = getClosestEdgeOnRoute();
	
	if(edge == null)
	    return false;
	else {
	    String key = edge.getWay();
	    Way parent = map.getWay(key);

	    printInfo(edge, parent);

	    return true;
	}
//	Check bearing
//	GPSNode a = (GPSNode) edge.getVertexA();
//	GPSNode b = (GPSNode) edge.getVertexB();
//		
//	double bearing = Map.calcBearing(a.getLatitude(), a.getLongitude(), 
//		b.getLatitude(), b.getLongitude());
//	
//	System.out.println("Expected bearing: " + bearing);
//	System.out.println("Actual: " + heading);
//
//	if(bearing > heading + maxDeviation || bearing < heading - maxDeviation)
//	    return false;
	
//	return true;
    }
    
    /**
     * Sets the route 
     */
    public void setRoute(ArrayList<GraphEdge> newRoute, GPSNode finalPoint) {
	if(newRoute == null)
	    return;
	
	route = newRoute;
	this.finalPoint = finalPoint;
	
	double dist = 0.0;
	for(GraphEdge e : route)
	    dist += e.getWeight();
	textArea.setText("Distance to go: " + dist + " meters" );
    }
    
    /**
     * Get the closest edge the user is to/on.
     * 
     * @return
     */
    private GraphEdge getClosestEdgeOnRoute() {
	if(route == null || userLoc == null)
	    return null;
	
	GPSNode closest = map.getClosestNodeOnRoad(userLoc.getY(), userLoc.getX());
	
	if(closest != null) {
	    for(GraphEdge edge : route) {
		if(edge.contains(closest)) {
		    return edge;
		}
	    }
	}
	return null;
    }
}
