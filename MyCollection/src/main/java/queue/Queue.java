package queue;

public interface Queue<T> {

    void put(T element);

    T peek();

    T pull();

    T firstElement();

    T lastElement();

}
