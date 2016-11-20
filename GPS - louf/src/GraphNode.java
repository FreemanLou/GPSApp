import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents a node in a graph
 * Used to perform graph related operations
 */

public interface GraphNode {
    /**
     * Add edge to node
     * 
     * @param GraphEdge object
     */
    public void addEdge(GraphEdge e);
    
    /**
     * Gets the edges coming out of this node
     * 
     * @return ArrayList of edges
     */
    public ArrayList<GraphEdge> getEdges();
    
    /**
     * Gets adjacent nodes
     * 
     * @return A set of adjacent nodes
     */
    public HashSet<GraphNode> getAdjacent();
}
