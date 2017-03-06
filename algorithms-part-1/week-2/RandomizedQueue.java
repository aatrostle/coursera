import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] resizingArray;
  private int count = 0;

  public RandomizedQueue() {
    resizingArray = (Item[]) new Object[2]; // NOTE Eww! Casting!
  }

  public boolean isEmpty() { return count == 0; }

  public int size() { return count; }

  public void enqueue(Item item) {
    if (item == null) throw new NullPointerException();
    if (count == resizingArray.length) { resize(2 * resizingArray.length); }

    resizingArray[count++] = item;
  }

  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException();
    int rand = StdRandom.uniform(count);

    Item toRemove = resizingArray[rand];
    resizingArray[rand] = resizingArray[count - 1];
    resizingArray[count - 1] = null;
    count--;

    if (count > 0 && count == resizingArray.length / 4) { resize(resizingArray.length / 2); }

    return toRemove;
  }

  public Item sample() {
    if (isEmpty()) throw new NoSuchElementException();

    int rand = StdRandom.uniform(count);
    while (resizingArray[rand] == null) { rand = StdRandom.uniform(resizingArray.length); }

    Item sample = resizingArray[rand];

    return sample;
  }

  public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

  private class RandomizedQueueIterator implements Iterator<Item> {
    private Item[] items;
    private int remaining = size();

    public RandomizedQueueIterator() {
      items = (Item[]) new Object[count]; // NOTE Eww! Casting!
      int j = 0;
      for (int i = 0; i < resizingArray.length; i++) {
        if (resizingArray[i] != null) { items[j++] = resizingArray[i]; }
      }
      remaining = count;
    }

    public boolean hasNext() { return remaining > 0; }
    public void remove() { throw new UnsupportedOperationException(); }
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      } else {
        int rand = StdRandom.uniform(items.length);
        while (items[rand] == null) { rand = StdRandom.uniform(items.length); }

        Item elem = items[rand];
        items[rand] = null;
        remaining--;

        return elem;
      }
    }
  }

  private void resize(int size) {
    Item[] nextArray = (Item[]) new Object[size]; // NOTE Eww! Casting!

    for (int i = 0; i < count; i++) {
      nextArray[i] = resizingArray[i];
    }

    resizingArray = nextArray;
  }

  private float fractionOccupied() {
    float m = (float) resizingArray.length;
    float c = (float) count;
    return c / m;
  }

  public static void main(String[] args) {
    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

    rq.enqueue(732);
    StdOut.println(rq.dequeue());
    StdOut.println(rq.size());
    StdOut.println(rq.isEmpty());
    rq.enqueue(790);
    StdOut.println(rq.dequeue());
    rq.enqueue(337);
    StdOut.println(rq.dequeue());
    rq.enqueue(728);
    StdOut.println(rq.dequeue());
    rq.enqueue(644);

    // StdOut.println("rq#sample: " + rq.sample());
    // StdOut.println("rq: " + rq.length() + ", " + rq.size()); // 16, 9

    // Iterator<String> i = rq.iterator();
    // while (i.hasNext()) {
    //   String s = i.next();
    //   StdOut.println(s);
    // }
  }
}
