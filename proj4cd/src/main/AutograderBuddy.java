package main;

import browser.NgordnetQueryHandler;
import browser.NgordnetServer;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordHistoryFile, String yearHistoryFile,
            String synsetFile, String hyponymFile) {

        return new HyponymsHandler(synsetFile, hyponymFile);

    }
}
