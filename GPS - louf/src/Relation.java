import java.util.ArrayList;
import java.util.HashMap;

/*
 * Represents GPS relation object
 */
public class Relation extends GPSObject{
    private ArrayList<Member> members;

    /**
     * @param id
     * @param user
     * @param uid
     * @param visible
     * @param version
     * @param changeSet
     * @param timeStamp
     */
    public Relation(String user, String uid, String id, boolean visible, String version,
	    String changeSet, String timeStamp) {
	super(user, uid, id, visible, version, changeSet, timeStamp);
	
	members = new ArrayList<Member>();
    }

    /*
     * Add a member
     * @param member object
     */
    public void addMember(Member m) {
	members.add(m);
    }
    
    /*
     * Get the members 
     * Might be replaced with clone of list?
     */
    public ArrayList<Member> getMembers() {
	return members;
    }


    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Relation [user=");
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
	builder.append(", member=");
	builder.append(members.toString());
	builder.append("]");
	builder.append("\n");
	return builder.toString();
    }
    

}
