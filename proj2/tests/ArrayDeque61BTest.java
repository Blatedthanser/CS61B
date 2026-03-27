import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
    /** Test for addFirst() and addLast() using toList() */
    @Test
    public void addFirstAndAddLastTest() {
        ArrayDeque61B<Integer> aDeque = new ArrayDeque61B<>();
        assertThat(aDeque.toList()).isEmpty();
        aDeque.addLast(0);
        assertThat(aDeque.toList()).containsExactly(0).inOrder();
        aDeque.addFirst(-1);
        assertThat(aDeque.toList()).containsExactly(-1, 0).inOrder();
        aDeque.addLast(1);
        assertThat(aDeque.toList()).containsExactly(-1, 0, 1).inOrder();
        aDeque.addFirst(-2);
        assertThat(aDeque.toList()).containsExactly(-2, -1, 0, 1).inOrder();
        aDeque.addLast(2);
        assertThat(aDeque.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
        // Below are adds that exceed space
        for (int i = 3; i < 20; i++) {
            aDeque.addLast(i);
        }
        assertThat(aDeque.getLast()).isEqualTo(19);
    }

    /** Test for getFirst() and getLast() */
    @Test
    public void getFirstAndGetLastTest() {
        ArrayDeque61B<Integer> aDeque = new ArrayDeque61B<>();
        aDeque.addLast(0);
        aDeque.addFirst(-1);
        assertThat(aDeque.getFirst()).isEqualTo(-1);
        assertThat(aDeque.getLast()).isEqualTo(0);
        aDeque.addLast(1);
        assertThat(aDeque.getLast()).isEqualTo(1);
        aDeque.addFirst(-2);
        assertThat(aDeque.getFirst()).isEqualTo(-2);
        aDeque.addLast(2);
        assertThat(aDeque.getLast()).isEqualTo(2);
    }

    /** Test for get() */
    @Test
    public void getTest() {
        ArrayDeque61B<Integer> aDeque = new ArrayDeque61B<>();
        aDeque.addLast(0);
        aDeque.addFirst(-1);
        aDeque.addLast(1);
        aDeque.addFirst(-2);
        aDeque.addLast(2);
        // aDeque: [-2, -1, 0, 1, 2]
        for (int i = 0; i < aDeque.size(); i++) {
            assertThat(aDeque.get(i)).isEqualTo(i - 2);
        }
    }

    /** Test for isEmpty() and size() */
    @Test
    public void isEmptyAndSizeTest() {
        ArrayDeque61B<Integer> aDeque = new ArrayDeque61B<>();
        assertThat(aDeque.isEmpty()).isTrue();
        assertThat(aDeque.size()).isEqualTo(0);
        aDeque.addLast(0);
        assertThat(aDeque.isEmpty()).isFalse();
        assertThat(aDeque.size()).isEqualTo(1);
        aDeque.addFirst(-1);
        aDeque.addLast(1);
        assertThat(aDeque.size()).isEqualTo(3);
        aDeque.addFirst(-2);
        aDeque.addLast(2);
        assertThat(aDeque.isEmpty()).isFalse();
        assertThat(aDeque.size()).isEqualTo(5);
    }

    /** Test for toList() */
    @Test
    public void toListTest() {
        ArrayDeque61B<Integer> aDeque = new ArrayDeque61B<>();
        assertThat(aDeque.toList()).isEmpty();
        aDeque.addLast(0);
        assertThat(aDeque.toList()).containsExactly(0).inOrder();
        aDeque.addFirst(-1);
        assertThat(aDeque.toList()).containsExactly(-1, 0).inOrder();
        aDeque.addLast(1);
        assertThat(aDeque.toList()).containsExactly(-1, 0, 1).inOrder();
        aDeque.addFirst(-2);
        assertThat(aDeque.toList()).containsExactly(-2, -1, 0, 1).inOrder();
        aDeque.addLast(2);
        assertThat(aDeque.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    /** Test for removeFirst() and removeLast() */
    @Test
    public void removeFirstAndRemoveLastTest() {
        ArrayDeque61B<Integer> aDeque = new ArrayDeque61B<>();
        assertThat(aDeque.removeFirst()).isNull();
        aDeque.addLast(0);
        aDeque.addFirst(-1);
        aDeque.addLast(1);
        aDeque.addFirst(-2);
        aDeque.addLast(2);
        assertThat(aDeque.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
        assertThat(aDeque.removeLast()).isEqualTo(2);
        assertThat(aDeque.toList()).containsExactly(-2, -1, 0, 1).inOrder();
        assertThat(aDeque.removeFirst()).isEqualTo(-2);
        assertThat(aDeque.toList()).containsExactly(-1, 0, 1).inOrder();
        assertThat(aDeque.removeFirst()).isEqualTo(-1);
        assertThat(aDeque.toList()).containsExactly(0, 1).inOrder();

        // Below are tests for resizeDown
        for (int i = 2; i < 99; i++) {
            aDeque.addLast(i);
        }
        aDeque.addLast(99);
        for (int i = 0; i < 74; i++) {
            aDeque.removeLast();
        }
        aDeque.removeLast();
        assertThat(aDeque.size()).isEqualTo(25);
        assertThat(aDeque.getLast()).isEqualTo(24);
        assertThat(aDeque.getFirst()).isEqualTo(0);
    }
}
