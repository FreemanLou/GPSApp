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
 * @author freeman
 *
 */
public class MapPanel extends JPanel implements ComponentListener{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Map map;
    private Point startPoint;

    private double scaleFactorA;
    private double scaleFactorB;
    
    private double centLat;
    private double centLon;
    
    private double centX;
    private double centY;

    private double zoomFactor;
    
    public MapPanel(Map map) {
	super();
	this.map = map;
	
	addComponentListener(this);
	centLat = (map.getMaxLat() + map.getMinLat()) / 2.0;
	centLon = (map.getMaxLon() + map.getMinLon()) / 2.0;
	
	centX = getWidth() / 2.0;
	centY = getHeight() / 2.0;
	
	scaleFactorA = 6500;

	zoomFactor = 1;
	
	this.setOpaque(true);
	this.setBackground(Color.WHITE);

	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent event) {
		if(event != null) {
		    startPoint = event.getPoint();
		}
	    }
	    
	    @Override
	    public void mouseReleased(MouseEvent event) {
		startPoint = null;
	    }
	    
	});
	
	this.addMouseMotionListener(new MouseMotionAdapter() {
	    @Override
	    public void mouseDragged(MouseEvent event) {
		Point dragPoint = event.getPoint();
		double startX = startPoint.getX();
		double startY = startPoint.getY();
		double dragX = dragPoint.getX();
		double dragY = dragPoint.getY();
		
		double dx = dragX - startX;
		double dLon = dx / scaleFactorA;
		centLon += dLon;
		double dy = dragY - startY;
		double dLat = dy/scaleFactorB;
		centLat -= dLat;
		
		startPoint = dragPoint;
		
		repaint();
	    }
	    
	});
	
	this.addMouseWheelListener(new MouseWheelListener() {
	    @Override
	    public void mouseWheelMoved(MouseWheelEvent e) {
		double rotations = e.getPreciseWheelRotation();
		if(rotations < 0) {	// zoom in
		    if(zoomFactor < 14.0)
			zoomFactor += 0.1;
		} else {	// zoom out
		    if(zoomFactor > .5)
			zoomFactor -= 0.1;
		}
		repaint();
	    }
	});

    }

    /* 
     * Draws map
     */
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	
	HashMap<String, Way> ways = map.getWays();
	for(Way w : ways.values()) {
	    ArrayList<String> refs = w.getRefs();
	    if(refs.size() > 0) {
		//Should prob check for null values
		Node prev = map.getNode(refs.get(0));
		Node curr = null;
		for(int i = 1; i < refs.size(); i++) {
		    curr = map.getNode(refs.get(i));
		    if(curr != null) {
			Point2D.Double prevCoord = convertToPoint(
				prev.getLatitude(), 
				prev.getLongitude());

			Point2D.Double currCoord = convertToPoint(
				curr.getLatitude(), 
				curr.getLongitude());

			g2.drawLine((int) prevCoord.getX(), 
					(int) prevCoord.getY(),
					(int) currCoord.getX(), 
					(int) currCoord.getY());
			prev = curr;
		    } else {
			continue;
		    }
		}				    
	    }
	}
    }
    
    private double calculateX(double lon) {
	return 0.0;
    }
    
    private double calculateY(double lat) {
	return 0.0;
    }
    
    /*
     * Converts lat and lon to a Point2D object
     */
    public Point2D.Double convertToPoint(double lat, double lon) {
	double x = 0;
	double y = 0;

	scaleFactorB = scaleFactorA * Math.cos(Math.toRadians(lat));
	
	x = zoomFactor * scaleFactorA * (centLat - lat) + centX;
	y = zoomFactor * scaleFactorB * (lon - centLon) + centY;
	
	return new Point2D.Double(y, x);
    }
    
    public void componentMoved(ComponentEvent e) {
	return;
    }
    
    public void componentResized(ComponentEvent e) {
	centX = getWidth() / 2.0;
	centY = getHeight() / 2.0;
	repaint();
    }
    
    public void componentShown(ComponentEvent e) {
	return;
    }

    public void componentHidden(ComponentEvent e) {
	return;
    }
}
