import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents an undirected graph.
 * @author Filip Maletic (250866829)
 */
public class Graph implements GraphADT{

    private int n;
    private ArrayList<ArrayList<Edge>> adjList;
    private ArrayList<Node> nodes;
    /**
     * This constructor creates an empty graph with n nodes and no edges.
     * @param n
     */
    public Graph(int n) {
        this.n = n;
        this.adjList = new ArrayList<ArrayList<Edge>>(n);
        this.nodes = new ArrayList<>(n);
        // initializing the lists
        for (int i = 0; i < n; i++) {
            this.adjList.add(new ArrayList<>());
            this.nodes.add(new Node(i));
        }
    }
    /**
     * This a private helper method that checks if a node is valid.
     * @param x
     * @return
     */
    private boolean notValidNode(Node x){
        return (x == null || x.getName() < 0 || x.getName() >= n);
    }
   
    @Override
    /**
     * This method  adds to the graph an edge connecting nodes u and v. The type for this new edge is as indicated by the last parameter. 
       This method throws a GraphException if either node does not exist or if there is already an edge connecting the given nodes.
     */
    public void insertEdge(Node nodeu, Node nodev, int edgeType) throws GraphException {
        if (notValidNode((nodeu)) || notValidNode((nodev)) || areAdjacent(nodeu, nodev)) {
            throw new GraphException("Insertion error: node doesn't exist or an edge connecting the nodes already exists.");
        }
        Edge edge = new Edge(nodeu, nodev, edgeType);
        adjList.get(nodeu.getName()).add(edge);		// for the iterator
        adjList.get(nodev.getName()).add(edge);
    }
    /**
     * This method is the same as the one above except it takes an additional String parameter at the end representing the label of an edge.
     */
    @Override
    public void insertEdge(Node nodeu, Node nodev, int edgeType, String label) throws GraphException {
        if (notValidNode(nodeu) || notValidNode(nodev) || areAdjacent(nodeu, nodev)) {
           throw new GraphException("Insertion error: node doesn't exist or an edge connecting the nodes already exists.");
        }
        Edge edge = new Edge(nodeu, nodev, edgeType, label);
        adjList.get(nodeu.getName()).add(edge);
        adjList.get(nodev.getName()).add(edge);
    }
    /**
     * This method returns the node with the specified name. If no node with this name exists, it throws a GraphException.
     */
    @Override
    public Node getNode(int u) throws GraphException {
        if (u < 0 || u >= n || nodes.get(u) == null) {
            throw new GraphException("Error: No node with this name exists.");
        }
        return nodes.get(u);
    }
    /**
     * This method  returns a Java Iterator storing all the edges incident on node u. It returns null if u does not 
       have any edges incident on it. If u is not a node of the graph, a GraphException is thrown.
     */
    @Override
    public Iterator incidentEdges(Node u) throws GraphException {
        if (adjList.get(u.getName())==null) {
            throw new GraphException("Error: u is not a node of the graph.");
        }
        return adjList.get(u.getName()).iterator();
    }
    /**
     * This method returns the edge connecting nodes u and v. It throws a GraphException
       if there is no edge between u and v or if u or v are not nodes of the graph.
     */
    @Override
    public Edge getEdge(Node u, Node v) throws GraphException {
        if (notValidNode(u) || notValidNode(v))
            throw new GraphException("Error: there is no edge between u and v or one of them are not nodes in the graph.");

        for ( Edge edge : adjList.get(u.getName())) {
            if (edge.secondEndpoint().getName() == v.getName()) {
                return edge;
            }
        }
        return null;
    }
    /**
     * This method returns true if nodes u and v are adjacent; returns false otherwise.
       It throws a GraphException if u or v are not nodes of the graph.
     */
    @Override
    public boolean areAdjacent(Node u, Node v) throws GraphException {
        if (notValidNode(u) || notValidNode(v))
            throw new GraphException("Error: Either u or v are not nodes of the graph.");

        for (Edge edge : adjList.get(u.getName())) {
            if (edge.secondEndpoint().getName() == v.getName() || edge.firstEndpoint().getName()== v.getName())
                return true;
        }
        return  false;
    }
}
