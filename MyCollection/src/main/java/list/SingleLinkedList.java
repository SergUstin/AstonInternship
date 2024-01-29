package list;

import java.util.Objects;

/**
 * Односвязный список, который реализует интерфейс List.
 * Позволяет хранить и управлять элементами, представленными типом T.
 */
public class SingleLinkedList<T> implements List<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> next;
    }

    /**
     * Возвращает текущий размер списка.
     *
     * @return текущий размер списка
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Добавляет указанный элемент в конец списка.
     *
     * @param element добавляемый элемент
     */
    @Override
    public void add(T element) {
        add(size, element);
    }

    /**
     * Добавляет указанный элемент по указанному индексу.
     *
     * @param index индекс, по которому будет добавлен элемент
     * @param element добавляемый элемент
     * @throws IllegalArgumentException если указанный индекс некорректен
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("не корректный индекс");
        }
        Node<T> node = new Node<>();
        node.element = element;

        if (index == size) {
            if (size == 0) {
                first = node;
            } else {
                last.next = node;
            }
            last = node;
            size++;
            return;
        }

        if (index == 0) {
            node.next = first;
            first = node;
            size++;
            return;
        }

        Node<T> previous = first;
        for (int i = 1; i < index; i++) {
            previous = previous.next;
        }

        node.next = previous.next;
        previous.next = node;
        size++;
    }

    /**
     * Удаляет элемент из списка по индексу. Если индекс выходит за допустимые границы,
     * будет сгенерировано исключение IllegalArgumentException.
     *
     * @param index индекс удаляемого элемента
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("не корректный индекс");
        }

        if (size == 1) {
            first = null;
            last = null;
            size--;
            return;
        }

        if (size == 2) {
            if (index == 0) {
                first = last;
            } else {
                last = first;
                first.next = null;
            }
            size--;
            return;
        }

        if (index == 0) {
            first = first.next;
            size--;
            return;
        }

        Node<T> previous = first;

        for (int i = 1; i < index ; i++) {
            previous = previous.next;
        }

        previous.next = previous.next.next;

        if (index == size - 1) {
            last = previous;
        }
        size--;
    }

    /**
     * Удаляет первое вхождение указанного элемента из списка.
     *
     * @param element удаляемый элемент
     * @return true, если элемент был найден и удален, иначе - false
     */
    @Override
    public boolean remove(T element) {
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            return removeWhenOne(element);
        }
        if (size == 2) {
            return removeWhenTwo(element);
        }
        return findAndRemove(element);
    }

    private boolean removeWhenOne(T elment) {
        if (Objects.equals(first.element, elment)) {
            first = null;
            last = null;
            size--;
            return true;
        } else {
            return false;
        }
    }

    private boolean removeWhenTwo(T elment) {
        if (Objects.equals(first.element, elment)) {
            first = last;
            size--;
            return true;
        } else if (Objects.equals(last.element, elment)) {
            last = first;
            first.next = null;
            size--;
            return true;
        } else {
            return false;
        }
    }

    private boolean findAndRemove(T elment) {
        if (Objects.equals(first.element, elment)) {
            first = first.next;
            size--;
            return true;
        }

        Node<T> previous = first;

        for (int i = 1; i < size; i++) {
            if (Objects.equals(previous.next.element, elment)) {
                previous.next = previous.next.next;
                if (i == size - 1) {
                    last = previous;
                }
                size--;
                return true;
            }
            previous = previous.next;
        }
        return false;
    }

    /**
     * Возвращает элемент из списка по указанному индексу.
     *
     * @param index индекс элемента в списке
     * @return элемент, находящийся по указанному индексу
     * @throws IllegalArgumentException если индекс выходит за границы списка
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("не корректный индекс");
        }
        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.element;
    }

    /**
     * Заменяет элемент в списке по указанному индексу новым элементом.
     *
     * @param index индекс заменяемого элемента в списке
     * @param element новый элемент, который будет помещен по указанному индексу
     * @throws IllegalArgumentException если индекс выходит за границы списка
     */
    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("не корректный индекс");
        }
        Node<T> firstNode = first;
        for (int i = 0; i < index; i++) {
            firstNode = firstNode.next;
        }
        firstNode.element = element;
    }

    /**
     * Возвращает индекс первого вхождения указанного элемента в этот список или -1, если этот список не содержит элемент.
     *
     * @param element элемент для поиска в этом списке
     * @return индекс первого вхождения указанного элемента в этот список или -1, если этот список не содержит элемент
     */
    @Override
    public int indexOf(T element) {
        Node<T> firstNode = first;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(firstNode.element, element)) {
                return i;
            }
            firstNode = firstNode.next;
        }
        return -1;
    }

    /**
     * Удаляет все элементы из этого списка. Список будет пуст после вызова этого метода.
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Возвращает true, если этот список содержит указанный элемент.
     *
     * @param element элемент, наличие которого в этом списке требуется определить
     * @return true, если этот список содержит указанный элемент
     */
    @Override
    public boolean contains(T element) {
        return indexOf(element) >= 0;
    }
}
