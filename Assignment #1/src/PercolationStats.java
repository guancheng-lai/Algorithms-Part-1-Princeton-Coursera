import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int numOfTrials;
    private final double[] statsArray;
    private static final double CONFIDENCE_95 = 1.96;

    private double mean;
    private double stddev;
    private double lowCon;
    private double hiCon;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n < 1 || trials < 1)
        {
            throw new IllegalArgumentException("n and trials must bigger than 0");
        }

        numOfTrials = trials;
        statsArray = new double[trials];

        for (int i = 0; i < trials; i++)
        {
            Percolation grid = new Percolation(n);
            while (!grid.percolates())
            {
                int randomRow = StdRandom.uniform(n) + 1;
                int randomCol = StdRandom.uniform(n) + 1;
                if (!grid.isOpen(randomRow, randomCol))
                {
                    grid.open(randomRow, randomCol);
                }
            }
            // int number = grid.numberOfOpenSites();
            statsArray[i] = (double) grid.numberOfOpenSites() / (n * n);
        }

        mean = StdStats.mean(statsArray);
        stddev = StdStats.stddev(statsArray);
        lowCon = mean - (CONFIDENCE_95 * stddev/sqrt(numOfTrials));
        hiCon = mean + (CONFIDENCE_95 * stddev/sqrt(numOfTrials));

        // System.out.println("mean                    = " + mean());
        // System.out.println("stddev                  = " + stddev());
        // System.out.println("95% confidence interval = [" + confidenceLo() + ", " + confidenceHi() + "]");
    }


    public double mean()
    {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return lowCon;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return hiCon;
    }

    private static double sqrt(int number) {
        if (number == 1)
        {
            return 1;
        }
        else if (number == 0)
        {
            return 0;
        }

        double t;

        double squareRoot = number / 2.0;

        do {
            t = squareRoot;
            squareRoot = (t + (number / t)) / 2;
        } while ((t - squareRoot) != 0);

        return squareRoot;
    }

    // test client (described below)
    public static void main(String[] args)
    {
        // new PercolationStats( 10, 10 );
    }
}