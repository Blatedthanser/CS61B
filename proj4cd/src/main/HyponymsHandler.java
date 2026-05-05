package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {

    WordNet wordNet;

    public HyponymsHandler(String synsetFilename, String hyponymsFilename) {
        wordNet = new WordNet(synsetFilename, hyponymsFilename);
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        String Response = "";
        for (String word : q.words()) {
            Response += wordNet.hyponyms(word).toString();
        }

        return Response;
    }
}
