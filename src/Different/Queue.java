package Different;

public interface Queue<E> {
    void enqueue(E value);
    E dequeue() throws EmptyQueueException;
    void clear();
    int size();
    boolean isEmpty();
}
