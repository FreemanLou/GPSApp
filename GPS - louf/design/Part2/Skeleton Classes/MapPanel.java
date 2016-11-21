/**
 * 
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;


/**
 * Handles the displaying of the map
 *
 */
public class MapPanel extends JPanel implements ComponentListener{
    /**
     * 
     */
    private Map map;
    private Point startPoint;

    private double scaleFactorA;
    private double scaleFactorB;
    
    private double centLat;
    private double centLon;
    
    private double centX;
    private double centY;

    private double zoomFactor;
    
    public MapPanel(Map map) {}

    /**
     * Draws map
     */
    @Override
    public void paintComponent(Graphics g) {}
    
    /**
     * Converts lat and lon to a Point2D object
     * 
     * @param double latitude and double longitude
     */
    public Point2D.Double convertToPoint(double lat, double lon) {}

    /**
     * Handles when the panel is moved
     */
    public void componentMoved(ComponentEvent e) { }

    /**
     * Handles when the panel is resized
     */
    public void componentResized(ComponentEvent e) {}
    
    /**
     * Handles when the component is shown
     */
    public void componentShown(ComponentEvent e) {}
    
    /**
     * Handles when the component is hidden
     */
    public void componentHidden(ComponentEvent e) {}
}
