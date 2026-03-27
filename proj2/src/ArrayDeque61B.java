import org.apache.bcel.generic.INEG;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayDeque61B<T> implements Deque61B<T>, Iterable<T> {

    private T[] array;
    private int space;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Constructor */
    public ArrayDeque61B() {
        space = 8;
        array = (T[]) new Object[space];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        array[nextFirst] = x;
        size++;
        if (size == space) {
            resizeUp();
        } else {
            nextFirst = (nextFirst - 1 + space) % space;
        }
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        array[nextLast] = x;
        size++;
        if (size == space) {
            resizeUp();
        } else {
            nextLast = (nextLast + 1) % space;
        }
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        if (size == 0) return null;
        return array[(nextFirst + 1) % space];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        if (size == 0) return null;
        return array[(nextLast - 1 + space) % space];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (size == 0) return null;
        T oldFirst = get(0);
        nextFirst = (nextFirst + 1) % space;
        size--;
        if (size <= space / 4) {
            resizeDown();
        }
        return oldFirst;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) return null;
        T oldLast = get(size - 1);
        nextLast = (nextLast - 1 + space) % space;
        size--;
        if (size <= space / 4) {
            resizeDown();
        }
        return oldLast;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        return array[(nextFirst + 1 + index) % space];
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    /**
     * This method resizes the available space (the max size) of ArrayDeque
     * by doubling it. No return.
     */
    private void resizeUp() {
        T[] newArray = (T[]) new Object[2 * space];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        space *= 2;
        array = newArray;
        nextFirst = space - 1;
        nextLast = size;
    }

    /**
     * This method resizes the available space (the max size) of ArrayDeque
     * by halfing it. No return.
     */
    private void resizeDown() {
        T[] newArray = (T[]) new Object[space / 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        space /= 2;
        array = newArray;
        nextFirst = space - 1;
        nextLast = size;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T>{

        private int position;

        public ArraySetIterator() {
            position = 0;
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return position < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            return get(position ++);
        }
    }

    public boolean equals(ArrayDeque61B<T> uddaADeque) {
        if (size != uddaADeque.size()) return false;
        for (int i = 0; i < size; i++){
            if (get(i) != uddaADeque.get(i)) return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder returnString = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            returnString.append(get(i)).append(", ");
        }
        returnString.append(get(size - 1)).append("]");
        return returnString.toString();
    }
}
