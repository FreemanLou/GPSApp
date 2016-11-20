import java.util.ArrayList;
import java.util.HashMap;

/*
 * Represents GPS relation object
 */
public class Relation extends GPSObject{
    private HashMap<String, String> members;

    /**
     * @param id
     * @param visible
     */
    public Relation(String id, boolean visible) {
	super(id, visible);
	
	members = new HashMap<>();
    }

    /**
     * Add a member
     * @param string GPSObject id
     * @param string type
     */
    public void addMember(String id, String type) {
	members.put(id, type);
    }
    
    /**
     * Get the members 
     * Might be replaced with clone of list?
     */
    public HashMap<String, String> getMembers() {
	return members;
    }

    /**
     * Returns string representation of the relation
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Relation [id=");
	builder.append(id);
	builder.append(", visible=");
	builder.append(visible);
	builder.append(", tags=");
	builder.append(tags.toString());
	builder.append(", member=");
	builder.append(members.toString());
	builder.append("]");
	builder.append("\n");
	return builder.toString();
    }
}
