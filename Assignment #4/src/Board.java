import edu.princeton.cs.algs4.Stack;

public class Board {
    private final char[] gameBoard;
    private final int dimension;

    public Board(int[][] blocks)            // construct a board from an n-by-n array of blocks
    {
        // (where blocks[i][j] = block in row i, column j)
        validate(blocks);
        gameBoard = new char[blocks.length * blocks.length + 1];
        dimension = blocks.length;
        initialize(blocks);
    }

    private void validate(int[][] blocks)
    {
        if (blocks == null) {
            throw new java.lang.IllegalArgumentException("Null board to pass in.");
        }

        if (blocks.length < 2) {
            throw new java.lang.IllegalArgumentException("Board too small.");
        }

        int countZero = 0;
        for (int[] block : blocks) {
            for (int j = 0; j < blocks.length; j++) {
                if (block[j] == 0) {
                    countZero++;
                }
            }
        }

        if (countZero == blocks.length * blocks.length) {
            throw new IllegalArgumentException("Empty board.");
        }
    }

    private void initialize(int[][] blocks) {
        int indexCount = 1;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                gameBoard[indexCount] = (char) blocks[i][j];
                if (blocks[i][j] == 0) {
                    gameBoard[0] = (char) indexCount;
                }
                indexCount++;
            }
        }
    }

    public int dimension()                 // board dimension n
    {
        return dimension;
    }

    public int hamming()                   // number of blocks out of place
    {
        int hammingCount = 0;
        int indexCount = 1;
        for (int i = 1; i < gameBoard.length - 1; i++) {
                if (gameBoard[i] != indexCount) {
                    hammingCount++;
                }
                indexCount++;
        }

        return hammingCount;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manhattanCount = 0;
        int indexCount = 1;

        for (int i = 1; i < gameBoard.length; i++) {
            if (gameBoard[i] != indexCount && gameBoard[i] != 0) {
                manhattanCount += distanceTo(gameBoard[i], i);
            }
            indexCount++;
        }

        return manhattanCount;
    }

    private int getRow(int index)
    {
        if (index % dimension == 0){
            return index / dimension;
        }
        return (index / dimension) + 1;
    }

    private int getColumn(int index)
    {
        if (index % dimension == 0){
            return dimension;
        }

        return (index % dimension);
    }

    private int distanceTo(int targetIndex, int currentIndex)
    {
        int currentRow = getRow(currentIndex);
        int currentCol = getColumn(currentIndex);
        int targetRow = getRow(targetIndex);
        int targetCol = getColumn(targetIndex);

        return abs(currentRow, targetRow) + abs(currentCol, targetCol);
    }

    private int abs(int a, int b)
    {
        if (a == b) {
            return 0;
        }

        if (a > b) {
            return a - b;
        } else {
            return b - a;
        }
    }

    public boolean isGoal()                // is this board the goal board?
    {
        return hamming() == 0;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int swapCount = 0;
        int availableIndex1 = 0;
        int availableIndex2 = 0;

            for (int i = 1; i < gameBoard.length && swapCount < 2; i++) {
                if (gameBoard[i] != 0 && swapCount == 0) {
                    availableIndex1 = i;
                    swapCount++;
                } else if (gameBoard[i] != 0 && swapCount == 1) {
                    availableIndex2 = i;
                    swapCount++;
                }
            }

        return swap(availableIndex1, availableIndex2);
    }

    private Board swap(int first, int second)    // Helper function to swap any two blocks in the board
    {
        int[][] newBlocks = new int[dimension][dimension];
        int indexCount = 1;

        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                if (i + 1 == getRow(first) && j + 1== getColumn(first)){
                    newBlocks[i][j] = gameBoard[second];
                } else if (i + 1 == getRow(second) && j + 1 == getColumn(second)){
                    newBlocks[i][j] = gameBoard[first];
                } else {
                    newBlocks[i][j] = gameBoard[indexCount];
                }
                indexCount++;
            }
        }

        return new Board(newBlocks);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == null || y.getClass() != this.getClass()){
            return false;
        }

        if (this.dimension != ((Board) y).dimension) {
            return false;
        }

        Board b = (Board) y;
        int count = 0;
        for (int i = 0; i < gameBoard.length; i++){
            if (gameBoard[i] == ((Board) b).gameBoard[i]) {
                count++;
            }
        }

        return count == gameBoard.length;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> neighbors = new Stack<>();
        int blankRow = getRow(gameBoard[0]);
        int blankCol = getColumn(gameBoard[0]);

        if (blankRow > 1)           neighbors.push(swap(gameBoard[0], gameBoard[0] - dimension));
        if (blankCol > 1)           neighbors.push(swap(gameBoard[0], gameBoard[0] - 1));
        if (blankRow < dimension)   neighbors.push(swap(gameBoard[0], gameBoard[0] + dimension));
        if (blankCol < dimension)   neighbors.push(swap(gameBoard[0], gameBoard[0] + 1));

        return neighbors;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(dimension).append("\n");
        for (int i = 1; i < gameBoard.length; i++){
            stringBuilder.append(String.format("%2d ", (int) gameBoard[i]));
            if (i % dimension == 0){
                stringBuilder.append("\n");
            }
        }
        return new String(stringBuilder);
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] gameBlocks = {{1, 2, 3, 4}, {5, 6, 0, 8}, {9, 10, 11, 12}, {13, 14, 15, 7}};
        int[][] gameBlocks2 = {{1, 2, 3, 4}, {5, 6, 0, 8}, {9, 10, 11, 12}, {13, 14, 15, 7}};
        Board board = new Board(gameBlocks);
        Board board2 = new Board(gameBlocks2);

        System.out.println(board.toString());

        System.out.println("Length: " + board.dimension());
        System.out.println("Hamming: " + board.hamming());
        System.out.println("Manhattan: " + board.manhattan());

        if (board.isGoal()){
            System.out.println("Is Goal!");
        }

        if (board.equals(board2)){
            System.out.println("Equal!");
        }

        Board twinBoard = board.twin();
        System.out.println(twinBoard.toString());

        Iterable<Board> itBoard = board.neighbors();
        for (Board it: itBoard){
            System.out.println(it.toString());
        }

    }

}