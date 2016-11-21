/**
 * Represents an edge in a graph 
 * Edges should be non-negative
 * 
 * Immutable!
 * 
 * @author Freeman
 */

public class GraphEdge {
    /**
     * Gets the weight of the edge
     * @return double weight
     */
    public double getWeight();

    /**
     * Gets vertexA
     * @return string ID
     */
    public GraphNode getVertexA();

    /**
     * Gets vertexB
     * @return  string ID
     */
    public GraphNode getVertexB();
}