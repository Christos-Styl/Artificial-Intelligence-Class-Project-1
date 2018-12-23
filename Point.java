package Taxis;

public class Point {
    private double x;
    private double y;
    
    public class PointException extends Exception {
        public PointException(String msg) {
            super(msg);
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public Point() throws PointException{
        throw new PointException("Point created with no starting values");
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }
    
    /** 
     * Measures the distance between two Points after converting longitude and latitude to meters.
     */
    public double distance(Point other){
        final int R = 6371000; // Radius of the earth
        double latDistance = Math.toRadians(this.x - other.x);
        double lonDistance = Math.toRadians(this.y - other.y);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) 
                   + Math.cos(Math.toRadians(this.x)) * Math.cos(Math.toRadians(other.x))* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to meters
        return distance;
    }
}
