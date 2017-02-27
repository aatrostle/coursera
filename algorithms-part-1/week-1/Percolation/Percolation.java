import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
  private boolean[][] opened;
  private int openCount = 0;
  private int top = 0;
  private int bottom;
  private int size;
  private WeightedQuickUnionUF qf;

  public Percolation(int n) {
    if (n <= 0) throw new IllegalArgumentException();
    size = n;
    bottom = size * size + 1;
    qf = new WeightedQuickUnionUF(size * size + 2);
    opened = new boolean[size][size];
  }

  public void open(int row, int col) {
    if (isOpen(row, col)) return;
    if (isOutOfOpenRange(row, col)) throw new IndexOutOfBoundsException();
    opened[row - 1][col - 1] = true;
    openCount++;
    if (row == 1) qf.union(getQFIndex(row, col), top);
    if (row == size) qf.union(getQFIndex(row, col), bottom);
    if (col > 1 && isOpen(row, col - 1)) {
      qf.union(getQFIndex(row, col), getQFIndex(row, col - 1));
    }
    if (col < size && isOpen(row, col + 1)) {
      qf.union(getQFIndex(row, col), getQFIndex(row, col + 1));
    }
    if (row > 1 && isOpen(row - 1, col)) {
      qf.union(getQFIndex(row, col), getQFIndex(row - 1, col));
    }
    if (row < size && isOpen(row + 1, col)) {
      qf.union(getQFIndex(row, col), getQFIndex(row + 1, col));
    }
  }

  public boolean isOpen(int row, int col) {
    if (isOutOfOpenRange(row, col)) throw new IndexOutOfBoundsException();
    return opened[row - 1][col - 1];
  }

  public boolean isFull(int row, int col) {
    if (isOutOfOpenRange(row, col)) throw new IndexOutOfBoundsException();
    return qf.connected(top, getQFIndex(row, col));
  }

  public int numberOfOpenSites() {
    return openCount;
  }

  public boolean percolates() {
    return qf.connected(top, bottom);
  }

  private int getQFIndex(int row, int col) {
    return size * (row - 1) + col;
  }

  private boolean isOutOfOpenRange(int row, int col) {
    return !(0 < row && row <= size && 0 < col && col <= size);
  }

  public static void main(String[] args) {
    int n = StdIn.readInt();

    Percolation p = new Percolation(n);

    while (!StdIn.isEmpty()) {
      int q = StdIn.readInt();
      int r = StdIn.readInt();
      p.open(q, r);
    }

    StdOut.println(p.numberOfOpenSites());
    StdOut.println(p.percolates());
  }
}
