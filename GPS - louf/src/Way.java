/*
 * Represents a GPS way object
 */
import java.util.*;

public class Way extends GPSObject implements Comparable<Way>{
    private ArrayList<String> refs;	//Reference
    private HashMap<String, GPSNode> nodes;
    
    public Way(String id, boolean visible) {
	super(id, visible);	

	refs = new ArrayList<String>();
	nodes = new HashMap<String, GPSNode>();
    }
    
    /**
     * @return the refs
     */
    public ArrayList<String> getRefs() {
        return refs;
    }

    /**
     * Add a ref to the way object
     * @param string containing ref code
     */
    public void addRef(String ref) {
	refs.add(ref);
    }
    
    /**
     * @return the nodes
     */
    public HashMap<String, GPSNode> getNodes() {
        return nodes;
    }

    /**
     * Add a node to the way
     * @param string id
     * @param Node object
     */
    public void addNode(String id, GPSNode n) {
	nodes.put(id, n);
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Way o) {
	String name1 = getTag("name");
	String name2 = o.getTag("name");
	if(name1 == null || name2 == null)
	    return 0;
	else 
	    return name1.compareTo(name2); 
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
	result = prime * result + ((refs == null) ? 0 : refs.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Way other = (Way) obj;
	if (nodes == null) {
	    if (other.nodes != null)
		return false;
	} else if (!nodes.equals(other.nodes))
	    return false;
	if (refs == null) {
	    if (other.refs != null)
		return false;
	} else if (!refs.equals(other.refs))
	    return false;
	return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Way [id=");
	builder.append(id);
	builder.append(", tags=");
	builder.append(tags.toString());
	builder.append(", refs:");
	builder.append(refs.toString());
	builder.append("]");
	builder.append("\n");
	return builder.toString();
    }
}
