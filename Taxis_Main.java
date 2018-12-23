package Taxis;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Taxis_Main {
    
    public static class Taxis_MainException extends Exception{
        public Taxis_MainException(String msg) {
            super(msg);
        }
    }
    
    public static void main(String[] args) {    //args[0]: clientsFile, args[1]: nodesFile, args[2]: taxisFile
        InputReader reader = new InputReader(args[0], args[1], args[2]);
        HashMap<Node, ArrayList<Node>> hashmap = reader.runInputReader();
        System.out.println("Main: Matching taxis with nodes.");
        ArrayList<Node> taxiNodes = new ArrayList<>();
        for(Taxi taxi : reader.taxis){
            taxiNodes.add(taxi.findTaxiNode(hashmap));
        }
        System.out.println("Main: Finished matching taxis with nodes.");
        System.out.println("Main: Beginning initialization of A* Algorithm for all taxis.");
        int bestTaxiId = -1;
        double bestDistance = Double.MAX_VALUE;
        try{
            int j = 0;
            ArrayList<HashMap<Node, Node>> results = new ArrayList<>();
            for(Node t : taxiNodes){
                HashMap<Node, ArrayList<Node>> passedHashMap = new HashMap<>(hashmap);
                AStar astar = new AStar(passedHashMap, reader.client);
                System.out.println("Main: A* Algorithm initialized successfully for taxi with ID " + reader.taxis.get(j).getId() + ".");
                Pair pair = astar.runAStar(t, reader.taxis.get(j));
                results.add(pair.getHashmap());
                if(pair.getDistance() < bestDistance){
                    bestDistance = pair.getDistance();
                    bestTaxiId = reader.taxis.get(j).getId();
                }
                j++;
            }
            if(bestTaxiId == -1) throw new Taxis_MainException("Something went wrong... No taxi reached the client.");
            System.out.println("Main: Exiting A* Algorithm.");
            System.out.println("\t***Best taxi was taxi with ID " + bestTaxiId + " with a distance of " + bestDistance + " meters from the client.");
            System.out.println("Main: Began creation of kml file.");
            kml("results2.kml", results, reader.client, reader.taxis);
            System.out.println("Main: Finished creation of kml file.");
            System.out.println("Main: Repeating: ***Best taxi was taxi with ID " + bestTaxiId + " with a distance of " + bestDistance + " meters from the client.");
            System.out.println("Main: Exiting now...");
        }
        catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName() + ", " + e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Creates the kml file with a different style for each taxi.
     */
    public static void kml(String filename, ArrayList<HashMap<Node,Node>> results, Node client, ArrayList<Taxi> taxis) {
        Random rand = new Random();
        Color color;
        PrintWriter writer = null;

        try{
            writer = new PrintWriter(filename);
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<kml xmlns=\"http://earth.google.com/kml/2.1\">");
            writer.println("<Document>");
            writer.println("<name>Taxi Routes</name>");
            int i = 0;
            while (i < results.size()){
                int r = rand.nextInt(205);
                int g = rand.nextInt(105);
                int b = rand.nextInt(255);
                color = new Color(r, g, b);
                writer.println("<Style id=\"taxi" + taxis.get(i).getId() + "\">");
                writer.println("<LineStyle>");
                writer.println("<color>" + Integer.toHexString(color.getRGB()) + "</color>");
                writer.println("<width>4</width>");
                writer.println("</LineStyle>");
                writer.println("</Style>");
                i++;
            }
            writer.println("<Placemark>");
            writer.println("<name>Client</name>");
            writer.println("<Point>");
            writer.println("<coordinates>");
            writer.println(client.getCoords().getX() + "," + client.getCoords().getY());
            writer.println("</coordinates>");
            writer.println("</Point>");
            writer.println("</Placemark>");
            
            i = 0;
            while(i < results.size()){
                System.out.println("KML: Began writing taxi with ID " + taxis.get(i).getId() + ".");
                writer.println("<Placemark>");
                writer.println("<name>Taxi with ID " + taxis.get(i).getId() + "</name>");
                writer.println("<styleUrl>#taxi" + taxis.get(i).getId() + "</styleUrl>");
                writer.println("<LineString>");
                writer.println("<altitudeMode>relative</altitudeMode>");
                writer.println("<coordinates>");

                Node current = results.get(i).get(client);  //get the clients parent in this taxi's HashMap
                while(current != null){
                    writer.println(current.getCoords().getX() + "," + current.getCoords().getY());
                    current = results.get(i).get(current);
                }
            
                writer.println("</coordinates>");
                writer.println("</LineString>");
                writer.println("</Placemark>");
                i++;
            }
            writer.println("</Document>");
            writer.println("</kml>");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } finally {
            if (writer != null){
                writer.close();
            }
        }
    }
}