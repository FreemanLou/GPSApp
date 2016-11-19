/**
 * Represents an edge in a graph 
 * Edges should be non-negative
 * 
 * Immutable!
 * 
 * @author Freeman
 */

public class GraphEdge {
    
    // Weight value of the edge
    private double weight;
    
    // ID of first vertex
    private String vertexA;
    
    // ID of second vertex
    private String vertexB;
    
    public GraphEdge(String vertexA, String vertexB, double weight) {
	this.vertexA = vertexA;
	this.vertexB = vertexB;
	this.weight = weight;
    }

    /**
     * Gets the weight of the edge
     * @return double weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the ID of vertexA
     * @return string ID
     */
    public String getVertexA() {
        return vertexA;
    }
    
    /**
     * Gets the ID of vertexB
     * @return  string ID
     */
    public String getVertexB() {
        return vertexB;
    }
}
