package tads;

public class List<T> {
    private Node<T> head;
    private int size;

    public List() {
        this.head = null;
        this.size = 0;
    }

    public void add(T el) {
        if (head == null) {
            head = new Node<>(el);
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(el);
        }
        size++;
    }

    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Posición fuera de rango: " + pos);
        }
        Node<T> current = head;
        for (int i = 0; i < pos; i++) {
            current = current.next;
        }
        return current.data;
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
