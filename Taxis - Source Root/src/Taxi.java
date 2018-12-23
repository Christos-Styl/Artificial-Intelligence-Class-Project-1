package Taxis;

import java.util.ArrayList;
import java.util.HashMap;

public class Taxi {

    private Point coords;
    private int id;

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

    public Taxi(double x, double y, int id) {
        this.coords = new Point(x, y);
        this.id = id;
    }
    
    /** 
     * Finds the closest node to a taxi and returns it.
     */
    public Node findTaxiNode(HashMap <Node, ArrayList<Node>> hashmap){
        Node result = null;
        double minDistance = Double.MAX_VALUE, newDistance;
        for(Node key : hashmap.keySet()){
            newDistance = key.getCoords().distance(this.coords);
            if(newDistance < minDistance){
                minDistance = newDistance;
                result = key;
            }
        }
        System.out.println("\tTaxi with id " + this.id + " has distance from closest node: " + minDistance + " meters.");
        return result;
    }
}