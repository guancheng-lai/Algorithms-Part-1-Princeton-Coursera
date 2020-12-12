import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import static java.lang.Math.sqrt;

public class RectHV {
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;

    public    RectHV(double xmin, double ymin,      // construct the rectangle [xmin, xmax] x [ymin, ymax]
                     double xmax, double ymax)      // throw a java.lang.IllegalArgumentException if (xmin > xmax) or (ymin > ymax)
    {
        validate(xmin, ymin, xmax, ymax);

        xMin = xmin;
        yMin = ymin;
        xMax = xmax;
        yMax = ymax;
    }

    private void validate(double xmin, double ymin, double xmax, double ymax) {
        if (xmin == Double.NaN || ymin == Double.NaN ||xmax == Double.NaN ||ymax == Double.NaN) {
            throw new java.lang.IllegalArgumentException("Argument not a number");
        }
    }

    public  double xmin()                           // minimum x-coordinate of rectangle
    {
        return xMin;
    }

    public  double ymin()                           // minimum y-coordinate of rectangle
    {
        return yMin;
    }

    public  double xmax()                           // maximum x-coordinate of rectangle
    {
        return xMax;
    }

    public  double ymax()                           // maximum y-coordinate of rectangle
    {
        return yMax;
    }

    public boolean contains(Point2D p)              // does this rectangle contain the point p (either inside or on boundary)?
    {
        boolean result = false;

        if ((xMin <= p.x() && p.x() <= xMax) && (yMin <= p.y() && p.y() <= yMax)) {
            result = true;
        }

        return result;
    }

    public boolean intersects(RectHV that)          // does this rectangle intersect that rectangle (at one or more points)?
    {
        boolean result = false;

        Point2D leftBottom = new Point2D(this.xMin, this.yMin);
        Point2D rightBottom = new Point2D(this.xMax, this.yMin);
        Point2D leftTop = new Point2D(this.xMin, this.yMax);
        Point2D rightTop = new Point2D(this.xMax, this.yMax);

        if (that.contains(leftBottom) || that.contains(rightBottom) || that.contains(leftTop) || that.contains(rightTop)) {
            result = true;
        }

        if ((this.xMin <= that.xMin && that.xMax <= this.xMax) && (this.yMin <= that.yMin && that.yMax <= this.yMax)) {
            result = true;
        }

        return result;
    }

    public  double distanceTo(Point2D p)            // Euclidean distance from point p to closest point in rectangle
    {
        if (p == null) {
            throw new java.lang.IllegalArgumentException("Null point passed in.");
        }

        double result = -1;

        if (this.contains(p)) {
            double least = p.x() - xMin;

            if (xMax - p.x() < least) {
                least = xMax - p.x();
            }

            if (yMax - p.y() < least) {
                least = yMax - p.y();
            }

            if (p.y() - yMin < least) {
                least = p.y() - yMin;
            }

            result = least;
        } else {
            if (p.x() >= xMax && p.y() >= yMax) {
                result = p.distanceTo(new Point2D(xMax, yMax));
            } else if (p.x() < xMin && p.y() > yMax) {
                result = p.distanceTo(new Point2D(xMax, yMin));
            } else if (p.x() > xMax && p.y() < yMin) {
                result = p.distanceTo(new Point2D(xMin, yMax));
            } else if (p.x() < xMin && p.y() < yMin) {
                result = p.distanceTo(new Point2D(xMin, yMin));
            } else if (p.x() >= xMax) {
                result = p.x() - xMax;
            } else if (p.y() >= yMax) {
                result = p.y() - yMax;
            } else if (p.x() <= xMin) {
                result = xMin - p.x();
            } else if (p.y() <= yMin) {
                result = yMin - p.y();
            }

        }

        return result;
    }

    public  double distanceSquaredTo(Point2D p)     // square of Euclidean distance from point p to closest point in rectangle
    {
        if (p == null) {
            throw new java.lang.IllegalArgumentException("Null point passed in.");
        }

        return Math.pow(distanceTo(p), 2);
    }

    public boolean equals(Object that)              // does this rectangle equal that object?
    {
        if (that.getClass() != this.getClass()) {
            return false;
        }

        if (this == that) {
            return true;
        }

        return this.xMin == ((RectHV) that).xMin && this.xMax == ((RectHV) that).xMax && this.yMin == ((RectHV) that).yMin && this.yMax == ((RectHV) that).yMax;
    }

    public    void draw()                           // draw to standard draw
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.rectangle(xMin, yMin, (xMax - xMin)/2, (yMax - yMin)/2);
    }

    public  String toString()                       // string representation
    {
        return "(" + new Point2D(xMin, yMax).toString() + new Point2D(xMax, yMax).toString() + ")\n(" + new Point2D(xMin, yMin).toString() + new Point2D(xMax, yMin).toString() + ")";
    }

    public static void main(String[] args)
    {
        Point2D a = new Point2D(0.1, 0.1);
        Point2D b = new Point2D(0.2, 0.3);

        RectHV r1 = new RectHV(0.1, 0.1, 0.3, 0.2);
        RectHV r2 = new RectHV(0.2, 0.2, 0.3, 0.3);
        if (r1.contains(a)) {
            StdOut.println("Contain.");
        }

        if (!r2.contains(a)) {
            StdOut.println("Contain.");
        }

        if (r1.intersects(r2)) {
            StdOut.println("Intersect.");
        }

        StdOut.println(r1.distanceTo(a));
        StdOut.println(r2.distanceTo(a));
        StdOut.println(r2.distanceSquaredTo(a));

        r1.draw();
    }

}