import java.util.ArrayList;

/**
 * Graph interface that requires classes to implement graph related
 * functionality
 * 
 * @author Freeman
 *
 */

public interface Graph {
    /**
     * Computes a route between two nodes in a graph
     */
    public ArrayList<GraphEdge> getRoute(GraphNode a, GraphNode b);
}
