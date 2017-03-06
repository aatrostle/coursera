import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
  private Node first;
  private Node last;
  private int count;

  private class Node {
    private Item item;
    private Node prev;
    private Node next;

    Node(Item item) {
      item = item;
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
    if (isEmpty()) {
      first = new Node(item);
      last = first;
    } else {
      Node node = new Node(item);
      node.next = first;
      first.prev = node;
      first = node;
    }
    count++;
  }

  public void addLast(Item item) {
    if (item == null) throw new NullPointerException();
    if (isEmpty()) {
      last = new Node(item);
      first = last;
    } else {
      Node node = new Node(item);
      last.next = node;
      node.prev = last;
      last = node;
    }
    count++;
  }

  public Item removeFirst() {
    if (isEmpty()) throw new NoSuchElementException();
    Node node = first;
    if (this.size() == 1) {
      first = null;
      last = null;
    } else {
      first.next.prev = null;
      first = first.next;
    }
    count--;
    node.next = null;
    return node.item;
  }

  public Item removeLast() {
    if (isEmpty()) throw new NoSuchElementException();
    Node node = first;
    if (this.size() == 1) {
      first = null;
      last = null;
    } else {
      last.prev.next = null;
      last = last.prev;
    }
    count--;
    node.next = null;
    return node.item;
  }

  public Iterator<Item> iterator() { return new DequeIterator(); }

  private class DequeIterator implements Iterator<Item> {
    private Node current;

    public DequeIterator() {
      current = first;
    }

    public boolean hasNext() { return current != null; }

    public void remove() { throw new UnsupportedOperationException(); }

    public Item next() {
      if (hasNext()) {
        throw new NoSuchElementException();
      } else {
        Node node = current;
        current = current.next;
        return node.item;
      }
    }
  }

  public static void main(String[] args) {
    Deque<String> d = new Deque<String>();

    StdOut.println("d: " + d.size());

    d.addFirst("hello");
    d.addLast("world");
    StdOut.println("d: " + d.size());

    d.removeFirst();
    StdOut.println("d: " + d.size());
    d.removeFirst();
    StdOut.println("d: " + d.size());
  }
}
