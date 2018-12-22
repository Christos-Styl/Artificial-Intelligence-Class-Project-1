package Taxis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class AStar {
    //variables will not be declared private for simplicity
    HashMap<Node, ArrayList<Node>> hashmap;
    //ArrayList<Node> taxiNodes;
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
        /*if(!taxiNodes.isEmpty()){
            this.taxiNodes = taxiNodes;
        }
        else{
            throw new AStarException("A* Algorithm initiated with empty taxi node ArrayList.");
        }*/
        if(!(client == null)){
            this.client = client;
        }
        else{
            throw new AStarException("A* Algorithm initiated with no client.");
        }
    }
    
    public HashMap<Node, Node> runAStar(Node starting_node){
        System.out.println("A*: Algorithm started running.");
        boolean successful = false;
        HashMap<Node, Node> parents = new HashMap<>();
        closedSet = new HashMap<>();
        openSet = new PriorityQueue<>(100000, new NodeComparator());
        //client.setfScore(-1);
        //client.setgScore(Double.MAX_VALUE);
        long test__counter = 0;
        starting_node.setgScore(0);
        starting_node.setfScore(starting_node.distance(client));    //set starting node's cost equal to the heuristic
        parents.put(starting_node, null);
        openSet.add(starting_node);
        while(!openSet.isEmpty()){
            test__counter++;
            //if(test__counter % 10000 == 0) System.out.println(test__counter);
            Node current = openSet.remove();    //get node with lowest f from queue
            if(current.equals(client)){
                client = current;
                successful = true;
                break;
            }
            closedSet.put(current,current);
            int test__neighbourCounter = 0;
            for(Node n : hashmap.get(current)){ //for all neighbours of current
                //if(n.equals(client)) System.out.println("Found the client!!" + test__neighbourCounter);
                //if(openSet.contains(client)) System.out.println("He is in the open set!!" + test__neighbourCounter);
                //if(closedSet.containsKey(client)) System.out.println("He is in the closed set!!");
                test__neighbourCounter++;
                if(closedSet.containsKey(n)) continue;  //if neighbour has already been explored, ignore it
                if(openSet.contains(n)){
                    double tentative_gScore = current.getgScore() + n.distance(current);
                    if(tentative_gScore < n.getgScore()){
                        openSet.remove(n);
                        n.setgScore(tentative_gScore);
                        n.setfScore(tentative_gScore + n.distance(client));
                        n.setParent(current);
                        parents.put(n, current);
                        openSet.add(n);
                    }
                }
                else{
                    double tentative_gScore = current.getgScore() + n.distance(current);
                    n.setgScore(tentative_gScore);
                    n.setfScore(tentative_gScore + n.distance(client));
                    n.setParent(current);
                    parents.put(n, current);
                    openSet.add(n);
                }
            }
            if(test__neighbourCounter == 0) System.out.println("No neighbours");
        }
        if(successful){
            System.out.println("A*: Algorithm finished running. - SUCCESS");
        }
        else{
            System.out.println("A*: Algorithm finished running. - FAILURE");
        }
        System.out.println("\tNodes explored: " + test__counter + ", distance to client: " + client.getfScore() + " meters.");
        return parents;
    }
}
