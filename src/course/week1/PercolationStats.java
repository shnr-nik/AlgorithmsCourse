package course.week1;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] thresholds;
    private int cnt;

    public PercolationStats(int N, int T) {

        if (N < 1 || T < 1) throw new IllegalArgumentException();
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, N + 1);
                int y = StdRandom.uniform(1, N + 1);
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    cnt++;
                }
                PercolationVisualizer.draw(percolation, N);
                System.out.println(percolation.percolates());
            }
            thresholds[i] = (cnt / ((double) (N * N)));
            cnt = 0;
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(thresholds.length));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(thresholds.length));
    }

    public static void main(String[] args) {

        if (args.length != 2) throw new IllegalArgumentException();

        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.printf("mean                    = %f%n", percolationStats.mean());
        System.out.printf("stddev                  = %f%n", percolationStats.stddev());
        System.out.printf("95%% confidence interval = %f, %f", percolationStats.confidenceLo(),
                percolationStats.confidenceHi());
    }
}
