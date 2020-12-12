import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// import java.util.Arrays;

public class Percolation {
    private final int numIndex;                 // The length and width of the grid
    private int numOfOpenSites = 0;
    private boolean [][] grid;                      // 0: unopened, 1: opened(not connected), 2: opened(connected)
    private final WeightedQuickUnionUF uf;
    private boolean isPercolation = false;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n)
    {
        if (n < 1)
        {
            throw new IllegalArgumentException("n must bigger than 0");
        }

        numIndex = n;
        uf = new WeightedQuickUnionUF((n+1)*(n+1));
        grid = new boolean [n + 1][n + 1];
    }

    private void validate(int row, int col)
    {
        if (row < 1 || col < 1)
        {
            throw new IllegalArgumentException("n must bigger than 0");
        }
        else if (row > numIndex || col > numIndex)
        {
            throw new IllegalArgumentException("n must smaller than index");
        }
    }

    // open site (row, col) if it is not open already
    public    void open(int row, int col)
    {
        validate(row, col);

        if (!grid[row][col])
        {
            grid[row][col] = true; // open
            numOfOpenSites++;
            connect(row, col);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        validate(row, col);
        boolean result = false;

        if (grid[row][col])
        {
            result = true;
        }

        return result;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col)
    {
        validate(row, col);
        boolean result = false;

        if (uf.connected(idOfGrid(row, col), 0))
        {
            result = true;
        }

        return result;
    }

    private boolean connect(int row, int col)
    {
        boolean result = false;

        if (row == 1)
        {
            uf.union(0, idOfGrid(row, col));
            if (row + 1 <= numIndex && isOpen(row + 1, col))
            {
                uf.union(idOfGrid(row, col), idOfGrid(row + 1, col));
            }
        }
        else
        {
            if (row - 1 > 0 && isOpen(row - 1, col) && uf.find(idOfGrid(row, col)) !=  uf.find(idOfGrid(row - 1, col)))
            {
                uf.union(idOfGrid(row - 1, col), idOfGrid(row, col));
                result = true;
            }

            if (row < numIndex && isOpen(row + 1, col) && uf.find(idOfGrid(row, col)) !=  uf.find(idOfGrid(row + 1, col)))
            {
                uf.union(idOfGrid(row, col), idOfGrid(row + 1, col));
                result = true;
            }

            if (col > 1 && isOpen(row, col - 1) && uf.find(idOfGrid(row, col)) !=  uf.find(idOfGrid(row, col - 1)))
            {
                uf.union(idOfGrid(row, col - 1), idOfGrid(row, col));
                result = true;
            }

            if (col + 1 <= numIndex && isOpen(row, col + 1) && uf.find(idOfGrid(row, col)) !=  uf.find(idOfGrid(row, col + 1)))
            {
                uf.union(idOfGrid(row, col), idOfGrid(row, col + 1));
                result = true;
            }
        }

        if(result)
        {
            for (int bottomCol = 1; bottomCol <= numIndex; bottomCol++)
            {
                if (isFull( numIndex, bottomCol ))
                {
                    isPercolation = true;
                }
            }
        }
        return result;
    }

    // number of open sites
    public     int numberOfOpenSites()
    {
        return numOfOpenSites;
    }

    private int idOfGrid(int row, int col)
    {
        return (row * numIndex + col);
    }

    /*
    private void printGrid(){
        System.out.println(Arrays.deepToString(grid).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }
    */

    // does the system percolate?
    public boolean percolates()
    {
        return isPercolation;
    }

    // test client (optional)
    public static void main(String[] args)
    {
        int n = 2000;
        Percolation grid1 = new Percolation(n);
         while (!grid1.percolates())
         {
             int randomRow = StdRandom.uniform(n) + 1;
             int randomCol = StdRandom.uniform(n) + 1;
             if (!grid1.isOpen(randomRow, randomCol))
             {
                 grid1.open(randomRow, randomCol);
             }

         }
          System.out.println(grid1.numberOfOpenSites());
         // grid1.printGrid();
    }
}