import java.util.*;

/**
 * Represents a node in the open source map format
 * 
 * Keeps track of the latitude and longitude at a point
 */
public class Node extends GPSObject {
	/*Latitude of the Node*/
    private double latitude;
    /*Longitude of the Node*/
    private double longitude;
    
    public Node(double latitude, double longitude, String id, boolean visible) {} 
    
    /**
     * Returns the latitude of the node as a double
     * 
     * @return the latitude
     */
    public double getLatitude() {}

    /**
     * Returns the longitude of the node as a double
     * 
     * @return the longitude
     */
    public double getLongitude() {}

    /**
     * Calculates and return the hash code of the node
     *
     *@return the hash code of the Node as an integer
     */
    @Override
    public int hashCode() {}

    /**
     * Checks to see if this node object contains the same data
     * as another one
     * 
     * @param another object
     * @return true is equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {}

    /**
     * Creates and returns the string representation of the node
     * 
     * @return string representation of the node
     */
    @Override
    public String toString() {}
}
