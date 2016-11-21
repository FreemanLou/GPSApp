import java.awt.geom.Point2D;

/**
 * Handles the events that come from a GPS device
 * 
 * @author Freeman
 *
 */
class DrivingTracker implements GPSListener {
    /**
     * Process the events received from a GPSDevice
     * Retrieves the current location so that the MapPanel can be updated
     * also allows the map to track if the GPSDevice is following the current
     * route.
     * 
     * @param e GPSEvent
     */
    public void processEvent(GPSEvent e);
    
    /**
     * If there has been a processed event,
     * returns the last known coordinates
     * 
     * @return Point2D.Double containing current coordinates
     */
    public Point2D.Double getCurrentLocation();
}


