import java.awt.geom.Point2D;
import java.util.*;
/**
 * Object representing a map and also
 * an undirected graph
 * 
 * @author Freeman
 */

public class Map implements Graph {
    private HashMap<String, GPSNode> nodes;	// Id and node
    private HashMap<String, Way> ways;		// Id and way
    private HashMap<String, Relation> relations; // Id and relation
    
    private HashSet<GraphNode> drivableNodes;
    
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;
    
    private Point2D.Double userLocation;
    
    public Map() {
    	nodes = new HashMap<String, GPSNode>();
    	ways = new HashMap<String, Way>();
    	relations = new HashMap<String, Relation>();
    	
    	drivableNodes = new HashSet<GraphNode>();
    	
    	userLocation = null;
    }
    
    /**
     * @return the minLat
     */
    public double getMinLat() {
        return minLat;
    }

    /**
     * @param minLat the minLat to set
     */
    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    /**
     * @return the maxLat
     */
    public double getMaxLat() {
        return maxLat;
    }

    /**
     * @param maxLat the maxLat to set
     */
    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    /**
     * @return the minLon
     */
    public double getMinLon() {
        return minLon;
    }

    /**
     * @param minLon the minLon to set
     */
    public void setMinLon(double minLon) {
        this.minLon = minLon;
    }

    /**
     * @return the maxLon
     */
    public double getMaxLon() {
        return maxLon;
    }

    /**
     * @param maxLon the maxLon to set
     */
    public void setMaxLon(double maxLon) {
        this.maxLon = maxLon;
    }

    /**
     * Update the user location. Meant to be called by tracker
     * @param lat
     * @param lon
     */
    public void setUserLocation(double lat, double lon) {
	userLocation = new Point2D.Double(lon,lat);
    }
    
    /**
     * Returns user location
     * @return Point2D.Double
     */
    public Point2D.Double getUserLocation() {
	if(userLocation == null)
	    return null;
	return new Point2D.Double(userLocation.getX(), userLocation.getY());
    }
    
    /**
     * Adds a node
     */
    public void addNode(String key, GPSNode value) {
    	nodes.put(key, value);
    }
    
    /**
     * Adds a way
     */
    public void addWay(String key, Way value) {
    	ways.put(key, value);
    }
    
    /**
     * Adds a relation
     */
    public void addRelation(String key, Relation value) {
    	relations.put(key, value);
    }

    /**
     * @return the nodes
     */
    public HashMap<String, GPSNode> getNodes() {
        return nodes;
    }

    /**
     * Get node by id
     */
    public GraphNode getNode(String key) {
	return nodes.get(key);
    }
    
    /**
     * Retrieves the HashMap containing all the ways
     * 
     * @return the ways
     */
    public HashMap<String, Way> getWays() {
        return ways;
    }
    
    /**
     * Searches for a way given a key
     * 
     * @return way corresponding to key
     */
    public Way getWay(String key) {
	return ways.get(key);
    }

    /**
     * Retrieves HashMap containing all the relations
     * 
     * @return the relations
     */
    public HashMap<String, Relation> getRelations() {
        return relations;
    }
    
    /**
     * Computes route between two graph nodes
     * 
     * @return ArrayList containing the route or null if not possible 
     */
    @Override
    public ArrayList<GraphEdge> getRoute(GraphNode a, GraphNode b) {
    	HashMap<GraphNode, Double> distances = new HashMap<GraphNode, Double>();
    	HashMap<GraphNode, GraphEdge> predecessor = new HashMap<GraphNode, GraphEdge>();
    	HashSet<GraphNode> visited = new HashSet<GraphNode>();
    	
    	ArrayList<GraphEdge> result = new ArrayList<GraphEdge>();
	
    	//Initialize distances, predecessor, and visited
    	for(GraphNode g : drivableNodes) {
    	    distances.put(g, Double.MAX_VALUE);
    	    predecessor.put(g, null);
    	}
    	
    	int remaining = drivableNodes.size();
    	
    	//Put starting node
    	distances.put(a, 0.0);
    	//predecessor.put(a, null);
    	
    	//Main loop
    	while(remaining > 0) {
    	    GraphNode closest = null;
    	    double minDist = Double.MAX_VALUE;

    	    for(GraphNode n : distances.keySet()) {
    		double dist = distances.get(n);
    		if(!visited.contains(n) &&
    			dist != Double.MAX_VALUE &&
    			(minDist == Double.MAX_VALUE || dist < minDist)) {
    		    closest = n;
    		    minDist = dist;
    		}
    	    }
    	    
    	    if(closest == null)
    		return null;
    	    
    	    if(closest.equals(b))
    		break;
    	    
    	    visited.add(closest);
    	    
    	    for(GraphEdge edge : closest.getEdges()) {
    		GraphNode adjacent = edge.getOtherNode(closest);
    		if(adjacent != null && !visited.contains(adjacent)) {
    		    //Map distance value for the other Node
    		    double otherDist = distances.get(adjacent);
    		    //Weight of edge from closest node to adjacent node
    		    double weight = edge.getWeight();
    		    String way = edge.getWay();
    		    
    		    if(otherDist == Double.MAX_VALUE ||
    			    weight + minDist < otherDist) {
    			distances.put(adjacent, weight + minDist);
    			
    			//Make new edge in correct order
    			GraphEdge corrected = new GraphEdge(closest, adjacent, weight, way);
    			
    			predecessor.put(adjacent, corrected);
    		    }
    		}
    	    }

    	    remaining--;
    	}
    	
    	//Backtrack to build route
    	if(distances.get(b) == Double.MAX_VALUE) {
    	    return null;
    	} else {
    	    //buildPath(predecessor, a, b, result);
    	    //Non recursive version
    	    Stack<GraphEdge> stack = new Stack<GraphEdge>(); 
    	    while(!b.equals(a)) {
    		GraphEdge edge = predecessor.get(b);
    		
    		//Make sure vertices are in correct order
    		GraphNode start = edge.getOtherNode(b);
    		//double weight = edge.getWeight();
    		//GraphEdge corrected = new GraphEdge(start, b, weight);
    		
    		stack.push(edge);
    		b = start;
    	    }
    	    
    	    while(!stack.isEmpty()) {
    		GraphEdge edge = stack.pop();
    		result.add(edge);
    	    }
    	}
    	
	return result;
    }
    
    /**
     * Recursively goes through predecessor map to create a route list
     * 
     * @param pred
     * @param start
     * @param nextToCheck
     * @param result
     * @return
     */
    private boolean buildPath(HashMap<GraphNode, GraphEdge> pred,
	    GraphNode start, GraphNode nextToCheck, ArrayList<GraphEdge> result) {
	if(nextToCheck.equals(start)	) {
	    return true;
	}
	
	GraphEdge previousEdge = pred.get(nextToCheck);
	
	if(previousEdge == null)
	    return false;

	GraphNode otherNode = previousEdge.getOtherNode(nextToCheck);
	
	if(buildPath(pred, start, otherNode, result)) {
	    result.add(previousEdge);
	} else
	    return false;
	
	return true;
    }
    
    /**
     * Get closest node
     * 
     * @param latitude
     * @param longitude
     * @return GPSNode that is closest to specified point
     */
    public GPSNode getClosestNode(double lat, double lon) {
		GPSNode closest = null;
		double min = Double.MAX_VALUE;
		
		for(GPSNode node : nodes.values()) {
		    double nodeLat = node.getLatitude();
		    double nodeLon = node.getLongitude();
		    
		    double dist = calcDistance(lat, lon, nodeLat, nodeLon);
		    
		    if(dist < min) {
			min = dist;
			closest = node;
		    }
		}
	
		return closest;
    }
    
    /**
     * Returns closest node that is part of a drivable Way
     * 
     * @param lat
     * @param lon
     * @return GPSNode
     */
    public GPSNode getClosestNodeOnRoad(double lat, double lon) {
	GPSNode closest = null;
	double min = Double.MAX_VALUE;
	
	Set<String> wayList = ways.keySet();
	for(String str : wayList) {
	    Way way = ways.get(str);
	    if(way != null && way.canDrive()) {
		ArrayList<String> refs = way.getRefs();
		if(refs != null && refs.size() > 0) {
		    for(String ref: refs) {
			GPSNode node = (GPSNode) getNode(ref);
			
			if(node == null)
			    continue;
			
			double nodeLat = node.getLatitude();
			double nodeLon = node.getLongitude();
			    
			double dist = calcDistance(lat, lon, nodeLat, nodeLon);
			    
			if(dist < min) {
			    min = dist;
			    closest = node;
			}
		    }
		}
	    }
	}
	
	return closest;
    }
    
    /**
     * Calculates distance between two coordinate sets
     * 
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     * @return double distance in meters
     */
    public double calcDistance(double lat1, double lon1, double lat2, double lon2) {
    	//Calculate great-circle distance
    	double radius = 6371000;	//radius of earth in meters
    	double deltaLat = Math.toRadians(lat2 - lat1);
    	double deltaLong = Math.toRadians(lon2 - lon1);
	
    	lat1 = Math.toRadians(lat1);
    	lat2 = Math.toRadians(lat2);
		
    	double a = Math.sin(deltaLat / 2.0) * Math.sin(deltaLat / 2.0) +
    			Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLong / 2.0) *
    			Math.sin(deltaLong / 2.0);
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
	
    	return radius * c;
    }
    
    /**
     * Calculates the bearing between two points
     * 
     * @return double bearing
     */
    public static double calcBearing(double lat1, double lon1, double lat2, double lon2) {
	double x = Math.sin(lon2 - lon1) * Math.cos(lat2);
	double y = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) *
		Math.cos(lat2) * Math.cos(lon2 - lon1);
	
	return ((Math.toDegrees(Math.atan2(x,y)) + 360) % 360);
    }
    
    /**
     * Creates edges by calculating distance and adding to the 
     * relevant nodes. Should only be called once after the entire
     * Map has been parsed.
     */
    public void createEdges() {
    	for(String key : ways.keySet()) {
    	    Way way = ways.get(key);
    	    if(way.canDrive()) {
    		ArrayList<String> refs = way.getRefs();
    			
    		if(refs.size() > 0) {
    		    GPSNode prev = (GPSNode) this.getNode(refs.get(0));
    		    drivableNodes.add(prev);
    		    
    		    GPSNode curr = null;
    		    for(int i = 1; i <refs.size(); i++) {
    			curr = (GPSNode) this.getNode(refs.get(i));
    			if(curr == null || prev == null)
    			    continue;
    			else {
    			    double distance = calcDistance(curr.getLatitude(), curr.getLongitude(),
    				    prev.getLatitude(), prev.getLongitude());

    			    GraphEdge edge = new GraphEdge(prev, curr, distance, way.id);
    			    prev.addEdge(edge);
    			    curr.addEdge(edge);
    			    
    			    drivableNodes.add(curr);
    			    prev = curr;
    			}
    		    }	
    		}
    	    }
    	}
    }
}
