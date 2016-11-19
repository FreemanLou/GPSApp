import java.util.HashMap;

public abstract class GPSObject {

    protected String id;
    protected boolean visible;
    protected HashMap<String, String> tags;

    public GPSObject(String id, boolean visible) {
	super();
	this.id = id;
	this.visible = visible;
	tags = new HashMap<String, String>();
    }

    /**
     * Checks whether or not the object is supposed to be visible on the map
     * 
     * @return true if object is visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the object
     * 
     * @param visible true if shown false otherwise
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Gets all tags of the object
     * 
     * @return hashmap of the tags
     */
    public HashMap<String, String> getTags() {
        return tags;
    }
    
    /**
     * Finds a tag's value for the object
     * 
     * @return value if found, null otherwise
     */
    public String getTag(String key) {
	return tags.get(key);
    }
    
    /**
     * @param the tags to set
     */
    public void addTag(String key, String value) {
        if(key == null || value == null)
            return;
        tags.put(key, value);
    }
}
