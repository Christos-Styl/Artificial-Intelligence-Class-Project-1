package Taxis;

public class Node {
    /** 
     * Node's coordinates (x,y), id number, list of neighbours, f-score and g-score (for the a-star algorithm).
     */
    private Point coords;
    private int id;
    private double fScore;
    private double gScore;
    
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
    
    public Node() throws NodeException{
        throw new NodeException("Node created with no starting values");
    }
    
    public Node(double x, double y, int id) {
        coords = new Point(x, y);
        this.id = id;
        this.fScore = Double.MAX_VALUE;
        this.gScore = Double.MAX_VALUE;
    }
    
    public Node(Node other) {
        this.coords = new Point(other.coords);
        this.id = other.id;
        this.fScore = other.fScore;
        this.gScore = other.gScore;
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