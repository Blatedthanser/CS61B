public class Node<T> {
    public Node<T> prev;
    public T data;
    public Node<T> next;
    public Node() {
        data = null;
        prev = null;
        next = null;
    }
    public Node(T data_) {
        data = data_;
        prev = null;
        next = null;
    }
    public Node(T data_, Node<T> previous_, Node<T> next_) {
        data = data_;
        prev = previous_;
        next = next_;
    }
}
