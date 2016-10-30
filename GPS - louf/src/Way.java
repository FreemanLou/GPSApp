/*
 * Represents a GPS way object
 */
import java.util.*;

public class Way extends GPSObject implements Comparable<Way>{
    private String role;
    
    private ArrayList<String> refs;	//Reference
    private HashMap<String, Node> nodes;
    
    public Way(String user, String uid, String id, boolean visible, String version,
	    String changeSet, String timeStamp) {
	super(user, uid, id, visible, version, changeSet, timeStamp);	

	this.role = null;
	
	refs = new ArrayList<String>();
	nodes = new HashMap<String, Node>();
    }
    
    /**
     * @return the refs
     */
    public ArrayList<String> getRefs() {
        return refs;
    }

    public void addRef(String ref) {
	refs.add(ref);
    }
    
    /**
     * @return the nodes
     */
    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    public void addNode(String id, Node n) {
	nodes.put(id, n);
    }

    public String getRole() {
	return role;
    }
    
    public void setRole(String role) {
	this.role = role;
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
	result = prime * result + ((role == null) ? 0 : role.hashCode());
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
	if (role == null) {
	    if (other.role != null)
		return false;
	} else if (!role.equals(other.role))
	    return false;
	return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Way [role=");
	builder.append(role);
	builder.append(", nodes=");
	builder.append(nodes);
	builder.append(", user=");
	builder.append(user);
	builder.append(", uid=");
	builder.append(uid);
	builder.append(", id=");
	builder.append(id);
	builder.append(", visible=");
	builder.append(visible);
	builder.append(", version=");
	builder.append(version);
	builder.append(", changeSet=");
	builder.append(changeSet);
	builder.append(", timeStamp=");
	builder.append(timeStamp);
	builder.append(", tags=");
	builder.append(tags.toString());
	builder.append(", refs:");
	builder.append(refs.toString());
	builder.append("]");
	builder.append("\n");
	return builder.toString();
    }
}
