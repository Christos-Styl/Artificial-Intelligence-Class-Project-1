package Taxis;

import java.util.ArrayList;

public class Node {
    /** Node's Cartesian coordinates (x,y), id number, list of neighbours, f-score and g-score (for the a-star algorithm) and parent node.
     */
    private Point coords;
    private int id;
    private ArrayList<Node> neighbours;
    private double fScore;
    private double gScore;
    private Node parent;
    
    public class NodeException extends Exception {
        public NodeException(String msg) {
            super(msg);
        }
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
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
    
    public Node() throws NodeException{
        throw new NodeException("Node created with no starting values");
    }
    
    public Node(double x, double y, int id) {
        coords = new Point(x, y);
        this.id = id;
        this.neighbours = new ArrayList<>();
        this.parent = null;
    }
    
    public void addNeighbours(Node neighbour) {
        this.neighbours.add(neighbour);
    }
    
    public void printNode(){
        System.out.println(this.coords.getX() + ", " + this.coords.getY() + ", id:" + id);
    }
    
    public double distance(Node other){
        return this.coords.distance(other.coords);
    }
    
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Node)){
            System.out.println("Invalid \"equals\" operation");
            return false;
        }
        Node other = (Node)o;
        if (this == other){
            return true;
        }
        return (this.coords.getX() == other.coords.getX() && this.coords.getY() == other.coords.getY());
    }
	
    @Override
    public int hashCode(){
        return (int)(10000000*this.coords.getX() + 10000000*this.coords.getY());
    }
}
