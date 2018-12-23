package Taxis;

import java.util.Comparator;

/**
 * A custom Comparator for use in the priority queue.
 */
public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node a, Node b) {
        if(a.getfScore() < b.getfScore()){
            return -1;
        }
        else if(a.getfScore() > b.getfScore()){
            return 1;
        }
        else{
            return 0;
        }
    }
}

