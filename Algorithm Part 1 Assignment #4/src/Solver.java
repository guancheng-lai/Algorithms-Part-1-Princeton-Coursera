import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

private Move lastMove;

    private static class Move implements Comparable<Move>{
        private Move prev;
        private Board currentBoard;
        private int moves = 0;
        private final int weight;

        private Move(Board board){
            this.currentBoard = board;
            this.weight = board.manhattan() + moves;
        }

        private Move(Board board, Move previousMove){
            this.currentBoard = board;
            this.prev = previousMove;
            this.moves = previousMove.moves + 1;
            this.weight = board.manhattan() + moves;
        }

        @Override
        public int compareTo(Move o) {
            return this.weight - o.weight;
        }
    }

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        validate(initial);

        final MinPQ<Move> boardMinPQ= new MinPQ<>();
        boardMinPQ.insert(new Move(initial));

        final MinPQ<Move> twinBoardMinPQ = new MinPQ<>();
        twinBoardMinPQ.insert(new Move(initial));

        while (true) {
            lastMove = extract(boardMinPQ);

            if (lastMove != null || extract(twinBoardMinPQ) != null){
                return;
            }
        }
    }

    private Move extract(MinPQ<Move> boardMinPQ) {
        if (boardMinPQ.isEmpty()){
            return null;
        }

        Move bestMove = boardMinPQ.delMin();
        if (bestMove.currentBoard.isGoal()){
            return bestMove;
        }

        for (Board b : bestMove.currentBoard.neighbors()){
            if (bestMove.prev == null || !b.equals(bestMove.prev.currentBoard)){
                boardMinPQ.insert(new Move(b, bestMove));
            }
        }

        return null;

    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return lastMove != null;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return lastMove.moves;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!isSolvable()){
            return null;
        }

        Stack<Board> solutions = new Stack<>();

        while (lastMove != null){
            solutions.push(lastMove.currentBoard);
            lastMove = lastMove.prev;
        }

        return solutions;
    }

    private void validate(Board board)
    {
        if (board == null){
            throw new java.lang.IllegalArgumentException("Null board.");
        }

        if (board.dimension() < 2){
            throw new java.lang.IllegalArgumentException("Board too small.");
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In("puzzle03hm.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                // StdOut.println(movesMade);
                StdOut.println(board);
            }
            StdOut.println();
        }
    }
}