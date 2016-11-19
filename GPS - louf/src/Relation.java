import java.util.ArrayList;
import java.util.HashMap;

/*
 * Represents GPS relation object
 */
public class Relation extends GPSObject{
    private ArrayList<GPSObject> members;

    /**
     * @param id
     * @param user
     * @param uid
     * @param visible
     * @param version
     * @param changeSet
     * @param timeStamp
     */
    public Relation(String id, boolean visible) {
	super(id, visible);
	
	members = new ArrayList<GPSObject>();
    }

    /*
     * Add a member
     * @param member object
     */
    public void addMember(GPSObject g) {
	members.add(g);
    }
    
    /*
     * Get the members 
     * Might be replaced with clone of list?
     */
    public ArrayList<GPSObject> getMembers() {
	return members;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
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
