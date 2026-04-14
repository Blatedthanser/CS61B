import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private WeightedQuickUnionUF uf;
    private boolean[] opennessArray;
    private int openedSiteCount;
    private int top, down;

    public Percolation(int _N) {
        N = _N;
        uf = new WeightedQuickUnionUF(N * N + 2); // plus top and down
        top = N * N;
        down = N * N + 1;
        opennessArray = new boolean[N * N];
        for (int i = 0; i < N; i ++) {
            uf.union(i, top);
        }
        for (int i = 0; i < N; i ++) {
            uf.union(xyToInt(N - 1, i), down);
        }
        openedSiteCount = 0;
    }

    public void open(int row, int col) {
        if (!isCoordinateValid(row, col)) throw new IndexOutOfBoundsException();
        if (!isOpen(row, col)) {
            openedSiteCount ++;
            opennessArray[xyToInt(row, col)] = true;
            if (isCoordinateValid(row - 1, col) && isOpen(row - 1, col)) {
                uf.union(xyToInt(row, col), xyToInt(row - 1, col));
            }
            if (isCoordinateValid(row + 1, col) && isOpen(row + 1, col)) {
                uf.union(xyToInt(row, col), xyToInt(row + 1, col));
            }
            if (isCoordinateValid(row, col - 1) && isOpen(row, col - 1)) {
                uf.union(xyToInt(row, col), xyToInt(row, col - 1));
            }
            if (isCoordinateValid(row, col + 1) && isOpen(row, col + 1)) {
                uf.union(xyToInt(row, col), xyToInt(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isCoordinateValid(row, col)) throw new IndexOutOfBoundsException();
        return opennessArray[xyToInt(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (!isCoordinateValid(row, col)) throw new IndexOutOfBoundsException();
        if (!opennessArray[xyToInt(row, col)]) return false;
        return uf.connected(xyToInt(row, col), top);
    }

    public int numberOfOpenSites() {
        return openedSiteCount;
    }

    public boolean percolates() {
        return uf.connected(top, down);
    }

    private int xyToInt(int row, int col) {
        return row * N + col;
    }

    private boolean isCoordinateValid(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) return false;
        return true;
    }

}
