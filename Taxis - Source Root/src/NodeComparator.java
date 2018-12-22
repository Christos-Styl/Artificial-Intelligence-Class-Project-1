package Taxis;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node a, Node b) {
        int res;
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

