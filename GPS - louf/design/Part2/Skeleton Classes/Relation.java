import java.util.HashMap;

/**
 * Represents a GPS relation object
 * 
 * Stores other GPSObjects and their roles in the relation
 */
public class Relation extends GPSObject{
	/**
	 * Stores other GPSObjects and their role in the relation
	 * Can include relations 
	 */
    private HashMap<GPSObject, String> members;

    public Relation(String id, boolean visible) {}

    /**
     * Add a member
     * @param member object
     */
    public void addMember(GPSObject m, String role) {}
    
    /**
     * Get the members
     * 
     * @return iterator for the members
     */
    public Iterator<GPSObject> getMembers() {}

    /**
     * Creates and returns string representation of the relation
     * 
     * @return string for the relation
     */
    @Override
    public String toString() {}
    
    /**
     * Computes and returns integer hashcode for the object
     * 
     * @return hashcode as an int
     */
    @Override
    public int hashCode {}
    
    /**
     * Checks to see if another relation is equal to this one
     * 
     * @param an object
     * @return true is equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {}
}
