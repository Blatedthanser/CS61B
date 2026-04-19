package main;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a string word (e.g. "dog") to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author HY Lin
 */
public class WordCountOfOneYear extends TreeMap<String, Integer> {

    /**
     * Constructs a new empty WordCountOfOneYear.
     */
    public WordCountOfOneYear() {
        super();
    }

    /**
     * Creates a WordCountOfOneYear with WORD and COUNT
     */
    public WordCountOfOneYear(String word, Integer count) {
        super();
        this.put(word, count);
    }

    /**
     * Creates a copy of wc.
     */
    public WordCountOfOneYear(WordCountOfOneYear wc) {
        super(wc);
    }


    /**
     * Returns the word-wise sum of this WordCountOfOneYear with the given WC. In other words, for
     * each year, sum the data from this WordCountOfOneYear with the data from WC. Should return a
     * new WordCountOfOneYear (does not modify this WordCountOfOneYear).
     *
     * If both WordCountOfOneYear don't contain any years, return an empty WordCountOfOneYear.
     * If one WordCountOfOneYear contains a year that the other one doesn't, the returned WordCountOfOneYear
     * should store the value from the WordCountOfOneYear that contains that year.
     */
    public WordCountOfOneYear plus(WordCountOfOneYear wc) {
        WordCountOfOneYear copy = new WordCountOfOneYear(this);
        List<String> keys = new ArrayList<>(wc.keySet());
        for (String key : keys) {
            copy.merge(key, wc.get(key), Integer::sum);
        }
        return copy;
    }

}
