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

    // Second vertex
    private GraphNode vertexB;

    public GraphEdge(GraphNode vertexA, GraphNode vertexB, double weight) {
	this.vertexA = vertexA;
	this.vertexB = vertexB;
	this.weight = weight;
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
     */
    public GraphNode getOtherNode(GraphNode a) {
	if(a != null && (a == vertexA || a == vertexB))
	    return a == vertexA? vertexA : vertexB;
	else
	    return null;
    }
}