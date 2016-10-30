import java.util.HashMap;

public abstract class GPSObject {

    protected String user;
    protected String uid;
    protected String id;
    protected boolean visible;
    protected String version;
    protected String changeSet;
    protected String timeStamp;
    protected HashMap<String, String> tags;

    public GPSObject(String user, String uid, String id, boolean visible, String version,
	    String changeSet, String timeStamp) {
	super();
	this.user = user;
	this.uid = uid;
	this.id = id;
	this.visible = visible;
	this.version = version;
	this.changeSet = changeSet;
	this.timeStamp = timeStamp;
	
	tags = new HashMap<String, String>();
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the tags
     */
    public HashMap<String, String> getTags() {
        return tags;
    }
    
    /*
     * Returns a tag based on key
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

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /*
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return the changeSet
     */
    public String getChangeSet() {
        return changeSet;
    }

    /**
     * @return the timeStamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

//    /* (non-Javadoc)
//     * @see java.lang.Object#hashCode()
//     */
//    @Override
//    public int hashCode() {
//	final int prime = 31;
//	int result = 1;
//	result = prime * result
//		+ ((changeSet == null) ? 0 : changeSet.hashCode());
//	result = prime * result + ((id == null) ? 0 : id.hashCode());
//	result = prime * result + ((tags == null) ? 0 : tags.hashCode());
//	result = prime * result
//		+ ((timeStamp == null) ? 0 : timeStamp.hashCode());
//	result = prime * result + ((uid == null) ? 0 : uid.hashCode());
//	result = prime * result + ((user == null) ? 0 : user.hashCode());
//	result = prime * result + ((version == null) ? 0 : version.hashCode());
//	result = prime * result + (visible ? 1231 : 1237);
//	return result;
//    }
//
//    /* (non-Javadoc)
//     * @see java.lang.Object#equals(java.lang.Object)
//     */
//    @Override
//    public boolean equals(Object obj) {
//	if (this == obj)
//	    return true;
//	if (obj == null)
//	    return false;
//	if (getClass() != obj.getClass())
//	    return false;
//	GPSObject other = (GPSObject) obj;
//	if (changeSet == null) {
//	    if (other.changeSet != null)
//		return false;
//	} else if (!changeSet.equals(other.changeSet))
//	    return false;
//	if (id == null) {
//	    if (other.id != null)
//		return false;
//	} else if (!id.equals(other.id))
//	    return false;
//	if (tags == null) {
//	    if (other.tags != null)
//		return false;
//	} else if (!tags.equals(other.tags))
//	    return false;
//	if (timeStamp == null) {
//	    if (other.timeStamp != null)
//		return false;
//	} else if (!timeStamp.equals(other.timeStamp))
//	    return false;
//	if (uid == null) {
//	    if (other.uid != null)
//		return false;
//	} else if (!uid.equals(other.uid))
//	    return false;
//	if (user == null) {
//	    if (other.user != null)
//		return false;
//	} else if (!user.equals(other.user))
//	    return false;
//	if (version == null) {
//	    if (other.version != null)
//		return false;
//	} else if (!version.equals(other.version))
//	    return false;
//	if (visible != other.visible)
//	    return false;
//	return true;
//    }
//
//    /* (non-Javadoc)
//     * @see java.lang.Object#toString()
//     */
//    @Override
//    public String toString() {
//	StringBuilder builder = new StringBuilder();
//	builder.append("GPSObject [user=");
//	builder.append(user);
//	builder.append(", uid=");
//	builder.append(uid);
//	builder.append(", id=");
//	builder.append(id);
//	builder.append(", visible=");
//	builder.append(visible);
//	builder.append(", version=");
//	builder.append(version);
//	builder.append(", changeSet=");
//	builder.append(changeSet);
//	builder.append(", timeStamp=");
//	builder.append(timeStamp);
//	builder.append(", tags=");
//	builder.append(tags.toString());
//	builder.append("]");
//	return builder.toString();
//    }
}
