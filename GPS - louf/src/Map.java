import java.util.*;
/**
 * 
 * Object representing a map and also
 * an undirected graph
 * 
 * @author Freeman
 */

public class Map implements Graph{
    private HashMap<String, GraphNode> nodes;	// Id and node
    private HashMap<String, Way> ways;		// Id and way
    private HashMap<String, Relation> relations; // Id and relation
    
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;

    public Map() {
	nodes = new HashMap<String, GraphNode>();
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
    public void addNode(String key, GraphNode value) {
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
    public HashMap<String, GraphNode> getNodes() {
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
}
