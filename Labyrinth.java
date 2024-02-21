import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * This class represents the Labyrinth. An object of the class Graph will be used to store the labyrinth and to find a solution for it.
 * @author Filip Maletic (250866829)
 */
public class Labyrinth {

    private Graph graph;
    private final int roomSize;
    private final int labWidth;
    private final int labLength;
    private final ArrayList<Integer>numberOfKeys;
    private Node EXIT;
    private Node START;

    /**
     * This constructor reads the input file and builds the graph representing the labyrinth. If the input file
       does not exist, or the format of the input file is incorrect this method throws a LabyrinthException.
     * @param fileName
     * @throws LabyrinthException
     */
    public Labyrinth(String inputFile) throws LabyrinthException {
        numberOfKeys = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String st;
            // input first three lines
            roomSize = Integer.parseInt(br.readLine());
            labWidth = Integer.parseInt(br.readLine());
            labLength = Integer.parseInt(br.readLine());

            // read number of keys
            st = br.readLine();
            Arrays.stream(st.split(" ")).mapToInt(Integer::parseInt).forEach(numberOfKeys::add);

            // read the grid
            int lineLength = -1;
            ArrayList<String> lines = new ArrayList<>();
            while ((st = br.readLine()) != null){
                // check if this is the first line (-1) or has same length as previous
                if (lineLength!=-1 && st.length()!=lineLength)
                    throw new LabyrinthException("Error: input file does not exist or format is incorrect.");
                lineLength = st.length();
                lines.add(st);
            }
            addToGraph(lines);		// Calls helper method

        } catch (Exception e) {
            throw new LabyrinthException("Error: input file does not exist or format is incorrect.");
        }
    }
    /**
     * This method returns a reference to the Graph object representing the labyrinth (throws a LabyrinthException if the graph is null).
     * @return
     * @throws LabyrinthException
     */
    public Graph getGraph() throws LabyrinthException {
        if(graph == null)
            throw new LabyrinthException("Error: input file does not exist or format is incorrect.");
       return this.graph;
    }
    
   /**
    * This method returns a java Iterator containing the nodes of the path from the entrance to the exit of the 
      labyrinth, if such a path exists. If the path does not exist, this method returns the value null.
    * @return
    * @throws LabyrinthException
    * @throws GraphException
    */
    public Iterator solve() throws LabyrinthException, GraphException {
    	if (graph == null) {
           throw new LabyrinthException("Error: input file does not exist or format is incorrect.");
       }
       ArrayList<Integer> curKeyCount = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
       Stack<Node> st = new Stack<>();
       Iterator solution = findSolution(START, curKeyCount, st);		// Calls helper method
       return solution;
   }
    /**
     * This is a private helper method for the Labyrinth constructor that adds to the graph and updates its contents. 
     * @param lines
     * @throws LabyrinthException
     */
    private void addToGraph(ArrayList<String> lines) throws LabyrinthException {
        this.graph = new Graph(labWidth * labLength);
        try {
            // create an matrix to track correspond node number
            int[][] nodes = new int[lines.size()][lines.get(0).length()];
            for (int i = 0, nodeCnt = 0; i < lines.size(); i += 2)
                for (int j = 0; j < lines.get(0).length(); j += 2) {
                    nodes[i][j] = nodeCnt;
                    nodeCnt++;
                }

            for (int i = 0; i < lines.size(); i += 2) {
                for (int j = 0; j < lines.get(0).length(); j += 2) {
                    // marks starting and ending points
                    if (lines.get(i).charAt(j) == 's')
                        START = new Node(nodes[i][j]);
                    else if (lines.get(i).charAt(j) == 'x')
                        EXIT = new Node(nodes[i][j]);

                    // right node
                    if (j < lines.get(i).length() - 1) {
                        if (lines.get(i).charAt(j + 1) != 'w') {
                            this.graph.insertEdge(new Node(nodes[i][j]),
                                    new Node(nodes[i][j + 2]),
                                    Character.isDigit(lines.get(i).charAt(j + 1)) ?
                                            Integer.parseInt(String.valueOf(lines.get(i).charAt(j + 1))) :
                                            -1
                            );
                        }
                    }
                    // upper node
                    if (i > 0) {
                        if (lines.get(i - 1).charAt(j) != 'w') {
                            this.graph.insertEdge(new Node(nodes[i][j]),
                                    new Node(nodes[i - 2][j]),
                                    Character.isDigit(lines.get(i - 1).charAt(j)) ?
                                            Integer.parseInt(String.valueOf(lines.get(i - 1).charAt(j))) :
                                            -1
                            );
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new LabyrinthException("Error: input file does not exist or format is incorrect.");
        }
    }
    /**
     * This is a recursive private helper method for the solve method above. It takes a Node stack as parameter to store the nodes.
     * @param curNode
     * @param curKeyCount
     * @param stack
     * @return returns an iterator of the stack
     * @throws GraphException
     */
    private Iterator findSolution(Node curNode, ArrayList<Integer> curKeyCount, Stack<Node> stack) throws GraphException {
        // find adjacent nodes
        Iterator edges = this.graph.incidentEdges(curNode);

        // add current node to the stack
        stack.add(curNode);

        // mark this node as visited
        this.graph.getNode(curNode.getName()).setMark(true);

        while (edges.hasNext()){
            // check if this node can be considered
            Edge edge = (Edge) edges.next();
            Node next = (edge.secondEndpoint().getName()==curNode.getName()?edge.firstEndpoint():edge.secondEndpoint());

            // if this node is the exit point
            if(next.getName() == EXIT.getName()){
                stack.add(next);
                // exit point found
                return stack.iterator();
            }

            if (this.graph.getNode(next.getName()).getMark()) {
                continue;
            }
            // run dfs from this node
            // if this is a door
            if(edge.getType() == -1) {
                findSolution(next, curKeyCount, stack);
            }
            else {
                // skip this node if this can't be taken
                if (curKeyCount.get(edge.getType())>=numberOfKeys.get(edge.getType())) {
                    continue;
                }
                // increase the count
                curKeyCount.set(edge.getType(),curKeyCount.get(edge.getType())+1);
                findSolution(next, curKeyCount, stack);
                // reset the count
                curKeyCount.set(edge.getType(),curKeyCount.get(edge.getType())-1);
            }
        }

        // reset mark
        this.graph.getNode(curNode.getName()).setMark(false);

        // remove current node from the stack
        if(curNode.getName() == EXIT.getName())
            stack.clear();
        else
            stack.pop();
        return stack.iterator();
    }
}
