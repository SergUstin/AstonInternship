package queue;

import java.util.Objects;

/**
 * Простая реализация очереди с методами {@code put}, {@code peek}, {@code pull}, {@code firstElement}, {@code lastElement}.
 * {@code put} - добавляет элемент в конец очереди.
 * {@code peek} - возвращает первый элемент очереди без удаления.
 * {@code pull} - возвращает и удаляет первый элемент очереди.
 * {@code firstElement} - возвращает первый элемент очереди без удаления.
 * {@code lastElement} - возвращает последний элемент очереди без удаления.
 *
 * @param <T> - тип элементов в очереди.
 */
public class SimpleQueue<T> implements Queue<T> {
    private Node<T> first; // Первый элемент очереди.
    private Node<T> last; // Последний элемент очереди.
    private int size; // Размер очереди.

    /**
     * Узел, представляющий элемент очереди.
     */
    private static class Node<T> {
        private T element;
        private Node<T> next;
    }

    /**
     * Возвращает размер очереди.
     *
     * @return размер очереди.
     */
    public int size() {
        return size;
    }

    /**
     * Добавляет элемент в конец очереди.
     *
     * @param element - добавляемый элемент.
     */
    @Override
    public void put(T element) {
        if (Objects.isNull(element)) {
            throw new IllegalArgumentException("Args not been null");
        }

        Node<T> node = new Node<>();
        node.element = element;
        if (size == 0) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size++;
    }

    /**
     * Возвращает первый элемент очереди без удаления.
     *
     * @return первый элемент очереди или {@code null}, если очередь пуста.
     */
    @Override
    public T peek() {
        if (size == 0) {
            return null;
        }
        return first.element;
    }

    /**
     * Возвращает и удаляет первый элемент очереди.
     *
     * @return первый элемент очереди или {@code null}, если очередь пуста.
     */
    @Override
    public T pull() {
        if (size == 0) {
            return null;
        }
        T element = first.element;
        first = first.next;
        size--;
        return element;
    }

    /**
     * Возвращает первый элемент очереди без удаления.
     *
     * @return первый элемент очереди или {@code null}, если очередь пуста.
     */
    @Override
    public T firstElement() {
        if (size == 0) {
            return null;
        }
        return first.element;
    }

    /**
     * Возвращает последний элемент очереди без удаления.
     *
     * @return последний элемент очереди или {@code null}, если очередь пуста.
     */
    @Override
    public T lastElement() {
        if (size == 0) {
            return null;
        }
        return last.element;
    }
}
