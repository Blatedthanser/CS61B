import java.util.*;

import edu.princeton.cs.algs4.Stopwatch;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

/** Performs a timing test on three different set implementations.
 *  @author Josh Hug
 *  @author Brendan Hu
 */
public class InsertRandomSpeedTest {

    public static void MapComparisonPlot() {

        List<Integer> nValues = new ArrayList<>();
        int dense = 10;
        for (int n = 10; n <= 10_000_000; n += 10_000_000 / dense) {
            nValues.add(n);
        }
        int L = 10;

        List<Double> ullTimes = new ArrayList<>();
        List<Double> bstTimes = new ArrayList<>();
        List<Double> treeTimes = new ArrayList<>();
        List<Double> hashTimes = new ArrayList<>();

        // 3. Run the benchmarks
        for (int N : nValues) {
            ullTimes.add(timeRandomMap61B(new ULLMap<>(), N, L));
            bstTimes.add(timeRandomMap61B(new BSTMap<>(), N, L));
            treeTimes.add(timeRandomTreeMap(new TreeMap<>(), N, L));
            hashTimes.add(timeRandomHashMap(new HashMap<>(), N, L));
        }

        // 4. Create the Chart
        XYChart chart = new XYChartBuilder()
                .width(800).height(600)
                .title("Map Performance Comparison")
                .xAxisTitle("N (Number of Operations)")
                .yAxisTitle("Time (seconds)")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        // 6. Add all four series
        chart.addSeries("ULLMap", nValues, ullTimes);
        chart.addSeries("BSTMap", nValues, bstTimes);
        chart.addSeries("TreeMap", nValues, treeTimes);
        chart.addSeries("HashMap", nValues, hashTimes);

        // 7. Display
        new SwingWrapper(chart).displayChart();
    }

    /**
     Requests user input and performs tests of three different set
     implementations. ARGS is unused.
     */
    public static void main(String[] args) {
        MapComparisonPlot();
//        Scanner input = new Scanner(System.in);
//
//        System.out.println("This program inserts random "
//                + "Strings of length L "
//                + "into different types of maps "
//                + "as <String, Integer> pairs.");
//        System.out.print("Please enter desired length of each string: ");
//        int L = waitForPositiveInt(input);
//
//        String repeat;
//        do {
//            System.out.print("\nEnter # strings to insert into the maps: ");
//            int N = waitForPositiveInt(input);
//            timeRandomMap61B(new ULLMap<>(), N, L);
//            timeRandomMap61B(new BSTMap<>(), N, L);
//            timeRandomTreeMap(new TreeMap<>(), N, L);
//            timeRandomHashMap(new HashMap<>(), N, L);
//
//            System.out.print("Would you like to try more timed-tests? (y/n)");
//            repeat = input.nextLine();
//        } while (!repeat.equalsIgnoreCase("n") && !repeat.equalsIgnoreCase("no"));
//        input.close();
    }

    /** Returns time needed to put N random strings of length L into the
     * Map61B 61bMap. */
    public static double insertRandom(Map61B<String, Integer> map61B, int N, int L) {
        Stopwatch sw = new Stopwatch();
        String s;
        for (int i = 0; i < N; i++) {
            s = StringUtils.randomString(L);
            map61B.put(s, i);
        }
        return sw.elapsedTime();
    }

    /** Returns time needed to put N random strings of length L into the
     * given TreeMap. */
    public static double insertRandom(TreeMap<String, Integer> treeMap, int N, int L) {
        Stopwatch sw = new Stopwatch();
        String s;
        for (int i = 0; i < N; i++) {
            s = StringUtils.randomString(L);
            treeMap.put(s, i);
        }
        return sw.elapsedTime();
    }

    /** Returns time needed to put N random strings of length L into the
     * HashMap treeMap. */
    public static double insertRandom(HashMap<String, Integer> treeMap, int N, int L) {
        Stopwatch sw = new Stopwatch();
        String s;
        for (int i = 0; i < N; i++) {
            s = StringUtils.randomString(L);
            treeMap.put(s, i);
        }
        return sw.elapsedTime();
    }

    /**
     Attempts to insert N random strings of length L into map,
     Prints time of the N insert calls, otherwise
     Prints a nice message about the error
     */
    public static double timeRandomMap61B(Map61B<String, Integer> map, int N, int L) {
        try {
            double mapTime = insertRandom(map, N, L);
            System.out.printf(map.getClass() + ": %.2f sec\n", mapTime);
            return mapTime;
        } catch (StackOverflowError e) {
            printInfoOnStackOverflow(N, L);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     Attempts to insert N random strings of length L into a TreeMap
     Prints time of the N insert calls, otherwise
     Prints a nice message about the error
     */
    public static double timeRandomTreeMap(TreeMap<String, Integer> treeMap, int N, int L) {
        try {
            double javaTime = insertRandom(treeMap, N, L);
            System.out.printf("Java's Built-in TreeMap: %.2f sec\n", javaTime);
            return javaTime;
        } catch (StackOverflowError e) {
            printInfoOnStackOverflow(N, L);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     Attempts to insert N random strings of length L into a HashMap
     Prints time of the N insert calls, otherwise
     Prints a nice message about the error
     */
    public static double timeRandomHashMap(HashMap<String, Integer> hashMap, int N, int L) {
        try {
            double javaTime = insertRandom(hashMap, N, L);
            System.out.printf("Java's Built-in HashMap: %.2f sec\n", javaTime);
            return javaTime;
        } catch (StackOverflowError e) {
            printInfoOnStackOverflow(N, L);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     Waits for the user on other side of Scanner
     to enter a positive int,
     and outputs that int
     */
    public static int waitForPositiveInt(Scanner input) {
        int ret;
        do {
            while (!input.hasNextInt()) {
                errorBadIntegerInput();
                input.next();
            }
            ret = input.nextInt();
            input.nextLine(); //consume not taken by nextInt()
        } while (ret <= 0);
        return ret;
    }
    /* ------------------------------- Private methods ------------------------------- */
    /**
     To be called after catching a StackOverflowError
     Prints the error with corresponding N and L
     */
    private static void printInfoOnStackOverflow(int N, int L) {
        System.out.println("--Stack Overflow -- couldn't add " + N
                + " strings of length " + L + ".");
    }

    /** Prints a nice message for the user on bad input */
    private static void errorBadIntegerInput() {
        System.out.print("Please enter a positive integer: ");
    }

}
