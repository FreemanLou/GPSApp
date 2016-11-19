import java.util.*;
/*
 * 
 * Object representing a map
 */

public class Map {
    private HashMap<String, GPSNode> nodes;	// Id and node
    private HashMap<String, Way> ways;		// Id and way
    private HashMap<String, Relation> relations; // Id and relation
    
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;

    private TreeMap<String, Way> wayNameMap;	// Used for display on left panel

    public Map() {
	nodes = new HashMap<String, GPSNode>();
	ways = new HashMap<String, Way>();
	relations = new HashMap<String, Relation>();
	
	wayNameMap = new TreeMap<String, Way>();
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

    /*
     * Adds a node
     */
    public void addNode(String key, GPSNode value) {
	nodes.put(key, value);
    }
    
    /*
     * Adds a way
     */
    public void addWay(String key, Way value) {
	ways.put(key, value);
    }
    
    /* 
     * Creates alphabetized map of names to Way
     */
    public void setNameMap() {
	for(Way value : ways.values()) {
        	String name = value.getTag("name");
        	if(name != null) {
                    wayNameMap.put(name, value);
        	}
	}
    }
    
    /*
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

    /*
     * Get node by id
     */
    public GPSNode getNode(String key) {
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

    /*
     * Finds Way by name and returns it
     */
    public Way getWayByName(String name) {
	return wayNameMap.get(name);
    }
    
    /*
     * Returns alphabetized set of Way names
     */
    public Set<String> getNameList() {
	return wayNameMap.keySet();
    }
    
}
