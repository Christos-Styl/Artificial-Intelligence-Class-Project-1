package Taxis;

import java.util.ArrayList;
import java.util.HashMap;

public class Taxis_Main {
    public static void main(String[] args) {    //args[0]: clientsFile, args[1]: nodesFile, args[2]: taxisFile
        InputReader reader = new InputReader(args[0], args[1], args[2]);
        HashMap<Node, ArrayList<Node>> hashmap = reader.runInputReader();
    }
}
