import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
    private ArrayList<Point> pointSet = new ArrayList<>();

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        validate(points);
        Point[] pointsCopy = points.clone();

        Arrays.sort(pointsCopy);
        System.out.println(Arrays.toString(pointsCopy));



        Point origin;
        double slope;

        for (int i = 0; i < pointsCopy.length - 3; i++){
            origin = pointsCopy[i];
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {
                slope = origin.slopeTo(pointsCopy[j]);
                int count = 0;
                Point lastPoint = null;



                for (int k = j + 1; k < pointsCopy.length; k++){
                    if (origin.slopeTo(pointsCopy[k]) == slope){
                        count++;
                        lastPoint = pointsCopy[k];
                    }
                }

                if (count >= 2){
                    if (!duplicate(lastPoint, slope)){
                        lineSegmentArrayList.add(new LineSegment(origin, lastPoint));
                    }

                    pointSet.add(origin);
                    pointSet.add(lastPoint);

                }
            }
        }
    }

    private void validate(Point[] points) {
        if (points == null){
            throw new java.lang.IllegalArgumentException("Pass in null points set.");
        }

        if (points.length < 4) {
            throw new java.lang.IllegalArgumentException("Too few points");
        }

        for (int i = 0; i < points.length; i++){
            if (points[i] == null){
                throw new java.lang.IllegalArgumentException("Pass in a null point.");
            }

            for (int j = 0; j < 4; j++){
                if (i == j){

                } else if (points[i].compareTo(points[j]) == 0){
                    throw new java.lang.IllegalArgumentException("Duplicate points.");
                }
            }
        }
    }

    public int numberOfSegments(){        // the number of line segments
        return lineSegmentArrayList.size();
    }

    public LineSegment[] segments(){                // the line segments
        return lineSegmentArrayList.toArray(new LineSegment[numberOfSegments()]);
    }

   private boolean duplicate(Point lastPoint, double slope){
        for (int i = 1; i < pointSet.size(); i = i + 2){
            if (pointSet.get(i) == lastPoint && pointSet.get(i - 1).slopeTo(pointSet.get(i)) == slope){
                return true;
            }
        }


        /*
        if (pointSet.size() > 1 && pointSet.size() % 2 == 0){
            for (int i = 0; i < pointSet.size(); i = i + 2) {
                if (pointSet.get(i).slopeTo(originPoint) == pointSet.get(i).slopeTo(pointSet.get(i + 1))) {
                    return true;
                }
            }
        }
        */

        return false;
   }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("grid4x4.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}