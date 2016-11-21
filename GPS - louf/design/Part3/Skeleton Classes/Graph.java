/**
 * Represents a graph object
 * 
 * Support the addition of nodes and edges
 * 
 * Handles computation of shortest path between two
 * nodes
 * 
 * @author Freeman
 *
 */

public interface Graph{
    /**
     * Computes the shortest path between two nodes in the graph
     * 
     * @param a Starting node
     * @param b Ending node
     * @return ArrayList containing the edges of the graph in sequential order
     */
    public ArrayList<GraphEdge> getRoute(GraphNode a, GraphNode b);
    
    
}
