import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private int trialsCount;
  private Percolation p;
  private double[] fractions;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

    trialsCount = trials;
    fractions = new double[trials];

    for (int trialNum = 0; trialNum < trialsCount; trialNum++) {
      p = new Percolation(n);

      while (!p.percolates()) {
        int i = StdRandom.uniform(1, n + 1);
        int j = StdRandom.uniform(1, n + 1);
        if (!p.isOpen(i, j)) {
          p.open(i, j);
        }
      }

      double fraction = (double) p.numberOfOpenSites() / (n * n);
      fractions[trialNum] = fraction;
    }

    Runtime runtime = Runtime.getRuntime();

    long memory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Used memory is bytes: " + memory);
    System.out.println("Used memory is megabytes: " + (memory / 1024L));
  }

  public double mean() {
    return StdStats.mean(fractions);
  }

  public double stddev() {
    return StdStats.stddev(fractions);
  }

  public double confidenceLo() {
    return mean() - ((1.96 * stddev()) / Math.sqrt(trialsCount));
  }

  public double confidenceHi() {
    return mean() + ((1.96 * stddev()) / Math.sqrt(trialsCount));
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);

    PercolationStats ps = new PercolationStats(n, t);

    String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
    StdOut.println("mean                     = " + ps.mean());
    StdOut.println("stddev                   = " + ps.stddev());
    StdOut.println("95% confidence interval  = " + confidence);
  }
}
