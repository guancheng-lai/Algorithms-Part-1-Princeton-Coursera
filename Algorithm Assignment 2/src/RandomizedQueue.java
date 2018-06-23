import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node current;

    private class Node {
        private Item item;
        private Node next;
        private Node before;

        private Node(Item insertItem)
        {
            item = insertItem;
            next = null;
            before = null;
        }
    }

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        first = null;
        size = 0;
    }

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return (size == 0);
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return size;
    }

    public void enqueue(Item item)           // add the item
    {
        validate(item);

        if (size() == 0) {
            first = new Node(item);
            first.next = null;
            first.before = null;
            current = first;
        } else {
            first.before = new Node(item);
            first.before.next = first;

            current.next = first.before;
            current.next.before = current;

            current = current.next;
        }

        size++;
    }

    public Item dequeue()                    // remove and return a random item
    {
        Item result;

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The queue is empty, can not dequeue.");
        } else if (size() == 1) {
            result = first.item;

            first.before = null;
            first.item = null;
            first.next = null;
            current.before = null;
            current.item = null;
            current.next = null;

            size--;

            return result;
        }

        int randomNumber = StdRandom.uniform(0, size());
        current = find(randomNumber);
        if (current == first) {
            result = dequeueFirstNode();
        } else if (current == first.before) {
            result = dequeueLastNode();
        } else {
            result = dequeueMiddleNode();
        }

        size--;

        return result;
    }

    private Item dequeueFirstNode()
    {
        Item result = current.item;

        current.before.next = current.next;
        current.next.before = current.before;

        first = first.next;
        current = first.before;

        return result;
    }

    private Item dequeueLastNode()
    {
        Item result = current.item;

        first.before = current.before;
        current.before.next = first;

        current = first.before;

        return result;
    }

    private Item dequeueMiddleNode()
    {
        Item result = current.item;

        current.before.next = current.next;
        current.next.before = current.before;

        current = first.before;

        return result;
    }


    public Item sample()                     // return a random item (but do not remove it)
    {
        Item result;

        if (isEmpty()){
            throw new java.util.NoSuchElementException("The queue is empty.");
        }

        int randomNumber = StdRandom.uniform(0, size());
        Node ptr = find(randomNumber);
        result = ptr.item;

        return result;
    }

    private Node find(int randomNumber) {
        Node ptr;

        if (randomNumber == 1 || randomNumber == 0) {
            ptr = first;
        } else if (randomNumber == size()) {
            ptr = first.before;
        } else {
            ptr = first;
            while (randomNumber > 1) {
                ptr = ptr.next;
                randomNumber--;
            }
            ptr = ptr.next;
        }

        return ptr;
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private Node itr;
        private int count = 0;
        private final Node itrNodeFirst;
        private final Node itrNodeLast;
        private final int queueSize;

        private RandomizedQueueIterator() {
            if (size > 1) {
                itr = first.before;

                itrNodeFirst = first.before;
                itrNodeLast = first;

                queueSize = size();
            } else if (size == 1) {
                itr = first;
                itrNodeFirst = first;
                itrNodeLast = first;
                queueSize = 1;
            } else {
                queueSize = 0;
                itrNodeLast = null;
                itrNodeFirst = null;
            }
        }


        @Override
        public boolean hasNext() {
            if (queueSize == 0) {
                return false;
            }

            if (itrNodeFirst != first.before || itrNodeLast != first || queueSize != size()) {
                throw new java.lang.IllegalArgumentException("The queue changed during the iterator processing.");
            }

            if (itr == itrNodeFirst && count == 0) {
                count++;
                return true;
            } else if (itr == itrNodeFirst){
                return false;
            }
            return true;
        }

        @Override
        public Item next() {
            if (itrNodeFirst != first.before || itrNodeLast != first || queueSize != size()) {
                throw new java.lang.IllegalArgumentException("The queue changed during the iterator processing.");
            }

            Item result = itr.item;

            itr = itr.before;

            return result;
        }

        @Override
        public void remove() {
            // throw new java.lang.UnsupportedOperationException("Not supported.");
            if (itrNodeFirst != first.before || itrNodeLast != first || queueSize != size()) {
                throw new java.lang.IllegalArgumentException("The queue changed during the iterator processing.");
            }
        }
    }

    private void validate(Item item)
    {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Can`t add a null item.");
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<Integer> integerRandomizedQueue = new RandomizedQueue<>();
        for (int i = 0; i < 100; i++)
        {
            //StdRandom.uniform(-1.0, 1.0)
            integerRandomizedQueue.enqueue(i);
            System.out.println(integerRandomizedQueue.current.item);
        }

        Iterator it = integerRandomizedQueue.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

        RandomizedQueue<Integer> integerRandomizedQueue2 = new RandomizedQueue<>();
        integerRandomizedQueue2.enqueue( 1 );
        int num = integerRandomizedQueue.sample();
        System.out.println( num );
        integerRandomizedQueue2.enqueue( 2 );
        num = integerRandomizedQueue.sample();
        System.out.println( num );

        RandomizedQueue<Integer> integerRandomizedQueue3 = new RandomizedQueue<>();
        Iterator it3 = integerRandomizedQueue3.iterator();
    }
}