import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        validate(points);
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        for (int i = 0; i < pointsCopy.length; i++){
            for (int j = i + 1; j < pointsCopy.length; j++){
                for (int k = j + 1; k < pointsCopy.length; k++){
                    for (int l = k + 1; l < pointsCopy.length; l++){
                        if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[j].slopeTo(pointsCopy[k]) && pointsCopy[k].slopeTo(pointsCopy[l]) == pointsCopy[i].slopeTo(pointsCopy[l])){
                            Point[] pointSet = new Point[]{pointsCopy[i], pointsCopy[j], pointsCopy[k], pointsCopy[l]};

                            Point highestPoint = highestPoint(pointSet);
                            Point lowestPoint = lowestPoint(pointSet);

                            lineSegments.add(new LineSegment(lowestPoint, highestPoint));
                        }
                    }
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

            for (int j = 0; j < points.length; j++){
                if (i == j){
                    continue;
                } else if (points[i].equals(points[j])){
                    throw new java.lang.IllegalArgumentException("Duplicate points.");
                }
            }
        }
    }

    private Point highestPoint(Point[] points) {
        Point high = points[0];

        for (int i = 0; i < 4; i++){
            if (high.compareTo(points[i]) >= 0){
                high = points[i];
            }
        }

        return high;
    }

    private Point lowestPoint(Point[] points) {
        Point low = points[0];

        for (int i = 0; i < 4; i++){
            if (low.compareTo(points[i]) <= 0){
                low = points[i];
            }
        }

        return low;
    }

    public int numberOfSegments()        // the number of line segments
    {
        return lineSegments.size();
    }

    public LineSegment[] segments()                // the line segments
    {
        return lineSegments.toArray(new LineSegment[numberOfSegments()]);
    }
}