package Taxis;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Taxis_Main {
    public static void main(String[] args) {    //args[0]: clientsFile, args[1]: nodesFile, args[2]: taxisFile
        InputReader reader = new InputReader(args[0], args[1], args[2]);
        HashMap<Node, ArrayList<Node>> hashmap = reader.runInputReader();
        System.out.println("Main: Matching taxis with nodes.");
        ArrayList<Node> taxiNodes = new ArrayList<>();
        for(Taxi taxi : reader.taxis){
            taxiNodes.add(taxi.findTaxiNode(hashmap));
        }
        System.out.println("Main: Finished matching taxis with nodes.");
        System.out.println("Main: Initializing A* Algorithm.");
        try{
            int j = 1;
            ArrayList<HashMap<Node, Node>> results = new ArrayList<>();
            for(Node t : taxiNodes){
                HashMap<Node, ArrayList<Node>> passedHashMap = new HashMap<>(hashmap);
                //Node passedClient = new Node(reader.client);
                AStar astar = new AStar(passedHashMap, reader.client);
                System.out.println("Main: A* Algorithm initialized successfully. Taxi #" + j + ".");
                results.add(astar.runAStar(t));
                j++;
            }
            System.out.println("Main: Exiting A* Algorithm.");
            System.out.println("Main: Began creation of kml file.");
            kml("results.kml", results, reader.client);
            System.out.println("Main: Finished creation of kml file.");
        }
        catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName() + ", " + e.getMessage());
            System.exit(1);
        }
    }
    
    public static void kml(String filename, ArrayList<HashMap<Node,Node>> results, Node client) {
        Random rand = new Random();
        Color green = Color.GREEN.darker(), color;
        PrintWriter writer = null;

        try{
            writer = new PrintWriter(filename);
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<kml xmlns=\"http://earth.google.com/kml/2.1\">");
            writer.println("<Document>");
            writer.println("<name>Taxi Routes</name>");
            writer.println("<Style id=\"green\">");
            writer.println("<LineStyle>");
            writer.println("<color>" + Integer.toHexString(green.getRGB()) + "</color>");
            writer.println("<width>4</width>");
            writer.println("</LineStyle>");
            writer.println("</Style>");
            int i = 1;
            /**
             *  Make random colors for the
             *  rest of the taxi routes.
             *  Don't make it green.
             */
            while (i < results.size()){

                int r = rand.nextInt(255);
                int g = rand.nextInt(127);
                int b = rand.nextInt(255);
                color = new Color(r, g, b);

                if (color.getRGB() == green.getRGB())
                    continue;

                writer.println("<Style id=\"taxi" + i + "\">");
                writer.println("<LineStyle>");
                writer.println("<color>" + Integer.toHexString(color.getRGB()) + "</color>");
                writer.println("<width>4</width>");
                writer.println("</LineStyle>");
                writer.println("</Style>");

                i++;

            }
            /**
             *  This is to make the client point
             */
            writer.println("<Placemark>");
            writer.println("<name>Client</name>");
            writer.println("<Point>");
            writer.println("<coordinates>");
            writer.println(client.getCoords().getX() + "," + client.getCoords().getY());
            writer.println("</coordinates>");
            writer.println("</Point>");
            writer.println("</Placemark>");
            
            int taxiNum = 0;
            /**
             *  This is to make the first route
             *  have green color.
             */
            while(taxiNum < results.size()){
                System.out.println("KML: Began writing taxi #" + taxiNum + ".");
                writer.println("<Placemark>");
                writer.println("<name>Taxi's ID " + taxiNum + "</name>");
                writer.println("<styleUrl>" + taxiNum + "</styleUrl>");
                writer.println("<LineString>");
                writer.println("<altitudeMode>relative</altitudeMode>");
                writer.println("<coordinates>");

                Node current = results.get(taxiNum).get(client);  //get the clients parent in this taxi's HashMap
                while(current != null){
                    writer.println(current.getCoords().getX() + "," + current.getCoords().getY());
                    current = results.get(taxiNum).get(current);
                }
            
                writer.println("</coordinates>");
                writer.println("</LineString>");
                writer.println("</Placemark>");
                taxiNum++;
            }

            writer.println("</Document>");
            writer.println("</kml>");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}

