import java.util.Arrays;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Item[] array;
    private int firstIndex;
    private int lastIndex;
    private int size = 0;

    public Deque()                           // construct an empty deque
    {
        array = (Item[]) new Object[11];
        firstIndex = 4;
        lastIndex = 5;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return (size == 0);
    }

    public int size()                        // return the number of items on the deque
    {
        return size;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        validateAdd(item);

        if (firstIndex < array.length) {
            array[++firstIndex] = item;
            size++;

            if (size == 1) {
                lastIndex = firstIndex;
            }

            if (array.length - firstIndex < array.length / 4) {
                array = resizingFront();
            }
        }
    }

    public void addLast(Item item)           // add the item to the end
    {
        validateAdd(item);

        if (lastIndex - 1 >= 0) {
            array[--lastIndex] = item;
            size++;

            if (size == 1) {
                firstIndex = lastIndex;
            }

            if (lastIndex < array.length / 4) {
                array = resizingLast();
            }
        }
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        Item result;

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The queue is empty, no element to remove.");
        }

        result = array[firstIndex];
        array[firstIndex] = null;
        size--;

        if (size != 0) {
            firstIndex--;
        }

        return result;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        Item result;

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The queue is empty, no element to remove.");
        }

        result = array[lastIndex];
        array[lastIndex] = null;
        size--;

        if (size != 0) {
            lastIndex++;
        }

        return result;
    }

    private Item[] resizingFront()
    {
        Item[] newArray = (Item[]) new Object[array.length/2*3];

        for (int i = lastIndex; i <= firstIndex; i++)
        {
            newArray[i] = array[i];
        }

        return newArray;
    }

    private Item[] resizingLast()
    {
        Item[] newArray = (Item[]) new Object[(array.length / 2) * 3 + 1];

        int newLastIndex = lastIndex + (array.length / 2);
        int newFirstIndex = firstIndex + (array.length / 2);

        for (int i = newLastIndex; i <= newFirstIndex; i++) {
            newArray[i] = array[lastIndex];
            lastIndex++;
        }

        lastIndex = newLastIndex;
        firstIndex = newFirstIndex;

        return newArray;
    }

    private void validateAdd(Item x)
    {
        if (x == null) {
            throw new java.lang.IllegalArgumentException("Can`t add a null item into the queue");
        }
    }

    /*
    Iterator class
     */

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private final int arrayFirstIndex = firstIndex;
        private final int arrayLastIndex = lastIndex;
        private int index = firstIndex;

        private DequeIterator()
        {

        }

        @Override
        public boolean hasNext() {
            if (arrayFirstIndex != firstIndex || arrayLastIndex != lastIndex) {
                throw new java.lang.IllegalArgumentException("The queue changed during the iterator processing.");
            }
            return (array[index] != null);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more item in the queue.");
            }

            Item result = array[index];
            index--;

            return result;
        }

        public void remove()
        {
            throw new java.lang.UnsupportedOperationException("Can not remove in iterator.");
        }
    }


    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<Integer> integerDeque = new Deque<>();

        for (int i = 0; i <= 10; i++) {
            integerDeque.addLast(i);
            System.out.println(Arrays.toString(integerDeque.array));
        }

        /*
        for (int i = 60; i >= 30; i--) {
            integerDeque.addFirst(i);
            System.out.println(Arrays.toString(integerDeque.array));
        }
        */

        System.out.println("-----------------------");

        Iterator itr = integerDeque.iterator();
        Iterator itr2 = integerDeque.iterator();
        while (itr.hasNext()) {
            System.out.println( itr.next() );
            System.out.println( itr2.next() );
        }


        // while (!integerDeque.isEmpty()) {
        //    integerDeque.removeLast();

          //  System.out.println(Arrays.toString(integerDeque.array));

        //}

    }
}
