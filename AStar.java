package Taxis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/** 
 * The A* Algorithm class.
 */
public class AStar {
    //variables will not be declared private for simplicity
    HashMap<Node, ArrayList<Node>> hashmap;
    Node client;
    HashMap<Node,Node> closedSet;
    PriorityQueue<Node> openSet;
    
    public class AStarException extends Exception {
        public AStarException(String msg) {
            super(msg);
        }
    }
    
    public AStar() throws AStarException{
        throw new AStarException("A* Algorithm initiated with no inputs.");
    }
    
    public AStar(HashMap<Node, ArrayList<Node>> hashmap, Node client) throws AStarException{
        if(!hashmap.isEmpty()){
            this.hashmap = hashmap;
        }
        else{
            throw new AStarException("A* Algorithm initiated with empty hashmap.");
        }
        if(!(client == null)){
            this.client = client;
        }
        else{
            throw new AStarException("A* Algorithm initiated with no client.");
        }
    }
    
    /** 
     * Runs the A* Algorithm for a single taxi-client pair.
     * 
     * @return a HashMap that matches every node in the taxi-client path to its parent
     */
    public Pair runAStar(Node starting_node, Taxi taxi){
        System.out.println("A*: Algorithm started running.");
        boolean successful = false;
        long nodeCounter = 0;
        HashMap<Node, Node> parents = new HashMap<>();
        closedSet = new HashMap<>();
        openSet = new PriorityQueue<>(100000, new NodeComparator());
        starting_node.setgScore(0);
        starting_node.setfScore(starting_node.distance(client));    //set starting node's cost equal to the heuristic
        parents.put(starting_node, null);
        openSet.add(starting_node);
        while(!openSet.isEmpty()){
            nodeCounter++;
            Node current = openSet.remove();    //get node with lowest f from queue
            if(current.equals(client)){
                client = current;
                successful = true;
                break;
            }
            closedSet.put(current,current);
            for(Node n : hashmap.get(current)){ //for all neighbours of current
                if(closedSet.containsKey(n)) continue;  //if neighbour has already been explored, ignore it
                if(openSet.contains(n)){
                    double tentative_gScore = current.getgScore() + n.distance(current);
                    if(tentative_gScore < n.getgScore()){   //if n is reachable faster from current node, make current node its parent
                        openSet.remove(n);
                        n.setgScore(tentative_gScore);
                        n.setfScore(tentative_gScore + n.distance(client));
                        parents.put(n, current);
                        openSet.add(n);
                    }
                }
                else{
                    double tentative_gScore = current.getgScore() + n.distance(current);
                    n.setgScore(tentative_gScore);
                    n.setfScore(tentative_gScore + n.distance(client));
                    parents.put(n, current);
                    openSet.add(n);
                }
            }
        }
        if(successful){
            System.out.println("A*: Algorithm finished running for taxi with ID " + taxi.getId() + ". - SUCCESS");
        }
        else{
            System.out.println("A*: Algorithm finished running for taxi with ID " + taxi.getId() + ". - FAILURE");
        }
        System.out.println("\tNodes explored: " + nodeCounter + ", distance to client: " + client.getfScore() + " meters.");
        return new Pair(parents, client.getfScore());
    }
}