import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] resizingArray;
  private int max;
  private int count;

  public RandomizedQueue() {
    count = 0;
    max = 10;
    resizingArray = (Item[]) new Object[max]; // NOTE Eww! Casting!
  }

  public boolean isEmpty() { return size() == 0; }

  public int size() { return count; }

  public void enqueue(Item item) {
    if (item == null) throw new NullPointerException();
    if (size() == resizingArray.length) { resize(2 * resizingArray.length); }

    resizingArray[count++] = item;
  }

  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException();
    int rand = StdRandom.uniform(count);

    while (resizingArray[rand] == null) { rand = StdRandom.uniform(resizingArray.length); }

    Item toRemove = resizingArray[rand];
    resizingArray[rand] = null;
    count--;

    if (fractionOccupied() < 0.25f) { resize(resizingArray.length / 2); }

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
        while(items[rand] == null) { rand = StdRandom.uniform(items.length); }

        Item elem = items[rand];
        items[rand] = null;
        remaining--;

        return elem;
      }
    }
  }

  private void resize(int size) {
    Item[] nextArray = (Item[]) new Object[size]; // NOTE Eww! Casting!

    int j = 0;
    for (int i = 0; i < resizingArray.length; i++) {
      if (resizingArray[i] != null) {
        nextArray[j++] = resizingArray[i];
      }
    }

    resizingArray = nextArray;
  }

  private float fractionOccupied() {
    float M = (float) resizingArray.length;
    float C = (float) count;
    return C / M;
  }

  public static void main(String[] args) {
    RandomizedQueue<String> rq = new RandomizedQueue<String>();

    rq.enqueue("1");
    rq.enqueue("2");
    rq.enqueue("3");
    rq.enqueue("4");
    rq.enqueue("5");
    // rq.dequeue();

    // StdOut.println("rq#sample: " + rq.sample());
    // StdOut.println("rq: " + rq.length() + ", " + rq.size()); // 16, 9

    // Iterator<String> i = rq.iterator();
    // while (i.hasNext()) {
    //   String s = i.next();
    //   StdOut.println(s);
    // }
  }
}
