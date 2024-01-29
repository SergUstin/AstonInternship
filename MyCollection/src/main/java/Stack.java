/**
 * Простая реализация стека (очереди с LIFO-подходом) с методами {@code size}, {@code push}, {@code pop}, {@code peek}.
 * {@code size} - возвращает текущий размер стека.
 * {@code push} - добавляет элемент на вершину стека.
 * {@code pop} - возвращает и удаляет элемент с вершины стека.
 * {@code peek} - возвращает элемент с вершины стека без удаления.
 *
 * @param <T> - тип элементов в стеке.
 */
public class Stack<T> {

    private Node<T> last;

    private int size;

    private static class Node<T> {

        private T element;

        private Node<T> next;
    }

    /**
     * Возвращает текущий размер стека.
     *
     * @return - текущий размер стека.
     */
    public int size() {
        return size;
    }

    /**
     * Добавляет элемент на вершину стека.
     *
     * @param element - добавляемый элемент.
     * @throws IllegalArgumentException если элемент null.
     */
    public void push(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element can not be Null");
        }

        Node<T> node = new Node<>();
        node.element = element;
        node.next = last;
        last = node;
        size++;
    }

    /**
     * Возвращает и удаляет элемент с вершины стека.
     *
     * @return - элемент с вершины стека. Null если стек пуст.
     */
    public T pop() {
        if (size == 0) {
            return null;
        }
        T element = last.element;
        last = last.next;
        size--;
        return element;
    }

    /**
     * Возвращает элемент с вершины стека без удаления.
     *
     * @return - элемент с вершины стека. Null если стек пуст.
     */
    public T peek() {
        if (size == 0) {
            return null;
        }
        return last.element;
    }
}
