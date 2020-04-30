package yellerapp1.collectors;


import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Class that acts as a priority queue but has a fixed size.
 * When the maximum number of elements is reached the lowest/highest element
 * will be removed.
 */
public class FixedSizePriorityQueue<T> {

    //TreeSet() 
    //A NavigableSet implementation based on a TreeMap. The elements are ordered using their natural ordering, or by a Comparator provided at set creation time, depending on which constructor is used.
    //This implementation provides guaranteed log(n) time cost for the basic operations (add, remove and contains).
    private TreeSet<t> inner;
    private int maxSize; 

    public FixedSizedPriorityQueue(Comparator<T> comparator, int maxSize) {
       this.inner = new Treeset<>(comparator);
       this.maxsize = maxSize;
    }

    //Java.util.concurrent.TreeSet.pollLast()
    //is an in-built function in Java which returns retrieves and removes the last (highest) element, or returns null if this set is empty.
    public FixedSizePriorityQueue<T> add(T element) {
       inner.add(element);
       if (inner.size() > maxSize) {
           inner.pollLast();
       }
       return this;
    }

    //TreeSet.contains(object O)
    //is used to check if a specific element is present in the TreeSet or not

    //TreeSet.remove(object O)
    //Java.util.TreeSet.remove(Object O) method is to remove a particular element from a Tree set
    public FixedSizePriorityQueue<T> add (T element) {
        if (inner.contains(element)) {
            inner.remove(element);
        }
        return this;
    }

    //Iterator
    //It is a universal iterator as we can apply it to any Collection object.
    //By using Iterator, we can perform both read and remove operations. It is
    //improved version of Enumeration with additional functionality of
    //remove-ability of a element.
    //Iterator must be used whenever we want to enumerate elements in all
    //Collection framework implemented interfaces like Set, List, Queue, Deque
    //and also in all implemented classes of Map interface. Iterator is the only
    //cursor available for entire collection framework.
    //Iterator object can be created by calling iterator() method present in 
    //Collection interface.
    public Iterator<T> iterator() {
        return inner.iterator();
    }

    @Override
    public String toString() {
        return "FixedSizePriorityQueue{" +
                "QueueContents=" + inner;

    }
}

