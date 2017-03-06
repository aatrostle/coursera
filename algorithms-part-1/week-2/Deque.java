import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
  private Node first, last;
  private int count;

  private class Node {
    private Item item;
    private Node prev;
    private Node next;

    Node(Item item) {
      this.item = item;
      next = null;
      prev = null;
    }
  }

  public Deque() {
    first = null;
    last = null;
    count = 0;
  }

  public boolean isEmpty() { return size() == 0; }

  public int size() { return count; }

  public void addFirst(Item item) {
    if (item == null) throw new NullPointerException();
    Node oldFirst = first;
    first = new Node(item);
    first.next = oldFirst;
    if (size() > 0) {
      oldFirst.prev = first;
    } else {
      last = first;
    }
    count++;
  }

  public void addLast(Item item) {
    if (item == null) throw new NullPointerException();
    Node oldLast = last;
    last = new Node(item);
    last.prev = oldLast;
    if (size() > 0) {
      oldLast.next = last;
    } else {
      first = last;
    }
    count++;
  }

  public Item removeFirst() {
    if (isEmpty()) throw new NoSuchElementException();
    Item item = first.item;
    if (size() > 1) {
      first = first.next;
      first.prev = null;
    } else {
      first = null;
      last = null;
    }
    count--;
    return item;
  }

  public Item removeLast() {
    if (isEmpty()) throw new NoSuchElementException();

    Item item = last.item;
    if (size() > 1) {
      last = last.prev;
      last.next = null;
    } else {
      first = null;
      last = null;
    }
    count--;
    return item;
  }

  public Iterator<Item> iterator() { return new DequeIterator(); }

  private class DequeIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() { return current != null; }

    public void remove() { throw new UnsupportedOperationException(); }

    public Item next() {
      if (current == null) throw new NoSuchElementException();
      Item value = current.item;
      current = current.next;
      return value;
    }
  }

  public static void main(String[] args) {
    Deque<Integer> d = new Deque<Integer>();

    d.addFirst(0);
    d.addFirst(1);
    d.addFirst(2);
    d.addFirst(3);
    d.addFirst(4);
    d.addFirst(5);
    d.addFirst(6);
    StdOut.println(d.removeFirst());
  }
}
