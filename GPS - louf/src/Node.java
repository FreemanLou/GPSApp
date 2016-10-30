import java.util.*;

/*
 * 
 * Represents a GPS node
 * 
 */
public class Node extends GPSObject {
    private double latitude;
    private double longitude;
    
    public Node(double latitude, double longitude, String user, String uid, String id, boolean visible, String version,
	    String changeSet, String timeStamp) {
	super(user, uid, id, visible, version, changeSet, timeStamp);
	
	this.longitude = longitude;
	this.latitude = latitude;
    } 
    
    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(latitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(longitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
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
	Node other = (Node) obj;
	if (Double.doubleToLongBits(latitude) != Double
		.doubleToLongBits(other.latitude))
	    return false;
	if (Double.doubleToLongBits(longitude) != Double
		.doubleToLongBits(other.longitude))
	    return false;
	return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Node [latitude=");
	builder.append(latitude);
	builder.append(", longitude=");
	builder.append(longitude);
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
	builder.append("]");
	return builder.toString();
    }
}
