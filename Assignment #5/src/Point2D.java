import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point2D implements Comparable<Point2D> {
    private final double xCoordinate;
    private final double yCoordinate;

    public Point2D(double x, double y)              // construct the point (x, y)
    {
        validate(x, y);

        xCoordinate = x;
        yCoordinate = y;
    }

    private void validate(double x, double y) {
        if (x == Double.NaN) {
            throw new java.lang.IllegalArgumentException("Null argument X (Not a number).");
        }

        if (y == Double.NaN) {
            throw new java.lang.IllegalArgumentException("Null argument Y (Not a number).");
        }
    }

    public  double x()                              // x-coordinate
    {
        return xCoordinate;
    }

    public  double y()                              // y-coordinate
    {
        return yCoordinate;
    }

    public  double distanceTo(Point2D that)         // Euclidean distance between two points
    {
        return Math.sqrt(distanceSquaredTo(that));
    }

    public  double distanceSquaredTo(Point2D that)  // square of Euclidean distance between two points
    {
        return Math.pow(this.xCoordinate - that.xCoordinate, 2) + Math.pow(this.yCoordinate - that.yCoordinate, 2);
    }

    public     int compareTo(Point2D that)          // for use in an ordered symbol table
    {
        return (int)(this.xCoordinate - that.xCoordinate);
    }

    public boolean equals(Object that)              // does this point equal that object?
    {
        if (that == null) {
            return false;
        }

        if (that.getClass() != this.getClass()) {
            return false;
            // throw new java.lang.IllegalArgumentException("Not a Point2D class.");
        }

        if (this == that) {
            return true;
        }

        return this.xCoordinate == ((Point2D) that).xCoordinate && this.yCoordinate == ((Point2D) that).yCoordinate;

    }

    public    void draw()                           // draw to standard draw
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(this.xCoordinate, this.yCoordinate);
    }

    public  String toString()                       // string representation
    {
        return "(" + xCoordinate + ", " + yCoordinate + ")";
    }

    public static void main(String[] args)
    {
        Point2D a = new Point2D(0.1, 0.1);
        Point2D b = new Point2D(0.2, 0.2);
        Point2D a1 = new Point2D(0.1, 0.1);

        StdOut.println(a.distanceTo(b));
        StdOut.println(a.distanceSquaredTo(b));
        StdOut.println(a.compareTo(b));
        if (a.equals(a1)) {
            StdOut.println("Equals.");
        }

        b.draw();
        StdOut.println(a.toString());

    }
}