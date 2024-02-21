/**
 * This class represents an edge of the graph. 
 * @author Filip Maletic (250866829)
 *
 */
public class Edge {
	
    private Node u;
    private Node v;
    private int type;
    private String label;
    
    /**
     * This constructor's first two parameters are the endpoints of the edge. The last parameter is the type of the edge;
       when representing the labyrinth, if an edge represents a door the type of the edge represents the type of the door.
     * @param u
     * @param v
     * @param type
     */
    public Edge(Node u, Node v, int type) {
        this.u = u;
        this.v = v;
        this.type = type;
    }
    /**
     * This is an alternate constructor for the class. The first two parameters are the endpoints of the edge. 
       The last parameters are the type and label of the edge. The label is used to distinguish between edges 
       representing corridors and edges representing doors.
     * @param u
     * @param v
     * @param type
     * @param label
     */
    public Edge(Node u, Node v, int type, String label) {
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
    }
    /**
     * This method returns the first endpoint of the edge.
     * @return
     */
    public Node firstEndpoint(){
        return this.u;
    }
    /**
     * This method returns the second endpoint of the edge.
     * @return
     */
    public Node secondEndpoint(){
        return this.v;
    }
    /**
     * This method returns the type of the edge.
     * @return
     */
    public int getType() {
        return type;
    }
    /**
     * This method  sets the type of the edge to the specified value.
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }
    /**
     * This method returns the label of the edge.
     * @return
     */
    public String getLabel() {
        return label;
    }
    /**
     * This method sets the label of the edge to the specified value.
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
