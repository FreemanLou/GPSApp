import java.util.*;

/**
 * 
 * Represents a GPS node
 * 
 */
public class GPSNode extends GPSObject implements GraphNode {
    private double latitude;
    private double longitude;
    
    private ArrayList<GraphEdge> edges;
    private HashSet<GraphNode> adjacents;
    
    public GPSNode(double latitude, double longitude, String id, boolean visible) {
	super(id, visible);
	
	this.longitude = longitude;
	this.latitude = latitude;
	
	edges = new ArrayList<>();
	adjacents = new HashSet<>();
    } 
    
    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Computes the hashCode of the object
     * 
     * @return integer hashCode
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(latitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(longitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
    }

    /**
     * Compares this GPSNode to another
     * 
     * @return true if two nodes are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	GPSNode other = (GPSNode) obj;
	if (Double.doubleToLongBits(latitude) != Double
		.doubleToLongBits(other.latitude))
	    return false;
	if (Double.doubleToLongBits(longitude) != Double
		.doubleToLongBits(other.longitude))
	    return false;
	return true;
    }

    /**
     * Gets string representation of the GPSNode
     * 
     * @return String with object info
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Node [latitude=");
	builder.append(latitude);
	builder.append(", longitude=");
	builder.append(longitude);
	builder.append(", id=");
	builder.append(id);
	builder.append(", visible=");
	builder.append(visible);
	builder.append(", tags=");
	builder.append(tags.toString());
	builder.append("]");
	return builder.toString();
    }

    /**
     * Gets edges with the current node as a vertex
     * 
     * @return ArrayList of edges
     */
    @Override
    public ArrayList<GraphEdge> getEdges() {
	return edges;
    }

    /**
     * Add edge to node. Handles the nodes adjacent to
     * the current node as well.
     * 
     * @param GraphEdge object
     */
    public void addEdge(GraphEdge e) {
	edges.add(e);
    }
    
    /**
     * Gets nodes adjacent to the current node
     * 
     * @return ArrayList of adjacent nodes
     */
    @Override
    public HashSet<GraphNode> getAdjacent() {
	return adjacents;
    }
}
