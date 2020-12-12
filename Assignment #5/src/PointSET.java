import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
    private SET<Point2D> point2DSET;

    public         PointSET()                               // construct an empty set of points
    {
        point2DSET = new SET<>();
    }
    public           boolean isEmpty()                      // is the set empty?
    {
        return point2DSET.isEmpty();
    }

    public               int size()                         // number of points in the set
    {
        return point2DSET.size();
    }

    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        validate(p);
        if (!contains(p)) {
            point2DSET.add( p );
        }
    }

    public           boolean contains(Point2D p)            // does the set contain point p?
    {
        validate(p);
        return point2DSET.contains(p);
    }

    public              void draw()                         // draw all points to standard draw
    {
        for (Point2D point: point2DSET) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        Queue<Point2D> pointsInRect = new Queue<>();

        for (Point2D point: point2DSET) {
            if (((rect.xmin() <= point.x()) && point.x() <= rect.xmax()) && ((rect.ymin() <= point.y()) && point.y() <= rect.ymax())) {
                pointsInRect.enqueue(point);
            }
        }

        return pointsInRect;
    }

    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (point2DSET.isEmpty()) {
            return null;
        }

        Point2D leastDistancePoint = point2DSET.min();
        double leastDistance = p.distanceSquaredTo(leastDistancePoint);

        for (Point2D point: point2DSET) {
                double distance = p.distanceSquaredTo( point );
                if (distance < leastDistance) {
                    leastDistance = distance;
                    leastDistancePoint = point;

            }
        }

        return leastDistancePoint;
    }

    private void validate(Point2D p)
    {
        if (p == null) {
            throw new java.lang.IllegalArgumentException("Null point passed in.");
        }
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        PointSET pointSET = new PointSET();

        Point2D a = new Point2D(1, 1);
        Point2D b = new Point2D(2, 2);
        Point2D c = new Point2D(3, 3);
        Point2D d = new Point2D(4, 4);
        Point2D e = new Point2D(5, 5);
        Point2D f = new Point2D(6, 6);
        Point2D g = new Point2D(7, 7);

        Point2D no = new Point2D(10, 10);

        pointSET.insert(a);
        pointSET.insert(b);
        pointSET.insert(c);
        pointSET.insert(d);
        pointSET.insert(e);
        pointSET.insert(f);
        pointSET.insert(g);

        pointSET.draw();

        if (!pointSET.isEmpty()) {
            StdOut.println("Not Empty.");
        }

        StdOut.println("Size: " + pointSET.size());

        if (pointSET.contains(a)) {
            StdOut.println("Contained a.");
        }

        if (!pointSET.contains(no)) {
            StdOut.println("Not Contained no.");
        }

        RectHV rectHV = new RectHV(1.5,1.5,3.5,3.5);

        StdOut.println(pointSET.range(rectHV));

        StdOut.println(pointSET.nearest(new Point2D(8,8)));

    }

}
