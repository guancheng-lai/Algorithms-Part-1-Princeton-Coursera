import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node root = null;
    private int size = 0;
    private double nearestDistance = 0;

    private static class Node {
        private Point2D p;          // the point
        // private RectHV rect;        // the axis-aligned rectangle corresponding to this node
        private Node lb;            // the left/bottom subtree
        private Node rt;            // the right/top subtree
        private int level;          // level%2 == 0 vertical, level%2 == 1 horizontal

        private Node(Point2D point) {
            p = point;
            // rect = null;
            lb = null;
            rt = null;
            level = 0;
        }

        private Node(Point2D point, int levelCount) {
            p = point;
            // rect = null;
            lb = null;
            rt = null;
            level = levelCount;
        }


    }

    public KdTree()                                 // construct an empty set of points
    {

    }

    public boolean isEmpty()                      // is the set empty?
    {
        return root == null || size == 0;
    }

    public int size()                         // number of points in the set
    {
        return size;
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        validate(p);
        if (contains(p)) return;
        if (root == null || root.p == null) {
            root = new Node(p, 0);
            size++;
            return;
        }

        int count = 1;
        Node current = root;

        boolean inserted = false;
        while (!inserted) {
            if (current.level % 2 == 0) {
                // Vertical
                if (p.x() < current.p.x()) {
                    if (current.lb == null) {
                        current.lb = new Node(p, count);
                        inserted = true;
                    } else {// if not null
                        current = current.lb;
                    }
                } else { // if p.x() >= current.p.x()
                    if (current.rt == null) {
                        current.rt = new Node(p, count);
                        inserted = true;
                    } else { // if not null
                        current = current.rt;
                    }
                }
            } else { // if current.level % 2 != 0
                // Horizontal
                if (p.y() < current.p.y()) {
                    if (current.lb == null) {
                        current.lb = new Node(p, count);
                        inserted = true;
                    } else { // if not null
                        current = current.lb;
                    }
                } else { // if p.y() >= current.p.y()
                    if (current.rt == null) {
                        current.rt = new Node(p, count);
                        inserted = true;
                    } else { // if not null
                        current = current.rt;
                    }
                }
            }

            count++;
        }

        size++;
    }

    private void validate(Point2D p)
    {
        if (p == null) {
            throw new java.lang.IllegalArgumentException("Null Point Passed in.");
        }
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        validate(p);

        if (root == null || root.p == null) {
            return false;
        }

        if (p.equals(root.p)) {
            return true;
        }

        Node current = root;

        while (current != null) {
            if (current.level % 2 == 0) {
                // Vertical
                if (p.equals(current.p)) {
                    return true;
                } else if (p.x() < current.p.x()) {
                    current = current.lb;
                } else {
                    current = current.rt;
                }
            } else {
                // Horizontal
                if (p.equals(current.p)) {
                    return true;
                } else if (p.y() < current.p.y()) {
                    current = current.lb;
                } else {
                    current = current.rt;
                }
            }
        }

        return false;
    }

    public void draw()                         // draw all points to standard draw
    {
        if (root == null || root.p == null) return;

        // Draw the root line
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(root.p.x(), 0, root.p.x(), 1.0);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(root.p.x(), root.p.y());


        // Draw the descendant
        if (root.lb != null) {
            drawNode(root.lb, root);
        }

        if (root.rt != null) {
            drawNode(root.rt, root);
        }
    }

    private void drawNode(Node cur, Node pre) {
        if (cur.level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.002);

            if (cur.p.y() < pre.p.y()) {
                StdDraw.line( cur.p.x(), 0, cur.p.x(), pre.p.y() );
            } else { // cur.p.y() >= pre.p.y()
                StdDraw.line( cur.p.x(), pre.p.y(), cur.p.x(), 1.0 );
            }

        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.002);

            if (cur.p.x() < pre.p.x()) {
                StdDraw.line(0, cur.p.y(), pre.p.x(), cur.p.y());
            } else { //
                StdDraw.line(pre.p.x(), cur.p.y(), 1.0, cur.p.y());
            }
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(cur.p.x(), cur.p.y());


        if (cur.lb != null) {
            drawNode(cur.lb, cur);
        }

        if (cur.rt != null) {
            drawNode(cur.rt, cur);
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException("Null rect.");
        }

        Queue<Point2D> pointQueue = new Queue<>();

        if (isEmpty()) return pointQueue;

        Node current = root;
        searchRect(pointQueue, current, rect);

        return pointQueue;
    }

    private void searchRect(Queue<Point2D> queue, Node cur, RectHV rect)
    {
        if (cur == null || cur.p == null) return;

        if (rect.contains(cur.p)) {
            queue.enqueue(cur.p);
        }
        if (cur.level % 2 == 0) {
            if (rect.xmin() <= cur.p.x() && cur.p.x() <= rect.xmax()) {
                // Rect contains the point

                if (cur.lb != null) {
                    searchRect( queue, cur.lb, rect );
                }

                if (cur.rt != null) {
                    searchRect( queue, cur.rt, rect );
                }

            } else if (rect.xmax() <= cur.p.x() && cur.lb != null) {
                // rect located left side
                searchRect( queue, cur.lb, rect );
            } else if (cur.p.x() <= rect.xmin() && cur.rt != null) {
                // rect located right side
                searchRect( queue, cur.rt, rect );
            }
        } else {
            if (rect.ymin() <= cur.p.y() && cur.p.y() <= rect.ymax()) {
                // Rect contains the point

                if (cur.lb != null) {
                    searchRect( queue, cur.lb, rect );
                }

                if (cur.rt != null) {
                    searchRect( queue, cur.rt, rect );
                }

            } else if (rect.ymax() <= cur.p.y() && cur.lb != null) {
                // rect located left side
                searchRect( queue, cur.lb, rect );
            } else if (cur.p.y() <= rect.ymin() && cur.rt != null) {
                // rect located right side
                searchRect( queue, cur.rt, rect );
            }
        }
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        validate(p);

        if (root == null || root.p == null) return null;

        if (root.p.distanceSquaredTo(p) == 0.0) {
            return root.p;
        }


        double distance = p.distanceSquaredTo(root.p);
        Point2D nearest = searchPoint(root, root, p, distance);

        if (nearest == null) {
            return root.p;
        }

        return nearest;
    }

    private Point2D searchPoint(Node cur, Node pre, Point2D that, double prevDistance)
    {
        Node currentPoint = cur;
        double currentDistance = currentPoint.p.distanceSquaredTo(that);
        Point2D near;

        if (currentDistance <= prevDistance) {
            near = currentPoint.p;
        } else {
            currentDistance = prevDistance;
            near = pre.p;
        }


        if (currentPoint.lb != null) {
            Point2D nextLBPoint = searchPoint(currentPoint.lb, currentPoint, that, currentDistance);
            if (nextLBPoint.distanceSquaredTo(that) <= currentDistance) {
                currentDistance = nextLBPoint.distanceSquaredTo(that);
                near = nextLBPoint;
            }
        }

        if (currentPoint.rt != null) {
            Point2D nextRTPoint = searchPoint(currentPoint.rt, currentPoint, that, currentDistance);
            if (nextRTPoint.distanceSquaredTo(that) <= currentDistance) {
                currentDistance = nextRTPoint.distanceSquaredTo(that);
                near = nextRTPoint;
            }
        }

        return near;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.372, 0.497));
        kdTree.insert(new Point2D(0.564, 0.413));
        kdTree.insert(new Point2D(0.226, 0.577));
        kdTree.insert(new Point2D(0.144, 0.179));
        kdTree.insert(new Point2D(0.083, 0.510));
        kdTree.insert(new Point2D(0.320, 0.708));
        kdTree.insert(new Point2D(0.417, 0.362));
        kdTree.insert(new Point2D(0.862, 0.825));
        kdTree.insert(new Point2D(0.785, 0.725));
        kdTree.insert(new Point2D(0.499, 0.208));








        if (kdTree.contains(new Point2D(0.3, 0.7))) {
            StdOut.println("Contained!");
        }

        if (!kdTree.contains(new Point2D(0.2, 0.7))) {
            StdOut.println("Not Contained!");
        }

        StdOut.println("Size: " + kdTree.size());
        // kdTree.draw();


        for (Point2D p: kdTree.range(new RectHV(0.6, 0.6, 0.9, 0.9))) {
            // StdOut.println(p.toString());
        }

        Point2D o = new Point2D(0.1, 0.1);
        StdOut.println(kdTree.nearest(o).toString());
    }
}