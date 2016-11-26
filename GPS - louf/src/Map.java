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
    
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;

    
    public Map() {
    	nodes = new HashMap<String, GPSNode>();
    	ways = new HashMap<String, Way>();
    	relations = new HashMap<String, Relation>();
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
     * @return the ways
     */
    public HashMap<String, Way> getWays() {
        return ways;
    }

    /**
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
    	return null;
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
     * Creates edges by calculating distance and adding to the 
     * relevant nodes. Should only be called once after the entire
     * Map has been parsed.
     */
    public void createEdges() {
	//int numEdge = 0;
    	for(String key : ways.keySet()) {
    	    Way way = ways.get(key);
    	    if(way.canDrive()) {
    		ArrayList<String> refs = way.getRefs();
    			
    		if(refs.size() > 0) {
    		    GPSNode prev = (GPSNode) this.getNode(refs.get(0));
    				
    		    GPSNode curr = null;
    		    for(int i = 1; i <refs.size(); i++) {
    			curr = (GPSNode) this.getNode(refs.get(i));
    			if(curr == null)
    			    continue;
    			else {
    			    double distance = calcDistance(curr.getLatitude(), curr.getLongitude(),
    				    prev.getLatitude(), prev.getLongitude());
//    			    System.out.println(distance);
//    			    System.out.println(numEdge);
//    			    numEdge++;

    			    GraphEdge edge = new GraphEdge(prev, curr, distance);
    			    prev.addEdge(edge);
    			    curr.addEdge(edge);
    			}
    		    }	
    		}
    	    }
    	}
    }
}
