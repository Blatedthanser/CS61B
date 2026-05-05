package main;

import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.stream.Collectors;

public class WordNet {
    /**
     * Instance variables
     */
    private Map<Integer, List<String>> synsetMapping;
    private DirectedGraph graph;

    /** Constructor **/
    public WordNet(String synsetFilename, String hyponymsFilename) {
        synsetMapping = new HashMap<>();
        graph = new DirectedGraph();

        In in = new In(synsetFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int id = Integer.parseInt(splitLine[0]);
            String synset = splitLine[1];
            String[] synonyms = synset.split(" ");
            String definition = splitLine[2];
            synsetMapping.put(id, List.of(synonyms));
        }

        In in2 = new In(hyponymsFilename);
        while (!in2.isEmpty()) {
            String nextLine = in2.readLine();
            String[] splitLine = nextLine.split(",");
            int wordID = Integer.parseInt(splitLine[0]);
            for (int i = 1; i < splitLine.length; i++) {
                graph.addEdge(wordID, Integer.parseInt(splitLine[i]));
            }
        }
    }

    /**
     * Returns all the hyponyms of {@code word} as a list. Elements are in alphabetic order.
     */
    public TreeSet<String> hyponyms(String word) {

        TreeSet<String> hypoSet = new TreeSet<>();
        for (Map.Entry<Integer, List<String>> entry : synsetMapping.entrySet()) {
            if (entry.getValue().contains(word)) {
                int synsetID = entry.getKey();
                List<String> synset = entry.getValue();
                hypoSet.addAll(synset);
                hypoSet.addAll(graph.preOrder(synsetID)
                        .stream()
                        .map(synsetMapping::get)
                        .filter(Objects::nonNull)
                        .flatMap(List::stream)
                        .toList());
            }
        }
        return hypoSet;
    }
}
