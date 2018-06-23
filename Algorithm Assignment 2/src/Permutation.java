import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// import java.util.Iterator;

public class Permutation {

    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<String> stringRandomizedQueue = new RandomizedQueue<>();
        while(!StdIn.isEmpty()) {
            stringRandomizedQueue.enqueue(StdIn.readString());
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(stringRandomizedQueue.dequeue());
        }

    }

}
