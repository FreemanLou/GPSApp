import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Sample parser for reading Open Street Map XML format files.
 * Illustrates the use of SAXParser to parse XML.
 *
 * @author E. Stark
 * @date September 20, 2009
 */
class OSMParser {

    /** OSM file from which the input is being taken. */
    private File file;
    
    /** Output saved as objects in Map */
    private Map map;
    
    private GPSObject currObject;
    /**
     * Initialize an OSMParser that takes data from a specified file.
     *
     * @param s The file to read.
     * @throws IOException
     */
    public OSMParser(File f, Map map) {
        file = f;
        this.map = map;

        currObject = null;
    }

    /**
     * Parse the OSM file underlying this OSMParser.
     */
    public void parse()
        throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(false);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        OSMHandler handler = new OSMHandler();
        xmlReader.setContentHandler(handler);
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            InputSource source = new InputSource(stream);
            xmlReader.parse(source);
        } catch(IOException x) {
            throw x;
        } finally {
            if(stream != null)
                stream.close();
        }
    }

    /**
     * Handler class used by the SAX XML parser.
     * The methods of this class are called back by the parser when
     * XML elements are encountered.
     */
    class OSMHandler extends DefaultHandler {

        /** Current character data. */
        private String cdata;
        
        /** Attributes of the current element. */
        private Attributes attributes;

        /**
         * Get the most recently encountered CDATA.
         */
        public String getCdata() {
            return cdata;
        }

        /**
         * Get the attributes of the most recently encountered XML element.
         */
        public Attributes getAttributes() {
            return attributes;
        }

        /**
         * Method called by SAX parser when start of document is encountered.
         */
        public void startDocument() {
            System.out.println("startDocument");
        }

        /**
         * Method called by SAX parser when end of document is encountered.
         */
        public void endDocument() {
            map.createEdges();
            System.out.println("endDocument");
        }

        /**
         * Method called by SAX parser when start tag for XML element is
         * encountered.
         */
        public void startElement(String namespaceURI, String localName,
                                 String qName, Attributes atts) {
            attributes = atts;
//            System.out.println("startElement: " + namespaceURI + ","
//                               + localName + "," + qName);
            HashMap<String, String> attributes = new HashMap<String, String>();
            
            for(int i=0; i < atts.getLength(); i++) {
                String name = atts.getQName(i);
                String value = atts.getValue(i);
                attributes.put(name, value);
            }
            
            //Take care of bounds
            if(qName.equals("bounds")) {
        	map.setMinLat(Double.parseDouble(attributes.get("minlat")));
        	map.setMaxLat(Double.parseDouble(attributes.get("maxlat")));
        	map.setMinLon(Double.parseDouble(attributes.get("minlon")));
        	map.setMaxLon(Double.parseDouble(attributes.get("maxlon")));
        	return;
            }
            
            if(qName.equals("bound")) {
            	String[] box = attributes.get("box").split(",");
            	if(box != null && box.length == 4) {
                	map.setMinLat(Double.parseDouble(box[0]));
                	map.setMaxLat(Double.parseDouble(box[2]));
                	map.setMinLon(Double.parseDouble(box[1]));
                	map.setMaxLon(Double.parseDouble(box[3]));  		
            	} else {
            		System.out.println("Invalid bounds attribute");
            	}
            	return;
            }
            
            boolean isNode = qName.equals("node");
            boolean isWay = qName.equals("way");
            boolean isRelation = qName.equals("relation");
            
            boolean isMember = qName.equals("member");
            boolean isTag = qName.equals("tag");
            boolean isNd = qName.equals("nd");
            
            //We only use these if its a node, way or relation
            if(isNode || isWay || isRelation) {
        	//Get the common attributes
                String id = attributes.get("id");
                //boolean visible = attributes.get("visible").equals("1") ? true : false;
                boolean visible = true;
                
                if(isNode) {
            		double lon = Double.valueOf(attributes.get("lon"));
            		double lat = Double.valueOf(attributes.get("lat"));
            		currObject = new GPSNode(lat, lon, id, visible);
            		map.addNode(id, (GPSNode)currObject);
                } else if (isWay) {
            		currObject = new Way(id, visible);
            		map.addWay(id, (Way)currObject);
                } else { // is Relation
                    	currObject = new Relation(id, visible);
                    	map.addRelation(id, (Relation)currObject);
                }
            } else { //other type
        	if(currObject == null) {
        	    return;	// currObject should not be null. Bad data
        	} else {
        	    if(isMember) {
        		String type = attributes.get("type");
        		String ref = attributes.get("ref");
        		String role = attributes.get("role");
        		((Relation) currObject).addMember(ref, type);
        	    } else if (isTag) {
        		String k = attributes.get("k");
        		String v = attributes.get("v");
        		currObject.addTag(k, v);
        	    } else if (isNd) {
        		String ref = attributes.get("ref");
        		((Way) currObject).addRef(ref);
        	    } else {	// not a supported tag
        		return;
        	    }
        	}
        	
            }

//            if(atts.getLength() > 0)
//                showAttrs(atts);
        }

        /**
         * Method called by SAX parser when end tag for XML element is
         * encountered.  This can occur even if there is no explicit end
         * tag present in the document.
         */
        public void endElement(String namespaceURI, String localName,
                               String qName) throws SAXParseException {
        }

        /**
         * Method called by SAX parser when character data is encountered.
         */
        public void characters(char[] ch, int start, int length)
            throws SAXParseException {

            cdata = (new String(ch, start, length)).trim();
        }

        /**
         * Auxiliary method to display the most recently encountered
         * attributes.
         */
        private void showAttrs(Attributes atts) {
            for(int i=0; i < atts.getLength(); i++) {
                String qName = atts.getQName(i);
                String type = atts.getType(i);
                String value = atts.getValue(i);
           
                System.out.println("\t" + qName + "=" + value
                                   + "[" + type + "]");
            }
        }
    }

//    /**
//     * Test driver.  Takes filenames to be parsed as command-line arguments.
//     */
//    public static void main(String[] args) throws Exception {
//	Map m = new Map();
//        for(int i = 0; i < args.length; i++) {
//            OSMParser prsr = new OSMParser(new File(args[i]), m);
//            prsr.parse();
//        }
//        
//        System.out.println(m.getNodes().values().toString());
//        //System.out.println(m.getRelations().toString());
//        //System.out.println(m.getWays().toString());
//        //Print nodes?
//    }
}


