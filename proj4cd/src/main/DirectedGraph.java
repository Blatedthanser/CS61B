package main;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class DirectedGraph {

    /**
     * Instance variables.
     */
    private final HashMap<Integer, ArrayList<Integer>> edges;
    private int size;

    /** Constructor **/
    public DirectedGraph() {
        edges = new HashMap<>();
    }

    /**
     * Add an Edge from n1 to n2
     */
    public void addEdge(int n1, int n2) {
        if (edges.containsKey(n1)) {
            List<Integer> list = edges.get(n1);
            if (!list.contains(n2)) {
                list.add(n2);
            }
        } else {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(n2);
            edges.put(n1, list);
        }
    }

    /**
     * PreOrder traversal with root set to {@code root}.
     */
    public List<Integer> preOrder(int root) {
        List<Integer> list = new ArrayList<>();
        list.add(root);
        if (edges.containsKey(root)) {
            for (int child : edges.get(root)) {
                list.addAll(preOrder(child));
            }
        }
        return list;
    }
}
