This project simulates a GPS navigation system.

It loads OSM XML map files through a parser and displays the data in a window.

Users will be able to input coordinates they want to travel to and receive navigation directions.
The GPS program runs simultaneously with another GPSDevice which produces the mock GPSEvents that the main program picks up. The main program then uses the data to determine if the user is off course or not. When off course, an indicator is presented, and a new route is computed using the current location as the new starting point.

The map has an underlying graph, which is created after the entire map has been parsed. The graph is composed of a Graph interface, GraphNode interface, and GraphEdge class. The GraphEdge has been implemented since while GPSNodes and implement the GraphNode, Ways represent entire roads, so the GraphEdge is used to break them up into smaller segments.

MapPanel is the main display of the program. GPSApp contains other sections such as the selection menu and text box which has directions and more info.