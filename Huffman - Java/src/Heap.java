import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The keys in the heap must be stored in an array.
 * 
 * There may be duplicate keys in the heap.
 * 
 * The constructor takes an argument that specifies how objects in the 
 * heap are to be compared. This argument is a java.util.Comparator, 
 * which has a compare() method that has the same signature and behavior 
 * as the compareTo() method found in the Comparable interface. 
 * 
 * Here are some examples of a Comparator<String>:
 *    (s, t) -> s.compareTo(t);
 *    (s, t) -> t.length() - s.length();
 *    (s, t) -> t.toLowerCase().compareTo(s.toLowerCase());
 *    (s, t) -> s.length() <= 3 ? -1 : 1;  
 */

public class Heap<E> implements PriorityQueue<E> {
  protected List<E> keys;
  private Comparator<E> comparator;
  
  /**
   * Creates a heap whose elements are prioritized by the comparator.
   */
  public Heap(Comparator<E> comparator) {
    this.comparator = comparator;
    this.keys = new ArrayList<>(); 
  }

  /**
   * Returns the comparator on which the keys in this heap are prioritized.
   */
  public Comparator<E> comparator() {
    return comparator;
  }

  /**
   * Returns the top of this heap. This will be the highest priority key. 
   * @throws NoSuchElementException if the heap is empty.
   */
  public E peek() {
	  if(size() != 0) {
		  return keys.get(0);
	  }
	  else {
		  throw new NoSuchElementException();
	  }
  }

  /**
   * Inserts the given key into this heap. Uses siftUp().
   */
  public void insert(E key) {
	  keys.add(key);
	  siftUp(size() - 1);
  }

  /**
   * Removes and returns the highest priority key in this heap.
   * @throws NoSuchElementException if the heap is empty.
   */
  public E delete() {
	  if(size() != 0) {
		  E returnValue = keys.get(0);
		  swap(0, size() - 1);
		  keys.remove(size() - 1);
		  siftDown(0);
	      return returnValue;
	  }
	  else {
		  throw new NoSuchElementException();
	  }
  }

  /**
   * Restores the heap property by sifting the key at position p down
   * into the heap.
   */
  public void siftDown(int p) {
	if(getRight(p) <  size() && getLeft(p) < size()) {
		if(comparator.compare(keys.get(getLeft(p)), keys.get(getRight(p))) > 0) {
			if(comparator.compare(keys.get(getRight(p)), keys.get(p)) < 0){
				swap(getRight(p), p);
				siftDown(getRight(p));	
			}
		}
		else{
			if(comparator.compare(keys.get(getLeft(p)),  keys.get(p)) < 0) {
				swap(getLeft(p), p);
				siftDown(getLeft(p));
			}
		}		
	}
	else if (getLeft(p) < size()) {
		if(comparator.compare(keys.get(getLeft(p)),  keys.get(p)) < 0) {
			swap(getLeft(p), p);
			siftDown(getLeft(p));
		}
	}
  }
  
  /** 
   * Restores the heap property by sifting the key at position q up
   * into the heap. (Used by insert()).
   */
  public void siftUp(int q) {
    if(getParent(q) >= 0 && comparator.compare(keys.get(getParent(q)), (keys.get(q))) > 0) {
    	swap(getParent(q), q);
    	siftUp(getParent(q));
    }
  }

  /**
   * Exchanges the elements in the heap at the given indices in keys.
   */
  public void swap(int i, int j) {
    E tempHolder = keys.get(i);
    keys.set(i, keys.get(j));
    keys.set(j, tempHolder);
  }
  
  /**
   * Returns the number of keys in this heap.
   */
  public int size() {
    return keys.size();
  }

  /**
   * Returns a textual representation of this heap.
   */
  public String toString() {
    return keys.toString();
  }
  
  /**
   * Returns the index of the left child of p.
   */
  public static int getLeft(int p) {
    return (2 * p) + 1;
  }

  /**
   * Returns the index of the right child of p.
   */
  public static int getRight(int p) {
    return (2 * p) + 2;
  }

  /**
   * Returns the index of the parent of p.
   */
  public static int getParent(int p) {
    return (p - 1) / 2;
  }
}
