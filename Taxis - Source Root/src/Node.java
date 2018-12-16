package Taxis;

import java.util.ArrayList;

/**
 *
 * @author Christos
 */
public class Node {
    /** Node's Cartesian coordinates (x,y), id number, list of neighbours, f-score and g-score (for the a-star algorithm) and parent node.
     */
    private double x;
    private double y;
    private int id;
    private ArrayList<Node> neighbours;
    private double fScore;
    private double gScore;
    Node parent;
    
    public class NodeException extends Exception {
        public NodeException(String msg) {
            super(msg);
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public double getfScore() {
        return fScore;
    }

    public void setfScore(double fScore) {
        this.fScore = fScore;
    }

    public double getgScore() {
        return gScore;
    }

    public void setgScore(double gScore) {
        this.gScore = gScore;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public Node() throws Exception{
        throw new NodeException("Node created with no starting values");
    }
    
    public Node(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.neighbours = new ArrayList<>();
    }
    
    public void addNeighbours(Node neighbour) {
        this.neighbours.add(neighbour);
    }
}
