public class Node<T> {
    public Node<T> previous;
    public T data;
    public Node<T> next;
    public Node() {
        data = null;
        previous = null;
        next = null;
    }
    public Node(T data_, Node<T> previous_, Node<T> next_) {
        data = data_;
        previous = previous_;
        next = next_;
    }
}
