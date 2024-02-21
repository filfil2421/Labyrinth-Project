/**
 * This class represents a Node of the graph.
 * @author Filip Maletic (250866829)
 */
public class Node {
    private int name;
    private boolean mark;
    /**
     * This constructor takes in a name parameter and creates a Node, setting the mark as false as default.
     * @param name
     */
    public Node(int name) {
        this.name = name;
        this.mark = false;
    }
    /**
     * This method marks the node with the specified value.
     */
    public void setMark(boolean mark) {
        this.mark = mark;
    }
    /**
     * This method returns the value with which the node has been marked.
     * @return
     */
    public boolean getMark(){
        return  this.mark;
    }
    /**
     * This method returns the name of the node.
     * @return
     */
    public int getName() {
        return name;
    }
}
