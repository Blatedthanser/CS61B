package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;
import java.util.TreeSet;

public class HyponymsHandler extends NgordnetQueryHandler {

    WordNet wordNet;

    public HyponymsHandler(String synsetFilename, String hyponymsFilename) {
        wordNet = new WordNet(synsetFilename, hyponymsFilename);
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();

        TreeSet<String> hypoSet = wordNet.hyponyms(words.getFirst());
        for (int i = 1; i < words.size(); i++) {
            TreeSet<String> oneWordHypoSet = wordNet.hyponyms(words.get(i));
            hypoSet.retainAll(oneWordHypoSet);
        }
        return hypoSet.toString();
    }
}
