/**
 * Represents an edge in a graph Edges should be non-negative
 * 
 * Immutable!
 * 
 * @author Freeman
 */

public class GraphEdge {

    // Weight value of the edge
    private double weight;

    // First vertex
    private GraphNode vertexA;
    
    // The way that the edge is part of
    private String way;

    // Second vertex
    private GraphNode vertexB;

    public GraphEdge(GraphNode vertexA, GraphNode vertexB, double weight, String way) {
	this.vertexA = vertexA;
	this.vertexB = vertexB;
	this.weight = weight;
	this.way = way;
    }

    /**
     * Gets the weight of the edge
     * 
     * @return double weight
     */
    public double getWeight() {
	return weight;
    }

    /**
     * Gets vertexA
     * 
     * @return string ID
     */
    public GraphNode getVertexA() {
	return vertexA;
    }

    /**
     * Gets vertexB
     * 
     * @return string ID
     */
    public GraphNode getVertexB() {
	return vertexB;
    }
    
    /**
     * Get other node
     * 
     * @return other GraphNode if available, null if not
     */
    public GraphNode getOtherNode(GraphNode a) {
	if(a != null && (a.equals(vertexA) || a.equals(vertexB)))
	    return a.equals(vertexA) ? vertexB : vertexA;
	else
	    return null;
    }
    
    /**
     * Gets the id of the way the edge is part of
     * 
     * @return String Way
     */
    public String getWay() {
	return way;
    }
    
    /**
     * Checks to see if the edge contains the given GraphNode as a vertex
     * @param target
     * @return true if edge contains the node false otherwise
     */
    public boolean contains(GraphNode target) {
	if(target == null)
	    return false;
	else
	    return target.equals(vertexA) || target.equals(vertexB);
    }
}