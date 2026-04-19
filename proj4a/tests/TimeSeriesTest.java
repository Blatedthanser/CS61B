import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    private void assertTimeSeries(TimeSeries ts, List<Integer> expectedYears, List<Double> expectedData) {
        assertThat(ts.years()).isEqualTo(expectedYears);
        for (int i = 0; i < expectedData.size(); i += 1) {
            assertThat(ts.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
    }

    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testPlus() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 100.0);
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertTimeSeries(totalPopulation, List.of(1991, 1992, 1994, 1995),
                List.of(100.0, 100.0, 600.0, 500.0));
    }

    @Test
    public void testPutAndYearsAndData() {
        TimeSeries ts = new TimeSeries();
        ts.put(1991, 100.0);
        ts.put(1992, 200.0);
        ts.put(1993, 300.0);
        assertTimeSeries(ts, List.of(1991, 1992, 1993), List.of(100.0, 200.0, 300.0));
    }

    @Test
    public void testSlice() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        TimeSeries sliceCatPopulation = new TimeSeries(catPopulation, 1992, 1994);
        // expected:
        // 1992, 100.0
        // 1994, 200.0
        assertTimeSeries(sliceCatPopulation, List.of(1992, 1994), List.of(100.0, 200.0));
    }

    @Test
    public void testDividedByEqualSize() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 400.0);
        dogPopulation.put(1992, 500.0);
        dogPopulation.put(1994, 300.0);

        assertTimeSeries(catPopulation.dividedBy(dogPopulation), List.of(1991, 1992, 1994),
                List.of(0.0, 0.2, 2.0/3));
    }

    @Test
    public void testDividedByMissingThis() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 400.0);
        dogPopulation.put(1992, 500.0);
        dogPopulation.put(1994, 300.0);

        assertTimeSeries(catPopulation.dividedBy(dogPopulation), List.of(1991, 1992),
                List.of(0.0, 0.2));
    }

    @Test
    public void testDividedByMissingTS() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 400.0);
        dogPopulation.put(1992, 500.0);

        assertThrows(IllegalArgumentException.class, () -> {
            catPopulation.dividedBy(dogPopulation);
        });
    }
} 