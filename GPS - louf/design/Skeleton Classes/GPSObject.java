import java.util.HashMap;

/**
 * Super-class for the GPSObject types that will be contained in the Map, including
 * Node, Way, and Relation classes. It contains the common fields and methods for the
 * type.
 * 
 * All GPSObjects have tags associated with them. Tags are stored in the object as
 * key-value pairs.
 * 
 */

public abstract class GPSObject {

	/**
	 * Fields common to GPSObjects
	 */
    protected String id;
    protected boolean visible;

    protected HashMap<String, String> tags;

    public GPSObject(String id, boolean visible) {}

    /**
     * @return the visible
     */
    public boolean isVisible() {}

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {}

    /**
     * @return the tags
     */
    public HashMap<String, String> getTags() {}
    
    /**
     * Performs tag lookup based on the key and
     * returns the tag
     * 
     * @return a tag based on key
     */
    public String getTag(String key) {}
    
    /**
     * Adds a tag to the GPSObject
     * Every GPSObject subtype will have tags
     * 
     * @param key and value of the tag to add
     */
    public void addTag(String key, String value) {}
}
