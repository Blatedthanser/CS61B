package main;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.TreeMap;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author HY Lin
 */
public class NGramMap {

    TreeMap<Integer, WordCountOfOneYear> yearlyWordCount = new TreeMap<>();
    TimeSeries yearlyTotal = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        In in = new In(wordHistoryFilename);
        int i = 1;
        while (!in.isEmpty()) {
            i += 1;
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split("\t");

            Integer year = Integer.valueOf(splitLine[1]);
            String word = splitLine[0];
            Integer count = Integer.valueOf(splitLine[2]);

            yearlyWordCount.merge(year,
                    new WordCountOfOneYear(word, count),
                    WordCountOfOneYear::plus);
            // We use merge because there are multiple input lines for one year
        }
        In in2 = new In(yearHistoryFilename);
        int j = 1;
        in2.readLine();
        while (!in2.isEmpty()) {
            i += 1;
            String nextLine = in2.readLine();
            String[] splitLine = nextLine.split("\t");

            Integer year = Integer.valueOf(splitLine[0]);
            Double count = Double.valueOf(splitLine[1]);

            yearlyTotal.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries yearCount = new TimeSeries();
        for (int year = startYear; year <= endYear; year ++) {
            if (yearlyWordCount.get(year).containsKey(word)) {
                yearCount.put(year, Double.valueOf(yearlyWordCount.get(year).get(word)));
            }
        }
        return yearCount;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(yearlyTotal);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return countHistory(word, startYear, endYear).dividedBy(yearlyTotal);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        for (String word : words) {
            result.plus(weightHistory(word, startYear, endYear));
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
