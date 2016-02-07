package course.week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int N;
    private boolean[][] sites;
    private WeightedQuickUnionUF uf;

    public Percolation(int N) {
        if (N < 1) throw new IllegalArgumentException();

        this.N = N;
        sites = new boolean[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                sites[i][j] = false;
            }
        }

        uf = new WeightedQuickUnionUF(N * N + 2);
    }

    public void open(int i, int j) {
        checkIndex(i, j);
        sites[i][j] = true;

        if (i == 1) uf.union(0, convertIndex(i, j));
        if (i == N) uf.union(convertIndex(i, j), N * N + 1);

        unionRelated(i + 1, j, convertIndex(i, j));
        unionRelated(i, j + 1, convertIndex(i, j));
        unionRelated(i, j - 1, convertIndex(i, j));
        unionRelated(i - 1, j, convertIndex(i, j));

            // System.out.println(percolates());
    }

    public boolean isOpen(int i, int j) {
        checkIndex(i, j);
        return sites[i][j];
    }

    public boolean isFull(int i, int j) {
        checkIndex(i, j);
        return uf.connected(convertIndex(i, j), 0);
    }

    public boolean percolates() {
        return  uf.connected(N * N + 1, 0);
        //for (int i = (N * (N - 1)) + 1; i <= N * N; i++) {
        //    if (uf.connected(i, 0)) return true;
        //}
        //return false;
    }

    private void unionRelated(int i, int j, int curSite) {
        if ((i >= 1) && (i <= N) &&
                (j >= 1) && (j <= N)) {
            if (isOpen(i, j) && !uf.connected(convertIndex(i, j), curSite)) {
                uf.union(curSite, convertIndex(i, j));
                //if (i == N && isFull(i, j)) uf.union(N * N + 1, curSite);
            }
        }
    }

    private void checkIndex(int i, int j) {
        if ((i < 1) && (i > N) &&
                (j < 1) && (j > N)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int convertIndex(int i, int j) {
        return (i - 1) * N + j;
    }

    public static void main(String[] args) {

    }
}
