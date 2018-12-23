package Taxis;

import java.util.HashMap;
/**
 * Stores a pair of values: A HashMap that maps nodes to their parent nodes and the distance between the taxi and the client.
 * Used as a return value for the AStar.runAStar method.
 */
public class Pair {
    private HashMap<Node, Node> hashmap;
    private double distance;
    
    public class PairException extends Exception{
        public PairException(String msg) {
            super(msg);
        }
    }
   
    public HashMap<Node, Node> getHashmap() {
        return hashmap;
    }

    public void setHashmap(HashMap<Node, Node> hashmap) {
        this.hashmap = hashmap;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public Pair() throws PairException{
        throw new PairException("Pair constructor not given any values.");
    }

    public Pair(HashMap<Node, Node> hashmap, double distance) {
        this.hashmap = hashmap;
        this.distance = distance;
    }
}
