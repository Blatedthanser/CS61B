import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BEnhancementTest {
    /** Test for iteration of ArrayDeque61B */
    @Test
    public void iterationTest() {
        ArrayDeque61B<Integer> aDeque = new ArrayDeque61B<>();
        for (int i = 0; i < 100; i++) {
            aDeque.addLast(i);
        }
        int j = 0;
        for (int i : aDeque) {
            assertThat(i).isEqualTo(j);
            j++;
        }
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front"); // after this call we expect: ["front"]
        ad.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad).containsExactly("front", "middle", "back");
    }

    /** Test for equals() */
    @Test
    public void equalsTest() {
        ArrayDeque61B<Integer> aDeque = new ArrayDeque61B<>();
        for (int i = 0; i < 100; i++) {
            aDeque.addLast(i);
        }
        ArrayDeque61B<Integer> uddaADeque = new ArrayDeque61B<>();
        for (int i = 0; i < 100; i++) {
            uddaADeque.addLast(i);
        }
        ArrayDeque61B<Integer> udddaADeque = new ArrayDeque61B<>();
        assertThat(aDeque.equals(uddaADeque)).isTrue();
        assertThat(aDeque.equals(udddaADeque)).isFalse();
    }

    /** Test for toString() */
    @Test
    public void toStringTest() {
        ArrayDeque61B<Integer> aDeque = new ArrayDeque61B<>();
        for (int i = 0; i < 5; i++) {
            aDeque.addLast(i);
        }
        assertThat(aDeque.toString()).isEqualTo("[0, 1, 2, 3, 4]");
    }
}
