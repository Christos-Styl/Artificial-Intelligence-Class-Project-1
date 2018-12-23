package Taxis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InputReader {
    private String clientsFile, nodesFile, taxisFile;
    private Scanner scannerClients, scannerNodes, scannerTaxis;
    //variables will not be declared private for simplicity
    Node client;
    HashMap <Node, ArrayList<Node>> hashmap;    //hashmap for quick lookup of nodes:
                                                //the nodes are the keys and the lists of neighbours are the values
    ArrayList<Taxi> taxis;  //ArrayList of all Taxis
    
    public class InputReaderException extends Exception{
        public InputReaderException(String msg) {
            super(msg);
        }
    }
    
    public InputReader() throws Exception{
        throw new InputReaderException("No file names given.");
    }
    
    public InputReader(String clientsFile, String nodesFile, String taxisFile){
        try {
            this.clientsFile = clientsFile;
            this.nodesFile = nodesFile;
            this.taxisFile = taxisFile;
            scannerClients = new Scanner(new File(clientsFile));
            scannerNodes = new Scanner(new File(nodesFile), "UTF-8");   //the csv file must be in UTF-8 format, else the program will fail
            scannerTaxis = new Scanner(new File(taxisFile));
        }
        catch (FileNotFoundException e) {
            System.out.println("filename not found in InputReader constructor!");
            System.exit(1);
        }
        client = null;
        hashmap = new HashMap<>();
    }
    
    /**
     * Reads the clients position from a file and creates a Point
     * reference with his coordinates.
     */
    public Point getClientPosition(){
        double[] coords = new double[2];
        try{
            scannerClients.useDelimiter(",|\r\n");
            scannerClients.next();
            scannerClients.next();
            coords[0] = scannerClients.nextDouble();
            coords[1] = scannerClients.nextDouble();
            scannerClients.close();
        }
        catch(InputMismatchException e) {
            System.out.println("Incompatible Input File!");
            System.exit(1);
        }
        return new Point(coords[0],coords[1]);
    }
    
    /**
     * Creates the list of taxis and returns an ArrayList of Taxi
     * references.
     */
    public ArrayList<Taxi> getTaxisPositions(){
        double [] coords = new double[2];
        ArrayList<Taxi> t = new ArrayList<>();
        int id;
        try {
            scannerTaxis.useDelimiter(",|\r\n");
            scannerTaxis.nextLine();            
            while (scannerTaxis.hasNextLine()) {
                coords[0] = scannerTaxis.nextDouble();
                coords[1] = scannerTaxis.nextDouble();
                id = scannerTaxis.nextInt();
                t.add(new Taxi(coords[0], coords[1], id));
            }
            scannerTaxis.close();
        }
        catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName() + ", " + e.getMessage());
            System.exit(1);
        }
        return t;
    }
    
    /**
     * Creates the nodes used in the A* algorithm. Filters out
     * duplicate nodes and also finds the node closest to the client.
     * Calls functions defined above.
     */
    public void getNodesPositions(Point clientPosition){
        double [] coords = new double[2];
        int id, test__counter = 1;
        int previous_id, current_id;
        double dist, min_dist;
        Node temp, previous, current;

        try {
            scannerNodes.useDelimiter(",|\r\n");
            scannerNodes.nextLine();
            coords[0] = scannerNodes.nextDouble();
            coords[1] = scannerNodes.nextDouble();
            id = scannerNodes.nextInt();
            scannerNodes.nextLine();
            temp = new Node(coords[0], coords[1], id);
            previous = temp;
            previous_id = previous.getId();
            client = temp;
            min_dist = clientPosition.distance(client.getCoords());
            this.hashmap.put(previous, new ArrayList<>());
            
            while(scannerNodes.hasNextLine()){
                coords[0] = scannerNodes.nextDouble();
                coords[1] = scannerNodes.nextDouble();
                id = scannerNodes.nextInt();
                scannerNodes.nextLine();
                temp = new Node(coords[0], coords[1], id);
                current = temp;
                current_id = current.getId();
                dist = current.getCoords().distance(clientPosition);
                if(dist < min_dist) {
                    min_dist = dist;
                    client = current;
                }
                if(previous_id == current_id){
                    this.hashmap.get(previous).add(current); 

                    if(!this.hashmap.containsKey(current)){
                        this.hashmap.put(current, new ArrayList<>());
                    }
                    this.hashmap.get(current).add(previous);
                }
                else{
                    if(!this.hashmap.containsKey(current)) this.hashmap.put(current, new ArrayList<>());
                }
                previous = current;
		previous_id = current_id;
                test__counter++;
            }
            System.out.print("\tClient node is "); client.printNode();
            System.out.println ("\tStarting number of nodes: " + test__counter + ".");
            System.out.println("\tFinal number of nodes after removing duplicates: " + this.hashmap.size() + ".");
            System.out.println("\tDistance between client and client node: " + min_dist + " meters.");
            scannerNodes.close();
        }
        catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName() + ", " + e.getMessage());
            System.exit(1);
        }
    }
    
    public HashMap <Node, ArrayList<Node>> runInputReader(){
        System.out.println("InputReader: Started reading client position.");
        Point clientPoint = getClientPosition();
        System.out.println("InputReader: Done with client position.");
        System.out.println("InputReader: Started reading taxi positions.");
        taxis = getTaxisPositions();
        System.out.println("InputReader: Done with taxi positions.");
        System.out.println("InputReader: Started reading node positions.");
        getNodesPositions(clientPoint);
        System.out.println("InputReader: Done with node creation.");
        return this.hashmap;
    }
}