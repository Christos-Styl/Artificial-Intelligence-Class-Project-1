package Taxis;

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
}