import java.util.*;
/*
 * Map object which represents the Model for the application. Map object is initialized without
 * data but is filled with a parser.
 */

public class Map {
    private HashMap<String, Node> nodes;	// Id and node
    private HashMap<String, Way> ways;		// Id and way
    private HashMap<String, Relation> relations; // Id and relation
    
    /*
     * Boundary values
     */
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;

    public Map() {}
    
    /**
     * Gets the minimum latitude boundary value
     * 
     * @return the minimum latitude boundary value
     */
    public double getMinLat() {}

    /**
     * Sets the minimum latitude boundary value 
     * 
     * @param minLat the minLat to set
     */
    public void setMinLat(double minLat) {}

    /**
     * Returns the maximum latitude boundary value
     * 
     * @return the maxLat
     */
    public double getMaxLat() {}

    /**
     * Sets the maxium latitude value
     * 
     * @param maxLat the maxLat to set
     */
    public void setMaxLat(double maxLat) {}

    /**
     * Returns the minimum longitude value
     * 
     * @return the minLon
     */
    public double getMinLon() {}

    /**
     * Sets the minimum longitude boundary value
     * 
     * @param minLon the minLon to set
     */
    public void setMinLon(double minLon) {}

    /**
     * Returns the maximum longitude boundary value
     * 
     * @return the maxLon
     */
    public double getMaxLon() {}

    /**
     * Sets the maximum longitude boundary value
     * 
     * @param maxLon the maxLon to set
     */
    public void setMaxLon(double maxLon) {}

    /** 
     * Adds a node to the map
     * 
     * @param the string id of the node and the node object
     */
    public void addNode(String key, Node value) {}
    
    /**
     * Adds a way to the map
     * 
     * @param the string id of the way and the way object
     */
    public void addWay(String key, Way value) {}
    
    /** 
     * Creates alphabetized map of names to Way
     */
    public void setNameMap() {}
    
    /**
     * Adds a relation to the map
     * 
     * @param the string id of the relation and the relation object
     */
    public void addRelation(String key, Relation value) {}

    /**
     * Gets the nodes in the map
     * 
     * @return the nodes as an iterator
     */
    public Iterator<Node> getNodes() {}

    /**
     * Performs look up on a node by id
     * 
     * @param string id value
     * @return Node object if found, null otherwise
     */
    public Node getNode(String key) {}
    
    /**
     * Gets the ways in the map
     * 
     * @return the ways as an iterator
     */
    public Iterator<Way> getWays() {}

    /**
     * Gets the relations in the map
     * 
     * @return the relations as an iterator
     */
    public Iterator<Relation> getRelations() {}

    /**
     * Performs lookup for way by name instead of by id
     * 
     * @param string name
     * @return the way if found, null otherwise
     */
    public Way getWayByName(String name) {}    
}
