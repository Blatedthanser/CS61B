import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private WeightedQuickUnionUF ufForPercolation, ufForIsFull;
    private boolean[] opennessArray;
    private int openedSiteCount;
    private int top, down;

    public Percolation(int _N) {
        N = _N;
        ufForPercolation = new WeightedQuickUnionUF(N * N + 2); // plus top and down
        ufForIsFull = new WeightedQuickUnionUF(N * N + 1); // only the top
        top = N * N;
        down = N * N + 1;
        opennessArray = new boolean[N * N];
        for (int i = 0; i < N; i ++) {
            ufForPercolation.union(i, top);
            ufForIsFull.union(i, top);
        }
        for (int i = 0; i < N; i ++) {
            ufForPercolation.union(xyToInt(N - 1, i), down);
        }
        openedSiteCount = 0;
    }

    public void open(int row, int col) {
        if (!isCoordinateValid(row, col)) throw new IndexOutOfBoundsException();
        if (!isOpen(row, col)) {
            openedSiteCount ++;
            opennessArray[xyToInt(row, col)] = true;
            if (isCoordinateValid(row - 1, col) && isOpen(row - 1, col)) {
                ufForPercolation.union(xyToInt(row, col), xyToInt(row - 1, col));
                ufForIsFull.union(xyToInt(row, col), xyToInt(row - 1, col));
            }
            if (isCoordinateValid(row + 1, col) && isOpen(row + 1, col)) {
                ufForPercolation.union(xyToInt(row, col), xyToInt(row + 1, col));
                ufForIsFull.union(xyToInt(row, col), xyToInt(row + 1, col));
            }
            if (isCoordinateValid(row, col - 1) && isOpen(row, col - 1)) {
                ufForPercolation.union(xyToInt(row, col), xyToInt(row, col - 1));
                ufForIsFull.union(xyToInt(row, col), xyToInt(row + 1, col));
            }
            if (isCoordinateValid(row, col + 1) && isOpen(row, col + 1)) {
                ufForPercolation.union(xyToInt(row, col), xyToInt(row, col + 1));
                ufForIsFull.union(xyToInt(row, col), xyToInt(row + 1, col));
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
        return ufForIsFull.connected(xyToInt(row, col), top);
    }

    public int numberOfOpenSites() {
        return openedSiteCount;
    }

    public boolean percolates() {
        return ufForPercolation.connected(top, down);
    }

    private int xyToInt(int row, int col) {
        return row * N + col;
    }

    private boolean isCoordinateValid(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

}
