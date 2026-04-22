package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    NGramMap ngm;

    public HistoryTextHandler(NGramMap map) {
        super();
        ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String Response = "";
        for (String word : q.words()) {
            Response += word + ": " + ngm.countHistory(word, startYear, endYear).toString() + "\n";
        }
        return Response;
    }
}