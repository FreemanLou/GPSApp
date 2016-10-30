/*
 * Represents a GPS way object
 */
import java.util.*;

public class Way extends GPSObject implements Comparable<Way>{
    private String role;
    
    private ArrayList<String> refs;	//Reference
    private HashMap<String, Node> nodes;
    
    public Way(String id, boolean visible) {}
    
    /**
     * Gets all the references in the object
     * 
     * @return the refs as an iterator
     */
    public Iterator<String> getRefs() {}

    /**
     * Add a reference to the way
     * 
     * @param String reference
     */
    public void addRef(String ref) {}
    
    /**
     * Gets all the nodes referenced to by the way
     * 
     * @return the nodes as iterator
     */
    public Iterator <Node> getNodes() {}

    /**
     * Add node to the way. 
     * Usually done after all refs have been parsed
     * so that only valid nodes are added
     * 
     * @param id string
     * @param n object
     */
    public void addNode(String id, Node n) {}

    /**
     * Gets the role of the Way object
     * @return role as a string
     */
    public String getRole() {}
    
    /**
     * Sets the role of the way object
     * @param role
     */
    public void setRole(String role) {}

    /**
     * Compares this Way to another Way for sorting based on name
     * 
     * @return 0 if equal in order, -1 is less than, 1 if greater than
     */
    @Override
    public int compareTo(Way o) {}

    /**
     * Computes hash code for the object
     * 
     * @return hash code as an integer
     */
    @Override
    public int hashCode() {}

    /**
     * Checks if another Way object is equal to the current object
     * 
     * @return true is equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {}

    /**
     * Returns string representation of object
     * 
     * @return string representation of the Way
     */
    @Override
    public String toString() {}
}
